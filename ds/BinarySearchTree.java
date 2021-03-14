package ds;

import java.util.Iterator;

import ds.enums.TreeTraversalOrder;

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
    // Base case: leaf node
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
      this.root = this.remove(this.root, elem);
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
    if (this.root == null)
      return false;
    Node temp = new Node(this.root.value, this.root.left, this.root.right);
    int comp = temp.value.compareTo(elem);
    while (comp != 0) {
      temp = comp > 0 ? temp.left : temp.right;
      // Exit with false if we got a leaf node
      if (temp == null) {
        return false;
      }
      comp = temp.value.compareTo(elem);
    }
    return true;
  }

  public int height() {
    return this.height(this.root);
  }

  private int height(Node node) {
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

  public Iterator<T> traverse(TreeTraversalOrder order) {
    switch (order) {
    case PRE_ORDER:
      return this.preOrderTraversal(this.root);
    case POST_ORDER:
      return this.postOrderTraversal(this.root);
    case IN_ORDER:
      return this.inOrderTraversal(this.root);
    case LEVEL_ORDER:
      return this.levelOrderTraversal(this.root);
    default:
      return null;
    }
  }

  private Iterator<T> preOrderTraversal(Node node) {
    return new Iterator<T>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public T next() {
        return node.value;
      }
    };
  }

  private Iterator<T> postOrderTraversal(Node node) {
    return new Iterator<T>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public T next() {
        return node.value;
      }
    };
  }

  private Iterator<T> inOrderTraversal(Node node) {
    return new Iterator<T>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public T next() {
        return node.value;
      }
    };
  }

  private Iterator<T> levelOrderTraversal(Node node) {
    return new Iterator<T>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public T next() {
        return node.value;
      }
    };
  }

}
