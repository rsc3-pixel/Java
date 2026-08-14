// ============================================================================
//  Instalador do Curso de Java
//
//  Um assistente de instalacao classico do Windows, escrito em Win32 puro.
//  Carrega o "Curso de Java.exe" embutido como recurso e o instala por usuario
//  (sem exigir privilegio de administrador).
//
//  O que ele faz:
//    - copia o programa para %LOCALAPPDATA%\Programs\Curso de Java
//    - cria atalhos na Area de Trabalho e no Menu Iniciar (opcionais)
//    - registra a desinstalacao em "Adicionar ou remover programas"
//    - grava um desinstalador que reverte tudo isso
//
//  O mesmo binario funciona como desinstalador quando chamado com /desinstalar.
// ============================================================================

#define INITGUID
#define _WIN32_IE 0x0600

#include <windows.h>
#include <commctrl.h>
#include <shlobj.h>
#include <knownfolders.h>   // FOLDERID_Desktop, FOLDERID_Programs, ...
#include <shlwapi.h>
#include <objidl.h>
#include <string>

#define ID_PAYLOAD   201   // o "Curso de Java.exe" embutido
#define ID_ICONE     202

#define APP_NOME     L"Curso de Java"
#define APP_EXE      L"Curso de Java.exe"
#define APP_VERSAO   L"1.0.0"
#define APP_CHAVE    L"Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\CursoJava"

// Identificadores dos controles da janela
#define IDC_INSTALAR      1001
#define IDC_CANCELAR      1002
#define IDC_ATALHO_MESA   1003
#define IDC_ATALHO_MENU   1004
#define IDC_ABRIR_AO_FIM  1005
#define IDC_PROGRESSO     1006
#define IDC_PASTA         1007
#define IDC_MUDAR_PASTA   1008

