// USACO says no package
// apluscs 
// 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Solution {

  public static void main(String[] args) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    String line = reader.readLine();
    int n = Integer.parseInt(line);
    int[][] points = new int[n][2];
    for (int i = 0; i != n; i++) {
      line = reader.readLine();
      String[] tokens = line.split(" ");
      points[i][0] = Integer.parseInt(tokens[0]);
      points[i][1] = Integer.parseInt(tokens[1]);
    }
    System.out.println(solve(n, points));

  }

  static long solve(int n, int[][] points) {
    Arrays.sort(points, new ComparY()); // sort by y
    for (int i = 0; i != n; i++)
      points[i][1] = i; // y is now compressed to [0, n)
    long res = n + 1; // singular and all
    Arrays.sort(points, new ComparX()); // sort by x

    STree root = new STree(0, n);
    for (int i = 0; i != n; i++) { // left bound
      int a = points[i][1];
      if (i % 2 == 0) {
        for (int j = i + 1; j != n; j++) { // right bound
          int b = points[j][1];
          int low = Math.min(b, a), high = Math.max(b, a);
          res += (root.query(0, low - 1) + 1) * (root.query(high + 1, n) + 1);
          root.update(b, b, 1);
        }
      } else {
        root.update(a, a, -1);
        for (int j = n - 1; j != i; j--) { // right bound, reverse
          int b = points[j][1];
          int low = Math.min(b, a), high = Math.max(b, a);
          res += (root.query(0, low - 1) + 1) * (root.query(high + 1, n) + 1);
          root.update(b, b, -1);
        }
      }
    }
    return res;
  }

}


class ComparY implements Comparator<int[]> {
  @Override
  public int compare(int[] o1, int[] o2) {
    return o1[1] - o2[1];
  }
}


class ComparX implements Comparator<int[]> {
  @Override
  public int compare(int[] o1, int[] o2) {
    return o1[0] - o2[0];
  }
}


class STree {
  int s, m, e, f = 0;
  STree left, right;

  STree(int start, int end) {
    this.s = start;
    this.m = (start + end) / 2;
    this.e = end;
  }

  int update(int from, int to, int frq) {
    if (from > to)
      return f;
    if (from == s && to == e)
      return f += frq;
    if (left == null) {
      left = new STree(s, m);
      right = new STree(m + 1, e);
    }
    return f = left.update(Math.max(from, s), Math.min(to, m), frq)
        + right.update(Math.max(from, m + 1), Math.min(e, to), frq);
  }

  int query(int from, int to) {
    if (from > to)
      return 0;
    if (from == s && to == e)
      return this.f;
    if (left == null) {
      left = new STree(s, m);
      right = new STree(m + 1, e);
    }
    return left.query(Math.max(from, s), Math.min(to, m))
        + right.query(Math.max(from, m + 1), Math.min(e, to));
  }

  // void TreeDelete(STree* node) {
  // if (node == NULL) {
  // return;
  // }
  // TreeDelete(node->left); //deleting left subtree
  // TreeDelete(node->right); //deleting right subtree
  // // printf("\n Deleting node: %c", node->s);
  // free(node); //deleting the node
  // }

}
