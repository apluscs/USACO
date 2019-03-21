// 2019 February Contest, Gold Problem 1. Cow Land
// cannot handle many "value queries (2)"; need to learn heavy-light decomposition with segment
// trees
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class cowland {
  public static int N, Q;
  public static Node[] nodes;
  public static Node root;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("cowland.in"));
        PrintWriter out = new PrintWriter(new File("cowland.out"));) {
      double start = System.currentTimeMillis();
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      Q = Integer.parseInt(st.nextToken());
      nodes = new Node[N + 1];
      st = new StringTokenizer(in.readLine());
      for (int i = 1; i <= N; i++) {
        int val = Integer.parseInt(st.nextToken());
        nodes[i] = new Node(val, i);
      }
      for (int i = 1; i < N; i++) {
        st = new StringTokenizer(in.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        nodes[a].neighbors.add(nodes[b]);
        nodes[b].neighbors.add(nodes[a]);
      }
      findRoot();
      levels();
      // for (int i = 1; i <= N; i++) {
      // nodes[i].print();
      // }
      for (int i = 0; i < Q; i++) {
        st = new StringTokenizer(in.readLine());
        int q = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        if (q == 1) {
          nodes[a].val = b;
        } else {
          int res = findEnjoyment(a, b);
          out.println(res);
        }
      }
      // System.out.print(System.currentTimeMillis() - start);
    }
  }

  public static void findRoot() {
    Queue<Node> toVis = new LinkedList<>();
    for (int i = 1; i <= N; i++) {
      nodes[i].count = nodes[i].neighbors.size();
      if (nodes[i].count == 1)
        toVis.offer(nodes[i]);
    }
    while (!toVis.isEmpty()) {
      for (int i = toVis.size(); i != 0; i--) {
        Node curr = toVis.poll();
        root = curr;
        for (Node n : curr.neighbors) {
          n.count--;
          if (n.count == 1)
            toVis.offer(n);
        }
      }
    }
    // System.out.println("root: " + root.id);
  }
  public static void levels() {
    Queue<Node> toVis = new LinkedList<>();
    toVis.add(root);
    root.lev = 0;
    while (!toVis.isEmpty()) {
      Node curr = toVis.poll();
      for (Node n : curr.neighbors) {
        if (n.lev == -1) {
          n.lev = curr.lev + 1;
          n.parent = curr;
          toVis.add(n);
        }
      }
    }
  }

  public static int findEnjoyment(int i, int j) {
    Node d = nodes[i];
    Node u = nodes[j];
    if (d.lev < u.lev) {
      Node temp = d;
      d = u;
      u = temp;
    }
    int x = d.val ^ u.val;
    while (d.lev > u.lev) {
      d = d.parent;
      x ^= d.val;
    }
    while (d.id != u.id) {
      d = d.parent;
      u = u.parent;
      x ^= d.val ^ u.val;
    }
    x ^= d.val;
    return x;
  }
  private static class Node {
    int val, lev = -1, id, count;
    Node parent;
    List<Node> neighbors = new ArrayList<>();

    public Node(int val, int id) {
      this.id = id;
      this.val = val;
    }

    public void print() {
      if (parent == null)
        return;
      System.out
          .println("value: " + val + ", parent: " + parent.id + ", level: " + lev + ", id: " + id);
    }
  }
}
