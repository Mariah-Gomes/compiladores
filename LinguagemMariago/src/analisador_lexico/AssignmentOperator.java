package analisador_lexico;

import java.text.CharacterIterator;

public class AssignmentOperator extends AFD{

    @Override
    public Token evaluate(CharacterIterator code) {
        
        if(code.current() == '='){
            code.next();
            if(code.current() != '='){
                code.next();
                return new Token("ASSI_OP", "=");
            }
        }
        return null;
        
    }
    
}
