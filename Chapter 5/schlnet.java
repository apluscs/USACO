package usaco;
//@formatter:off
/*
ID: the.cla1
LANG: JAVA
TASK: schlnet
*/
//@formatter:on
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

public class schlnet {
  public static int N, sccCount;
  public static boolean[][] adj;
  public static int[] low;
  public static boolean[] onStack;
  public static Stack<Integer> stack;
  public static int[] sccIn, sccOut;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("schlnet.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("schlnet.out")));) {
      for (int t = 1; t <= 11; t++) {
        in.readLine();
      stack = new Stack<>();
        sccCount = 0;
      N = Integer.parseInt(in.readLine());
      adj = new boolean[N + 1][N + 1];
      low = new int[N + 1];
      sccIn = new int[N + 1];
      sccOut = new int[N + 1];
      onStack = new boolean[N + 1];
      for (int i = 1; i <= N; i++) {
        low[i] = -1;
        sccIn[i] = -1;
        sccOut[i] = -1; // will be replaced if i becomes the head of an SCC
        StringTokenizer st = new StringTokenizer(in.readLine());
        while (st.hasMoreTokens()) {
          int to = Integer.parseInt(st.nextToken());
          adj[i][to] = true;
        }
      }
        for (int i = 1; i <= N; i++)
          if (low[i] == -1)
          dfs(i);
      merge();
        // System.out.println(Arrays.toString(low));
        // System.out.println(Arrays.toString(sccIn));
        // System.out.println(Arrays.toString(sccOut));
      int res1 = 0;
      int res2 = 0;
      for (int i = 1; i <= N; i++) {
          if (sccIn[i] == 0)
          res1++;
          if (sccOut[i] == 0)
          res2++;
      }

        out.println("Test #" + t + ": ");
        if (sccCount == 1) {
          out.println(1 + "\n" + 0 + "\n");
          continue;
        }
      out.println(res1);
        out.println(Math.max(res1, res2) + "\n");
      }
    }
  }

  public static void merge() {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        if (adj[i][j] && low[i] != low[j]) {
          int from = low[i];
          int to = low[j];
          sccIn[to]++;
          sccOut[from]++;
        }
      }
    }
  }

  public static void dfs(int id) {
    stack.push(id);
    onStack[id] = true;
    low[id] = id;
    for (int n = 1; n <= N; n++) {
      if (!adj[id][n])
        continue;
      if (low[n] == -1) // only process neighbors
        dfs(n);
      if (onStack[n])
        low[id] = Math.min(low[id], low[n]);
    }
    if (low[id] == id) {
      sccIn[id] = sccOut[id] = 0;
      sccCount++;
      while (!stack.isEmpty()) {
        int curr = stack.pop();
        onStack[curr] = false;
        low[curr] = id;
        if (curr == id)
          break;
      }
    }
  }
}