namespace {

HINSTANCE g_inst = nullptr;
HWND      g_janela = nullptr;
HFONT     g_fonte = nullptr;
HFONT     g_fonteTitulo = nullptr;
HFONT     g_fonteMini = nullptr;
std::wstring g_destino;
bool      g_instalando = false;

// ---------------------------------------------------------------------------
// Utilidades
// ---------------------------------------------------------------------------

std::wstring pastaConhecida(REFKNOWNFOLDERID id) {
    PWSTR p = nullptr;
    std::wstring r;
    if (SUCCEEDED(SHGetKnownFolderPath(id, 0, nullptr, &p))) {
        r = p;
        CoTaskMemFree(p);
    }
    return r;
}

// Cria a arvore de diretorios inteira, como "mkdir -p".
bool criarArvore(const std::wstring &caminho) {
    if (caminho.empty()) return false;
    for (size_t i = 3; i <= caminho.size(); ++i) {
        if (i == caminho.size() || caminho[i] == L'\\') {
            std::wstring parcial = caminho.substr(0, i);
            if (!CreateDirectoryW(parcial.c_str(), nullptr) &&
                GetLastError() == ERROR_PATH_NOT_FOUND) {
                return false;
            }
        }
    }
    DWORD at = GetFileAttributesW(caminho.c_str());
    return at != INVALID_FILE_ATTRIBUTES && (at & FILE_ATTRIBUTE_DIRECTORY);
}

// Grava um recurso embutido em disco.
bool extrairRecurso(int id, const std::wstring &destino) {
    HRSRC achado = FindResourceW(nullptr, MAKEINTRESOURCEW(id), RT_RCDATA);
    if (!achado) return false;
    HGLOBAL bloco = LoadResource(nullptr, achado);
    if (!bloco) return false;
    const void *dados = LockResource(bloco);
    DWORD tamanho = SizeofResource(nullptr, achado);
    if (!dados || !tamanho) return false;

    HANDLE h = CreateFileW(destino.c_str(), GENERIC_WRITE, 0, nullptr,
                           CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, nullptr);
    if (h == INVALID_HANDLE_VALUE) return false;
    DWORD escrito = 0;
    BOOL ok = WriteFile(h, dados, tamanho, &escrito, nullptr);
    CloseHandle(h);
    return ok && escrito == tamanho;
}

// Cria um atalho .lnk apontando para o programa instalado.
bool criarAtalho(const std::wstring &destinoLnk, const std::wstring &alvo,
                 const std::wstring &descricao) {
    IShellLinkW *link = nullptr;
    if (FAILED(CoCreateInstance(CLSID_ShellLink, nullptr, CLSCTX_INPROC_SERVER,
                                IID_IShellLinkW, (void **)&link))) {
        return false;
    }
    link->SetPath(alvo.c_str());
    link->SetDescription(descricao.c_str());
    std::wstring pasta = alvo.substr(0, alvo.find_last_of(L'\\'));
    link->SetWorkingDirectory(pasta.c_str());
    link->SetIconLocation(alvo.c_str(), 0);

    IPersistFile *arquivo = nullptr;
    bool ok = false;
    if (SUCCEEDED(link->QueryInterface(IID_IPersistFile, (void **)&arquivo))) {
        ok = SUCCEEDED(arquivo->Save(destinoLnk.c_str(), TRUE));
        arquivo->Release();
    }
    link->Release();
    return ok;
}

void escreverRegistroTexto(HKEY chave, const wchar_t *nome, const std::wstring &valor) {
    RegSetValueExW(chave, nome, 0, REG_SZ, (const BYTE *)valor.c_str(),
                   (DWORD)((valor.size() + 1) * sizeof(wchar_t)));
}

// Remove um diretorio e todo o seu conteudo.
bool apagarArvore(const std::wstring &pasta) {
    std::wstring busca = pasta + L"\\*";
    WIN32_FIND_DATAW fd;
    HANDLE h = FindFirstFileW(busca.c_str(), &fd);
    if (h != INVALID_HANDLE_VALUE) {
        do {
            std::wstring nome = fd.cFileName;
            if (nome == L"." || nome == L"..") continue;
            std::wstring completo = pasta + L"\\" + nome;
            if (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
                apagarArvore(completo);
            } else {
                SetFileAttributesW(completo.c_str(), FILE_ATTRIBUTE_NORMAL);
                DeleteFileW(completo.c_str());
            }
        } while (FindNextFileW(h, &fd));
        FindClose(h);
    }
    return RemoveDirectoryW(pasta.c_str()) != 0;
}

// ---------------------------------------------------------------------------
// Instalacao
// ---------------------------------------------------------------------------

struct Opcoes {
    bool atalhoMesa = true;
    bool atalhoMenu = true;
    bool abrirAoFim = true;
};

bool instalar(const Opcoes &op, std::wstring *erro) {
    HWND barra = GetDlgItem(g_janela, IDC_PROGRESSO);
    auto avancar = [&](int pct) {
        SendMessageW(barra, PBM_SETPOS, pct, 0);
        // Deixa a janela respirar para a barra realmente aparecer se movendo.
        MSG m;
        while (PeekMessageW(&m, nullptr, 0, 0, PM_REMOVE)) {
            TranslateMessage(&m);
            DispatchMessageW(&m);
        }
        Sleep(90);
    };

    avancar(10);
    if (!criarArvore(g_destino)) {
        *erro = L"Nao foi possivel criar a pasta de instalacao:\n" + g_destino;
        return false;
    }

    avancar(35);
    std::wstring alvoExe = g_destino + L"\\" + APP_EXE;

    // Se estiver rodando de uma instalacao anterior, encerra antes de trocar.
    HWND aberto = FindWindowW(L"CursoJavaJanela", nullptr);
    if (aberto) {
        SendMessageW(aberto, WM_CLOSE, 0, 0);
        Sleep(1200);
    }

    if (!extrairRecurso(ID_PAYLOAD, alvoExe)) {
        *erro = L"Nao foi possivel gravar o programa em:\n" + alvoExe +
                L"\n\nVerifique se ha espaco livre em disco.";
        return false;
    }

    avancar(60);
    std::wstring desinstalador = g_destino + L"\\Desinstalar.exe";
    wchar_t meuCaminho[MAX_PATH];
    GetModuleFileNameW(nullptr, meuCaminho, MAX_PATH);
    CopyFileW(meuCaminho, desinstalador.c_str(), FALSE);

    avancar(75);
    if (op.atalhoMenu) {
        std::wstring menu = pastaConhecida(FOLDERID_Programs);
        if (!menu.empty()) {
            criarAtalho(menu + L"\\" APP_NOME L".lnk", alvoExe,
                        L"Curso de Java: dos fundamentos ao Spring Boot");
        }
    }
    if (op.atalhoMesa) {
        std::wstring mesa = pastaConhecida(FOLDERID_Desktop);
        if (!mesa.empty()) {
            criarAtalho(mesa + L"\\" APP_NOME L".lnk", alvoExe,
                        L"Curso de Java: dos fundamentos ao Spring Boot");
        }
    }

    avancar(90);
    // Registra em "Adicionar ou remover programas" (escopo do usuario).
    HKEY chave;
    if (RegCreateKeyExW(HKEY_CURRENT_USER, APP_CHAVE, 0, nullptr, 0,
                        KEY_WRITE, nullptr, &chave, nullptr) == ERROR_SUCCESS) {
        escreverRegistroTexto(chave, L"DisplayName", APP_NOME);
        escreverRegistroTexto(chave, L"DisplayVersion", APP_VERSAO);
        escreverRegistroTexto(chave, L"Publisher", L"Renato Chong");
        escreverRegistroTexto(chave, L"DisplayIcon", alvoExe);
        escreverRegistroTexto(chave, L"InstallLocation", g_destino);
        escreverRegistroTexto(chave, L"UninstallString",
                              L"\"" + desinstalador + L"\" /desinstalar");
        DWORD um = 1;
        RegSetValueExW(chave, L"NoModify", 0, REG_DWORD, (const BYTE *)&um, sizeof(um));
        RegSetValueExW(chave, L"NoRepair", 0, REG_DWORD, (const BYTE *)&um, sizeof(um));

        // Tamanho estimado, em KB, para o painel do Windows.
        HANDLE h = CreateFileW(alvoExe.c_str(), GENERIC_READ, FILE_SHARE_READ,
                               nullptr, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, nullptr);
        if (h != INVALID_HANDLE_VALUE) {
            LARGE_INTEGER tam{};
            if (GetFileSizeEx(h, &tam)) {
                DWORD kb = (DWORD)(tam.QuadPart / 1024);
                RegSetValueExW(chave, L"EstimatedSize", 0, REG_DWORD,
                               (const BYTE *)&kb, sizeof(kb));
            }
            CloseHandle(h);
        }
        RegCloseKey(chave);
    }

    avancar(100);
    return true;
}

// ---------------------------------------------------------------------------
// Desinstalacao
// ---------------------------------------------------------------------------

int desinstalar() {
    if (MessageBoxW(nullptr,
                    L"Deseja remover o Curso de Java deste computador?\n\n"
                    L"O programa e os atalhos serao apagados.",
                    APP_NOME, MB_ICONQUESTION | MB_YESNO | MB_DEFBUTTON2) != IDYES) {
        return 0;
    }

    HWND aberto = FindWindowW(L"CursoJavaJanela", nullptr);
    if (aberto) {
        SendMessageW(aberto, WM_CLOSE, 0, 0);
        Sleep(1200);
    }

    std::wstring mesa = pastaConhecida(FOLDERID_Desktop);
    if (!mesa.empty()) DeleteFileW((mesa + L"\\" APP_NOME L".lnk").c_str());
    std::wstring menu = pastaConhecida(FOLDERID_Programs);
    if (!menu.empty()) DeleteFileW((menu + L"\\" APP_NOME L".lnk").c_str());

    // Cache do WebView2 criado pelo programa em execucao.
    wchar_t temp[MAX_PATH];
    if (GetTempPathW(MAX_PATH, temp)) {
        apagarArvore(std::wstring(temp) + L"CursoJava");
    }

    RegDeleteKeyW(HKEY_CURRENT_USER, APP_CHAVE);

    // A pasta de instalacao contem este proprio desinstalador, que esta em uso.
    // Apagamos o que da, e deixamos um .bat para remover o resto apos a saida.
    wchar_t meu[MAX_PATH];
    GetModuleFileNameW(nullptr, meu, MAX_PATH);
    std::wstring pastaInst(meu);
    pastaInst = pastaInst.substr(0, pastaInst.find_last_of(L'\\'));
    DeleteFileW((pastaInst + L"\\" APP_EXE).c_str());

    std::wstring bat = std::wstring(temp) + L"remover_curso_java.bat";
    HANDLE h = CreateFileW(bat.c_str(), GENERIC_WRITE, 0, nullptr,
                           CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, nullptr);
    if (h != INVALID_HANDLE_VALUE) {
        std::string script =
            ":r\r\n"
            "ping -n 2 127.0.0.1 >nul\r\n"
            "rmdir /s /q \"" + std::string(pastaInst.begin(), pastaInst.end()) + "\" 2>nul\r\n"
            "if exist \"" + std::string(pastaInst.begin(), pastaInst.end()) + "\" goto r\r\n"
            "del \"%~f0\"\r\n";
        DWORD escrito = 0;
        WriteFile(h, script.c_str(), (DWORD)script.size(), &escrito, nullptr);
        CloseHandle(h);

        STARTUPINFOW si{};
        si.cb = sizeof(si);
        si.dwFlags = STARTF_USESHOWWINDOW;
        si.wShowWindow = SW_HIDE;
        PROCESS_INFORMATION pi{};
        std::wstring cmd = L"cmd.exe /c \"" + bat + L"\"";
        if (CreateProcessW(nullptr, &cmd[0], nullptr, nullptr, FALSE,
                           CREATE_NO_WINDOW, nullptr, temp, &si, &pi)) {
            CloseHandle(pi.hProcess);
            CloseHandle(pi.hThread);
        }
    }

    MessageBoxW(nullptr, L"O Curso de Java foi removido.", APP_NOME,
                MB_ICONINFORMATION | MB_OK);
    return 0;
}

// ---------------------------------------------------------------------------
// Interface do assistente
// ---------------------------------------------------------------------------

HWND criarRotulo(HWND pai, const wchar_t *txt, int x, int y, int w, int h, HFONT f) {
    HWND r = CreateWindowExW(0, L"STATIC", txt, WS_CHILD | WS_VISIBLE,
                             x, y, w, h, pai, nullptr, g_inst, nullptr);
    SendMessageW(r, WM_SETFONT, (WPARAM)f, TRUE);
    return r;
}

void montarInterface(HWND h) {
    // Cabecalho
    criarRotulo(h, L"Instalar o Curso de Java", 28, 24, 420, 30, g_fonteTitulo);
    criarRotulo(h, L"dos fundamentos ao Spring Boot  \x2022  6 modulos completos",
                28, 56, 460, 20, g_fonteMini);

    criarRotulo(h, L"O curso sera instalado em:", 28, 100, 300, 18, g_fonte);

    HWND pasta = CreateWindowExW(WS_EX_CLIENTEDGE, L"EDIT", g_destino.c_str(),
                                 WS_CHILD | WS_VISIBLE | ES_AUTOHSCROLL | ES_READONLY,
                                 28, 122, 380, 24, h, (HMENU)IDC_PASTA, g_inst, nullptr);
    SendMessageW(pasta, WM_SETFONT, (WPARAM)g_fonte, TRUE);

    HWND mudar = CreateWindowExW(0, L"BUTTON", L"Mudar...",
                                 WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON,
                                 416, 122, 76, 24, h, (HMENU)IDC_MUDAR_PASTA, g_inst, nullptr);
    SendMessageW(mudar, WM_SETFONT, (WPARAM)g_fonte, TRUE);

    // Opcoes
    HWND c1 = CreateWindowExW(0, L"BUTTON", L"Criar atalho na Area de Trabalho",
                              WS_CHILD | WS_VISIBLE | BS_AUTOCHECKBOX,
                              28, 168, 300, 22, h, (HMENU)IDC_ATALHO_MESA, g_inst, nullptr);
    SendMessageW(c1, WM_SETFONT, (WPARAM)g_fonte, TRUE);
    SendMessageW(c1, BM_SETCHECK, BST_CHECKED, 0);

    HWND c2 = CreateWindowExW(0, L"BUTTON", L"Adicionar ao Menu Iniciar",
                              WS_CHILD | WS_VISIBLE | BS_AUTOCHECKBOX,
                              28, 194, 300, 22, h, (HMENU)IDC_ATALHO_MENU, g_inst, nullptr);
    SendMessageW(c2, WM_SETFONT, (WPARAM)g_fonte, TRUE);
    SendMessageW(c2, BM_SETCHECK, BST_CHECKED, 0);

    HWND c3 = CreateWindowExW(0, L"BUTTON", L"Abrir o curso ao concluir",
                              WS_CHILD | WS_VISIBLE | BS_AUTOCHECKBOX,
                              28, 220, 300, 22, h, (HMENU)IDC_ABRIR_AO_FIM, g_inst, nullptr);
    SendMessageW(c3, WM_SETFONT, (WPARAM)g_fonte, TRUE);
    SendMessageW(c3, BM_SETCHECK, BST_CHECKED, 0);

    // Barra de progresso (oculta ate o inicio da instalacao)
    HWND barra = CreateWindowExW(0, PROGRESS_CLASSW, nullptr,
                                 WS_CHILD | PBS_SMOOTH,
                                 28, 262, 464, 16, h, (HMENU)IDC_PROGRESSO, g_inst, nullptr);
    SendMessageW(barra, PBM_SETRANGE, 0, MAKELPARAM(0, 100));

    // Botoes
    HWND inst = CreateWindowExW(0, L"BUTTON", L"Instalar",
                                WS_CHILD | WS_VISIBLE | BS_DEFPUSHBUTTON,
                                294, 300, 96, 30, h, (HMENU)IDC_INSTALAR, g_inst, nullptr);
    SendMessageW(inst, WM_SETFONT, (WPARAM)g_fonte, TRUE);

    HWND canc = CreateWindowExW(0, L"BUTTON", L"Cancelar",
                                WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON,
                                398, 300, 96, 30, h, (HMENU)IDC_CANCELAR, g_inst, nullptr);
    SendMessageW(canc, WM_SETFONT, (WPARAM)g_fonte, TRUE);
}

LRESULT CALLBACK Proc(HWND h, UINT msg, WPARAM wp, LPARAM lp) {
    switch (msg) {
    case WM_CREATE:
        montarInterface(h);
        return 0;

    // Fundo branco para a area do assistente.
    case WM_CTLCOLORSTATIC:
    case WM_CTLCOLORBTN:
        SetBkMode((HDC)wp, TRANSPARENT);
        return (LRESULT)GetStockObject(WHITE_BRUSH);

    case WM_ERASEBKGND: {
        RECT r;
        GetClientRect(h, &r);
        FillRect((HDC)wp, &r, (HBRUSH)GetStockObject(WHITE_BRUSH));
        return 1;
    }

    case WM_COMMAND:
        switch (LOWORD(wp)) {
        case IDC_MUDAR_PASTA: {
            BROWSEINFOW bi{};
            bi.hwndOwner = h;
            bi.lpszTitle = L"Escolha onde instalar o Curso de Java";
            bi.ulFlags = BIF_RETURNONLYFSDIRS | BIF_NEWDIALOGSTYLE;
            PIDLIST_ABSOLUTE pidl = SHBrowseForFolderW(&bi);
            if (pidl) {
                wchar_t escolhido[MAX_PATH];
                if (SHGetPathFromIDListW(pidl, escolhido)) {
                    g_destino = std::wstring(escolhido) + L"\\" APP_NOME;
                    SetDlgItemTextW(h, IDC_PASTA, g_destino.c_str());
                }
                CoTaskMemFree(pidl);
            }
            return 0;
        }

        case IDC_INSTALAR: {
            if (g_instalando) return 0;
            g_instalando = true;

            Opcoes op;
            op.atalhoMesa = IsDlgButtonChecked(h, IDC_ATALHO_MESA) == BST_CHECKED;
            op.atalhoMenu = IsDlgButtonChecked(h, IDC_ATALHO_MENU) == BST_CHECKED;
            op.abrirAoFim = IsDlgButtonChecked(h, IDC_ABRIR_AO_FIM) == BST_CHECKED;

            EnableWindow(GetDlgItem(h, IDC_INSTALAR), FALSE);
            EnableWindow(GetDlgItem(h, IDC_CANCELAR), FALSE);
            EnableWindow(GetDlgItem(h, IDC_MUDAR_PASTA), FALSE);
            ShowWindow(GetDlgItem(h, IDC_PROGRESSO), SW_SHOW);

            std::wstring erro;
            if (instalar(op, &erro)) {
                std::wstring alvo = g_destino + L"\\" APP_EXE;
                MessageBoxW(h,
                            L"O Curso de Java foi instalado com sucesso.\n\n"
                            L"Voce pode abri-lo pelo atalho na Area de Trabalho\n"
                            L"ou pelo Menu Iniciar.",
                            APP_NOME, MB_ICONINFORMATION | MB_OK);
                if (op.abrirAoFim) {
                    ShellExecuteW(nullptr, L"open", alvo.c_str(), nullptr,
                                  g_destino.c_str(), SW_SHOWNORMAL);
                }
                DestroyWindow(h);
            } else {
                MessageBoxW(h, erro.c_str(), APP_NOME, MB_ICONERROR | MB_OK);
                EnableWindow(GetDlgItem(h, IDC_INSTALAR), TRUE);
                EnableWindow(GetDlgItem(h, IDC_CANCELAR), TRUE);
                EnableWindow(GetDlgItem(h, IDC_MUDAR_PASTA), TRUE);
                SendMessageW(GetDlgItem(h, IDC_PROGRESSO), PBM_SETPOS, 0, 0);
                g_instalando = false;
            }
            return 0;
        }

        case IDC_CANCELAR:
            DestroyWindow(h);
            return 0;
        }
        return 0;

    case WM_CLOSE:
        if (g_instalando) return 0;   // nao interrompe no meio da copia
        DestroyWindow(h);
        return 0;

    case WM_DESTROY:
        PostQuitMessage(0);
        return 0;
    }
    return DefWindowProcW(h, msg, wp, lp);
}

}  // namespace

