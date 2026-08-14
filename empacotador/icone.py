# -*- coding: utf-8 -*-
"""Gera curso.ico (multi-resolucao) sem depender de bibliotecas externas.

Desenha um quadrado arredondado com degrade laranja/vermelho e a letra "J",
depois monta o container .ICO com um PNG por resolucao.
"""
import os
import struct
import zlib

AQUI = os.path.dirname(os.path.abspath(__file__))
TAMANHOS = [16, 24, 32, 48, 64, 128, 256]

# Bitmap 8 linhas x 7 colunas, usado para desenhar a letra de forma nitida.
# Um grid mais largo que o do curso irmao porque o "J" tem uma curva no pe,
# e em 5 colunas ela vira um degrau seco.
GLIFOS = {
    "J": [
        "0000110",
        "0000110",
        "0000110",
        "0000110",
        "0000110",
        "1000110",
        "1100100",
        "0111000",
    ],
}


def mistura(c1, c2, t):
    return tuple(round(a + (b - a) * t) for a, b in zip(c1, c2))


def desenhar(n):
    """Devolve os pixels RGBA do icone no tamanho n."""
    px = [[(0, 0, 0, 0)] * n for _ in range(n)]
    raio = max(2, round(n * 0.22))

    inicio = (232, 120, 30)  # laranja
    fim    = (176, 32, 42)   # vermelho

    for y in range(n):
        for x in range(n):
            # cantos arredondados, com suavizacao na borda
            dx = dy = 0.0
            if x < raio:            dx = raio - x - 0.5
            elif x >= n - raio:     dx = x - (n - raio) + 0.5
            if y < raio:            dy = raio - y - 0.5
            elif y >= n - raio:     dy = y - (n - raio) + 0.5

            alfa = 255
            if dx > 0 and dy > 0:
                dist = (dx * dx + dy * dy) ** 0.5
                if dist > raio:
                    continue
                if dist > raio - 1:
                    alfa = round(255 * (raio - dist))

            cor = mistura(inicio, fim, (x + y) / (2.0 * n))
            px[y][x] = (cor[0], cor[1], cor[2], alfa)

    # ---- letra "J" ----
    # Um glifo so, entao pode ocupar mais espaco que o "C++" do curso irmao.
    esc = max(1, round(n / 14))          # espessura do traco
    largura = len(GLIFOS["J"][0])
    altura = len(GLIFOS["J"])
    # Centra pelo que e de fato desenhado: a haste do "J" nao chega na coluna 0,
    # entao centrar pelo grid inteiro empurraria a letra para a direita.
    acesas = [x for linha in GLIFOS["J"] for x, c in enumerate(linha) if c == "1"]
    centro_glifo = (min(acesas) + max(acesas) + 1) / 2
    x0 = round(n / 2 - centro_glifo * esc)
    y0 = (n - altura * esc) // 2

    def carimbar(glifo, gx, gy, escala):
        linhas = GLIFOS[glifo]
        for ly, linha in enumerate(linhas):
            for lx, c in enumerate(linha):
                if c != "1":
                    continue
                for sy in range(escala):
                    for sx in range(escala):
                        X, Y = gx + lx * escala + sx, gy + ly * escala + sy
                        if 0 <= X < n and 0 <= Y < n and px[Y][X][3] > 0:
                            px[Y][X] = (255, 255, 255, 255)

    carimbar("J", x0, y0, esc)

    return px


def png(px, n):
    """Codifica os pixels como PNG (sem dependencias)."""
    linhas = bytearray()
    for y in range(n):
        linhas.append(0)  # filtro None
        for x in range(n):
            linhas.extend(px[y][x])

    def bloco(tipo, dados):
        c = struct.pack(">I", len(dados)) + tipo + dados
        return c + struct.pack(">I", zlib.crc32(tipo + dados) & 0xFFFFFFFF)

    return (b"\x89PNG\r\n\x1a\n"
            + bloco(b"IHDR", struct.pack(">IIBBBBB", n, n, 8, 6, 0, 0, 0))
            + bloco(b"IDAT", zlib.compress(bytes(linhas), 9))
            + bloco(b"IEND", b""))


imagens = [(n, png(desenhar(n), n)) for n in TAMANHOS]

# ---- container .ICO ----
cab = struct.pack("<HHH", 0, 1, len(imagens))
entradas = bytearray()
corpo = bytearray()
deslocamento = 6 + 16 * len(imagens)

for n, dados in imagens:
    entradas.extend(struct.pack(
        "<BBBBHHII",
        0 if n >= 256 else n, 0 if n >= 256 else n,
        0, 0, 1, 32, len(dados), deslocamento))
    corpo.extend(dados)
    deslocamento += len(dados)

destino = os.path.join(AQUI, "curso.ico")
with open(destino, "wb") as f:
    f.write(cab + bytes(entradas) + bytes(corpo))

print(f"curso.ico = {os.path.getsize(destino) / 1024:.1f} KB "
      f"({len(imagens)} resolucoes: {', '.join(str(n) for n in TAMANHOS)})")
