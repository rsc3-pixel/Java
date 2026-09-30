# -*- coding: utf-8 -*-
"""Converte o curso de Java (Markdown) em um unico documento HTML autocontido.

Roda em tempo de build: a saida e embutida dentro do executavel.
Nao ha dependencias externas, nem de rede em tempo de execucao.
"""
import html
import os
import re
import json

# A raiz do curso e a pasta acima desta (empacotador/ mora dentro do repo).
RAIZ = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

MODULOS = [
    ("Módulo 1", "Sintaxe Básica", "modulo1"),
    ("Módulo 2", "Orientação a Objetos", "modulo2"),
    ("Módulo 3", "Coleções de Dados", "modulo3"),
    ("Módulo 4", "Erros e Arquivos", "modulo4"),
    ("Módulo 5", "Java Moderno (8+)", "modulo5"),
    ("Módulo 6", "Spring Boot", "modulo6"),
    ("Módulo 7", "Revisão: Prova de POO", "modulo7"),
]

PARTES = [
    ("teoria.md", "Teoria", "book"),
    ("lista_exercicios.md", "Exercícios", "pencil"),
    ("lista_exercicios_prep.md", "Simulado", "check"),
]

# O modulo 6 nao tem arquivos soltos: tem um projeto Maven inteiro. A ordem
# aqui e didatica (da entrada da aplicacao ate as bordas), nao alfabetica.
PROJETO_SPRING = "modulo6/projeto-spring"
ARQUIVOS_SPRING = [
    ("pom.xml", "Dependências e build do projeto"),
    ("src/main/resources/application.properties", "Configuração"),
    ("src/main/java/com/renatochong/cursoapi/CursoApiApplication.java",
     "Ponto de entrada"),
    ("src/main/java/com/renatochong/cursoapi/model/Produto.java",
     "Entidade JPA, com Bean Validation"),
    ("src/main/java/com/renatochong/cursoapi/repository/ProdutoRepository.java",
     "Acesso a dados"),
    ("src/main/java/com/renatochong/cursoapi/service/ProdutoService.java",
     "Regras de negócio"),
    ("src/main/java/com/renatochong/cursoapi/controller/ProdutoController.java",
     "Endpoints REST"),
    ("src/main/java/com/renatochong/cursoapi/exception/ProdutoNaoEncontradoException.java",
     "Exceção de domínio"),
    ("src/main/java/com/renatochong/cursoapi/exception/TratadorDeErros.java",
     "Tratamento global de erros"),
    ("src/main/java/com/renatochong/cursoapi/config/CargaInicial.java",
     "Dados de exemplo na subida"),
]

# --------------------------------------------------------------------------
# Destaque de sintaxe para Java (feito a mao: a pagina nao pode baixar libs)
# --------------------------------------------------------------------------

PALAVRAS_CHAVE = r"""abstract assert boolean break byte case catch char class const continue
default do double else enum extends final finally float for goto if implements import instanceof
int interface long native new package private protected public return short static strictfp super
switch synchronized this throw throws transient try var void volatile while yield record sealed
permits non-sealed true false null""".split()

TIPOS_JAVA = """String Integer Double Boolean Character Long Short Byte Float Object Number
System out err in println print printf format Math Arrays Collections List ArrayList LinkedList
Map HashMap TreeMap LinkedHashMap Set HashSet TreeSet LinkedHashSet Queue Deque Optional Stream
Collectors Comparator Comparable Iterable Iterator Scanner Exception RuntimeException Error
Throwable IllegalArgumentException IllegalStateException NullPointerException IOException
StringBuilder StringBuffer Thread Runnable LocalDate LocalDateTime LocalTime Duration Period
DateTimeFormatter BigDecimal BigInteger Objects Function Predicate Consumer Supplier BiFunction
UnaryOperator equals hashCode toString compareTo size add remove get put contains isEmpty length
charAt substring stream filter map collect forEach sorted reduce count anyMatch allMatch
noneMatch findFirst orElse orElseThrow ofNullable""".split()

# A "cara" do Spring: sem isso, o modulo 6 fica visualmente pobre.
ANOTACOES_CONHECIDAS = """Override Entity Id GeneratedValue Column Table RestController
RequestMapping GetMapping PostMapping PutMapping PatchMapping DeleteMapping PathVariable
RequestBody RequestParam Service Repository Component Configuration Bean Autowired Transactional
Valid NotNull NotBlank Positive PositiveOrZero Min Max Size Query RestControllerAdvice
ExceptionHandler ResponseStatus SpringBootApplication FunctionalInterface Deprecated
SafeVarargs SuppressWarnings""".split()


