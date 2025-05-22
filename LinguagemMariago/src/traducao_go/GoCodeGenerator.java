package traducao_go;

import arvore_sintatica_abstrata.Node;

public class GoCodeGenerator {

    public String gerarCodigoCompleto(Node nodeRaiz) {
        StringBuilder codigo = new StringBuilder();

        codigo.append("package main\n\n");
        codigo.append("func main() {\n");

        // gera o corpo do main com base na AST
        String corpo = gerarCodigoParaNode(nodeRaiz);
        if (corpo.trim().isEmpty()) {
            codigo.append("    // Nenhum comando gerado\n");
        } else {
            for (String linha : corpo.split("\n")) {
                codigo.append("    ").append(linha).append("\n");
            }
        }

        codigo.append("}\n");

        return codigo.toString();
    }

    public String gerarCodigoParaNode(Node node) {
        String nome = node.nome;

        switch (nome) {
            case "main":
            case "bloco":
                return gerarCodigoFilhos(node);

            case "atribuicao":
                return gerarAtribuicao(node);

            case "expressao":
                return gerarExpressao(node);

            case "declaracao":
                return gerarDeclaracao(node);
            
            case "inserir":
                return gerarInput(node); 
              
            case "exibir":
                return gerarPrint(node);
                
            case "requisito":
                return gerarRequisito(node);
                
            case "quest":
                return gerarQuest(node);
                
            case "request":
                return gerarRequest(node);
                
            default:
                return "";
        }
    }

    private String gerarAtribuicao(Node node) {
        StringBuilder codigo = new StringBuilder();        
        Node idt = node.nodes.get(0);
        Node expressao = node.nodes.get(2);
        codigo.append(idt.nome).append(" = ").
                append(gerarCodigoParaNode(expressao)).append("\n");
        return codigo.toString();
    }
    
    private String gerarInput(Node node){
        StringBuilder codigo = new StringBuilder();
        Node variavel = node.nodes.get(2);
        codigo.append("fmt.Scan(&").append(variavel.nome).append(")\n");
        return codigo.toString();
    }
    
    private String gerarPrint(Node node) {
        StringBuilder codigo = new StringBuilder();
        // Encontra o que está entre os parênteses
        for (Node filho : node.nodes) {
            if (filho.nome.equals("idt")) {
                // É um idt com número ou nome
                if (!filho.nodes.isEmpty()) {
                    codigo.append("fmt.Print(").append(filho.nodes.get(0).nome).append(")\n");
                }
                
            } else if (!filho.nome.equals("Exibir") && !filho.nome.equals("(") && !filho.nome.equals(")") && !filho.nome.equals(";")) {
                // Caso seja só uma variável como "a"
               codigo.append("fmt.Print(").append(filho.nome).append(")\n");
            }
        }
        return codigo.toString();
    }
    
    private String gerarDeclaracao(Node node) {
        StringBuilder codigo = new StringBuilder();

        if (node.nodes.size() == 3) {
            Node tipoVar = node.nodes.get(0);
            Node variavel = node.nodes.get(1);

            // Pega o tipo diretamente, assumindo que tipoVar tem filhos
            String tipo = converterTipo(tipoVar.nodes.get(0).nome);

            codigo.append("var ").append(variavel.nome).append(" ").append(tipo).append("\n");

        } else if (node.nodes.size() == 4) {
            Node tipoVar = node.nodes.get(0);
            Node variavel = node.nodes.get(1);
            Node valoravel = node.nodes.get(2);

            String valor = valoravel.nodes.get(1).nodes.get(0).nome;
            String tipo = converterTipo(tipoVar.nodes.get(0).nome);

            codigo.append("var ")
                  .append(variavel.nome)
                  .append(" ")
                  .append(tipo)
                  .append(" = ")
                  .append(valor)
                  .append("\n");
        }

        return codigo.toString();
    }

