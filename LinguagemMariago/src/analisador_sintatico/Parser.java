package analisador_sintatico;

import analisador_lexico.Token;
import arvore_sintatica_abstrata.Node;
import arvore_sintatica_abstrata.Tree;
import java.util.List;

public class Parser {
    
    List<Token> tokens;
    Token token;
    
    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public Tree main(){
        int foda = 0;
        token = getNextToken();
        Node root = new Node("main");
        Tree tree = new Tree(root);
        tree.setRoot(root);
        while(true){
            if(declaracao(root) || quest(root) || enlace(root) || ciclo(root)){
                foda++;
            }else if(token.tipo == "EOF"){
                System.out.println("SINTATICAMENTE CORRETO KRLHO!!!");
                break;
            }else{
                erro();
                break;
            }
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
    
    //---------------
    //DECLARAÇÃO DE VARIÁVEL
    private boolean declaracao(Node node){
        Node declaracao = node.addNode("declaracao");
        if(tipoVar(declaracao) && matchT("VARIAVEL", declaracao) &&
                matchL("=", declaracao) && idt(declaracao) &&
                matchL(";", declaracao)){
            return true;
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
    //---------------
    
    //---------------
    //ESTRUTURA CONDICIONAL
    private boolean quest(Node node){
        Node quest = node.addNode("quest");
        if(matchL("Quest", quest) && matchL("(", quest) && requisito(quest) &&
                matchL(")", quest) && matchL("{", quest) && sn(quest) &&
                matchL("}", quest)){
            if(request(quest)){
                return true;
            }
        }
        return false;
    }
    private boolean requisito(Node node){
        Node requisito = node.addNode("requisito");
        if(matchT("VARIAVEL", requisito) && matchT("COMP_OP", requisito) &&
                (matchT("VARIAVEL", requisito) || idt(requisito))){
            return true;
        }
        return false;
    }
    private boolean sn(Node node){
        Node sn = node.addNode("sn");
        if(si(sn)){
            if(matchL("No", sn)){
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
        if(matchL("{", no) && bloco(no) && matchL("}", no)){
            return true;
        }
        return false;
    }
    private boolean bloco(Node node){
        Node bloco = node.addNode("bloco");
        if(atribuicao(bloco) || declaracao(bloco) || quest(bloco) ||
                enlace(bloco) || ciclo(bloco)){
            if(bloco(bloco) || token.lexema.equals("}")){
                return true;
            }
        }
        return false;
    }
    private boolean atribuicao(Node node){
        Node atribuicao = node.addNode("atribuicao");
        if(matchT("VARIAVEL", atribuicao) && matchL("=", atribuicao) &&
                (idt(atribuicao) || matchT("VARIAVEL", atribuicao)) &&
                matchL(";", atribuicao)){
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
                if(request(request)){
                    return true;
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
        if(requisito(rr) || matchL("Roda", rr)){
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
// sla