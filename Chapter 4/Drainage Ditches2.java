/*from https://github.com/williamfiset/Algorithms/blob/master/com/williamfiset/algorithms/graphtheory/networkflow/examples/DinicsExample.java
Fiset's YouTube videos also helped me a bunch with learning about network flow
Links: https: https://www.youtube.com/watch?v=LdOnanfc5TM (Ford-Fulkerson), https://www.youtube.com/watch?v=RppuJYwlcI8 (Edmond Karp)
//www.youtube.com/watch?v=M6cm8UeeziI&t=326s (Dinics)
Thanks so much! */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class ditch {
  public static final int INF = Integer.MAX_VALUE / 2;
  public static int maxFlow;
  public static List<Edge>[] graph; // graph[i]=list of edges leaving from node i
  public static int[] level;
  public static int n, m, s, t;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("ditch.in"));
        PrintWriter out = new PrintWriter(new File("ditch.out"));) {
      m = in.nextInt();
      n = in.nextInt(); // #nodes
      graph = new List[n + 1];
      level = new int[n + 1];
      for (int i = 0; i <= n; i++)
        graph[i] = new ArrayList<Edge>();
      for (int i = 1; i <= m; i++) {
        int from = in.nextInt();
        int to = in.nextInt();
        int cap = in.nextInt();
        addEdge(from, to, cap);
      }
      s = 1;
      t = n;
      solve();
      out.println(maxFlow);
      }
  }

  public static void addEdge(int from, int to, int capacity) {
    Edge e1 = new Edge(from, to, capacity);
    Edge e2 = new Edge(to, from, 0);
    e1.residual = e2;
    e2.residual = e1;
    graph[from].add(e1);
    graph[to].add(e2);
  }

  public static void solve() {
    int[] next = new int[n + 1];    //for optimization: instead of searching every neighbor again, pick up where you left off
    while (bfs()) {
      Arrays.fill(next, 0);
      int f = dfs(s, next, INF);
      maxFlow += f;
    }
   }

  public static boolean bfs() {
    Arrays.fill(level, -1);
    Deque<Integer> q = new ArrayDeque<>(n);
    q.offer(s);
    level[s] = 0;
    while (!q.isEmpty()) {
      int node = q.poll();
      for (Edge edge : graph[node]) {
        int cap = edge.remainingCapacity();
        if (cap > 0 && level[edge.to] == -1) {
          level[edge.to] = level[node] + 1;
          q.offer(edge.to);
        }
      }
    }
    return level[t] != -1;
    }

  public static int dfs(int at, int[] next, int flow) {
    if (at == t) {
      return flow;
    }
    final int numEdges = graph[at].size();
     for (; next[at] < numEdges; next[at]++) {
     Edge edge = graph[at].get(next[at]);
     int cap = edge.remainingCapacity();
     if (cap > 0 && level[edge.to] == level[at] + 1) {
     int bottleNeck = dfs(edge.to, next, Math.min(flow, cap));
     if (bottleNeck > 0) {
     edge.augment(bottleNeck);
     return bottleNeck;
     }
    
     }
     }
    return 0;
  }

  public static class Edge {
    public int from, to;
    public Edge residual;
    public int flow;
    public final int capacity;

    public Edge(int from, int to, int capacity) {
      this.from = from;
      this.to = to;
      this.capacity = capacity;
    }

    public boolean isResidual() {
      return capacity == 0;
    }

    public int remainingCapacity() {
      return capacity - flow;
      }

    public void augment(int bottleNeck) {
      flow += bottleNeck;
      residual.flow -= bottleNeck;
    }

    public String toString(int s, int t) {
      String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
      String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
      return String.format("Edge %s -> %s | flow = %3d | capacity = %3d | is residual: %s", u, v,
          flow, capacity, isResidual());
    }
  }
  }

