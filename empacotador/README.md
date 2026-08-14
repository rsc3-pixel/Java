# Empacotador: o curso como aplicativo do Windows

Transforma os arquivos `.md` e `.java` deste repositório em um aplicativo Windows, para entregar o curso a alguém que só quer **ler e estudar**, sem precisar de Git, JDK ou editor.

Gera dois arquivos:

| Arquivo | Tamanho | Para quê |
| :--- | :--- | :--- |
| **`Instalar Curso de Java.exe`** | ~1,6 MB | **É este que se compartilha.** Assistente de instalação: cria atalhos e registra a desinstalação |
| `Curso de Java.exe` | ~1,3 MB | O programa em si, avulso. Roda direto, sem instalar |

---

## Gerar

```powershell
.\compilar.ps1
```

Os dois arquivos saem em `../dist/`.

**Pré-requisitos:** `g++` (MinGW-w64), `python` e `windres` no PATH. O SDK do WebView2 é baixado sozinho na primeira execução.

Repare que o JDK **não** é pré-requisito: o empacotador lê os `.java` como texto, não os compila. Quem recebe o aplicativo também não precisa de JDK para ler o curso, só para rodar os exemplos.

Rode de novo sempre que alterar qualquer `.md` ou `.java`: o conteúdo é embutido no binário em tempo de compilação.

---

## O que o instalador faz

Instalação **por usuário**, em `%LOCALAPPDATA%\Programs\Curso de Java`. Essa escolha é o que evita o pedido de permissão de administrador: gravar em `Arquivos de Programas` exigiria elevação (o manifesto declara `asInvoker` justamente por isso).

- copia o programa para a pasta de instalação
- cria atalho na Área de Trabalho e no Menu Iniciar (ambos opcionais)
- registra em **Adicionar ou remover programas**, com nome, versão, editor e tamanho
- grava um `Desinstalar.exe` que reverte tudo

O desinstalador é **o mesmo binário** do instalador, apenas invocado com `/desinstalar`. Ele apaga a pasta, os atalhos, a chave de registro e o cache do WebView2 em `%TEMP%`.

Há uma sutileza aí: a pasta a remover contém o desinstalador que está rodando, e o Windows não deixa apagar um executável em uso. A solução é gravar um `.bat` em `%TEMP%` que espera o processo sair, apaga a pasta e por fim se autoapaga.

---

## Como funciona

O curso é Markdown, e Markdown não é um programa. A ponte entre as duas coisas tem três etapas:

| Etapa | Quem faz | O que acontece |
| :--- | :--- | :--- |
| 1. Converter | `build_html.py` | Lê os `.md` e `.java` dos 6 módulos e gera HTML, com destaque de sintaxe Java feito à mão |
| 2. Montar | `injetar.py` | Injeta esse conteúdo dentro de `shell.html` (a interface: menu, busca, temas), produzindo um HTML único |
| 3. Embutir | `recurso.rc` + `g++` | O HTML vira um **recurso** dentro do `.exe`, junto do ícone e do carregador do WebView2 |

Em execução, `app.cpp` cria uma janela Win32 comum e hospeda nela um controle **WebView2**, que é o motor de renderização do Edge, já presente em qualquer Windows 10/11 atualizado. É a mesma técnica usada pelo Discord e pelo VS Code, com uma diferença importante: aqui o motor é o que já existe no sistema, e não uma cópia própria de 100 MB.

O HTML nunca é gravado em disco. Um manipulador de recursos responde ao endereço virtual `https://curso.local/` servindo os bytes direto da memória, o que também dá à página uma origem estável para o `localStorage` guardar a preferência de tema.

---

## O módulo 6 é diferente

Nos módulos 1 a 5, cada pasta tem quatro arquivos soltos (`ExemploN.java`, `ExercicioN.java`) e o conversor simplesmente os enfileira.

