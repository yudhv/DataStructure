package ds;

public class Stack<T> {
  private ds.DoublyLinkedList<T> list = null;

  public Stack() {
    this.list = new ds.DoublyLinkedList<T>();
  }

  public Stack(T value) {
    this.list = new ds.DoublyLinkedList<T>(value);
  }

  public void push(T value) {
    this.list.addLast(value);
  }

  public T pop() {
    T lastItem = this.list.getLast();
    this.list.removeLast();
    return lastItem;
  }

  public T peek() {
    return this.list.getLast();
  }

  public void clear() {
    this.list.clear();
  }

  public int size() {
    return this.list.size();
  }

  public boolean isEmpty() {
    return this.list.isEmpty();
  }

  public static void main(String[] args) {
    Stack<Integer> stack = new Stack<Integer>();
    // it should allow user to add data to stack
    stack.push(0);
    stack.push(1);
    System.out.println(stack.peek());
    stack.clear();
    System.out.println("---Cleared---");
    // it should allow user to pop data from stack
    stack.push(0);
    stack.push(1);
    int firstInstack = stack.pop();
    System.out.println(firstInstack);
    System.out.println("Size is " + stack.size());
    stack.clear();
    System.out.println("---Cleared---");
  }
}
