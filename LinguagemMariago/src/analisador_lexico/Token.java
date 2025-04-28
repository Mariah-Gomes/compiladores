package analisador_lexico;

public class Token {

    public String tipo, lexema;

    public Token(String tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "< " + tipo + " , " + lexema + " >";
    }

    public String getTipo() {
        return tipo;
    }

}