O módulo 6 não: ele é um **projeto Maven inteiro**, com `pom.xml`, `application.properties` e dez fontes espalhadas em `src/main/java/...`. Enfileirar por ordem alfabética ali produziria uma leitura sem sentido, começando pelo `CargaInicial`.

Por isso a lista `ARQUIVOS_SPRING`, no `build_html.py`, é **escrita à mão e ordenada**: os arquivos aparecem na sequência em que uma requisição os atravessa, da configuração para a entidade, o repositório, o serviço e por fim o controlador. Cada um leva o caminho completo e uma nota de uma linha dizendo qual é o seu papel. O `README.md` do projeto entra convertido antes de todos, porque explica como subir a API.

Se você adicionar um arquivo ao projeto Spring, **inclua-o nessa lista**, ou ele não aparecerá no aplicativo.

---

## Arquivos

| Arquivo | Papel |
| :--- | :--- |
| `app.cpp` | O programa: janela, WebView2 e extração de recursos |
| `instalador.cpp` | O assistente de instalação, que também serve de desinstalador |
| `shell.html` | A interface: navegação, busca, tema claro/escuro |
| `build_html.py` | Conversor de Markdown para HTML |
| `injetar.py` | Junta conteúdo e interface num HTML só |
| `icone.py` | Gera `curso.ico` em 7 resoluções |
| `recurso.rc` / `instalador.rc` | Declaram o que vai embutido em cada executável |
| `app.manifest` / `instalador.manifest` | DPI, visual moderno e nível de privilégio |

Note a boneca russa: o `instalador.rc` embute o `Curso de Java.exe` recém-compilado, que por sua vez já embute o curso inteiro em HTML. Por isso o build gera o programa **antes** do instalador.

O empacotador é escrito em C++ mesmo empacotando um curso de Java. Não é contradição: o que se quer aqui é um executável nativo pequeno, sem exigir runtime instalado na máquina de quem recebe. Um empacotador em Java precisaria de JVM justamente em quem ainda não tem.

---

## Detalhes de implementação que valem nota

Três obstáculos reais apareceram ao usar **MinGW** em vez do compilador da Microsoft, e a solução de cada um está comentada no `app.cpp`:

1. **O MinGW não tem a WRL da Microsoft** (nem `ComPtr`, nem `Callback`). O arquivo implementa os dois à mão: um ponteiro inteligente COM e um adaptador que transforma lambda em objeto COM.

2. **`Invoke` não podia ser um template de método.** Um template nunca sobrescreve uma função virtual pura, então a classe continuaria abstrata. Os tipos dos argumentos são passados explicitamente.

3. **`__uuidof` não funciona no MinGW** com esses headers. O `IID` é passado como parâmetro, e `#define INITGUID` faz os símbolos `IID_*` existirem de fato.

Vale também notar por que a `WebView2LoaderStatic.lib` **não** é usada: linkar a biblioteca do MSVC com MinGW causa centenas de erros de "multiple definition" nos GUIDs. Carregar a DLL com `LoadLibrary` resolve e ainda economiza 10 MB no binário.

No destaque de sintaxe há uma decisão que economiza manutenção: qualquer identificador começando com maiúscula é pintado como tipo. É a convenção do Java, então `Produto` e `ContaBancaria`, que são classes do próprio curso, ficam destacadas sem precisar entrar em lista nenhuma.

---

## Requisitos na máquina de quem recebe

Windows 10 ou 11 com o Microsoft Edge atualizado, que é o padrão. O WebView2 acompanha o Edge desde 2021.

Na primeira execução, o Windows pode exibir o aviso azul do SmartScreen, porque o arquivo não tem assinatura digital (um certificado custa algumas centenas de dólares por ano). O caminho é **Mais informações → Executar assim mesmo**.

Nada é gravado em `Arquivos de Programas` nem em `HKEY_LOCAL_MACHINE`, e nenhuma permissão de administrador é solicitada. Tudo mora no perfil do usuário e sai inteiro pela desinstalação.
