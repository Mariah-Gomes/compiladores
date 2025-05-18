package analisador_lexico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import analisador_sintatico.Parser;
import arvore_sintatica_abstrata.Tree;
import traducao_go.GoCodeNavigator;

public class Main {

    public static void main(String[] args) throws IOException {
        // 1. Leitura do arquivo
        String codigo_fonte = Files.readString(Path.of("src/analisador_lexico/teste.txt"));

        // 2. Análise léxica
        Lexer lexer = new Lexer(codigo_fonte);
        List<Token> tokens = lexer.getTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }

        // 3. Análise sintática
        Parser parser = new Parser(tokens);
        Tree tree = parser.main();

        // 4. Impressão da árvore
        System.out.println("\nÁrvore Sintática:");
        tree.printTree();

        // 5. Tradução para Go
        GoCodeNavigator navigator = new GoCodeNavigator();
        String codigoGo = navigator.gerarCodigo(tree);

        // 6. Exibição do código traduzido
        System.out.println("\nCódigo Go gerado:");
        System.out.println(codigoGo);
    }

}

