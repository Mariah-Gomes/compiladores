package analisador_lexico;

import java.text.CharacterIterator;

public class MathOperator extends AFD {

    @Override
    public Token evaluate(CharacterIterator code) {

        switch (code.current()) {
            case '+':
                code.next();
                return new Token("PLUS", "+");
            case '-':
                code.next();
                return new Token("MINUS", "-");
            case '*':
                code.next();
                return new Token("TIMES", "*");
            case '/':
                code.next();
                return new Token("DIVIDE", "/");
            case CharacterIterator.DONE:
                return new Token("EOF", "EOF");
            default:
                return null;
        }

    }
}
