package analisador_semantico;

public class Simbolo {
    
    String nome, tipoVar, tipoVal;
    Object valor;
    
    public Simbolo(String nome, String tipoVar, String tipoVal, Object valor){
        this.nome = nome;
        this.tipoVar = tipoVar;
        this.tipoVal = tipoVal;
        this.valor = valor;
    }
    
    @Override
    public String toString(){
        return "Simbolo{" + "nome:" + nome + ", tipoVar:" + tipoVar +
                ", tipoVal:" + tipoVal + ", valor:" + valor + "}";
    }
    
}
