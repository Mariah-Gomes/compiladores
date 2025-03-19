package analisador_lexico;

import java.text.CharacterIterator;

public class Symbol extends AFD{

    @Override
    public Token evaluate(CharacterIterator code) {
        
        switch (code.current()) {
            case '(':
                code.next();
                return new Token("SYMBOL", "(");
            case ')':
                code.next();
                return new Token("SYMBOL", ")");
            case '[':
                code.next();
                return new Token("SYMBOL", "[");
            case ']':
                code.next();
                return new Token("SYMBOL", "]");
            case '{':
                code.next();
                return new Token("SYMBOL", "{");
            case '}':
                code.next();
                return new Token("SYMBOL", "}");
            case ';':
                code.next();
                return new Token("SYMBOL", ";");
            case CharacterIterator.DONE:
                return new Token("EOF", "EOF");
            default:
                return null;
        }
        
    }
    
}
