package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.*;
import ds.UnionFind;

public class UnionFindTest {

  @Test
  public void testNumComponents() {

    UnionFind uf = new UnionFind(5);
    assertEquals(uf.components(), 5);

    uf.unify(0, 1);
    assertEquals(uf.components(), 4);

    uf.unify(1, 0);
    assertEquals(uf.components(), 4);

    uf.unify(1, 2);
    assertEquals(uf.components(), 3);

    uf.unify(0, 2);
    assertEquals(uf.components(), 3);

    uf.unify(2, 1);
    assertEquals(uf.components(), 3);

    uf.unify(3, 4);
    assertEquals(uf.components(), 2);

    uf.unify(4, 3);
    assertEquals(uf.components(), 2);

    uf.unify(1, 3);
    assertEquals(uf.components(), 1);

    uf.unify(4, 0);
    assertEquals(uf.components(), 1);
  }

  @Test
  public void testComponentSize() {

    UnionFind uf = new UnionFind(5);
    assertEquals(uf.componentSize(0), 1);
    assertEquals(uf.componentSize(1), 1);
    assertEquals(uf.componentSize(2), 1);
    assertEquals(uf.componentSize(3), 1);
    assertEquals(uf.componentSize(4), 1);

    uf.unify(0, 1);
    assertEquals(uf.componentSize(0), 2);
    assertEquals(uf.componentSize(1), 2);
    assertEquals(uf.componentSize(2), 1);
    assertEquals(uf.componentSize(3), 1);
    assertEquals(uf.componentSize(4), 1);

    uf.unify(1, 0);
    assertEquals(uf.componentSize(0), 2);
    assertEquals(uf.componentSize(1), 2);
    assertEquals(uf.componentSize(2), 1);
    assertEquals(uf.componentSize(3), 1);
    assertEquals(uf.componentSize(4), 1);

    uf.unify(1, 2);
    assertEquals(uf.componentSize(0), 3);
    assertEquals(uf.componentSize(1), 3);
    assertEquals(uf.componentSize(2), 3);
    assertEquals(uf.componentSize(3), 1);
    assertEquals(uf.componentSize(4), 1);

    uf.unify(0, 2);
    assertEquals(uf.componentSize(0), 3);
    assertEquals(uf.componentSize(1), 3);
    assertEquals(uf.componentSize(2), 3);
    assertEquals(uf.componentSize(3), 1);
    assertEquals(uf.componentSize(4), 1);

    uf.unify(2, 1);
    assertEquals(uf.componentSize(0), 3);
    assertEquals(uf.componentSize(1), 3);
    assertEquals(uf.componentSize(2), 3);
    assertEquals(uf.componentSize(3), 1);
    assertEquals(uf.componentSize(4), 1);

    uf.unify(3, 4);
    assertEquals(uf.componentSize(0), 3);
    assertEquals(uf.componentSize(1), 3);
    assertEquals(uf.componentSize(2), 3);
    assertEquals(uf.componentSize(3), 2);
    assertEquals(uf.componentSize(4), 2);

    uf.unify(4, 3);
    assertEquals(uf.componentSize(0), 3);
    assertEquals(uf.componentSize(1), 3);
    assertEquals(uf.componentSize(2), 3);
    assertEquals(uf.componentSize(3), 2);
    assertEquals(uf.componentSize(4), 2);

    uf.unify(1, 3);
    assertEquals(uf.componentSize(0), 5);
    assertEquals(uf.componentSize(1), 5);
    assertEquals(uf.componentSize(2), 5);
    assertEquals(uf.componentSize(3), 5);
    assertEquals(uf.componentSize(4), 5);

    uf.unify(4, 0);
    assertEquals(uf.componentSize(0), 5);
    assertEquals(uf.componentSize(1), 5);
    assertEquals(uf.componentSize(2), 5);
    assertEquals(uf.componentSize(3), 5);
    assertEquals(uf.componentSize(4), 5);
  }

