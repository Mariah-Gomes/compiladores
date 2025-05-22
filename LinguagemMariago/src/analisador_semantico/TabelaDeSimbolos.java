package analisador_semantico;

import java.util.HashMap;
import java.util.List;

public class TabelaDeSimbolos {
    
    private HashMap<String, Simbolo> tabela;
    
    public TabelaDeSimbolos(){
        tabela = new HashMap<>();
    }
    
    public void inserirTabela(String nome, String tipoVar, String tipoVal){
        if(!tabela.containsKey(nome)){
            if(tipoVal == null || 
                    (tipoVar.equals("Inteiro") && tipoVal.equals("NUM_INTEIRO")) ||
                    (tipoVar.equals("Decimal") && tipoVal.equals("NUM_DECIMAL")) ||
                    (tipoVar.equals("Texto") && tipoVal.equals("TEXTO"))){
                tabela.put(nome, new Simbolo(nome, tipoVar, tipoVal));
            }else{
                System.out.println("Erro: valor da variavel '" + nome +
                        "' nao condiz com o tipo declarado.");
            }
        }else{
            System.out.println("Erro: identficador '" + nome +
                    "' já declarado.");
        }
    }
    
    public Simbolo buscarTabela(String nome){
        return tabela.get(nome);
    }
    
    public boolean existirTabela(String nome){
        if(!tabela.containsKey(nome)){
            System.out.println("Erro: identficador '" + nome +
                    "' nao declarado.");
            return false;
        }
        return true;
    }
    
    public boolean mesmoTipo(String nome, String tipoVar, List<Object> tipoVal){
        for(Object objeto : tipoVal){
            if(objeto == null){
                System.out.println("Erro: valores atribuidos nao condizem com o "
                    + "tipo de variavel declarada.");
                return false;
            }else if(tipoVar.equals("Inteiro")){
                if(!objeto.equals("NUM_INTEIRO")){
                    System.out.println("Erro: valores atribuidos nao condizem com o "
                    + "tipo de variavel declarada.");
                    return false;
                }
            }else if(tipoVar.equals("Decimal")){
                if(!objeto.equals("NUM_DECIMAL")){
                    System.out.println("Erro: valores atribuidos nao condizem com o "
                    + "tipo de variavel declarada.");
                    return false;
                }
            }else if(tipoVar.equals("Texto")){
                if(!objeto.equals("TEXTO")){
                    System.out.println("Erro: valores atribuidos nao condizem com o "
                    + "tipo de variavel declarada.");
                    return false;
                }
            }
        }
        return true;
    }
    
    public void imprimirTabela(){
        for(Simbolo simbolo : tabela.values()){
            System.out.println(simbolo);
        }
    }
    
}
