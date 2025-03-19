package analisador_lexico;

import java.text.CharacterIterator;

public class MathOperator extends AFD {

    @Override
    public Token evaluate(CharacterIterator code) {

        switch (code.current()) {
            case '+':
                code.next();
                return new Token("MATH_OP", "+");
            case '-':
                code.next();
                return new Token("MATH_OP", "-");
            case '*':
                code.next();
                return new Token("MATH_OP", "*");
            case '/':
                code.next();
                return new Token("MATH_OP", "/");
            case CharacterIterator.DONE:
                return new Token("EOF", "EOF");
            default:
                return null;
        }

    }
}
