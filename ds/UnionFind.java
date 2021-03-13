package ds;

public class UnionFind {
  // Array of ids pointing to other ids
  private int[] ids;
  // Size of each group of ids
  private int[] sz;
  // Length of data
  private int size;
  // Total number of groups
  private int numComponents;

  public UnionFind(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException();
    }

    this.ids = new int[size];
    this.sz = new int[size];
    this.size = size;

    for (int i = 0; i < size; i++) {
      this.ids[i] = i;
      this.sz[i] = 1;
    }

    this.numComponents = size;
  }

  public int components() {
    return this.numComponents;
  }

  public int find(int id) {
    int root = id;
    while (this.ids[root] != root) {
      root = this.ids[root];
    }
    int compress = id;
    // Path compression
    while (this.ids[compress] != root) {
      int temp = this.ids[compress];
      this.ids[compress] = root;
      compress = temp;
    }
    return root;
  }

  public boolean connected(int id1, int id2) {
    return find(id1) == find(id2);
  }

  public int componentSize(int id) {
    return this.sz[find(id)];
  }

  public int size() {
    return this.size;
  }

  public int totalGroups() {
    return this.numComponents;
  }

  public void unify(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException();
    }
    int rootx = this.find(x);
    int rooty = this.find(y);

    if (rootx == rooty) {
      return;
    }

    if (componentSize(x) >= componentSize(y)) {
      this.ids[rooty] = rootx;
      this.sz[rootx] += this.sz[rooty];
    } else {
      this.ids[rootx] = rooty;
      this.sz[rooty] += this.sz[rootx];
    }

    this.numComponents--;
  }
}
