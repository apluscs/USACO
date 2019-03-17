//2019 January Contest, Gold Problem 3. Shortcut 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class shortcut {
  public static int N, M, T;
  public static double start;
  public static int[] parent, dist, frqs, pass;
  public static int[][] adj;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("shortcut.in"));
        PrintWriter out = new PrintWriter(new File("shortcut.out"));) {
      start = System.currentTimeMillis();
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      T = Integer.parseInt(st.nextToken());
      frqs = new int[N + 1];
      parent = new int[N + 1];
      dist = new int[N + 1];
      pass = new int[N + 1]; // how many cows pass through each node?
      adj = new int[N + 1][N + 1];
      st = new StringTokenizer(in.readLine());
      for (int i = 1; i <= N; i++) // initial #cows/field
        frqs[i] = Integer.parseInt(st.nextToken());
      for (int i = 0; i < M; i++) {
        st = new StringTokenizer(in.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());// cost
        if (adj[a][b] == 0) // Note to self: if using adj instead of List<Edge>, must check
          adj[a][b] = adj[b][a] = c; // for existing edge!!
        else
          adj[a][b] = adj[b][a] = Math.min(adj[a][b], c);
      }
      dijkstra();
      fillPass();
      long res = 0;
      for (int i = 2; i <= N; i++) { // time saved*#cows served
        long save = (long) pass[i] * (dist[i] - T);
        res = Math.max(res, save);
      }
      out.println(res);
    }
  }

  public static void fillPass() {
    for (int i = 2; i <= N; i++) {
      int curr = i;
      while (curr != 0) {
        pass[curr] += frqs[i];
        curr = parent[curr];
      }
    }
    // System.out.println(Arrays.toString(pass));
  }
  public static void dijkstra() {
    PriorityQueue<long[]> toVis = new PriorityQueue<>((a, b) -> (int) (a[1] - b[1]));
    boolean[] vis = new boolean[N + 1];
    toVis.add(new long[] {1, 0});
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[1] = 0;
    while (!toVis.isEmpty()) {
      int curr = (int) toVis.poll()[0];
      if (vis[curr]) 
        continue;
      vis[curr] = true;
      for (int i = 2; i <= N; i++) {
        if (adj[curr][i] > 0 && !vis[i]) {
          int newDist = dist[curr] + adj[curr][i];
          if (newDist < dist[i]) {
            parent[i] = curr;
            dist[i] = newDist;
            toVis.add(new long[] {i, dist[i]});
          } else if (newDist == dist[i] && curr < parent[i]) {
            parent[i] = curr;
            dist[i] = newDist;
            toVis.add(new long[] {i, dist[i]});
          }
        }
      }
    }
    // System.out.println(Arrays.toString(dist));
    // System.out.println(Arrays.toString(frqs));
    // System.out.println(Arrays.toString(parent));
  }
}
