package analisador_sintatico;

import analisador_lexico.Token;
import analisador_semantico.Simbolo;
import arvore_sintatica_abstrata.Node;
import arvore_sintatica_abstrata.Tree;
import analisador_semantico.TabelaDeSimbolos;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    
    List<Token> tokens;
    Token token, tokenAnterior;
    List<String> tradutor = new ArrayList<>();
    StringBuilder codBuilder = new StringBuilder();
    TabelaDeSimbolos tabela = new TabelaDeSimbolos();
    String nomeForT, tipoVarForT, tipoValForT;
    List<Object> valores = new ArrayList<>();
    
    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public Tree main(){
        token = getNextToken();
        Node root = new Node("main");
        Tree tree = new Tree(root);
        tree.setRoot(root);
        codBuilder.append("package main\n").append("import \"fmt\"\n").
                append("import \"sort\"\n");
        bloco(root);
        if(token.tipo.equals("EOF")){
            System.out.println("");
            System.out.println("SINTATICAMENTE CORRETO!!!");
            System.out.println("");
            System.out.print(codBuilder);
            System.out.println("");
            tabela.imprimirTabela();
            System.out.println("");
            String codigoGo = codBuilder.toString();
            
            //---------------
            // Não sei direito como funciona, só coloquei pq queria testar e compilar o Go aqui mesmo.
//            try {
//                Files.write(Paths.get("gerado.go"), codigoGo.getBytes()); // Escreve o conteúdo no arquivo "gerado.go"
//                Process processo = Runtime.getRuntime().exec("go run gerado.go"); // Executa o código Go usando o terminal
//                processo.waitFor(); // Espera o processo terminar
//                new java.util.Scanner(processo.getInputStream()).useDelimiter("\\A") // Imprime a saída do processo
//                    .forEachRemaining(System.out::println);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
            //---------------
            
        }else{
            erro();
        }
        System.out.println("");
        System.out.println("");
        return tree;
    }
    
    public Token getNextToken(){
        if(tokens.size() > 0){
            tokenAnterior = token;
            return tokens.remove(0);
        }else{
            return null;
        }
    }
    
    private void erro(){
        System.out.println("token inválido: " + token.lexema);
    }
    
    // Aqui coloca todas as opções primárias de chamadas.
    public boolean opcao(Node root, String nome){
        if(token.lexema.equals("Inteiro") || token.lexema.equals("Decimal")||
                token.lexema.equals("Texto")){
            Node node = root.addNode(nome);
            if(declaracao(node)){
                return true;
            }
        }else if(token.tipo.equals("VARIAVEL")){
            Node node = root.addNode(nome);
            if(atribuicao(node)){
                return true;
            }
        }else if(token.lexema.equals("Quest")){
            Node node = root.addNode(nome);
            if(quest(node)){
                return true;
            }
        }else if(token.lexema.equals("Enlace")){
            Node node = root.addNode(nome);
            if(enlace(node)){
                return true;
            }
        }else if(token.lexema.equals("Quebra")){
            Node node = root.addNode(nome);
            if(quebra(node)){
                return true;
            }
        }else if(token.lexema.equals("Ciclo")){
            Node node = root.addNode(nome);
            if(ciclo(node)){
                return true;
            }
        }else if(token.lexema.equals("Destino")){
            Node node = root.addNode(nome);
            if(destino(node)){
                return true;
            }
        }else if(token.lexema.equals("Retorna")){
            Node node = root.addNode(nome);
            if(retorna(node)){
                return true;
            }
        }else if(token.lexema.equals("D")){
            Node node = root.addNode(nome);
            if(destinando(node)){
                return true;
            }
        }else if(token.lexema.equals("Inserir")){
            Node node = root.addNode(nome);
            if(inserir(node)){
                return true;
            }
        }else if(token.lexema.equals("Exibir")){
            Node node = root.addNode(nome);
            if(exibir(node)){
                return true;
            }
        }else if(token.lexema.equals("Conjunto")){
            Node node = root.addNode(nome);
            if(conjunto(node)){
                return true;
            }
        }else if(token.lexema.equals("Insere")){
            Node node = root.addNode(nome);
            if(insere(node)){
                return true;
            }
        }else if(token.lexema.equals("Remove")){
            Node node = root.addNode(nome);
            if(remove(node)){
                return true;
            }
        }else if(token.lexema.equals("Ordenar")){
            Node node = root.addNode(nome);
            if(ordenar(node)){
                return true;
            }
        }
        return false;
    }
    
    private String tipoVarTipoVar(String tipoVar){
        if(tipoVar.equals("Inteiro")){
            return "int";
        }else if(tipoVar.equals("Decimal")){
            return "float64";
        }else if(tipoVar.equals("Texto")){
            return "string";
        }
        return null;
    }
    
    //---------------
    //DECLARAÇÃO DE VARIÁVEL
    private boolean declaracao(Node node){
        Node declaracao = node.addNode("declaracao");
        if(tipoVar(declaracao)){
            tipoVarForT = tokenAnterior.lexema;
            tradutor.add(tipoVarTipoVar(tokenAnterior.lexema)); // já converte direto
            if(matchT("VARIAVEL", declaracao)){
                nomeForT = tokenAnterior.lexema;
                tradutor.add(tokenAnterior.lexema);
                if(token.lexema.equals("=")){
                    tradutor.add(token.lexema);
                    if(valoravel(declaracao)){
                        tipoValForT = tokenAnterior.tipo;
                        tradutor.add(tokenAnterior.lexema);
                        if(matchL(";", declaracao)){
                            tabela.inserirTabela(nomeForT, tipoVarForT, tipoValForT);
                            codBuilder.append("var ").append(tradutor.get(1) +
                                    " ").append(tradutor.get(0) + " ").
                                    append(tradutor.get(2) + " ").
                                    append(tradutor.get(3) + "\n");
                            tradutor.clear();
                            return true;
                        }
                    }
                }else if(matchL(";", declaracao)){
                    tabela.inserirTabela(nomeForT, tipoVarForT, null);
                    codBuilder.append("var ").append(tradutor.get(1) + " ").
                            append(tradutor.get(0) + "\n");
                    tradutor.clear();
                    return true;
                }
            }
        }
        return false;
    }
    private boolean tipoVar(Node node){
        Node tipoVar = node.addNode("tipoVar");
        if(matchL("Inteiro", tipoVar) || matchL("Decimal", tipoVar) ||
                matchL("Texto", tipoVar)){
            return true;
        }
        return false;
    }
    private boolean idt(Node node){
        Node idt = node.addNode("idt");
        if(matchT("NUM_INTEIRO", idt) || matchT("NUM_DECIMAL", idt) ||
                matchT("TEXTO", idt)){
            return true;
        }
        return false;
    }
    private boolean valoravel(Node node){
        Node valoravel = node.addNode("valoravel");
        if(matchL("=", valoravel) && idt(valoravel)){
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //ESTRUTURA CONDICIONAL
    private boolean quest(Node node){
        Node quest = node.addNode("quest");
        if(matchL("Quest", quest) && matchL("(", quest)){
            codBuilder.append("if ");
            if(requisito(quest)){
                for (String token : tradutor){
                    codBuilder.append(token + " ");
                }
                tradutor.clear();
                if(matchL(")", quest) && matchL("{", quest)){
                    codBuilder.append("{\n");
                    if(sn(quest) && matchL("}", quest)){
                        codBuilder.append("}");
                        if(token.lexema.equals("Request")){
                            if(request(quest)){
                                codBuilder.append("\n");
                                return true;
                            }
                        }
                        codBuilder.append("\n");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean requisito(Node node){
        Node requisito = node.addNode("requisito");
        if(matchT("VARIAVEL", requisito)){
            tradutor.add(tokenAnterior.lexema);
            if(matchT("COMP_OP", requisito)){
                tradutor.add(tokenAnterior.lexema);
                if(matchT("VARIAVEL", requisito) || idt(requisito)){
                    tradutor.add(tokenAnterior.lexema);
                    if(matchT("LOGI_OP", requisito)){
                        tradutor.add(tokenAnterior.lexema);
                        if(requisito(requisito)){
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    private boolean sn(Node node){
        Node sn = node.addNode("sn");
        if(si(sn)){
            if(token.lexema.equals("No")){
                if(no(sn)){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
    private boolean si(Node node){
        Node si = node.addNode("si");
        if(matchL("Si", si) && matchL("{", si) && bloco(si) && matchL("}", si)){
            return true;
        }
        return false;
    }
    private boolean no(Node node){
        Node no = node.addNode("no");
        if(matchL("No", no) && matchL("{", no)){
            codBuilder.append("} else {\n");
            if(bloco(no) && matchL("}", no)){
                return true;
            }
        }
        return false;
    }
    private boolean bloco(Node node){
        if(opcao(node, "bloco")){
            if(bloco(node)){ // se não for mais um bloco retorna falso!
                return true;
            }
        }else if(token.lexema.equals("}")){
            return true;
        }
        return false;
    }
    private boolean atribuicao(Node node){
        Node atribuicao = node.addNode("atribuicao");
        if(matchT("VARIAVEL", atribuicao)){
            nomeForT = tokenAnterior.lexema;
            tabela.existirTabela(nomeForT);
            tradutor.add(tokenAnterior.lexema);
            if(matchL("=", atribuicao)){
                tradutor.add(tokenAnterior.lexema);
                if(expressao(atribuicao)){
                    Simbolo dados = tabela.buscarTabela(nomeForT);
                    tabela.mesmoTipo(nomeForT, dados.getTipoVar(), valores);
                    if(matchL(";", atribuicao)){
                        for (String token : tradutor){
                            codBuilder.append(token + " ");
                        }
                        codBuilder.append("\n");
                        tradutor.clear();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean expressao(Node node){ // corrigir que fica aparecendo expressão toda hora
        Node expressao = node.addNode("expressao");
        if(matchT("VARIAVEL", expressao) || idt(expressao)){
            if(tokenAnterior.tipo.equals("VARIAVEL")){
                if(tabela.existirTabela(tokenAnterior.lexema)){
                    Simbolo var = tabela.buscarTabela(tokenAnterior.lexema);
                    valores.add(var.getTipoVal());
                }
            }else{
                valores.add(tokenAnterior.tipo);
            }
            tradutor.add(tokenAnterior.lexema);
            if(matchT("MATH_OP", expressao)){
                tradutor.add(tokenAnterior.lexema);
                if(expressao(expressao)){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
    private boolean request(Node node){
        Node request = node.addNode("request");
        if(matchL("Request", request) && matchL("(", request)){
            codBuilder.append(" else if ");
            if(requisito(request)){
                for (String token : tradutor){
                    codBuilder.append(token + " ");
                }
                tradutor.clear();
                if(matchL(")", request) && matchL("{", request)){
                    codBuilder.append("{\n");
                    if(sn(request) && matchL("}", request)){
                        codBuilder.append("}");
                        if(token.lexema.equals("Request")){
                            if(request(request)){
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }
    //---------------
    
    //---------------
    //ENLACE
    private boolean enlace(Node node){
        Node enlace = node.addNode("enlace");
        if(matchL("Enlace", enlace) && matchL("(", enlace)){
            codBuilder.append("for ");
            if(rr(enlace) && matchL(")", enlace)){
                for (String token : tradutor){
                    codBuilder.append(token + " ");
                }
                tradutor.clear();
                codBuilder.append("{\n");
                if(matchL("{", enlace) && bloco(enlace) 
                        && matchL("}", enlace)){
                    codBuilder.append("}\n");
                    return true;
                }
            }
        }
        return false;
    }
    private boolean rr(Node node){
        Node rr = node.addNode("rr");
        if(token.tipo.equals("VARIAVEL")){
            if(requisito(rr)){
                return true;
            }
        }else if(token.lexema.equals("Roda")){
            if(matchL("Roda", rr)){
                return true;
            }
        }
        return false;
    }
    private boolean quebra(Node node){
        Node quebra = node.addNode("quebra");
        if(matchL("Quebra", quebra) && matchL(";", quebra)){
            codBuilder.append("break\n");
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //CICLO
    private boolean ciclo(Node node){
        Node ciclo = node.addNode("ciclo");
        if(matchL("Ciclo", ciclo) && matchL("(", ciclo) && declaracao(ciclo)){
            codBuilder.append("for ; ");
            if(requisito(ciclo) && matchL(";", ciclo)){
                for (String token : tradutor){
                    codBuilder.append(token + " ");
                }
                codBuilder.append("; ");
                tradutor.clear();
                if(atualiza(ciclo) && matchL(")", ciclo) && matchL("{", ciclo)){
                    codBuilder.append("{\n");
                    if(bloco(ciclo) && matchL("}", ciclo)){
                        codBuilder.append("}\n");
                    }
                }
            }
        }
        return false;
    }
    private boolean atualiza(Node node){
        Node atualiza = node.addNode("atualiza");
        if(matchL("Atualiza", atualiza) && matchL("(", atualiza) &&
                matchT("VARIAVEL", atualiza)){
            tradutor.add(tokenAnterior.lexema);
            if(matchT("MATH_OP", atualiza)){
                tradutor.add(tokenAnterior.lexema);
                if(idt(atualiza)){
                    tradutor.add(tokenAnterior.lexema);
                    if(matchL(")", atualiza)){
                        codBuilder.append(tradutor.get(0) + " = ");
                        for (String token : tradutor){
                            codBuilder.append(token + " ");
                        }
                        tradutor.clear();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //---------------
    
    //---------------
    //DESTINO
    private boolean destino(Node node){
        Node destino = node.addNode("destino");
        if(matchL("Destino", destino) && matchT("VARIAVEL", destino)){
            codBuilder.append("func ");
            codBuilder.append(tokenAnterior.lexema);
            if(tokenAnterior.lexema.equals("main")){
                if(matchL("(", destino)){
                    codBuilder.append("(");
                    if(parametro(destino)){
                        if(matchL(")", destino)){
                            codBuilder.append(")");
                            if(matchL("{", destino)){
                                codBuilder.append(" {\n").
                                        append("fmt.Println(\" \")\n"). // PARA EVITAR ERRO COM A IMPORÇÃO
                                        append("sort.Ints([]int{1, 2, 3})\n"); // PARA EVITAR ERRO COM A IMPORÇÃO
                                if(bloco(destino) && matchL("}", destino)){
                                    codBuilder.append("}\n");
                                    return true;
                                }
                            }
                        }
                    }
                }
            }else{
                if(matchL("(", destino)){
                    codBuilder.append("(");
                    if(parametro(destino)){
                        if(matchL(")", destino)){
                            codBuilder.append(") ");
                            codBuilder.append("any");
                            if(matchL("{", destino)){
                                codBuilder.append(" {\n");
                                if(bloco(destino) && matchL("}", destino)){
                                    codBuilder.append("}\n");
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean parametro(Node node){
        if(simpleTipoVar()){
            Node parametro = node.addNode("parametro");
            if(tipoVar(parametro)){
                tradutor.add(tokenAnterior.lexema);
                if(matchT("VARIAVEL", parametro)){
                    tradutor.add(tokenAnterior.lexema);
                    codBuilder.append(tradutor.get(1) + " ");
                    codBuilder.append(tipoVarTipoVar(tradutor.get(0)));
                    tradutor.clear();
                    if(matchL(",", parametro)){
                        if(parametro(parametro)){
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    private boolean simpleTipoVar(){
        if(token.lexema.equals("Inteiro") || token.lexema.equals("Decimal") ||
                token.lexema.equals("Texto")){
            return true;
        }
        return false;
    }
    private boolean retorna(Node node){
        Node retorna = node.addNode("retorna");
        if(matchL("Retorna", retorna)){
            codBuilder.append("return ");
            if(expressao(retorna) || destinando(retorna)){
                if(matchL(";", retorna)){
                    for (String token : tradutor){
                        codBuilder.append(token + " ");
                    }
                    codBuilder.append("\n");
                    tradutor.clear();
                    return true;
                }
            }
        }
        return false;
    }
    private boolean destinando(Node node){
        Node destinando = node.addNode("destinando");
        if(matchL("D", destinando) && matchT("VARIAVEL", destinando)){
            codBuilder.append(tokenAnterior.lexema);
            if(matchL("(", destinando)){
                codBuilder.append(tokenAnterior.lexema);
                if(pd(destinando, "parametrizando") && matchL(")", destinando)
                        && matchL(";", destinando)){
                    codBuilder.append(")\n");
                }
            }
        }
        return false;
    }
    private boolean pd(Node node, String qual){ // assim serve para o Destino e o Conjunto
        if(token.tipo.equals("VARIAVEL") || token.tipo.equals("NUM_INTEIRO") ||
                token.tipo.equals("NUM_DECIMAL") || token.tipo.equals("TEXTO")){
            Node pdpd = node.addNode(qual);
            if(matchT("VARIAVEL", pdpd) || idt(pdpd)){
                codBuilder.append(tokenAnterior.lexema);
                if(matchL(",", pdpd)){
                    codBuilder.append(tokenAnterior.lexema);
                    if(pd(pdpd, qual)){
                        return true;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }
    //---------------
    
    //---------------
    //INSERIR
    private boolean inserir(Node node){
        Node inserir = node.addNode("inserir");
        if(matchL("Inserir", inserir) && matchL("(", inserir)){
            if(matchT("VARIAVEL", inserir)){
                tradutor.add(tokenAnterior.lexema);
                if(matchL(")", inserir) && matchL(";", inserir)){
                    codBuilder.append("fmt.Scanln(&").append(tradutor.get(0)).
                            append(")\n");
                    tradutor.clear();
                    return true;
                }
            }
        }
        return false;
    }
    //---------------
    
    //---------------
    //EXIBIR
    private boolean exibir(Node node){
        Node exibir = node.addNode("exibir");
        if(matchL("Exibir", exibir) && matchL("(", exibir)){
            if(matchT("VARIAVEL", exibir) || idt(exibir)){
                tradutor.add(tokenAnterior.lexema);
                if(matchL(")", exibir) && matchL(";", exibir)){
                    codBuilder.append("fmt.Println(").append(tradutor.get(0)).
                            append(")\n");
                    tradutor.clear();
                    return true;
                }
            }
        }
        return false;
    }
    //---------------
    
    //---------------
    //CONJUNTO
    private boolean conjunto(Node node){
        Node conjunto = node.addNode("conjunto");
        if(matchL("Conjunto", conjunto) && matchL("(", conjunto) &&
                tipoVar(conjunto)){
            tradutor.add(tokenAnterior.lexema);
            if(matchL(";", conjunto) && matchT("VARIAVEL", conjunto)){
                tradutor.add(tokenAnterior.lexema);
                if(matchL(";", conjunto) && tamanho(conjunto)){
                    if(tokenAnterior.lexema.equals("Dinamico")){
                        codBuilder.append(tradutor.get(1)).append(" := []").
                                append(tipoVarTipoVar(tradutor.get(0)) + "{");
                        if(matchL(")", conjunto) && matchL("=", conjunto) &&
                            matchL("[", conjunto) && pd(conjunto, "dentro") &&
                            matchL("]", conjunto) &&matchL(";", conjunto)){
                            codBuilder.append("}\n");
                            tradutor.clear();
                            return true;
                        }
                    }else{
                        tradutor.add(tokenAnterior.lexema);
                        codBuilder.append(tradutor.get(1)).append(" := [").
                                append(tradutor.get(2) + "]").
                                append(tipoVarTipoVar(tradutor.get(0)) + "{");
                        if(matchL(")", conjunto) && matchL("=", conjunto) &&
                                matchL("[", conjunto) && pd(conjunto, "dentro")
                                && matchL("]", conjunto) &&matchL(";", conjunto)){
                            codBuilder.append("}\n");
                            tradutor.clear();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean tamanho(Node node){
        Node tamanho = node.addNode("tamanho");
        if(matchL("Dinamico", tamanho) || matchT("NUM_INTEIRO", tamanho)){
            return true;
        }
        return false;
    }
    private boolean insere(Node node){
        Node insere = node.addNode("insere");
        if(matchL("Insere", insere) && matchL("(", insere) &&
                matchT("VARIAVEL", insere)){
            tradutor.add(tokenAnterior.lexema);
            if(matchL(";", insere) && index(insere)){
                if(tokenAnterior.lexema.equals("Final")){
                    if(matchL(")", insere) && matchL("=", insere) && idt(insere)){
                        tradutor.add(tokenAnterior.lexema);
                        if(matchL(";", insere)){
                            codBuilder.append(tradutor.get(0)).
                                    append(" = append(").
                                    append(tradutor.get(0) + ", ").
                                    append(tradutor.get(1) + ")");
                            codBuilder.append("\n");
                            tradutor.clear();
                            return true;
                        }
                    }
                }else if(tokenAnterior.lexema.equals("Inicio")){
                    if(matchL(")", insere) && matchL("=", insere) && idt(insere)){
                        tradutor.add(tokenAnterior.lexema);
                        if(tokenAnterior.tipo.equals("NUM_INTEIRO")){
                            tradutor.add("int");
                        }else if(tokenAnterior.tipo.equals("NUM_DECIMAL")){
                            tradutor.add("float64");
                        }else if(tokenAnterior.tipo.equals("TEXTO")){
                            tradutor.add("string");
                        }
                        if(matchL(";", insere)){
                            codBuilder.append(tradutor.get(0) + " = append([]" +
                                    tradutor.get(2) + "{" + tradutor.get(1) +
                                    "}, " + tradutor.get(0) + "...)");
                            codBuilder.append("\n");
                            tradutor.clear();
                            return true;
                        }
                    }
                }else{
                    tradutor.add(tokenAnterior.lexema);
                    if(matchL(")", insere) && matchL("=", insere) && idt(insere)){
                        tradutor.add(tokenAnterior.lexema);
                        if(matchL(";", insere)){
                            codBuilder.append(tradutor.get(0) + "[" +
                                    tradutor.get(1) + "] = " + tradutor.get(2));
                            codBuilder.append("\n");
                            tradutor.clear();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean index(Node node){
        Node index = node.addNode("index");
        if(matchL("Inicio", index) || matchL("Final", index) ||
                matchT("NUM_INTEIRO", index)){
            return true;
        }
        return false;
    }
    private boolean remove(Node node){
        Node remove = node.addNode("remove");
        if(matchL("Remove", remove) && matchL("(", remove) &&
                matchT("VARIAVEL", remove)){
            tradutor.add(tokenAnterior.lexema);
            if(matchL(";", remove) && index(remove)){
                if(tokenAnterior.lexema.equals("Inicio")){
                    if(matchL(")", remove) && matchL(";", remove)){
                        codBuilder.append(tradutor.get(0) + " = " +
                                tradutor.get(0) + "[1:]");
                        codBuilder.append("\n");
                        tradutor.clear();
                        return true;
                    }
                }else if(tokenAnterior.lexema.equals("Final")){
                    if(matchL(")", remove) && matchL(";", remove)){
                        codBuilder.append(tradutor.get(0) + " = " +
                                tradutor.get(0) + "[:len(" + tradutor.get(0) +
                                ")-1]");
                        codBuilder.append("\n");
                        tradutor.clear();
                        return true;
                    }
                }else{
                    tradutor.add(tokenAnterior.lexema);
                    if(matchL(")", remove) && matchL(";", remove)){
                        codBuilder.append(tradutor.get(0) + " = " + "append(" +
                                tradutor.get(0) + "[:" + tradutor.get(1) + "], " + 
                                tradutor.get(0) + "[" + tradutor.get(1) + 
                                "+1:]...)");
                        codBuilder.append("\n");
                        tradutor.clear();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean ordenar(Node node){
        Node ordenar = node.addNode("ordenar");
        if(matchL("Ordenar", ordenar) && matchL("(", ordenar) &&
                tipoVar(ordenar)){ // NOVO!!! ARRUMAR NO GITHUB DEPOIS
            tradutor.add(tokenAnterior.lexema);
            if(matchL(";", ordenar) && matchT("VARIAVEL", ordenar)){
                tradutor.add(tokenAnterior.lexema);
                if(matchL(";", ordenar) && tipoORD(ordenar)){
                    if(tokenAnterior.lexema.equals("MenorTo")){
                        if(matchL(")", ordenar) && matchL(";", ordenar)){
                            if(tradutor.get(0).equals("Inteiro")){
                                codBuilder.append("sort.Ints(" + tradutor.get(1) + 
                                        ")");
                            }else if(tradutor.get(0).equals("Decimal")){
                                codBuilder.append("sort.Float64s(" + tradutor.get(1) + 
                                        ")");
                            }else if(tradutor.get(0).equals("Texto")){
                                codBuilder.append("sort.Strings(" + tradutor.get(1) + 
                                        ")");
                            }
                            codBuilder.append("\n");
                            tradutor.clear();
                            return true;
                        }
                    }else if(tokenAnterior.lexema.equals("MaiorTo")){
                        if(matchL(")", ordenar) && matchL(";", ordenar)){
                            if(tradutor.get(0).equals("Inteiro")){
                                codBuilder.append("sort.Sort(sort.Reverse(sort.IntSlice(" + 
                                        tradutor.get(1) + ")))");
                            }else if(tradutor.get(0).equals("Decimal")){
                                codBuilder.append("sort.Sort(sort.Reverse(sort.Float64Slice(" + 
                                        tradutor.get(1) + ")))");
                            }else if(tradutor.get(0).equals("Texto")){
                                codBuilder.append("sort.Sort(sort.Reverse(sort.StringSlice(" + 
                                        tradutor.get(1) + ")))");
                            }
                            codBuilder.append("\n");
                            tradutor.clear();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean tipoORD(Node node){
        Node tipoORD = node.addNode("tipoORD");
        if(matchL("MaiorTo", tipoORD) || matchL("MenorTo", tipoORD)){
            return true;
        }
        return false;
    }
    //---------------
    
    private boolean matchL(String palavra, Node node){
        if(token.lexema.equals(palavra)){
            node.addNode(token.lexema);
            token = getNextToken();
            return true;
        }
        return false;
    }
    
    private boolean matchT(String palavra, Node node){
        if(token.tipo.equals(palavra)){
            node.addNode(token.lexema);
            token = getNextToken();
            return true;
        }
        return false;
    }
    
}