def marcar_codigo(codigo, lang):
    """Aplica destaque de sintaxe devolvendo HTML ja escapado.

    Estrategia: uma unica varredura com regex alternado, para que strings e
    comentarios tenham prioridade sobre palavras-chave (senao um 'int' dentro
    de uma string seria colorido por engano).
    """
    if lang not in ("java", "bash", "xml", "properties", "http", ""):
        lang = ""

    if lang == "bash":
        padrao = re.compile(
            r"(?P<com>#[^\n]*)"
            r"|(?P<str>\"[^\"\n]*\"|'[^'\n]*')"
            r"|(?P<flag>(?<=\s)-{1,2}[A-Za-z][\w.-]*)"
            r"|(?P<cmd>^\s*(?:javac|java|mvn|mvnw|\.\\mvnw\.cmd|curl|cd|ls|echo|git|\./[\w.]+))"
        )
    elif lang == "xml":
        # O pom.xml e o unico XML do curso: tag, atributo e comentario bastam.
        padrao = re.compile(
            r"(?P<com><!--.*?-->)"
            r"|(?P<str>\"[^\"\n]*\")"
            r"|(?P<kw></?[A-Za-z_][\w.:-]*)"
            r"|(?P<pre>/?>)",
            re.S,
        )
    elif lang == "properties":
        padrao = re.compile(
            r"(?P<com>[#!][^\n]*)"
            r"|(?P<ty>^[ \t]*[\w.\-]+(?=\s*[=:]))"
            r"|(?P<num>\b\d+\b)",
            re.M,
        )
    elif lang == "http":
        # Blocos de exemplo de requisicao, no estilo "GET /api/produtos".
        padrao = re.compile(
            r"(?P<com>#[^\n]*)"
            r"|(?P<kw>^\s*(?:GET|POST|PUT|PATCH|DELETE|HEAD|OPTIONS)\b)"
            r"|(?P<str>\"(?:[^\"\\\n]|\\.)*\")"
            r"|(?P<num>\b\d+\b)",
            re.M,
        )
    else:
        padrao = re.compile(
            r"(?P<com>//[^\n]*|/\*.*?\*/)"
            r"|(?P<str>\"(?:[^\"\\\n]|\\.)*\"|'(?:[^'\\\n]|\\.)*')"
            r"|(?P<ann>@[A-Za-z_]\w*)"
            r"|(?P<num>\b(?:0[xXbB][0-9a-fA-F_]+|\d[\d_]*\.?[\d_]*(?:[eE][+-]?\d+)?)[dDfFlL]?\b)"
            r"|(?P<fn>\b[A-Za-z_]\w*(?=\s*\())"
            r"|(?P<id>\b[A-Za-z_]\w*\b)",
            re.S | re.M,
        )

    saida = []
    pos = 0
    for m in padrao.finditer(codigo):
        saida.append(html.escape(codigo[pos:m.start()]))
        tipo = m.lastgroup
        texto = html.escape(m.group())

        if tipo == "id":
            bruto = m.group()
            if bruto in PALAVRAS_CHAVE:
                classe = "kw"
            elif bruto in TIPOS_JAVA:
                classe = "ty"
            elif bruto[0].isupper():
                # Convencao do Java: classe comeca com maiuscula. Pega os tipos
                # do proprio curso (Produto, ContaBancaria) sem precisar lista-los.
                classe = "ty"
            else:
                classe = None
            saida.append(f'<span class="{classe}">{texto}</span>' if classe else texto)
        elif tipo == "fn":
            bruto = m.group()
            classe = "kw" if bruto in PALAVRAS_CHAVE else "fn"
            saida.append(f'<span class="{classe}">{texto}</span>')
        elif tipo == "ann":
            # Anotacao desconhecida ainda e anotacao: destaca do mesmo jeito.
            saida.append(f'<span class="ann">{texto}</span>')
        else:
            saida.append(f'<span class="{tipo}">{texto}</span>')
        pos = m.end()

    saida.append(html.escape(codigo[pos:]))
    return "".join(saida)


# --------------------------------------------------------------------------
# Markdown -> HTML
# --------------------------------------------------------------------------

