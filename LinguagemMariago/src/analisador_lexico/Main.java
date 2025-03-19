package analisador_lexico;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Token> tokens = null;
        String codigo_fonte = "+ - * /";
        Lexer lexer = new Lexer(codigo_fonte);
        tokens = lexer.getTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }

    }

}
