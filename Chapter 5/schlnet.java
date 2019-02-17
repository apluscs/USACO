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
      int res1 = 0;
      int res2 = 0;
      for (int i = 1; i <= N; i++) {
        if (sccIn[i] == 0)
          res1++;
        if (sccOut[i] == 0)
          res2++;
      }
      out.println("Test #" + t + ": ");
      if (sccCount == 1) { // special case: if one big circle, no arrow needs to be added
        out.println(1 + "\n" + 0 + "\n");
        continue;
      }
      out.println(res1);
      out.println(Math.max(res1, res2) + "\n");   //every SCC needs an incoming arrow no matter what
    }
  }

  public static void merge() {  //consider each SCC as just one node
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        if (adj[i][j] && low[i] != low[j]) { // arrow here and 2 nodes are part of diff SCCs
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
    low[id] = id;   //low link value - every node in this group should share this
    for (int n = 1; n <= N; n++) {
      if (!adj[id][n]) // only process neighbors
        continue;
      if (low[n] == -1) // unvisited
        dfs(n);
      if (onStack[n]) // ex. bumps into a node in same group = we are able to visit each other
        low[id] = Math.min(low[id], low[n]);
    }
    if (low[id] == id) {
      sccIn[id] = sccOut[id] = 0;
      sccCount++;
      while (!stack.isEmpty()) {
        int curr = stack.pop();
        onStack[curr] = false;
        low[curr] = id;//why do you need this line?
        if (curr == id)
          break;
      }
    }
  }
}
