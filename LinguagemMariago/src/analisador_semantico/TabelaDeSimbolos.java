package analisador_semantico;

import java.util.HashMap;

public class TabelaDeSimbolos {
    
    private HashMap<String, Simbolo> tabela;
    
    public TabelaDeSimbolos(){
        tabela = new HashMap<>();
    }
    
    public void inserirTabela(String nome, String tipoVar, String tipoVal,
            Object valor){
        if(!tabela.containsKey(nome)){
            tabela.put(nome, new Simbolo(nome, tipoVar, tipoVal, valor));
        }else{
            System.out.println("Erro: identficador '" + nome +
                    "' já declarado.");
        }
    }
    
    public Simbolo buscarTabela(String nome){
        return tabela.get(nome);
    }
    
    public boolean existirTabela(String nome){
        return tabela.containsKey(nome);
    }
    
//    public boolean mesmoTipo(String atual, String recebido){
//        if(recebido.equals(atual)){
//            return true;
//        }
//        return false;
//    }
    
    public void imprimirTabela(){
        for(Simbolo simbolo : tabela.values()){
            System.out.println(simbolo);
        }
    }
    
}
