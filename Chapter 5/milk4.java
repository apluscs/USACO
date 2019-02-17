import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class milk4 {

  public static int Q, P;
  public static List<Integer> use;
  public static List<Integer> result;
  public static int[] pls;
  public static boolean found;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("milk4.in"));
        PrintWriter out = new PrintWriter(new File("milk4.out"));) {
      Q = Integer.parseInt(in.readLine());
      P = Integer.parseInt(in.readLine());
      found = false;
      pls = new int[P];
      result = new ArrayList<>();
      for (int i = 0; i < P; i++) pls[i] = Integer.parseInt(in.readLine());
      Arrays.sort(pls); // ensures that lowest value pails come first
      for (int d = 1; d <= P; d++) {
        use = new ArrayList<>();
        dfs(d, 0); // for each possible #pails
        if (found)
        break;
      }
      out.print(result.size());
      for (int r : result)
      out.print(" " + r);
      out.println();
    }
  }

  public static void dfs(int maxDepth, int curr) {
    if (found) return;
    if (use.size() == maxDepth) { // filled up to maxDepth
      boolean[] dp = new boolean[Q + 1];
      dp[0] = true;
      for (int i = 1; i <= Q; i++)
        for (int u : use)
          if (i - u >= 0 && dp[i - u]) {
            dp[i] = true;
            break;
          }
      if (dp[Q]) { // if we can reach target
        found = true;
        result.addAll(use);
      }
      return;
    }
    if (curr == P) return;
    use.add(pls[curr]); // try with this size
    dfs(maxDepth, curr + 1);
    use.remove(use.size() - 1); // try without this size
    dfs(maxDepth, curr + 1);
  }
}
