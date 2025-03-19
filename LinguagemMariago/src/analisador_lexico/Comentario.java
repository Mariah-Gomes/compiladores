package analisador_lexico;

import java.text.CharacterIterator;

public class Comentario extends AFD{
    
    private String comentario(CharacterIterator code){
        String texto = "";
        code.next();
        while(code.current() != '_' && code.current() != CharacterIterator.DONE){
            texto += code.current();
            code.next();
        }
        if(code.current() == '_'){
            code.next();
        }
        return texto;
    }

    @Override
    public Token evaluate(CharacterIterator code) {
        
        if(code.current() == '_'){
            String comentario = comentario(code);
            return new Token("COMENTARIO", comentario);
        }
        return null;
        
    }
    
}
