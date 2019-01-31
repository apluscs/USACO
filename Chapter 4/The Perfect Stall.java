import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class stall4 {

  public static int[] levels;
  public static int[][] adj;
  public static int n, m, s, t;

  // Pretty much the same thing as Drainage Ditches, except all initial caps are 1. *uses Dinics
  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("stall4.in"));
        PrintWriter out = new PrintWriter(new File("stall4.out"));) {
      n = in.nextInt();
      m = in.nextInt();
      s = 0;
      t = n + m + 1;
      adj = new int[n + m + 2][n + m + 2];
      levels = new int[n + m + 2];
      for (int i = 0; i < adj.length; i++) {
        Arrays.fill(adj[i], -1); // unconnected
      }
      for (int c = 1; c <= n; c++) {
        adj[s][c] = 1; // source connects to all cows
        adj[c][s] = 0; // backwards arrows allow you to reverse any "bad" moves you made before
        int stalls = in.nextInt();
        for (int i = 0; i < stalls; i++) {
          int stall = in.nextInt() + n;
          adj[c][stall] = 1; // each cow connects to some stalls
          adj[stall][c] = 0;
        }
      }
      for (int stall = n + 1; stall <= n + m; stall++) {
        adj[stall][t] = 1; // all stalls connect to sink
        adj[t][stall] = 0;
      }
      out.println(maxFlow());
    }

  }

  public static int maxFlow() {
    int result = 0;
    while (refreshBFS()) {
      if (sendFlow(s)) {
        result++;
      }
    }
    return result;
  }

  public static boolean sendFlow(int v) {
    if (v == t) {
      return true; // max capacity is 1 for each arc, so max flow for any gust is 1
    }
    for (int i = 0; i <= t; i++) {
      if (adj[v][i] == 1 && levels[i] == levels[v] + 1) {
        boolean tempFlow = sendFlow(i);
        if (tempFlow) {
          adj[v][i]--;
          adj[i][v]++;
          return true;
        }
      }
    }
    return false; // if this path doesn't reach sink
  }
  public static boolean refreshBFS() {
    Queue<Integer> toVis = new LinkedList<>();
    Arrays.fill(levels, 1000);
    levels[s] = 0;
    toVis.add(s);
    while (!toVis.isEmpty()) {
      int curr = toVis.poll();
      for (int i = 0; i <= t; i++) {
        if (adj[curr][i] == 1 && levels[i] == 1000) {
          levels[i] = levels[curr] + 1;
          toVis.add(i);
        }
      }
    }
    return levels[t] != 1000;
  }

}
