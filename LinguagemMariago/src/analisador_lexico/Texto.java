package analisador_lexico;

import java.text.CharacterIterator;

public class Texto extends AFD{
    
    private String texto(CharacterIterator code){
        String texto = "";
        code.next();
        while(code.current() != '"' && code.current() != CharacterIterator.DONE){
            texto += code.current();
            code.next();
        }
        if(code.current() == '"'){
            code.next();
        }
        return texto;
    }

    @Override
    public Token evaluate(CharacterIterator code) {
        
        if(code.current() == '"'){
            String texto = texto(code);
            return new Token("TEXTO", texto);
        }
        return null;
        
    }
    
}
