package traducao_go;

import arvore_sintatica_abstrata.Node;
import arvore_sintatica_abstrata.Tree;

public class GoCodeNavigator {
    private GoCodeGenerator codeGen = new GoCodeGenerator();

    public String gerarCodigo(Tree tree) {
        Node raiz = tree.root;
        return codeGen.gerarCodigoCompleto(raiz); // Usa o método que gera o código Go com package, import, main etc.
    }
}
