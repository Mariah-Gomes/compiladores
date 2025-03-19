package analisador_lexico;

import java.text.CharacterIterator;

public class ComparisonOperator extends AFD{

    @Override
    public Token evaluate(CharacterIterator code) {
        
        if(code.current() == '='){
            code.next();
            if(code.current() == '='){
                code.next();
                return new Token("COMP_OP", "==");
            }
            return null;
        }else if(code.current() == '!'){
            code.next();
            if(code.current() == '='){
                code.next();
                return new Token("COMP_OP", "!=");
            }
            return null;
        }else if(code.current() == '>'){
            code.next();
            if(code.current() == '='){
                code.next();
                return new Token("COMP_OP", ">=");
            }
            return new Token("COMP_OP", ">");
        }else if(code.current() == '<'){
            code.next();
            if(code.current() == '='){
                code.next();
                return new Token("COMP_OP", "<=");
            }
            return new Token("COMP_OP", "<");
        }
        return null;
        
    }
    
}