    private String gerarExpressao(Node node) {
        StringBuilder codigo = new StringBuilder();

        for (Node filho : node.nodes) {
            switch (filho.nome) {
                case "idt":
                    if (!filho.nodes.isEmpty()) {
                        codigo.append(filho.nodes.get(0).nome);
                    } else {
                        codigo.append(filho.nome);
                    }
                    break;

                case "expressao":
                    codigo.append(gerarExpressao(filho));
                    break;

                case "+": case "-": case "*": case "/": case "%":
                    codigo.append(" ").append(filho.nome).append(" ");
                    break;

                default:
                    codigo.append(filho.nome);
            }
        }

        return codigo.toString();
    }

    private String gerarRequisito(Node node) {
        StringBuilder condicao = new StringBuilder();

        String var1 = node.nodes.get(0).nome;
        String operador = node.nodes.get(1).nome;

        Node rouIdtNode = node.nodes.get(2);
        String var2;
        if (!rouIdtNode.nodes.isEmpty()) {
            var2 = rouIdtNode.nodes.get(0).nome;  // Ex: NUM_INTEIRO ou TEXTO
        } else {
            var2 = rouIdtNode.nome;               // Ex: VARIAVEL
        }

        condicao.append(var1).append(" ").append(operador).append(" ").append(var2);

        // Verifica continuação lógica
        if (node.nodes.size() > 3) {
            Node cont = node.nodes.get(3);
            if (!cont.nome.equals("e")) {  // caso tenha um LOGI_OP e mais uma requisição
                String logico = cont.nodes.get(0).nome;  // ex: &&
                Node outroRequisito = cont.nodes.get(1);
                condicao.append(" ").append(logico).append(" ")
                        .append(gerarRequisito(outroRequisito));
            }
        }
        return condicao.toString();
    }
    
    private String gerarQuest(Node node) {
        StringBuilder codigo = new StringBuilder();

        Node requisitoNode = node.nodes.get(2);
        String condicao = gerarRequisito(requisitoNode);

        Node snNode = node.nodes.get(5);
        String corpoIf = gerarCodigoParaNode(snNode);

        codigo.append("if ").append(condicao).append(" {\n");
        for (String linha : corpoIf.split("\n")) {
            codigo.append("    ").append(linha).append("\n");
        }
        codigo.append("}\n");

        // Trata o request (pode ser um else if ou else)
        Node requestNode = node.nodes.get(6);
        codigo.append(gerarRequest(requestNode));

        return codigo.toString();
    }

    private String gerarRequest(Node node) {
    StringBuilder codigo = new StringBuilder();

    if (node.nome.equals("request") && !node.nodes.isEmpty()) {
        if (node.nodes.get(0).nome.equals("Request")) {
            Node reqRequisito = node.nodes.get(2);
            String condicao = gerarRequisito(reqRequisito);

            Node snNode = node.nodes.get(5);
            String corpo = gerarCodigoParaNode(snNode);

            codigo.append("else if ").append(condicao).append(" {\n");
            for (String linha : corpo.split("\n")) {
                codigo.append("    ").append(linha).append("\n");
            }
            codigo.append("}\n");

            // recursivo: pode ter outro request
            Node proximoRequest = node.nodes.get(6);
            codigo.append(gerarRequest(proximoRequest));
        } else if (node.nodes.get(0).nome.equals("No")) {
            // else simples
            Node blocoNode = node.nodes.get(2);
            String corpo = gerarCodigoParaNode(blocoNode);

            codigo.append("else {\n");
            for (String linha : corpo.split("\n")) {
                codigo.append("    ").append(linha).append("\n");
            }
            codigo.append("}\n");
        }
    }

    return codigo.toString();
}

    private String gerarCodigoFilhos(Node node) {
        StringBuilder codigo = new StringBuilder();
        for (Node filho : node.nodes) {
            codigo.append(gerarCodigoParaNode(filho));
        }
        return codigo.toString();
    }

    private String converterTipo(String tipo) {
        switch (tipo) {
            case "Inteiro":
                return "int";
            case "Decimal":
                return "float64";
            case "Texto":
                return "string";
            // Adicione mais casos conforme precisar
            default:
                return "int"; // padrão
        }
    }
}
