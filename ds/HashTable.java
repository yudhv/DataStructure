package ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Entry<K, V> {
  K key;
  V value;
  int hash;

  public Entry(K key, V value) {
    this.key = key;
    this.value = value;
    this.hash = key.hashCode();
  }

  public boolean equals(Entry<K, V> entry) {
    if (this.hash != entry.hash)
      return false;
    return this.key.equals(entry.key);
  }

  @Override
  public String toString() {
    return this.key + "=>" + this.value;
  }
}

public class HashTable<K, V> implements Iterable<K> {

  private static final int DEFAULT_CAPACITY = 3;
  private static final double DEFAULT_THRESHOLD = 0.75;

  private int capacity, threshold, size = 0;
  private double maxLoadFactor;
  private LinkedList<Entry<K, V>>[] table;

  public HashTable() {
    this(DEFAULT_CAPACITY, DEFAULT_THRESHOLD);
  }

  public HashTable(int capacity) {
    this(capacity, DEFAULT_THRESHOLD);
  }

  public HashTable(int capacity, double maxLoadFactor) {
    if (capacity < 0)
      throw new IllegalArgumentException("Capacity not correct");
    if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
      throw new IllegalArgumentException("maxLoadFactor is not correct");
    this.maxLoadFactor = maxLoadFactor;
    this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
    this.threshold = (int) (this.capacity * this.maxLoadFactor);
    this.table = new LinkedList[this.capacity];
  }

  public int size() {
    return this.size;
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  public void clear() {
    Arrays.fill(this.table, null);
  }

  private int normalizeIndex(int hash) {
    return (hash & 0x7FFFFFFF) % this.capacity;
  }

  public boolean hasKey(K key) {
    return this.containsKey(key);
  }

  public boolean containsKey(K key) {
    int bucketIndex = this.normalizeIndex(key.hashCode());
    return this.bucketSeekEntry(key, bucketIndex) != null;
  }

  private Entry<K, V> bucketSeekEntry(K key, int bucketIndex) {
    if (key == null)
      throw new IllegalArgumentException();
    LinkedList<Entry<K, V>> bucket = this.table[bucketIndex];
    for (Entry<K, V> entry : bucket) {
      if (entry.key.equals(key))
        return entry;
    }
    return null;
  }

  public V put(K key, V value) {
    return this.insert(key, value);
  }

  public V add(K key, V value) {
    return this.insert(key, value);
  }

  public V insert(K key, V value) {
    if (key == null)
      throw new IllegalArgumentException();
    int bucketIndex = this.normalizeIndex(key.hashCode());
    Entry<K, V> entry = new Entry<>(key, value);
    return this.bucketInsertEntry(entry, bucketIndex);
  }

  private V bucketInsertEntry(Entry<K, V> entry, int bucketIndex) {
    LinkedList<Entry<K, V>> bucket = this.table[bucketIndex];
    if (bucket != null) {
      Entry<K, V> seekEntry = this.bucketSeekEntry(entry.key, bucketIndex);
      if (seekEntry != null) {
        seekEntry.value = entry.value;
        return seekEntry.value;
      }
    } else {
      this.table[bucketIndex] = bucket = new LinkedList<>();
      this.size++;
    }
    bucket.add(entry);
    this.resizeTable();
    return entry.value;
  }

  private void resizeTable() {
    if (this.size < this.threshold)
      return;
    // We've reached threshold
    this.capacity *= 2;
    this.threshold = (int) (this.capacity * this.maxLoadFactor);
    LinkedList<Entry<K, V>>[] newTable = new LinkedList[this.capacity];
    for (LinkedList<Entry<K, V>> bucket : this.table) {
      if (bucket != null) {
        for (Entry<K, V> entry : bucket) {
          int newIndex = this.normalizeIndex(entry.hash);
          LinkedList<Entry<K, V>> newBucket = newTable[newIndex];
          if (newBucket == null) {
            newTable[newIndex] = newBucket = new LinkedList<>();
          }
          newBucket.add(entry);
        }
        bucket.clear();
      }
    }
    this.table = newTable;
  }

  public V get(K key) {
    if (key == null)
      throw new IllegalArgumentException();
    int bucketIndex = this.normalizeIndex(key.hashCode());
    Entry<K, V> entry = this.bucketSeekEntry(key, bucketIndex);
    if (entry != null)
      return entry.value;
    return null;
  }

  public V remove(K key) {
    if (key == null)
      throw new IllegalArgumentException();
    int bucketIndex = this.normalizeIndex(key.hashCode());
    Entry<K, V> entry = this.bucketSeekEntry(key, bucketIndex);
    if (entry != null) {
      LinkedList<Entry<K, V>> bucket = this.table[bucketIndex];
      bucket.remove(entry);
      return entry.value;
    }
    return null;
  }

  public List<K> keys() {
    List<K> returnKeys = new ArrayList<>();
    for (int i = 0; i < this.size; i++) {
      if (this.table[i] != null) {
        for (Entry<K, V> entry : this.table[i]) {
          returnKeys.add(entry.key);
        }
      }
    }
    return returnKeys;
  }

  public List<V> values() {
    List<V> returnValues = new ArrayList<>();
    for (int i = 0; i < this.size; i++) {
      if (this.table[i] != null) {
        for (Entry<K, V> entry : this.table[i]) {
          returnValues.add(entry.value);
        }
      }
    }
    return returnValues;
  }

  // Return an iterator to iterate over all the keys in this map
  @Override
  public java.util.Iterator<K> iterator() {
    final int elementCount = size();
    return new java.util.Iterator<K>() {

      int bucketIndex = 0;
      java.util.Iterator<Entry<K, V>> bucketIter = (table[0] == null) ? null : table[0].iterator();

      @Override
      public boolean hasNext() {

        // An item was added or removed while iterating
        if (elementCount != size)
          throw new java.util.ConcurrentModificationException();

        // No iterator or the current iterator is empty
        if (bucketIter == null || !bucketIter.hasNext()) {

          // Search next buckets until a valid iterator is found
          while (++bucketIndex < capacity) {
            if (table[bucketIndex] != null) {

              // Make sure this iterator actually has elements -_-
              java.util.Iterator<Entry<K, V>> nextIter = table[bucketIndex].iterator();
              if (nextIter.hasNext()) {
                bucketIter = nextIter;
                break;
              }
            }
          }
        }
        return bucketIndex < capacity;
      }

      @Override
      public K next() {
        return bucketIter.next().key;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

}
