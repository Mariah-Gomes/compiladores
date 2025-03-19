package analisador_lexico;

import java.text.CharacterIterator;

public class Variavel extends AFD{
    
    private String readPalavra(CharacterIterator code){
        String palavra = "";
        while(Character.isLetter(code.current())){
            palavra += code.current();
            code.next();
        }
        return palavra;
    }

    @Override
    public Token evaluate(CharacterIterator code) {
        
        if(Character.isLowerCase(code.current())){
            String palavra = readPalavra(code);
                return new Token("VARIAVEL", palavra);
        }
        return null;
        
    }
    
}
