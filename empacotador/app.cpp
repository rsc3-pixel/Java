// ============================================================================
//  Curso de Java - visualizador autonomo para Windows
//
//  Abre uma janela nativa e exibe o curso inteiro, que esta embutido no proprio
//  executavel. Nao instala nada, nao acessa a internet, nao escreve no registro.
//
//  Como funciona:
//    1. O curso (HTML) e a WebView2Loader.dll ficam embutidos como recursos.
//    2. No inicio, a DLL e extraida para a pasta temporaria do usuario.
//    3. Uma janela Win32 hospeda um controle WebView2 (o motor do Edge, que ja
//       vem instalado no Windows 10/11).
//    4. O HTML e entregue ao WebView2 por um manipulador de recurso virtual,
//       entao nenhum arquivo de conteudo toca o disco.
//
//  Compilar:
//    windres recurso.rc -o recurso.o
//    g++ -std=c++17 -O2 -municode -mwindows app.cpp recurso.o -o "Curso de Java.exe" ...
// ============================================================================

// INITGUID faz os simbolos IID_* serem definidos neste arquivo, em vez de
// apenas declarados. Sem isso o link falha, porque o MinGW nao tem a biblioteca
// de GUIDs do WebView2 que o MSVC usa.
#define INITGUID

#include <windows.h>
#include <shlwapi.h>
#include <algorithm>
#include <string>
#include <utility>

#include "WebView2.h"

// Identificadores dos recursos embutidos (ver recurso.rc)
#define ID_HTML   101
#define ID_DLL    102
#define ID_ICONE  103

// ---------------------------------------------------------------------------
//  Miniatura de COM
//
//  O MinGW nao inclui a WRL da Microsoft (nem ComPtr, nem Callback), entao
//  implementamos o minimo necessario: um ponteiro inteligente e um adaptador
//  que transforma uma lambda em um objeto COM.
// ---------------------------------------------------------------------------

// Ponteiro inteligente para interfaces COM: cuida de AddRef e Release.
template <typename T>
class Ptr {
public:
    Ptr() = default;
    ~Ptr() { if (p_) p_->Release(); }

    Ptr(const Ptr &o) : p_(o.p_) { if (p_) p_->AddRef(); }
    Ptr &operator=(const Ptr &o) {
        if (this != &o) {
            if (o.p_) o.p_->AddRef();
            if (p_) p_->Release();
            p_ = o.p_;
        }
        return *this;
    }

    T *operator->() const { return p_; }
    T **operator&()       { return &p_; }   // para as chamadas que preenchem a saida
    T *get() const        { return p_; }
    explicit operator bool() const { return p_ != nullptr; }

    void anexar(T *bruto) { if (p_) p_->Release(); p_ = bruto; }

    // Consulta outra interface do mesmo objeto.
    template <typename U>
    bool como(Ptr<U> *destino) const {
        if (!p_) return false;
        U *saida = nullptr;
        if (FAILED(p_->QueryInterface(IID_PPV_ARGS(&saida)))) return false;
        destino->anexar(saida);
        return true;
    }

private:
    T *p_ = nullptr;
};

// Envolve uma lambda num objeto COM que implementa a interface de callback I.
//
// Todos os callbacks do WebView2 tem a forma Invoke(A1, A2). Os tipos exatos
// de A1 e A2 sao passados explicitamente porque Invoke e virtual pura: um
// template de metodo nao sobrescreveria a assinatura, e a classe continuaria
// abstrata.
template <typename I, typename A1, typename A2, typename F>
class Manipulador : public I {
public:
    // O IID da interface e passado de fora: o __uuidof do MinGW exigiria uma
    // declaracao de GUID que o header do WebView2 so fornece ao MSVC.
    Manipulador(const IID &iid, F f) : iid_(iid), f_(std::move(f)) {}

    // --- IUnknown ---
    HRESULT STDMETHODCALLTYPE QueryInterface(REFIID iid, void **saida) override {
        if (!saida) return E_POINTER;
        if (iid == IID_IUnknown || iid == iid_) {
            *saida = static_cast<I *>(this);
            AddRef();
            return S_OK;
        }
        *saida = nullptr;
        return E_NOINTERFACE;
    }
    ULONG STDMETHODCALLTYPE AddRef() override {
        return (ULONG)InterlockedIncrement(&ref_);
    }
    ULONG STDMETHODCALLTYPE Release() override {
        LONG n = InterlockedDecrement(&ref_);
        if (n == 0) delete this;
        return (ULONG)n;
    }

    // --- o callback em si ---
    HRESULT STDMETHODCALLTYPE Invoke(A1 a1, A2 a2) override { return f_(a1, a2); }

private:
    const IID &iid_;
    F          f_;
    LONG       ref_ = 1;
};