// ---------------------------------------------------------------------------

int WINAPI wWinMain(HINSTANCE inst, HINSTANCE, PWSTR linhaComando, int mostrar) {
    g_inst = inst;
    CoInitializeEx(nullptr, COINIT_APARTMENTTHREADED);

    INITCOMMONCONTROLSEX icc{sizeof(icc), ICC_PROGRESS_CLASS | ICC_STANDARD_CLASSES};
    InitCommonControlsEx(&icc);

    // O mesmo binario serve de desinstalador.
    if (linhaComando && wcsstr(linhaComando, L"/desinstalar")) {
        int r = desinstalar();
        CoUninitialize();
        return r;
    }

    std::wstring base = pastaConhecida(FOLDERID_LocalAppData);
    if (base.empty()) base = L"C:\\Curso de Java";
    g_destino = base + L"\\Programs\\" APP_NOME;

    // Fontes do sistema, para o assistente parecer nativo.
    NONCLIENTMETRICSW ncm{sizeof(ncm)};
    SystemParametersInfoW(SPI_GETNONCLIENTMETRICS, sizeof(ncm), &ncm, 0);
    g_fonte = CreateFontIndirectW(&ncm.lfMessageFont);

    LOGFONTW lfT = ncm.lfMessageFont;
    lfT.lfHeight = (LONG)(lfT.lfHeight * 1.65);
    lfT.lfWeight = FW_SEMIBOLD;
    g_fonteTitulo = CreateFontIndirectW(&lfT);

    LOGFONTW lfM = ncm.lfMessageFont;
    g_fonteMini = CreateFontIndirectW(&lfM);

    WNDCLASSEXW wc{sizeof(wc)};
    wc.lpfnWndProc   = Proc;
    wc.hInstance     = inst;
    wc.lpszClassName = L"CursoJavaInstalador";
    wc.hCursor       = LoadCursor(nullptr, IDC_ARROW);
    wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
    wc.hIcon         = (HICON)LoadImageW(inst, MAKEINTRESOURCEW(ID_ICONE),
                                         IMAGE_ICON, 0, 0, LR_DEFAULTSIZE);
    wc.hIconSm       = wc.hIcon;
    RegisterClassExW(&wc);

    UINT dpi = 96;
    if (HDC dc = GetDC(nullptr)) {
        dpi = (UINT)GetDeviceCaps(dc, LOGPIXELSX);
        ReleaseDC(nullptr, dc);
    }
    double escala = dpi / 96.0;
    int largura = (int)(536 * escala);
    int altura  = (int)(392 * escala);

    RECT area{};
    SystemParametersInfoW(SPI_GETWORKAREA, 0, &area, 0);
    int x = area.left + ((area.right - area.left) - largura) / 2;
    int y = area.top + ((area.bottom - area.top) - altura) / 2;

    g_janela = CreateWindowExW(
        0, L"CursoJavaInstalador", L"Instalar o Curso de Java",
        WS_OVERLAPPED | WS_CAPTION | WS_SYSMENU | WS_MINIMIZEBOX,
        x, y, largura, altura, nullptr, nullptr, inst, nullptr);
    if (!g_janela) return 1;

    ShowWindow(g_janela, mostrar);
    UpdateWindow(g_janela);

    MSG msg;
    while (GetMessageW(&msg, nullptr, 0, 0)) {
        if (!IsDialogMessageW(g_janela, &msg)) {
            TranslateMessage(&msg);
            DispatchMessageW(&msg);
        }
    }

    CoUninitialize();
    return (int)msg.wParam;
}