def inline(txt, escapar=True):
    """Converte marcacao inline. Codigo inline e protegido antes do resto."""
    guardados = []

    def guardar(m):
        guardados.append(m.group(1))
        return f"\x00{len(guardados) - 1}\x00"

    txt = re.sub(r"`([^`]+)`", guardar, txt)
    if escapar:
        txt = html.escape(txt)

    txt = re.sub(r"\*\*\*(.+?)\*\*\*", r"<strong><em>\1</em></strong>", txt)
    txt = re.sub(r"\*\*(.+?)\*\*", r"<strong>\1</strong>", txt)
    txt = re.sub(r"(?<![\w*])\*([^*\n]+)\*(?!\w)", r"<em>\1</em>", txt)
    txt = re.sub(r"~~(.+?)~~", r"<del>\1</del>", txt)

    # Nomes de arquivo do repositorio nao fazem sentido dentro do aplicativo.
    ROTULOS = {
        "roadmap.md": "Roteiro do curso",
        "README.md": "Início",
    }

    # Links: internos ao curso viram ancoras; externos abrem no navegador.
    def link(m):
        rotulo, alvo = m.group(1), m.group(2)
        rotulo = ROTULOS.get(rotulo, rotulo)
        if alvo.startswith("http"):
            return f'<a href="{alvo}" target="_blank" rel="noopener">{rotulo}</a>'
        alvo_norm = alvo.lstrip("./").replace("\\", "/")
        if alvo.startswith("#"):
            # Ancoras no estilo do GitHub ("#-modulo-2-orientacao-...") nao
            # existem aqui: cada modulo virou uma secao propria.
            # O prefixo tolerante nao e capricho: o GitHub gera a ancora a
            # partir do titulo, e titulos com emoji deixam residuos invisiveis
            # antes do "modulo" (o seletor de variacao do "⚠️", por exemplo).
            m_mod = re.match(r"#[^a-z]*m[oó]dulo[\s-]*(\d)", alvo, re.I)
            if m_mod:
                return f'<a href="#modulo{m_mod.group(1)}-teoria">{rotulo}</a>'
            return f'<a href="{alvo}">{rotulo}</a>'

        # Qualquer arquivo do projeto Spring aponta para a secao de codigo do
        # modulo 6. A pasta em si (link sem arquivo) e o README levam ao topo
        # dessa secao, que ja abre explicando como rodar o projeto.
        if alvo_norm.startswith(PROJETO_SPRING) or alvo_norm.startswith("projeto-spring"):
            relativo = re.sub(r"^(?:modulo6/)?projeto-spring/?", "", alvo_norm)
            if not relativo or relativo.lower() == "readme.md":
                return f'<a href="#modulo6-codigo">{rotulo}</a>'
            return f'<a href="#modulo6-codigo|{ancora_spring(relativo)}">{rotulo}</a>'

        m2 = re.match(r"(modulo\d)/(\w+)\.(md|java)", alvo_norm)
        if m2:
            pasta, arquivo, ext = m2.group(1), m2.group(2), m2.group(3)
            if ext == "java":
                # Os .java moram todos na secao de codigo do modulo, com uma
                # ancora por arquivo.
                return f'<a href="#{pasta}-codigo|{pasta}-{arquivo.lower()}">{rotulo}</a>'
            return f'<a href="#{pasta}-{arquivo}">{rotulo}</a>'

        if alvo_norm.startswith("modulo") and "/" not in alvo_norm.rstrip("/"):
            pasta = alvo_norm.rstrip("/")
            return f'<a href="#{pasta}-teoria">{rotulo}</a>'

        if alvo_norm in ("roadmap.md", "README.md"):
            return f'<a href="#{"roadmap" if "road" in alvo_norm else "inicio"}">{rotulo}</a>'
        return f"<span>{rotulo}</span>"

    txt = re.sub(r"\[([^\]]+)\]\(([^)]+)\)", link, txt)

    for i, c in enumerate(guardados):
        txt = txt.replace(f"\x00{i}\x00", f"<code>{html.escape(c)}</code>")
    return txt


def slug(texto):
    t = re.sub(r"[^\w\s-]", "", texto.lower(), flags=re.U).strip()
    # Emojis como "⚠️" sao um par: o simbolo mais um seletor de
    # variacao invisivel. A linha acima leva o simbolo, mas o seletor e
    # invisivel, escapa do filtro e produz um id comecando com lixo.
    t = t.replace("️", "").replace("‍", "").strip("-").strip()
    return re.sub(r"[\s]+", "-", t).strip("-")


