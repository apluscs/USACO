//This is MY solution - using adj[i][j] to track edges instead of an Edge class. Both this and ditch2 passed the test
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ditch {
  public static int[] levels;
  public static int[][] adj;
  public static int N, M, S, T;

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("ditch.in"));
        PrintWriter out = new PrintWriter(new File("ditch.out"));) {
      N = in.nextInt();
      M = in.nextInt();
      levels = new int[M + 1];
      adj = new int[M + 1][M + 1];
      for (int i = 1; i <= M; i++)
        Arrays.fill(adj[i], -1);
      for (int i = 1; i <= N; i++) {
        int from = in.nextInt();
        int to = in.nextInt();
        int cap = in.nextInt();
        adj[from][to] += cap + 1; //add back -1
        int temp = adj[to][from];
        if (temp < 0)
          adj[to][from] = 0;
      }
      S = 1;
      T = M;    //luckily for us, sink is ALWAYS last node :D
      out.println(maxFlow());
    }
  }

  public static int maxFlow() {
    int result = 0;
    while (refreshLevels()) { // refreshes after each gust of flow
      result += sendFlow(S, Integer.MAX_VALUE);
    }
    return result;

  }

  public static int sendFlow(int v, int flow) { // sending TO v
    if (v == T) {
      return flow;
    }
    for (int n = 1; n <= M; n++) {
      if (adj[v][n] > 0 && levels[n] == levels[v] + 1) {    //level graph ensures you are making max progress towards source
        int myFlow = Math.min(adj[v][n], flow);
        int tempFlow = sendFlow(n, myFlow);
        if (tempFlow > 0) { // found a path to sink
          adj[v][n] -= tempFlow;
          adj[n][v] += tempFlow;
          return tempFlow;
        }

      }
    }
    return 0; // only returns 0 if you never reached the sink
  }

  public static boolean refreshLevels() {
    Arrays.fill(levels, 1000);
    Queue<Integer> toVis = new LinkedList<>();
    toVis.add(S);
    levels[S] = 0;
    while (!toVis.isEmpty()) {
      int curr = toVis.poll();
      for (int n = 1; n <= M; n++) {
        if (levels[n] == 1000 && adj[curr][n] > 0) { // unvisited and there is cap from curr to n
          levels[n] = levels[curr] + 1;
          toVis.add(n);
        }
      }
    }
    return levels[T] != 1000; //there is a path to source
  }
}
