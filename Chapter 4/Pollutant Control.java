package usaco;
//@formatter:off
/*
ID: the.cla1
LANG: JAVA
TASK: milk6
*/
//@formatter:on
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class milk6 {
  public static int[] levels;
  public static double[][] adj;
  public static int N, M;
  public static HashMap<Integer, List<Integer>> map = new HashMap<>();

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("milk6.in"));
        PrintWriter out = new PrintWriter(new File("milk6.out"));) {
      N = in.nextInt();
      M = in.nextInt();
      levels = new int[N + 1];
      adj = new double[N + 1][N + 1];
      for (int i = 0; i < adj.length; i++) Arrays.fill(adj[i], -1);
      for (int i = 1; i <= M; i++) {
        int from = in.nextInt();
        int to = in.nextInt();
        double cap = 1001 * in.nextInt() + 1;
        if (adj[from][to] == -1) adj[from][to] = cap;  //special case
        else adj[from][to] += cap;
        if (adj[to][from] == -1) { adj[to][from] = 0;  //only if backwards path doesn't already exist
        int id = from * 100 + to;
        List<Integer> inds = map.get(id);
        if (inds == null) inds = new ArrayList<>();
        inds.add(i);
        map.put(id, inds);
      }
      long res = maxFlow();
      List<Integer> result = new ArrayList<>();
      for (int i = 1; i <= N; i++) 
        if (levels[i] != 1000)  // this part is accessible
          for (int j = 1; j <= N; j++) 
            if (levels[j] == 1000) {
              if (map.containsKey(i * 100 + j)) //the edge that leads to a dead end
                result.addAll(map.get(i * 100 + j));
      out.println(res / 1001 + " " + result.size());
      Collections.sort(result);
      for (int r : result) out.println(r);
    }
  }

  public static long maxFlow() {
    long result = 0;
    while (BFS()) result += sendFlow(1, Integer.MAX_VALUE);
    return result;
  }

  public static double sendFlow(int v, double flow) {
    if (v == N)mreturn flow;
    for (int i = 1; i <= N; i++) 
      if (levels[i] == levels[v] + 1 && adj[v][i] != -1) {
        double myFlow = Math.min(flow, adj[v][i]);
        double tempFlow = sendFlow(i, myFlow);
        if (tempFlow != 0) {
          adj[v][i] -= tempFlow;
          adj[i][v] += tempFlow;
          return tempFlow;
        }
      }
    return 0;
  }
  
  public static boolean BFS() {
    Queue<Integer> toVis = new LinkedList<>();
    Arrays.fill(levels, 1000);
    levels[1] = 0;
    toVis.add(1);
    while (!toVis.isEmpty()) {
      int curr = toVis.poll();
      for (int i = 1; i <= N; i++) {
        if (levels[i] == 1000 && adj[curr][i] > 0) {
          levels[i] = levels[curr] + 1;
          toVis.add(i);
        }
      }
    }
    return levels[N] != 1000;
  }
}