  @Test
  public void testConnectivity() {

    int sz = 7;
    UnionFind uf = new UnionFind(sz);

    for (int i = 0; i < sz; i++)
      assertEquals(uf.connected(i, i), true);

    uf.unify(0, 2);

    assertEquals(uf.connected(0, 2), true);
    assertEquals(uf.connected(2, 0), true);

    assertEquals(uf.connected(0, 1), false);
    assertEquals(uf.connected(3, 1), false);
    assertEquals(uf.connected(6, 4), false);
    assertEquals(uf.connected(5, 0), false);

    for (int i = 0; i < sz; i++)
      assertEquals(uf.connected(i, i), true);

    uf.unify(3, 1);

    assertEquals(uf.connected(0, 2), true);
    assertEquals(uf.connected(2, 0), true);
    assertEquals(uf.connected(1, 3), true);
    assertEquals(uf.connected(3, 1), true);

    assertEquals(uf.connected(0, 1), false);
    assertEquals(uf.connected(1, 2), false);
    assertEquals(uf.connected(2, 3), false);
    assertEquals(uf.connected(1, 0), false);
    assertEquals(uf.connected(2, 1), false);
    assertEquals(uf.connected(3, 2), false);

    assertEquals(uf.connected(1, 4), false);
    assertEquals(uf.connected(2, 5), false);
    assertEquals(uf.connected(3, 6), false);

    for (int i = 0; i < sz; i++)
      assertEquals(uf.connected(i, i), true);

    uf.unify(2, 5);
    assertEquals(uf.connected(0, 2), true);
    assertEquals(uf.connected(2, 0), true);
    assertEquals(uf.connected(1, 3), true);
    assertEquals(uf.connected(3, 1), true);
    assertEquals(uf.connected(0, 5), true);
    assertEquals(uf.connected(5, 0), true);
    assertEquals(uf.connected(5, 2), true);
    assertEquals(uf.connected(2, 5), true);

    assertEquals(uf.connected(0, 1), false);
    assertEquals(uf.connected(1, 2), false);
    assertEquals(uf.connected(2, 3), false);
    assertEquals(uf.connected(1, 0), false);
    assertEquals(uf.connected(2, 1), false);
    assertEquals(uf.connected(3, 2), false);

    assertEquals(uf.connected(4, 6), false);
    assertEquals(uf.connected(4, 5), false);
    assertEquals(uf.connected(1, 6), false);

    for (int i = 0; i < sz; i++)
      assertEquals(uf.connected(i, i), true);

    // Connect everything
    uf.unify(1, 2);
    uf.unify(3, 4);
    uf.unify(4, 6);

    for (int i = 0; i < sz; i++) {
      for (int j = 0; j < sz; j++) {
        assertEquals(uf.connected(i, j), true);
      }
    }
  }

  @Test
  public void testSize() {

    UnionFind uf = new UnionFind(5);
    assertEquals(uf.size(), 5);
    uf.unify(0, 1);
    uf.find(3);
    assertEquals(uf.size(), 5);
    uf.unify(1, 2);
    assertEquals(uf.size(), 5);
    uf.unify(0, 2);
    uf.find(1);
    assertEquals(uf.size(), 5);
    uf.unify(2, 1);
    assertEquals(uf.size(), 5);
    uf.unify(3, 4);
    uf.find(0);
    assertEquals(uf.size(), 5);
    uf.unify(4, 3);
    uf.find(3);
    assertEquals(uf.size(), 5);
    uf.unify(1, 3);
    assertEquals(uf.size(), 5);
    uf.find(2);
    uf.unify(4, 0);
    assertEquals(uf.size(), 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadUnionFindCreation1() {
    new UnionFind(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadUnionFindCreation2() {
    new UnionFind(-3463);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadUnionFindCreation3() {
    new UnionFind(0);
  }

}
