package ds;

@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T> {
  private T[] arr;
  private int length = 0;
  private int capacity = 0;

  public DynamicArray() {
    this(16);
  }

  public DynamicArray(int capacity) {
    if (capacity < 0)
      throw new IllegalArgumentException();
    this.capacity = capacity;
    this.arr = (T[]) new Object[capacity];
  }

  private void expand() {
    this.capacity *= 2;
    T[] tempArray = (T[]) new Object[this.capacity];
    for (int i = 0; i < this.size(); i++) {
      tempArray[i] = this.arr[i];
    }
    this.arr = tempArray;
  }

  public void push(T value) {
    if (value == null)
      throw new IllegalArgumentException();
    if (this.size() == this.capacity) {
      this.expand();
    }
    this.arr[this.length++] = value;
  };

  public T pop() {
    T returnValue = this.arr[this.size() - 1];
    this.remove(this.size() - 1);
    return returnValue;
  };

  public void remove(int index) {
    for (int i = index; i < this.size(); i++) {
      if (this.size() - i == 1) {
        this.arr[i] = null;
        this.length--;
        return;
      }
      this.arr[i] = this.arr[i + 1];
    }
  };

  public int size() {
    return this.length;
  };

  public T get(int index) {
    return this.arr[index];
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public T next() {
        return arr[++index];
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public static void main(String[] args) {
    DynamicArray<Integer> arr = new DynamicArray<Integer>(2);
    arr.push(1);
    arr.push(2);
    for (int i = 0; i < arr.size(); i++) {
      System.out.println(arr.get(i));
    }
    System.out.println(arr.capacity);
    System.out.println(arr.pop());
    System.out.println(arr.size());
    arr.remove(0);
  }
};
