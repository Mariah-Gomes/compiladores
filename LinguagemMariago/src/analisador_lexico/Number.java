package analisador_lexico;

import java.text.CharacterIterator;

public class Number extends AFD{

    private String readNumber(CharacterIterator code){
        String number = "";
        while(Character.isDigit(code.current())){
            number += code.current();
            code.next();
        }
        return number;
    }
    
    @Override
    public Token evaluate(CharacterIterator code) {
        
        if(Character.isDigit(code.current())){
            String number = readNumber(code);
            if(code.current() == '.'){
                number += '.';
                code.next();
                number += readNumber(code);
                return new Token("NUM_DECIMAL", number);
            }else{
                return new Token("NUM_INTEIRO", number);
            }
        }
        return null;
        
    }
    
}
