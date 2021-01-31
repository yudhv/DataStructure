package ds;

public class Queue<T> {
  private ds.DoublyLinkedList<T> list = null;

  public Queue() {
    this.list = new ds.DoublyLinkedList<T>();
  }

  public Queue(T value) {
    this.list = new ds.DoublyLinkedList<T>(value);
  }

  public void enqueue(T value) {
    this.list.addLast(value);
  }

  public T dequeue() {
    T firstItem = this.list.getFirst();
    this.list.removeFirst();
    return firstItem;
  }

  public T peek() {
    return this.list.getFirst();
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
    Queue<Integer> queue = new Queue<Integer>();
    // it should allow user to add data to queue
    queue.enqueue(0);
    queue.enqueue(1);
    System.out.println(queue.peek());
    queue.clear();
    System.out.println("---Cleared---");
    // it should allow user to dequeue data from queue
    queue.enqueue(0);
    queue.enqueue(1);
    int firstInQueue = queue.dequeue();
    System.out.println(firstInQueue);
    System.out.println("Size is " + queue.size());
    queue.clear();
    System.out.println("---Cleared---");
  }
}