// Cria o manipulador com uma referencia inicial. Quem recebe faz o proprio
// AddRef, entao o chamador deve soltar a dele com Release().
template <typename I, typename A1, typename A2, typename F>
Manipulador<I, A1, A2, F> *criarManipulador(const IID &iid, F f) {
    return new Manipulador<I, A1, A2, F>(iid, std::move(f));
}

// Assinatura da unica funcao que precisamos da WebView2Loader.dll.
// Carregamos por LoadLibrary para nao depender da .lib do MSVC, que o MinGW
// nao consegue linkar sem colidir os simbolos de IID.
typedef HRESULT(STDAPICALLTYPE *PFN_CriarAmbiente)(
    PCWSTR, PCWSTR, ICoreWebView2EnvironmentOptions *,
    ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandler *);

namespace {

Ptr<ICoreWebView2Controller> g_controlador;
Ptr<ICoreWebView2>           g_webview;
Ptr<ICoreWebView2Environment> g_ambiente;
HWND                         g_janela = nullptr;
std::wstring                 g_pastaDados;

// ---------------------------------------------------------------------------
// Recursos embutidos
// ---------------------------------------------------------------------------

// Devolve ponteiro e tamanho de um recurso RCDATA embutido no executavel.
bool lerRecurso(int id, const void **dados, DWORD *tamanho) {
    HRSRC achado = FindResourceW(nullptr, MAKEINTRESOURCEW(id), RT_RCDATA);
    if (!achado) return false;
    HGLOBAL bloco = LoadResource(nullptr, achado);
    if (!bloco) return false;
    *dados   = LockResource(bloco);
    *tamanho = SizeofResource(nullptr, achado);
    return *dados != nullptr && *tamanho > 0;
}

std::wstring pastaTemporaria() {
    wchar_t buf[MAX_PATH];
    DWORD n = GetTempPathW(MAX_PATH, buf);
    if (n == 0 || n > MAX_PATH) return L".";
    std::wstring p(buf, n);
    p += L"CursoJava";
    CreateDirectoryW(p.c_str(), nullptr);
    return p;
}

// Grava a WebView2Loader.dll embutida em disco e a carrega.
// Se um arquivo valido ja existir (execucao anterior), reaproveita.
HMODULE carregarLoader() {
    const void *dados = nullptr;
    DWORD tamanho = 0;
    if (!lerRecurso(ID_DLL, &dados, &tamanho)) return nullptr;

    std::wstring destino = g_pastaDados + L"\\WebView2Loader.dll";

    bool precisaGravar = true;
    HANDLE h = CreateFileW(destino.c_str(), GENERIC_READ, FILE_SHARE_READ, nullptr,
                           OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, nullptr);
    if (h != INVALID_HANDLE_VALUE) {
        LARGE_INTEGER tam{};
        if (GetFileSizeEx(h, &tam) && tam.QuadPart == (LONGLONG)tamanho)
            precisaGravar = false;   // ja esta la, do tamanho certo
        CloseHandle(h);
    }

    if (precisaGravar) {
        HANDLE saida = CreateFileW(destino.c_str(), GENERIC_WRITE, 0, nullptr,
                                   CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, nullptr);
        if (saida == INVALID_HANDLE_VALUE) return nullptr;
        DWORD escrito = 0;
        BOOL ok = WriteFile(saida, dados, tamanho, &escrito, nullptr);
        CloseHandle(saida);
        if (!ok || escrito != tamanho) return nullptr;
    }

    return LoadLibraryW(destino.c_str());
}

// ---------------------------------------------------------------------------
// Entrega do HTML sem tocar o disco
// ---------------------------------------------------------------------------

// Serve o curso a partir da memoria, respondendo a um endereco virtual.
// Assim o conteudo nunca vira arquivo, e a pagina roda numa origem estavel
// (necessario para que o localStorage guarde a preferencia de tema).
void registrarManipulador() {
    g_webview->AddWebResourceRequestedFilter(L"*", COREWEBVIEW2_WEB_RESOURCE_CONTEXT_ALL);

    auto *h = criarManipulador<ICoreWebView2WebResourceRequestedEventHandler,
                               ICoreWebView2 *,
                               ICoreWebView2WebResourceRequestedEventArgs *>(
        IID_ICoreWebView2WebResourceRequestedEventHandler,
        [](ICoreWebView2 *, ICoreWebView2WebResourceRequestedEventArgs *args) -> HRESULT {
            Ptr<ICoreWebView2WebResourceRequest> req;
            if (FAILED(args->get_Request(&req))) return S_OK;

            LPWSTR bruto = nullptr;
            if (FAILED(req->get_Uri(&bruto)) || !bruto) return S_OK;
            std::wstring uri(bruto);
            CoTaskMemFree(bruto);

            // So respondemos ao nosso endereco virtual.
            if (uri.compare(0, 20, L"https://curso.local/") != 0) return S_OK;

            const void *dados = nullptr;
            DWORD tamanho = 0;
            if (!lerRecurso(ID_HTML, &dados, &tamanho)) return S_OK;

            Ptr<IStream> fluxo;
            fluxo.anexar(SHCreateMemStream((const BYTE *)dados, tamanho));
            if (!fluxo || !g_ambiente) return S_OK;

            Ptr<ICoreWebView2WebResourceResponse> resposta;
            g_ambiente->CreateWebResourceResponse(
                fluxo.get(), 200, L"OK",
                L"Content-Type: text/html; charset=utf-8\r\n"
                L"Cache-Control: no-cache\r\n",
                &resposta);
            if (resposta) args->put_Response(resposta.get());
            return S_OK;
        });

    EventRegistrationToken t;
    g_webview->add_WebResourceRequested(h, &t);
    h->Release();   // o WebView2 ficou com a sua propria referencia
}

// ---------------------------------------------------------------------------
// Janela
// ---------------------------------------------------------------------------

LRESULT CALLBACK Proc(HWND h, UINT msg, WPARAM wp, LPARAM lp) {
    switch (msg) {
    case WM_SIZE:
        if (g_controlador) {
            RECT r;
            GetClientRect(h, &r);
            g_controlador->put_Bounds(r);
        }
        return 0;

    case WM_DESTROY:
        PostQuitMessage(0);
        return 0;

    // Ao arrastar a janela para um monitor com escala diferente, o Windows
    // sugere o novo retangulo; adota-lo evita a janela ficar desproporcional.
    case WM_DPICHANGED: {
        RECT *sugerido = (RECT *)lp;
        SetWindowPos(h, nullptr, sugerido->left, sugerido->top,
                     sugerido->right - sugerido->left,
                     sugerido->bottom - sugerido->top,
                     SWP_NOZORDER | SWP_NOACTIVATE);
        return 0;
    }

    // Sem isso a janela pisca em branco ao ser redimensionada.
    case WM_ERASEBKGND:
        return 1;
    }
    return DefWindowProcW(h, msg, wp, lp);
}

void erroFatal(const wchar_t *texto) {
    MessageBoxW(nullptr, texto, L"Curso de Java", MB_ICONERROR | MB_OK);
}

}  // namespace