def markdown(md, prefixo_id=""):
    """Converte um documento Markdown inteiro em HTML. Devolve (html, indice)."""
    linhas = md.split("\n")
    out = []
    indice = []
    i = 0
    pilha_lista = []  # ('ul'|'ol', indentacao)

    def fechar_listas(ate=-1):
        while pilha_lista and pilha_lista[-1][1] > ate:
            out.append(f"</{pilha_lista.pop()[0]}>")

    while i < len(linhas):
        ln = linhas[i]
        strip = ln.strip()

        # --- bloco de codigo cercado ---
        if strip.startswith("```"):
            lang = strip[3:].strip().lower()
            i += 1
            buf = []
            while i < len(linhas) and not linhas[i].strip().startswith("```"):
                buf.append(linhas[i])
                i += 1
            i += 1
            fechar_listas()
            codigo = "\n".join(buf)
            rotulo = {"java": "Java", "xml": "XML", "properties": "properties",
                      "bash": "terminal", "http": "HTTP"}.get(lang, "")
            tag_rotulo = f'<span class="lang">{rotulo}</span>' if rotulo else ""
            out.append(
                f'<div class="bloco-codigo">{tag_rotulo}'
                f'<button class="copiar" type="button">copiar</button>'
                f"<pre><code>{marcar_codigo(codigo, lang)}</code></pre></div>"
            )
            continue

        # --- citacao, com ou sem marcador de alerta do GitHub ---
        if strip.startswith(">"):
            m = re.match(r">\s*\[!(\w+)\]", strip)
            especie = m.group(1).lower() if m else "note"
            if m:
                i += 1  # consome a linha do marcador
            buf = []
            while i < len(linhas) and linhas[i].strip().startswith(">"):
                buf.append(re.sub(r"^\s*>\s?", "", linhas[i]))
                i += 1
            fechar_listas()
            titulos = {
                "note": "Nota", "tip": "Dica", "important": "Importante",
                "warning": "Atenção", "caution": "Cuidado",
            }
            # O corpo da citacao e Markdown completo: pode conter blocos de
            # codigo, listas e tabelas. Converte recursivamente.
            corpo, _ = markdown("\n".join(buf), prefixo_id)
            cabecalho = (
                f'<div class="alerta-tit">{titulos.get(especie, especie.title())}</div>'
                if m else ""
            )
            out.append(f'<div class="alerta {especie}">{cabecalho}{corpo}</div>')
            continue

        # --- tabela ---
        if strip.startswith("|") and i + 1 < len(linhas) and re.match(r"^\s*\|[\s:|-]+\|?\s*$", linhas[i + 1]):
            fechar_listas()
            cabecalho = [c.strip() for c in strip.strip("|").split("|")]
            sep = [c.strip() for c in linhas[i + 1].strip().strip("|").split("|")]
            alinhas = []
            for s in sep:
                if s.startswith(":") and s.endswith(":"):
                    alinhas.append(" style=\"text-align:center\"")
                elif s.endswith(":"):
                    alinhas.append(" style=\"text-align:right\"")
                else:
                    alinhas.append("")
            i += 2
            corpo = []
            while i < len(linhas) and linhas[i].strip().startswith("|"):
                corpo.append([c.strip() for c in linhas[i].strip().strip("|").split("|")])
                i += 1
            ths = "".join(
                f"<th{alinhas[j] if j < len(alinhas) else ''}>{inline(c)}</th>"
                for j, c in enumerate(cabecalho)
            )
            trs = []
            for linha in corpo:
                tds = "".join(
                    f"<td{alinhas[j] if j < len(alinhas) else ''}>{inline(c)}</td>"
                    for j, c in enumerate(linha)
                )
                trs.append(f"<tr>{tds}</tr>")
            out.append(
                f'<div class="rolagem-tabela"><table><thead><tr>{ths}</tr></thead>'
                f"<tbody>{''.join(trs)}</tbody></table></div>"
            )
            continue

        # --- cabecalho ---
        m = re.match(r"^(#{1,6})\s+(.*)$", strip)
        if m:
            fechar_listas()
            nivel = len(m.group(1))
            texto = m.group(2).strip()
            ident = prefixo_id + "-" + slug(texto) if prefixo_id else slug(texto)
            if nivel <= 3:
                indice.append({"n": nivel, "t": re.sub(r"<[^>]+>", "", inline(texto)), "id": ident})
            out.append(f'<h{nivel} id="{ident}">{inline(texto)}</h{nivel}>')
            i += 1
            continue

        # --- regua ---
        if re.match(r"^(---+|\*\*\*+|___+)$", strip):
            fechar_listas()
            out.append("<hr>")
            i += 1
            continue

        # --- itens de lista ---
        m = re.match(r"^(\s*)([*+-]|\d+\.)\s+(.*)$", ln)
        if m:
            recuo = len(m.group(1).expandtabs(4))
            ordenada = m.group(2)[0].isdigit()
            tag = "ol" if ordenada else "ul"
            while pilha_lista and pilha_lista[-1][1] > recuo:
                out.append(f"</{pilha_lista.pop()[0]}>")
            if not pilha_lista or pilha_lista[-1][1] < recuo:
                pilha_lista.append((tag, recuo))
                out.append(f"<{tag}>")
            elif pilha_lista[-1][0] != tag:
                out.append(f"</{pilha_lista.pop()[0]}>")
                pilha_lista.append((tag, recuo))
                out.append(f"<{tag}>")
            out.append(f"<li>{inline(m.group(3))}</li>")
            i += 1
            continue

        # --- linha em branco ---
        if not strip:
            i += 1
            continue

        # --- paragrafo ---
        fechar_listas()
        buf = [ln]
        i += 1
        while i < len(linhas) and linhas[i].strip() and not re.match(
            r"^(\s*)([*+-]|\d+\.)\s|^#{1,6}\s|^```|^\||^>|^(---+|\*\*\*+)$", linhas[i]
        ):
            buf.append(linhas[i])
            i += 1
        out.append(f"<p>{inline(' '.join(l.strip() for l in buf))}</p>")

    fechar_listas()
    return "\n".join(out), indice


