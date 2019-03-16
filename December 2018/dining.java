import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class dining {
  public static int N, M, K;
  public static List[] adj;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("dining.in"));
        PrintWriter out = new PrintWriter(new File("dining.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      adj = new List[N + 1];
      for (int i = 0; i <= N; i++)
        adj[i] = new ArrayList<int[]>(); // [0]=neighbor, [1]=time
      for (int i = 0; i < M; i++) {
        st = new StringTokenizer(in.readLine());
        int a = Integer.parseInt(st.nextToken()) - 1;
        int b = Integer.parseInt(st.nextToken()) - 1;
        int wt = Integer.parseInt(st.nextToken());
        adj[a].add(new int[] {b, wt});
        adj[b].add(new int[] {a, wt});
      }
      long[] ognDist = dijkstra(N - 1); // real barn
      System.out.println(Arrays.toString(ognDist));
      for (int i = 0; i < K; i++) {
        st = new StringTokenizer(in.readLine());
        int loc = Integer.parseInt(st.nextToken()) - 1;
        int wt = Integer.parseInt(st.nextToken());
        adj[N].add(new int[] {loc, (int) ognDist[loc] - wt}); // benefit hay provides to path time
        adj[loc].add(new int[] {N, (int) ognDist[loc] - wt});
      }
      long[] hayDist = dijkstra(N); // fake barn, can only reach through haybales
      System.out.println(Arrays.toString(hayDist));
      for (int i = 0; i < N - 1; i++) {
        if (hayDist[i] <= ognDist[i])
          out.println(1);
        else
          out.println(0);
      }
    }

  }

  public static long[] dijkstra(int src) {
    long[] dist = new long[N + 1];
    boolean[] vis = new boolean[N + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    PriorityQueue<int[]> toVis = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    toVis.add(new int[] {src, 0});
    while (!toVis.isEmpty()) {
      int curr = toVis.poll()[0];
      if (vis[curr])
        continue;
      vis[curr] = true;
      List<int[]> neighobrs = adj[curr];
      for (int[] ad : neighobrs) {
        int a = ad[0];
        if (!vis[a] && dist[a] >= dist[curr] + ad[1]) {
          dist[a] = dist[curr] + ad[1];
          toVis.add(new int[] {a, (int) dist[a]});
        }
      }
    }
    return dist;
  }
}