// ---------------------------------------------------------------------------
// Ponto de entrada
// ---------------------------------------------------------------------------

int WINAPI wWinMain(HINSTANCE inst, HINSTANCE, PWSTR, int mostrar) {
    // Uma so instancia: se o curso ja estiver aberto, traz a janela para frente.
    HANDLE mutex = CreateMutexW(nullptr, TRUE, L"CursoJavaUnicaInstancia");
    if (mutex && GetLastError() == ERROR_ALREADY_EXISTS) {
        HWND ja = FindWindowW(L"CursoJavaJanela", nullptr);
        if (ja) {
            if (IsIconic(ja)) ShowWindow(ja, SW_RESTORE);
            SetForegroundWindow(ja);
        }
        return 0;
    }

    // O reconhecimento de DPI ja vem declarado no manifesto (permonitorv2),
    // que e o caminho recomendado; chamar SetProcessDPIAware aqui seria
    // redundante e poderia rebaixar o modo para o legado.
    if (FAILED(CoInitializeEx(nullptr, COINIT_APARTMENTTHREADED))) {
        erroFatal(L"Falha ao iniciar o componente COM do Windows.");
        return 1;
    }

    g_pastaDados = pastaTemporaria();

    HMODULE loader = carregarLoader();
    if (!loader) {
        erroFatal(L"Nao foi possivel preparar os componentes de exibicao.\n\n"
                  L"Verifique se ha espaco livre em disco e tente novamente.");
        return 1;
    }

    auto criarAmbiente =
        (PFN_CriarAmbiente)GetProcAddress(loader, "CreateCoreWebView2EnvironmentWithOptions");
    if (!criarAmbiente) {
        erroFatal(L"Componente de exibicao incompativel.");
        return 1;
    }

    WNDCLASSEXW wc = {};
    wc.cbSize        = sizeof(wc);
    wc.lpfnWndProc   = Proc;
    wc.hInstance     = inst;
    wc.lpszClassName = L"CursoJavaJanela";
    wc.hCursor       = LoadCursor(nullptr, IDC_ARROW);
    wc.hbrBackground = CreateSolidBrush(RGB(13, 17, 23));
    wc.hIcon         = (HICON)LoadImageW(inst, MAKEINTRESOURCEW(ID_ICONE), IMAGE_ICON,
                                         0, 0, LR_DEFAULTSIZE);
    wc.hIconSm       = wc.hIcon;
    RegisterClassExW(&wc);

    // Dimensiona a janela a partir da area util do monitor (fora a barra de
    // tarefas). O tamanho ideal e escalado pelo DPI, senao a janela sai
    // pequena demais em telas com escala acima de 100%.
    RECT areaUtil{};
    if (!SystemParametersInfoW(SPI_GETWORKAREA, 0, &areaUtil, 0)) {
        areaUtil = {0, 0, GetSystemMetrics(SM_CXSCREEN), GetSystemMetrics(SM_CYSCREEN)};
    }
    int larguraUtil = areaUtil.right - areaUtil.left;
    int alturaUtil  = areaUtil.bottom - areaUtil.top;

    UINT dpi = 96;
    if (HDC dc = GetDC(nullptr)) {
        dpi = (UINT)GetDeviceCaps(dc, LOGPIXELSX);
        ReleaseDC(nullptr, dc);
    }
    double escala = dpi / 96.0;

    int largura = (std::min)((int)(1380 * escala), (int)(larguraUtil * 0.94));
    int altura  = (std::min)((int)(900 * escala),  (int)(alturaUtil * 0.94));

    g_janela = CreateWindowExW(
        0, L"CursoJavaJanela", L"Curso de Java  -  dos fundamentos ao Spring Boot",
        WS_OVERLAPPEDWINDOW,
        areaUtil.left + (larguraUtil - largura) / 2,
        areaUtil.top + (alturaUtil - altura) / 2,
        largura, altura,
        nullptr, nullptr, inst, nullptr);
    if (!g_janela) {
        erroFatal(L"Nao foi possivel criar a janela.");
        return 1;
    }

    ShowWindow(g_janela, mostrar);
    UpdateWindow(g_janela);

    // Cria o ambiente WebView2; o restante da montagem acontece nos callbacks.
    auto *aoTerAmbiente =
        criarManipulador<ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandler,
                         HRESULT, ICoreWebView2Environment *>(
            IID_ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandler,
            [](HRESULT r, ICoreWebView2Environment *ambiente) -> HRESULT {
                if (FAILED(r) || !ambiente) {
                    erroFatal(L"Nao foi possivel iniciar o motor de exibicao.\n\n"
                              L"Este programa precisa do WebView2, que acompanha o\n"
                              L"Microsoft Edge. Atualize o Edge e tente novamente.");
                    PostQuitMessage(1);
                    return S_OK;
                }

                ambiente->AddRef();
                g_ambiente.anexar(ambiente);

                auto *aoTerControlador =
                    criarManipulador<ICoreWebView2CreateCoreWebView2ControllerCompletedHandler,
                                     HRESULT, ICoreWebView2Controller *>(
                        IID_ICoreWebView2CreateCoreWebView2ControllerCompletedHandler,
                        [](HRESULT r2, ICoreWebView2Controller *ctrl) -> HRESULT {
                            if (FAILED(r2) || !ctrl) {
                                erroFatal(L"Falha ao montar a area de exibicao.");
                                PostQuitMessage(1);
                                return S_OK;
                            }

                            ctrl->AddRef();
                            g_controlador.anexar(ctrl);
                            ctrl->get_CoreWebView2(&g_webview);

                            // Aparencia de aplicativo, nao de navegador.
                            Ptr<ICoreWebView2Settings> cfg;
                            if (SUCCEEDED(g_webview->get_Settings(&cfg))) {
                                cfg->put_AreDefaultContextMenusEnabled(FALSE);
                                cfg->put_IsStatusBarEnabled(FALSE);
                                cfg->put_AreDevToolsEnabled(FALSE);
                                cfg->put_IsZoomControlEnabled(TRUE);
                            }

                            registrarManipulador();

                            RECT rc;
                            GetClientRect(g_janela, &rc);
                            ctrl->put_Bounds(rc);

                            g_webview->Navigate(L"https://curso.local/index.html");
                            return S_OK;
                        });

                ambiente->CreateCoreWebView2Controller(g_janela, aoTerControlador);
                aoTerControlador->Release();
                return S_OK;
            });

    HRESULT hr = criarAmbiente(nullptr, g_pastaDados.c_str(), nullptr, aoTerAmbiente);
    aoTerAmbiente->Release();

    if (FAILED(hr)) {
        erroFatal(L"Este programa precisa do WebView2, que normalmente acompanha\n"
                  L"o Microsoft Edge no Windows 10 e 11.\n\n"
                  L"Atualize o Microsoft Edge e tente novamente.");
        return 1;
    }

    MSG msg;
    while (GetMessageW(&msg, nullptr, 0, 0)) {
        TranslateMessage(&msg);
        DispatchMessageW(&msg);
    }

    if (mutex) CloseHandle(mutex);
    CoUninitialize();
    return (int)msg.wParam;
}
