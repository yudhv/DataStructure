package ds;

public class BinarySearchTree<T extends Comparable<T>> {
  private class Node {
    private Node left;
    private Node right;
    private T value;

    Node(T value, Node left, Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }
  }

  private Node root;
  private int nodeCount = 0;

  public boolean isEmpty() {
    return nodeCount == 0;
  }

  public int size() {
    return nodeCount;
  }

  public boolean add(T elem) {
    if (this.contains(elem)) {
      return false;
    } else {
      this.root = this.add(this.root, elem);
      this.nodeCount++;
      return true;
    }
  }

  private Node add(Node parent, T elem) {
    // Base case: left node
    if (parent == null) {
      return new Node(elem, null, null);
    }
    if (elem.compareTo(parent.value) > 0) {
      parent.right = add(parent.right, elem);
    } else {
      parent.left = add(parent.left, elem);
    }
    return parent;
  }

  public boolean remove(T elem) {
    if (this.contains(elem)) {
      this.remove(this.root, elem);
      nodeCount--;
      return true;
    }
    return false;
  }

  private Node remove(Node node, T elem) {
    // Base case: left node: return null
    if (node == null)
      return null;
    int comp = elem.compareTo(node.value);

    if (comp > 0) {
      node.right = this.remove(node.right, elem);
    } else if (comp < 0) {
      node.left = this.remove(node.left, elem);
    } else {
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        Node temp = this.findMin(node.right);
        node.value = temp.value;
        node.right = this.remove(node.right, temp.value);
        return node.right;
      }
    }
    return node;
  }

  private Node findMin(Node node) {
    Node traverser = node;
    while (traverser.left != null) {
      traverser = traverser.left;
    }
    return traverser;
  }

  // private Node findMax(Node node) {
  // Node traverser = node;
  // while (traverser.right != null) {
  // traverser = traverser.right;
  // }
  // return traverser;
  // }

  public boolean contains(T elem) {
    Node temp = this.root;
    while (temp.value.compareTo(elem) != 0) {
      temp = temp.value.compareTo(elem) < 0 ? temp.left : temp.right;
      if (temp == null) {
        return false;
      }
    }
    return true;
  }

  public int height(Node node) {
    if (node == null) {
      return 0;
    } else {
      int leftHeight = 1;
      int rightHeight = 1;
      leftHeight += this.height(node.left);
      rightHeight += this.height(node.right);
      return Math.max(leftHeight, rightHeight);
    }
  }
}
