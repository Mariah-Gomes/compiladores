package arvore_sintatica_abstrata;

public class Tree {
    
    Node root;

    public Tree() {
    }

    public Tree(Node root) {
      this.root = root;
    }

    public void setRoot(Node node) {
      this.root = node;
    }

    public void preOrder() {
      preOrder(this.root);
      System.out.println("");
    }

    public void printCode() {
      printCode(this.root);
      System.out.println("");
    }

    public void preOrder(Node node) {
      System.out.print(node);
      for (Node n : node.nodes) {
        preOrder(n);
      }
    }

    public void printCode(Node node) {
      System.out.print(node.enter);
      if (node.nodes.isEmpty())
        System.out.print(node);
      for (Node n : node.nodes) {
        printCode(n);
      }
      System.out.print(node.exit);
    }

    public void printTree() {
      System.out.println(this.root.getTree());
    }
    
}
