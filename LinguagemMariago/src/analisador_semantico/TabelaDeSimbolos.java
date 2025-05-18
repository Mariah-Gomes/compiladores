package analisador_semantico;

import java.util.HashMap;

public class TabelaDeSimbolos {
    
    private HashMap<String, Simbolo> tabela;
    
    public TabelaDeSimbolos(){
        tabela = new HashMap<>();
    }
    
    public void inserirTabela(String nome, String tipo, Object valor){
        if(!tabela.containsKey(nome)){
            tabela.put(nome, new Simbolo(nome, tipo, valor));
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
    
}