def ler(caminho):
    with open(caminho, encoding="utf-8") as f:
        return f.read()


def ancora_spring(relativo):
    """Transforma 'src/main/java/.../Produto.java' numa ancora estavel."""
    return "spring-" + re.sub(r"[^\w]+", "-", relativo.lower()).strip("-")


def bloco_codigo(caminho, titulo, ident, lang="java", nota=""):
    """Renderiza um arquivo de codigo como uma secao."""
    codigo = ler(caminho)
    rotulo = {"java": "Java", "xml": "XML", "properties": "properties"}.get(lang, "")
    tag_rotulo = f'<span class="lang">{rotulo}</span>' if rotulo else ""
    linha_nota = f'<p class="nota-arquivo">{html.escape(nota)}</p>' if nota else ""
    return (
        f'<h3 id="{ident}">{html.escape(titulo)}</h3>'
        f"{linha_nota}"
        f'<div class="bloco-codigo">{tag_rotulo}'
        f'<button class="copiar" type="button">copiar</button>'
        f"<pre><code>{marcar_codigo(codigo, lang)}</code></pre></div>"
    )


def secao_codigo_modulo(base, pasta, rotulo):
    """Monta a secao de codigo dos modulos 1 a 5 (arquivos .java soltos)."""
    partes = []
    for prefixo, sufixo in (("Exemplo", ""), ("Exercicio", "  (com TODO)")):
        for n in (1, 2):
            nome = f"{prefixo}{n}.java"
            caminho = os.path.join(base, nome)
            if os.path.exists(caminho):
                ident = f"{pasta}-{prefixo.lower()}{n}"
                partes.append(bloco_codigo(caminho, nome + sufixo, ident))
    if not partes:
        return None
    cabecalho = (
        f"<h1>{rotulo}: Código-fonte</h1>"
        "<p>Os arquivos <code>ExemploN.java</code> estão prontos para ler e rodar. "
        "Os <code>ExercicioN.java</code> estão incompletos de propósito, marcados com "
        "<code>// TODO:</code>.</p>"
    )
    return cabecalho + "\n".join(partes)


