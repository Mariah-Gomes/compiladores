package analisador_lexico;

import java.text.CharacterIterator;

public class LogicalOperator extends AFD{

    @Override
    public Token evaluate(CharacterIterator code) {
        
        if(code.current() == '&'){
            code.next();
            if(code.current() == '&'){
                code.next();
                return new Token("LOGI_OP", "&&");
            }
        }else if(code.current() == '|'){
            code.next();
            if(code.current() == '|'){
                code.next();
                return new Token("LOGI_OP", "||");
            }
        }
        return null;
        
    }
    
}
