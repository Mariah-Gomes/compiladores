package analisador_lexico;

import java.text.CharacterIterator;

public class PalavraReservada extends AFD{
    
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
        
        if(Character.isUpperCase(code.current())){
            String palavra = readPalavra(code);
            switch (palavra) {
                
                // TIPOS DE VARIAVEL
                case "Inteiro":
                    return new Token("RESERVADA", palavra);
                case "Decimal":
                    return new Token("RESERVADA", palavra);
                case "Texto":
                    return new Token("RESERVADA", palavra);
                
                // "VETOR"
                case "Conjunto":
                    return new Token("RESERVADA", palavra);
                case "Insere":
                    return new Token("RESERVADA", palavra);
                case "Remove":
                    return new Token("RESERVADA", palavra);
                case "Ordenar":
                    return new Token("RESERVADA", palavra);
                case "Dinamico":
                    return new Token("RESERVADA", palavra);
                case "Inicio":
                    return new Token("RESERVADA", palavra);
                case "Final":
                    return new Token("RESERVADA", palavra);
                case "MaiorTo":
                    return new Token("RESERVADA", palavra);
                case "MenorTo":
                    return new Token("RESERVADA", palavra);
                case "C":
                    return new Token("RESERVADA", palavra);
                    
                // ESTRUTURA CONDICIONAL
                case "Quest":
                    return new Token("RESERVADA", palavra);
                case "Request":
                    return new Token("RESERVADA", palavra);
                case "Si":
                    return new Token("RESERVADA", palavra);
                case "No":
                    return new Token("RESERVADA", palavra);
                    
                // LAÇOS DE REPETIÇÃO
                case "Enlace":
                    return new Token("RESERVADA", palavra);
                case "Roda":
                    return new Token("RESERVADA", palavra);
                case "Ciclo":
                    return new Token("RESERVADA", palavra);
                case "Atualiza":
                    return new Token("RESERVADA", palavra);
                case "Quebra":
                    return new Token("RESERVADA", palavra);
                    
                // FUNÇÂO
                case "Destino":
                    return new Token("RESERVADA", palavra);
                case "Retorna":
                    return new Token("RESERVADA", palavra);
                case "D":
                    return new Token("RESERVADA", palavra);
                    
                // INPUT
                case "Inserir":
                    return new Token("RESERVADA", palavra); 
                    
                // PRINT
                case "Exibir":
                    return new Token("RESERVADA", palavra);
                    
                default:
                    return null;
            }
        }
        return null;
        
    }
    
}
