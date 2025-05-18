package analisador_sintatico;

import analisador_lexico.Token;
import arvore_sintatica_abstrata.Node;
import arvore_sintatica_abstrata.Tree;
import analisador_semantico.TabelaDeSimbolos;
import java.util.List;

public class Parser {
    
    List<Token> tokens;
    Token token;
    
    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public Tree main(){
        token = getNextToken();
        Node root = new Node("main");
        Tree tree = new Tree(root);
        tree.setRoot(root);
        bloco(root);
        if(token.tipo.equals("EOF")){
            System.out.println("SINTATICAMENTE CORRETO!!!");
        }else{
            erro();
        }
        return tree;
    }
    
    public Token getNextToken(){
        if(tokens.size() > 0){
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
    
    //---------------
    //DECLARAÇÃO DE VARIÁVEL
    private boolean declaracao(Node node){
        Node declaracao = node.addNode("declaracao");
        if(tipoVar(declaracao) && matchT("VARIAVEL", declaracao)){
            if(token.lexema.equals("=")){
                if(valoravel(declaracao)){
                    if(matchL(";", declaracao)){
                        return true;
                    }
                }
            }else if(matchL(";", declaracao)){
                return true;
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
        if(matchL("Quest", quest) && matchL("(", quest) && requisito(quest) &&
                matchL(")", quest) && matchL("{", quest) && sn(quest) &&
                matchL("}", quest)){
            if(token.lexema.equals("Request")){
                if(request(quest)){
                return true;
                }
            }
        }
        return false;
    }
    private boolean requisito(Node node){
        Node requisito = node.addNode("requisito");
        if(matchT("VARIAVEL", requisito) && matchT("COMP_OP", requisito) &&
                (matchT("VARIAVEL", requisito) || idt(requisito))){
            if(matchT("LOGI_OP", requisito)){
                if(requisito(requisito)){
                    return true;
                }
                return false;
            }
            return true;
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
        if(matchL("No", no) && matchL("{", no) && bloco(no) && matchL("}", no)){
            return true;
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
        if(matchT("VARIAVEL", atribuicao) && matchL("=", atribuicao) &&
                expressao(atribuicao) && matchL(";", atribuicao)){
            return true;
        }
        return false;
    }
    private boolean expressao(Node node){ // corrigir que fica aparecendo expressão toda hora
        Node expressao = node.addNode("expressao");
        if(matchT("VARIAVEL", expressao) || idt(expressao)){
            if(matchT("MATH_OP", expressao)){
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
        if(matchL("Request", request)){
            if(matchL("(", request) && requisito(request) &&
                    matchL(")", request) && matchL("{", request) &&
                    sn(request) && matchL("}", request)){
                if(token.lexema.equals("Request")){
                    if(request(request)){
                        return true;
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
        if(matchL("Enlace", enlace) && matchL("(", enlace) && rr(enlace) &&
                matchL(")", enlace) && matchL("{", enlace) && bloco(enlace) &&
                matchL("}", enlace)){
            return true;
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
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //CICLO
    private boolean ciclo(Node node){
        Node ciclo = node.addNode("ciclo");
        if(matchL("Ciclo", ciclo) && matchL("(", ciclo) && declaracao(ciclo) &&
                requisito(ciclo) && matchL(";", ciclo) && atualiza(ciclo) &&
                matchL(")", ciclo) && matchL("{", ciclo) && bloco(ciclo) &&
                matchL("}", ciclo)){
            return true;
        }
        return false;
    }
    private boolean atualiza(Node node){
        Node atualiza = node.addNode("atualiza");
        if(matchL("Atualiza", atualiza) && matchL("(", atualiza) &&
                matchT("VARIAVEL", atualiza) && matchT("MATH_OP", atualiza) &&
                idt(atualiza) && matchL(")", atualiza)){
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //DESTINO
    private boolean destino(Node node){
        Node destino = node.addNode("destino");
        if(matchL("Destino", destino) && matchT("VARIAVEL", destino) &&
                matchL("(", destino) && parametro(destino) && 
                matchL(")", destino) && matchL("{", destino) && bloco(destino)
                && matchL("}", destino)){
            return true;
        }
        return false;
    }
    private boolean parametro(Node node){
        if(simpleTipoVar()){
            Node parametro = node.addNode("parametro");
            if(tipoVar(parametro) && matchT("VARIAVEL", parametro)){
                if(matchL(",", parametro)){
                    if(parametro(parametro)){
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
    private boolean simpleTipoVar(){
        if(token.lexema.equals("Inteiro") || token.lexema.equals("Decimal") ||
                token.lexema.equals("Texto")){
            return true;
        }
        return false;
    }
    private boolean retorna(Node node){
        Node retorna = node.addNode("retorna");
        if(matchL("Retorna", retorna) && expressao(retorna) && 
                matchL(";", retorna)){
            return true;
        }
        return false;
    }
    private boolean destinando(Node node){
        Node destinando = node.addNode("destinando");
        if(matchL("D", destinando) && matchT("VARIAVEL", destinando) &&
                matchL("(", destinando) && pd(destinando, "parametrizando") &&
                matchL(")", destinando) &&matchL(";", destinando)){
            return true;
        }
        return false;
    }
    private boolean pd(Node node, String qual){ // assim serve para o Destino e o Conjunto
        if(token.tipo.equals("VARIAVEL") || token.tipo.equals("NUM_INTEIRO") ||
                token.tipo.equals("NUM_DECIMAL") || token.tipo.equals("TEXTO")){
            Node pdpd = node.addNode(qual);
            if(matchT("VARIAVEL", pdpd) || idt(pdpd)){
                if(matchL(",", pdpd)){
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
        if(matchL("Inserir", inserir) && matchL("(", inserir) &&
                matchT("VARIAVEL", inserir) && matchL(")", inserir) &&
                matchL(";", inserir)){
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //EXIBIR
    private boolean exibir(Node node){
        Node exibir = node.addNode("exibir");
        if(matchL("Exibir", exibir) && matchL("(", exibir) &&
                (matchT("VARIAVEL", exibir) || idt(exibir)) && 
                matchL(")", exibir) && matchL(";", exibir)){
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //CONJUNTO
    private boolean conjunto(Node node){
        Node conjunto = node.addNode("conjunto");
        if(matchL("Conjunto", conjunto) && matchL("(", conjunto) &&
                tipoVar(conjunto) && matchL(";", conjunto) &&
                matchT("VARIAVEL", conjunto) && matchL(";", conjunto) &&
                tamanho(conjunto) && matchL(")", conjunto) &&
                matchL("=", conjunto) && matchL("[", conjunto) &&
                pd(conjunto, "dentro") && matchL("]", conjunto) &&
                matchL(";", conjunto)){
            return true;
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
                matchT("VARIAVEL", insere) && matchL(";", insere) &&
                index(insere) && matchL(")", insere) && matchL("=", insere) &&
                idt(insere) && matchL(";", insere)){
            return true;
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
                matchT("VARIAVEL", remove) && matchL(";", remove) &&
                index(remove) && matchL(")", remove) && matchL(";", remove)){
            return true;
        }
        return false;
    }
    private boolean ordenar(Node node){
        Node ordenar = node.addNode("ordenar");
        if(matchL("Ordenar", ordenar) && matchL("(", ordenar) &&
                matchT("VARIAVEL", ordenar) && matchL(";", ordenar) &&
                tipoORD(ordenar) && matchL(")", ordenar) &&
                matchL(";", ordenar)){
            return true;
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