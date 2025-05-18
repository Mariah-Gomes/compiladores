package traducao_go;

import arvore_sintatica_abstrata.Node;
import java.util.List;

public class GoCodeGenerator {

    public String gerarCodigoCompleto(Node nodeRaiz) {
        StringBuilder codigo = new StringBuilder();
        codigo.append("package main\n\n");
        codigo.append("import \"fmt\"\n\n");
        codigo.append("func main() {\n");

        String corpo = gerarCodigoParaNode(nodeRaiz);
        for (String linha : corpo.split("\n")) {
            if (!linha.trim().isEmpty()) {
                codigo.append("    ").append(linha).append("\n");
            }
        }

        codigo.append("}\n");
        return codigo.toString();
    }

    public String gerarCodigoParaNode(Node node) {
        if (node == null) return "";

        String nome = node.nome.toLowerCase();

        switch (nome) {
            case "main":
            case "bloco":
            case "sn":
                return gerarCodigoFilhos(node);

            case "declaracao":
                return gerarDeclaracao(node);

            case "atribuicao":
                return gerarAtribuicao(node);

            case "exibir":
                return gerarPrint(node);

            case "inserir":
                return gerarInput(node);

            case "quest":
                return gerarQuest(node);

            case "request":
                return gerarRequest(node);

            case "no":
                return gerarElse(node);

            case "expressao":
                return gerarExpressao(node);

            default:
                return "";
        }
    }

    private String gerarDeclaracao(Node node) {
        Node tipo = node.nodes.get(0);
        Node idt = node.nodes.get(1);
        String tipoGo = converterTipo(tipo.nome);
        return "var " + idt.nome + " " + tipoGo + "\n";
    }

    private String gerarAtribuicao(Node node) {
        Node idt = node.nodes.get(0);
        Node expr = node.nodes.get(2);
        return idt.nome + " = " + gerarExpressao(expr) + "\n";
    }

    private String gerarInput(Node node) {
        Node idt = node.nodes.get(2);
        return "fmt.Scan(&" + idt.nome + ")\n";
    }

    private String gerarPrint(Node node) {
        StringBuilder sb = new StringBuilder();
        for (Node filho : node.nodes) {
            if (!filho.nome.equalsIgnoreCase("exibir") &&
                !filho.nome.equals("(") && !filho.nome.equals(")") && !filho.nome.equals(";")) {
                sb.append("fmt.Print(").append(filho.nome).append(")\n");
            }
        }
        return sb.toString();
    }

    private String gerarQuest(Node node) {
        Node cond = node.nodes.get(2);
        Node bloco = node.nodes.get(5);

        StringBuilder sb = new StringBuilder();
        sb.append("if ").append(gerarRequisito(cond)).append(" {\n");
        sb.append(indent(gerarCodigoParaNode(bloco)));
        sb.append("}\n");

        for (int i = 6; i < node.nodes.size(); i++) {
            Node seg = node.nodes.get(i);
            if (seg.nome.equalsIgnoreCase("request")) sb.append(gerarRequest(seg));
            else if (seg.nome.equalsIgnoreCase("no")) sb.append(gerarElse(seg));
        }

        return sb.toString();
    }

    private String gerarRequest(Node node) {
        Node cond = node.nodes.get(2);
        Node bloco = node.nodes.get(5);

        StringBuilder sb = new StringBuilder();
        sb.append("else if ").append(gerarRequisito(cond)).append(" {\n");
        sb.append(indent(gerarCodigoParaNode(bloco)));
        sb.append("}\n");

        for (int i = 6; i < node.nodes.size(); i++) {
            Node seg = node.nodes.get(i);
            if (seg.nome.equalsIgnoreCase("request")) sb.append(gerarRequest(seg));
            else if (seg.nome.equalsIgnoreCase("no")) sb.append(gerarElse(seg));
        }

        return sb.toString();
    }

    private String gerarElse(Node node) {
        Node bloco = node.nodes.get(2); // else → "no" "{" bloco "}"
        return "else {\n" + indent(gerarCodigoParaNode(bloco)) + "}\n";
    }

    private String gerarRequisito(Node node) {
        // var1 operador var2
        String v1 = node.nodes.get(0).nome;
        String op = node.nodes.get(1).nome;
        String v2 = node.nodes.get(2).nome;
        return v1 + " " + op + " " + v2;
    }

    private String gerarExpressao(Node node) {
        StringBuilder sb = new StringBuilder();
        for (Node filho : node.nodes) {
            if (filho.nome.equals("expressao")) sb.append(gerarExpressao(filho));
            else sb.append(filho.nome).append(" ");
        }
        return sb.toString().trim();
    }

    private String gerarCodigoFilhos(Node node) {
        StringBuilder sb = new StringBuilder();
        for (Node filho : node.nodes) {
            sb.append(gerarCodigoParaNode(filho));
        }
        return sb.toString();
    }

    private String indent(String bloco) {
        StringBuilder sb = new StringBuilder();
        for (String linha : bloco.split("\n")) {
            if (!linha.trim().isEmpty()) {
                sb.append("    ").append(linha).append("\n");
            }
        }
        return sb.toString();
    }

    private String converterTipo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "inteiro": return "int";
            case "real": return "float64";
            case "texto": return "string";
            case "booleano": return "bool";
            default: return "interface{}";
        }
    }
}
