package analisador_semantico;

public class Simbolo {
    
    String nome, tipo;
    Object valor;
    
    public Simbolo(String nome, String tipo, Object valor){
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }
    
    @Override
    public String toString(){
        return "Simbolo{" + "nome:" + nome + ", tipo:" + tipo + ", valor:" +
                valor + "}";
    }
    
}
