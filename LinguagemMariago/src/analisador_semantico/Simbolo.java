package analisador_semantico;

public class Simbolo {
    
    String nome, tipoVar, tipoVal;
    
    public Simbolo(String nome, String tipoVar, String tipoVal){
        this.nome = nome;
        this.tipoVar = tipoVar;
        this.tipoVal = tipoVal;
    }
    
    @Override
    public String toString(){
        return "Simbolo{" + "nome:" + nome + ", tipoVar:" + tipoVar +
                ", tipoVal:" + tipoVal + "}";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoVar() {
        return tipoVar;
    }

    public void setTipoVar(String tipoVar) {
        this.tipoVar = tipoVar;
    }

    public String getTipoVal() {
        return tipoVal;
    }

    public void setTipoVal(String tipoVal) {
        this.tipoVal = tipoVal;
    }
    
}
