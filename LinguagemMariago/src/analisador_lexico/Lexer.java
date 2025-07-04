package analisador_lexico;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private List<Token> tokens;
    private List<AFD> afds;
    private CharacterIterator code;

    public Lexer(String code) {
        tokens = new ArrayList<>();
        afds = new ArrayList<>();
        this.code = new StringCharacterIterator(code);
        afds.add(new MathOperator());
        afds.add(new Number());
        afds.add(new PalavraReservada());
        afds.add(new Variavel());
        afds.add(new Texto());
        afds.add(new Comentario());
        afds.add(new ComparisonOperator());
        afds.add(new AssignmentOperator());
        afds.add(new Symbol());
        afds.add(new LogicalOperator());
    }

    // \r é quebra de linha no Windows
    public void skipWhiteSpace() {
        while (code.current() == ' ' || code.current() == '\n' || code.current() == '\r') {
            code.next();
        }
    }

    public Token searchNextToken() {
        int pos = code.getIndex();
        for (AFD afd : afds) {
            Token t = afd.evaluate(code);
            if (t != null) {
                return t;
            }
            code.setIndex(pos);
        }
        return null;
    }

    private void error() {
        throw new RuntimeException("Character não reconhecido: " + code.current());
    }

    public List<Token> getTokens() {
        Token t;
        do {
            skipWhiteSpace();
            t = searchNextToken();
            if (t == null) {
                error();
            }
            tokens.add(t);
        } while (t.getTipo() != "EOF");
        return tokens;
    }

}
