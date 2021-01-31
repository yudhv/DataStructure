public class DoublyLinkedList<T> {
  private class Node<T> {
    private T data;
    private Node<T> prev, next;

    public Node(T value, Node<T> prev, Node<T> next) {
      this.data = value;
      this.prev = prev;
      this.next = next;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }

  private int size = 0;
  private Node<T> head, tail = null;

  public DoublyLinkedList(T initialValue) {
    this.head = new Node<T>(initialValue, null, null);
    this.tail = this.head;
    this.size = 0;
  }

  public DoublyLinkedList() {
  }

  public void addAtIndex(int index, T value) {
    if (index < 0 || index > size) {
      throw new IllegalArgumentException();
    }

    Node<T> temp = this.head;
    if (index == 0) {
      this.addFirst(value);
      return;
    }
    if (index == size) {
      this.addLast(value);
      return;
    }
    for (int i = 0; i < index; temp = temp.next, i++) {
      if (i == index - 1) {
        Node<T> insertion = new Node<T>(value, temp, temp.next);
        temp.next.prev = insertion;
        temp.next = insertion;
        this.size++;
        return;
      }
    }
  }

  public void addLast(T value) {
    Node<T> insertion = new Node<T>(value, this.tail, null);
    if (this.tail != null) {
      this.tail.next = insertion;
    }
    if (this.head == null) {
      this.head = insertion;
    }
    this.tail = insertion;
    this.size++;
  }

  public void addFirst(T value) {
    Node<T> insertion = new Node<T>(value, null, this.head);
    if (this.head != null) {
      this.head.prev = insertion;
    }
    if (this.tail == null) {
      this.tail = insertion;
    }
    this.head = insertion;
    this.size++;
  }

  public void removeAtIndex(int index) {
    if (index < 0 || index > size) {
      throw new IllegalArgumentException();
    }

    if (index == 0) {
      this.removeFirst();
      return;
    }
    if (index == size - 1) {
      this.removeLast();
      return;
    }
    Node<T> temp = this.head.next;
    for (int i = 1; i <= index; temp = temp.next, i++) {
      if (i == index) {
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        temp = null;
        this.size--;
        return;
      }
    }
  }

  public void removeFirst() {
    if (this.size() == 1) {
      this.clear();
      return;
    }
    this.head.next.prev = null;
    this.head = this.head.next;
    this.size--;
  }

  public void removeLast() {
    if (this.size() == 1) {
      this.clear();
      return;
    }
    this.tail.prev.next = null;
    this.tail = this.tail.prev;
    this.size--;
  }

  public void clear() {
    if (this.size == 0) {
      return;
    }
    Node<T> temp = this.head;
    for (; temp.next != null; temp = temp.next) {
      temp.data = null;
      temp.prev = null;
    }
    this.size = 0;
    this.head = this.tail = null;
  }

  public T get(int index) {
    if (index < 0 || index > this.size) {
      throw new IllegalArgumentException();
    }
    Node<T> temp = this.head;
    for (int i = 0; i <= index; i++, temp = temp.next) {
      if (i == index) {
        return temp.data;
      }
    }
    return null;
  }

  public T getFirst() {
    return this.head.data;
  }

  public T getLast() {
    return this.tail.data;
  }

  public int size() {
    return this.size;
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  public static void main(String[] args) {
    DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    // it should add first and last elements without issue
    test.addFirst(0);
    test.addLast(1);
    for (int i = 0; i < test.size(); i++) {
      System.out.println(test.get(i));
    }
    test.clear();
    System.out.println("-----Cleared-----");
    // it should add at index
    test.addAtIndex(0, 0);
    test.addAtIndex(1, 1);
    System.out.println(test.getFirst());
    System.out.println(test.getLast());
    test.clear();
    System.out.println("-----Cleared-----");
    // it should remove first and last without issue
    test.addFirst(0);
    test.addLast(1);
    test.removeLast();
    test.removeFirst();
    System.out.println(test.isEmpty());
    test.clear();
    System.out.println("-----Cleared-----");
    // it should remove at index
    test.addFirst(0);
    test.addLast(1);
    test.addLast(2);
    test.addLast(3);
    test.removeAtIndex(2);
    test.removeAtIndex(1);
    test.removeAtIndex(1);
    test.removeAtIndex(0);
    System.out.println(test.isEmpty());
    test.clear();
    System.out.println("-----Cleared-----");
  }

}
