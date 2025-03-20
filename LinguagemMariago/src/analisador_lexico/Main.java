package analisador_lexico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Token> tokens = null;
        String codigo_fonte = Files.readString(Path.of("C:\\Users\\iago2\\dev\\compiladores\\LinguagemMariago\\src\\analisador_lexico\\teste.txt"));
        Lexer lexer = new Lexer(codigo_fonte);
        tokens = lexer.getTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }

    }

}
