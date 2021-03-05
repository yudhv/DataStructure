package ds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PriorityQueue<T extends Comparable<T>> {
  private List<T> heap;

  public PriorityQueue(int sz) {
    if (sz < 0)
      throw new IllegalArgumentException();
    this.heap = new ArrayList<>(sz);
  }

  public PriorityQueue(T[] data) {
    int len = data.length;
    this.heap = new ArrayList<>(len);

    for (int i = 0; i < len; i++) {
      this.heap.add(data[i]);
    }

    for (int i = (len / 2) - 1; i >= 0; i--) {
      this.sink(i);
    }
  }

  public PriorityQueue(Collection<T> data) {
    this.heap = new ArrayList<>(data);

    for (int i = (this.heap.size() / 2) - 1; i >= 0; i--) {
      this.sink(i);
    }
  }

  public int size() {
    return this.heap.size();
  }

  public void clear() {
    this.heap.clear();
  }

  private void swap(int x, int y) {
    T temp = this.heap.get(x);
    this.heap.set(x, this.heap.get(y));
    this.heap.set(y, temp);
  }

  private void removeAt(int index) {
    if (index < 0 || index > this.size()) {
      throw new IllegalArgumentException();
    }
    this.swap(index, this.size() - 1);
    // Remove last element
    this.heap.set(this.size() - 1, null);
    this.swim(index);
    if (this.isHeapInvariantAt(index) == false) {
      this.sink(index);
    }
  }

  private boolean isHeapInvariantAt(int index) {
    if (index < 0 || index > this.size()) {
      throw new IllegalArgumentException();
    }
    boolean isLeftChild = index % 2 == 1;
    int parent = isLeftChild ? (index - 1) / 2 : (index - 2) / 2;
    int childLeft = 2 * index + 1;
    int childRight = 2 * index + 2;
    if (this.less(parent, index)) {
      return false;
    }
    if (this.less(index, childLeft) || this.less(index, childRight)) {
      return false;
    }
    return true;
  }

  private boolean less(int x, int y) {
    int lastElementIndex = this.size() - 1;
    if (x > lastElementIndex || y > lastElementIndex)
      throw new IllegalArgumentException();
    if (this.heap.get(x).compareTo(this.heap.get(y)) >= 0) {
      return true;
    }
    return false;
  }

  public boolean isEmpty() {
    return this.heap.size() == 0;
  }

  public T poll() {
    int lastElementIndex = this.size() - 1;
    T returnData = this.heap.get(0);
    this.swap(0, lastElementIndex);
    this.removeAt(lastElementIndex);
    this.sink(0);
    return returnData;
  }

  public T peek() {
    if (this.isEmpty())
      return null;
    return this.heap.get(0);
  }

  public void sink(int index) {
    if (index < 0)
      throw new IllegalArgumentException();
    if (2 * index + 1 > this.size() - 1)
      return;
    int childLeft = 2 * index + 1;
    int childRight = 2 * index + 2;
    int higherChild = this.less(childRight, childLeft) ? childLeft : childRight;
    if (this.less(index, childLeft) || this.less(index, childRight)) {
      this.swap(index, higherChild);
      this.sink(higherChild);
    }
  }

  public void swim(int index) {
    if (index < 0)
      throw new IllegalArgumentException();
    if (index == 0)
      return;
    boolean isLeftOfParent = index % 2 == 1;
    int parent = isLeftOfParent ? (index - 1) / 2 : (index - 2) / 2;
    if (this.less(parent, index)) {
      this.swap(parent, index);
      this.swim(parent);
    }
  }

}