def secao_projeto_spring(rotulo):
    """Monta a secao de codigo do modulo 6, que e um projeto Maven inteiro.

    Diferente dos outros modulos, aqui a ordem importa: os arquivos aparecem
    na sequencia em que uma requisicao os atravessa.
    """
    base = os.path.join(RAIZ, *PROJETO_SPRING.split("/"))
    if not os.path.isdir(base):
        return None

    partes = []
    for relativo, nota in ARQUIVOS_SPRING:
        caminho = os.path.join(base, *relativo.split("/"))
        if not os.path.exists(caminho):
            continue
        ext = relativo.rsplit(".", 1)[-1]
        lang = {"java": "java", "xml": "xml", "properties": "properties"}.get(ext, "")
        # O nome do arquivo basta como titulo; o caminho completo vira nota.
        nome = relativo.rsplit("/", 1)[-1]
        partes.append(bloco_codigo(caminho, nome, ancora_spring(relativo), lang,
                                   f"{relativo}  ·  {nota}"))

    if not partes:
        return None

    cabecalho = (
        f"<h1>{rotulo}: Código-fonte do projeto Spring</h1>"
        "<p>Este módulo não tem arquivos soltos: é um projeto Maven completo, em "
        "<code>modulo6/projeto-spring/</code>. Os arquivos abaixo estão na ordem em que "
        "uma requisição os atravessa: da configuração para a entidade, o repositório, "
        "o serviço e por fim o controlador.</p>"
    )

    # O README do projeto explica como rodar e lista os endpoints. E conteudo,
    # nao codigo, entao entra convertido, antes dos arquivos.
    leiame = os.path.join(base, "README.md")
    if os.path.exists(leiame):
        corpo, _ = markdown(ler(leiame), "modulo6-projeto")
        cabecalho += f'<div class="leiame-projeto">{corpo}</div><hr>'

    return cabecalho + "\n".join(partes)


def construir():
    secoes = []   # (id, titulo, html)
    navegacao = []  # estrutura da barra lateral

    # ---------- Inicio ----------
    corpo, _ = markdown(ler(os.path.join(RAIZ, "README.md")), "inicio")
    secoes.append(("inicio", "Início", corpo))
    navegacao.append({"id": "inicio", "t": "Início", "icone": "home", "filhos": []})

    # ---------- Roadmap ----------
    corpo, _ = markdown(ler(os.path.join(RAIZ, "roadmap.md")), "roadmap")
    secoes.append(("roadmap", "Roteiro", corpo))
    navegacao.append({"id": "roadmap", "t": "Roteiro do curso", "icone": "map", "filhos": []})

    # ---------- Modulos ----------
    for rotulo, tema, pasta in MODULOS:
        base = os.path.join(RAIZ, pasta)
        filhos = []

        for arquivo, nome_parte, icone in PARTES:
            caminho = os.path.join(base, arquivo)
            if not os.path.exists(caminho):
                continue
            ident = f"{pasta}-{arquivo.replace('.md', '')}"
            corpo, indice = markdown(ler(caminho), ident)
            secoes.append((ident, f"{rotulo}: {nome_parte}", corpo))
            filhos.append({"id": ident, "t": nome_parte, "icone": icone,
                           "sub": [x for x in indice if x["n"] == 2][:14]})

        # O modulo 6 e um projeto Maven; os demais tem .java soltos.
        if pasta == "modulo6":
            corpo = secao_projeto_spring(rotulo)
            nome_secao = "Projeto Spring"
        else:
            corpo = secao_codigo_modulo(base, pasta, rotulo)
            nome_secao = "Código-fonte"

        if corpo:
            ident = f"{pasta}-codigo"
            secoes.append((ident, f"{rotulo}: {nome_secao}", corpo))
            filhos.append({"id": ident, "t": nome_secao, "icone": "code", "sub": []})

        navegacao.append({"id": filhos[0]["id"] if filhos else "",
                          "t": rotulo, "sub_t": tema, "icone": "mod",
                          "filhos": filhos})

    return secoes, navegacao


if __name__ == "__main__":
    secoes, navegacao = construir()
    dados = {
        "secoes": [{"id": s[0], "titulo": s[1], "html": s[2]} for s in secoes],
        "nav": navegacao,
    }
    destino = os.path.join(os.path.dirname(os.path.abspath(__file__)), "conteudo.json")
    with open(destino, "w", encoding="utf-8") as f:
        json.dump(dados, f, ensure_ascii=False, separators=(",", ":"))
    total = sum(len(s[2]) for s in secoes)
    print(f"secoes: {len(secoes)}")
    print(f"html total: {total // 1024} KB")
    print(f"gravado em: {destino}")
