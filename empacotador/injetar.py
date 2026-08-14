# -*- coding: utf-8 -*-
"""Injeta conteudo.json dentro de shell.html, produzindo curso.html autocontido."""
import json
import os
import re

AQUI = os.path.dirname(os.path.abspath(__file__))

with open(os.path.join(AQUI, "shell.html"), encoding="utf-8") as f:
    shell = f.read()
with open(os.path.join(AQUI, "conteudo.json"), encoding="utf-8") as f:
    dados = f.read()

# </script> dentro de uma string JS encerraria o bloco <script> do documento.
dados = dados.replace("</script>", "<\\/script>")

alvo = re.compile(r"/\*__CONTEUDO__\*/.*?/\*__FIM__\*/", re.S)
if not alvo.search(shell):
    raise SystemExit("marcador de injecao nao encontrado no shell.html")

final = alvo.sub(lambda _: dados, shell, count=1)

destino = os.path.join(AQUI, "curso.html")
with open(destino, "w", encoding="utf-8") as f:
    f.write(final)

print(f"curso.html = {len(final.encode('utf-8')) / 1024:.0f} KB")
print(f"gravado em: {destino}")
