# =============================================================================
#  Gera o "Curso de Java.exe" a partir dos arquivos .md e .java do repositorio.
#
#  Uso:  .\compilar.ps1
#
#  Pre-requisitos:
#    - g++ (MinGW-w64) no PATH
#    - python 3 no PATH
#  O SDK do WebView2 e baixado automaticamente na primeira execucao.
# =============================================================================
$ErrorActionPreference = "Stop"
$aqui = Split-Path -Parent $MyInvocation.MyCommand.Path
$raiz = Split-Path -Parent $aqui
$dist = Join-Path $raiz "dist"
$saida = Join-Path $dist "Curso de Java.exe"

foreach ($p in "g++", "python", "windres") {
  if (-not (Get-Command $p -ErrorAction SilentlyContinue)) {
    throw "'$p' nao encontrado no PATH. Instale o MinGW-w64 e o Python."
  }
}

# --- SDK do WebView2 (headers + DLL do carregador) --------------------------
$sdk = Join-Path $aqui "wv2"
if (-not (Test-Path "$sdk\build\native\include\WebView2.h")) {
  Write-Host "[0/4] baixando o SDK do WebView2 (uma unica vez)..." -ForegroundColor Cyan
  $zip = Join-Path $aqui "webview2.zip"
  Invoke-WebRequest -UseBasicParsing -TimeoutSec 180 `
    -Uri "https://www.nuget.org/api/v2/package/Microsoft.Web.WebView2/1.0.2903.40" `
    -OutFile $zip
  Expand-Archive -Path $zip -DestinationPath $sdk -Force
  Remove-Item $zip -Force
}
$inc = "$sdk\build\native\include"

Write-Host "[1/4] convertendo o curso (markdown -> html)..." -ForegroundColor Cyan
$env:PYTHONIOENCODING = 'utf-8'
python (Join-Path $aqui "build_html.py")
if ($LASTEXITCODE -ne 0) { throw "build_html.py falhou" }
python (Join-Path $aqui "injetar.py")
if ($LASTEXITCODE -ne 0) { throw "injetar.py falhou" }

Write-Host "[2/4] preparando recursos..." -ForegroundColor Cyan
Copy-Item "$sdk\build\native\x64\WebView2Loader.dll" (Join-Path $aqui "WebView2Loader.dll") -Force
if (-not (Test-Path (Join-Path $aqui "curso.ico"))) { python (Join-Path $aqui "icone.py") }

Write-Host "[3/4] compilando recursos (windres)..." -ForegroundColor Cyan
Push-Location $aqui
try {
  windres "recurso.rc" -o "recurso.o" --output-format=coff
  if ($LASTEXITCODE -ne 0) { throw "windres falhou" }
} finally { Pop-Location }

Write-Host "[4/4] compilando e linkando (g++)..." -ForegroundColor Cyan
New-Item -ItemType Directory -Force $dist | Out-Null

$flags = @("-std=c++17", "-O2", "-municode", "-mwindows",
           "-static", "-static-libgcc", "-static-libstdc++", "-DNDEBUG")
$libs  = @("-lole32", "-loleaut32", "-lshlwapi", "-luser32",
           "-lgdi32", "-ladvapi32", "-lversion", "-luuid")

& g++ @flags (Join-Path $aqui "app.cpp") (Join-Path $aqui "recurso.o") -I"$inc" @libs -o "$saida"
if ($LASTEXITCODE -ne 0) { throw "g++ falhou" }
& strip --strip-all "$saida"

$mb = [math]::Round((Get-Item $saida).Length / 1MB, 2)
Write-Host "   programa: $mb MB" -ForegroundColor Green

# --- Instalador -------------------------------------------------------------
# Embute o programa recem-gerado e monta o assistente de instalacao.
Write-Host "[5/5] gerando o instalador..." -ForegroundColor Cyan
$setup = Join-Path $dist "Instalar Curso de Java.exe"

Push-Location $aqui
try {
  windres "instalador.rc" -o "instalador.o" --output-format=coff
  if ($LASTEXITCODE -ne 0) { throw "windres do instalador falhou" }
} finally { Pop-Location }

$libsSetup = @("-lole32", "-loleaut32", "-lshlwapi", "-lshell32", "-lcomctl32",
               "-luser32", "-lgdi32", "-ladvapi32", "-luuid")

& g++ @flags (Join-Path $aqui "instalador.cpp") (Join-Path $aqui "instalador.o") @libsSetup -o "$setup"
if ($LASTEXITCODE -ne 0) { throw "g++ do instalador falhou" }
& strip --strip-all "$setup"

# Arquivos intermediarios nao precisam sobreviver ao build.
Remove-Item (Join-Path $aqui "recurso.o"), (Join-Path $aqui "instalador.o"),
            (Join-Path $aqui "conteudo.json") -ErrorAction SilentlyContinue

$mbSetup = [math]::Round((Get-Item $setup).Length / 1MB, 2)
Write-Host ""
Write-Host "PRONTO" -ForegroundColor Green
Write-Host "  instalador (para compartilhar): $setup  ($mbSetup MB)" -ForegroundColor Green
Write-Host "  programa avulso              : $saida  ($mb MB)" -ForegroundColor DarkGray
