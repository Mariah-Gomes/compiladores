package analisador_sintatico;

import analisador_lexico.Token;
import java.util.List;

public class Parser {
    
    List<Token> tokens;
    Token token;
    
    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public void main(){
        int foda = 0;
        token = getNextToken();
        while(true){
            if(declaracao() || quest() || enlace() || ciclo()){
                foda++;
            }else if(token.tipo == "EOF"){
                System.out.println("SINTATICAMENTE CORRETO KRLHO!!!");
                break;
            }else{
                erro();
                break;
            }
        }
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
    private boolean declaracao(){
        if(tipoVar() && matchT("VARIAVEL") && matchL("=") && idt() &&
                matchL(";")){
            return true;
        }
        return false;
    }
    private boolean tipoVar(){
        if(matchL("Inteiro") || matchL("Decimal") || matchL("Texto")){
            return true;
        }
        return false;
    }
    private boolean idt(){
        if(matchT("NUM_INTEIRO") || matchT("NUM_DECIMAL") || matchT("TEXTO")){
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //ESTRUTURA CONDICIONAL
    private boolean quest(){
        if(matchL("Quest") && matchL("(") && requisito() && matchL(")") &&
                matchL("{") && sn() && matchL("}")){
            if(matchL("Request")){
                if(request()){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
    private boolean requisito(){
        if(matchT("VARIAVEL") && matchT("COMP_OP") && (matchT("VARIAVEL") || 
                idt())){
            return true;
        }
        return false;
    }
    private boolean sn(){
        if(si()){
            if(matchL("No")){
                if(no()){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
    private boolean si(){
        if(matchL("Si") && matchL("{") && bloco() && matchL("}")){
            return true;
        }
        return false;
    }
    private boolean no(){
        if(matchL("{") && bloco() && matchL("}")){
            return true;
        }
        return false;
    }
    private boolean bloco(){ // corrigir: atribuicao() && bloco() ... ?
        if(atribuicao() || declaracao() || quest() || enlace() || ciclo()){
            while(true){ // B -> AB | DB | QB | EB | CB | "vazio"
                if(atribuicao() || declaracao() || quest() || enlace() ||
                        ciclo()){
                    ; // seria uma espera ocupada?
                }else{
                    break;
                }
            }
            return true;
        }
        return false;
    }
    private boolean atribuicao(){
        if(matchT("VARIAVEL") && matchL("=") && (idt() || matchT("VARIAVEL"))
                && matchL(";")){
            return true;
        }
        return false;
    }
    private boolean request(){
        if(matchL("(") && requisito() && matchL(")") &&
                matchL("{") && sn() && matchL("}")){
            if(matchL("Request")){
                if(request()){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //ENLACE
    private boolean enlace(){
        if(matchL("Enlace") && matchL("(") && rr() && matchL(")") &&
                matchL("{") && bloco() && matchL("}")){
            return true;
        }
        return false;
    }
    private boolean rr(){
        if(requisito() || matchL("Roda")){
            return true;
        }
        return false;
    }
    //---------------
    
    //---------------
    //CICLO
    private boolean ciclo(){
        if(matchL("Ciclo") && matchL("(") && declaracao() && requisito() &&
                matchL(";") && atualiza() && matchL(")") && matchL("{") &&
                bloco() && matchL("}")){
            return true;
        }
        return false;
    }
    private boolean atualiza(){
        if(matchL("Atualiza") && matchL("(") && matchT("VARIAVEL") &&
                matchT("MATH_OP") && idt() && matchL(")")){
            return true;
        }
        return false;
    }
    //---------------
    
    private boolean matchL(String palavra){
        if(token.lexema.equals(palavra)){
            token = getNextToken();
            return true;
        }
        return false;
    }
    
    private boolean matchT(String palavra){
        if(token.tipo.equals(palavra)){
            token = getNextToken();
            return true;
        }
        return false;
    }
    
}
// sla