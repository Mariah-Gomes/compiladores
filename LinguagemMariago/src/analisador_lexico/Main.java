package analisador_lexico;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Token> tokens = null;
        String codigo_fonte = " \"Oi 09!\" _Tchau 09!_ Enlace bundinha + 1 + 2.2 - 3 * 4.4 / 5 Quest";
        Lexer lexer = new Lexer(codigo_fonte);
        tokens = lexer.getTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }

    }

}
