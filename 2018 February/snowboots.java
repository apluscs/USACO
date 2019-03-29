package usacoPractice;
// 2018 February Contest, Silver Problem 2. Snow Boots

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class snowboots {
  public static int N, B;
  public static int[][] boots;
  public static int[] depth;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("snowboots.in"));
        PrintWriter out = new PrintWriter(new File("snowboots.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      B = Integer.parseInt(st.nextToken());
      depth = new int[N + 1];
      boots = new int[B][2];
      st = new StringTokenizer(in.readLine());
      for (int i = 1; i <= N; i++)
        depth[i] = Integer.parseInt(st.nextToken());
      for (int i = 0; i < B; i++) {
        st = new StringTokenizer(in.readLine());
        boots[i][0] = Integer.parseInt(st.nextToken()); // max depth
        boots[i][1] = Integer.parseInt(st.nextToken()); // step size
      }
      // for (int[] b : boots)
      // System.out.println(Arrays.toString(b));
      // System.out.println(Arrays.toString(depth));
      int res = solve();
      out.println(res);
    }
  }

  public static int solve() {
    boolean[] dp = new boolean[N + 1];
    dp[1] = true;
    for (int i = 0; i < B; i++) {
      // System.out.println(Arrays.toString(boots[i]));
      for (int p = 1; p < N; p++) {
        if (!dp[p]) // only advance if you were able to make it to this tile
          continue;
        int j = p + 1;
        while (j <= boots[i][1] + p && j <= N) {
          if (depth[j] <= boots[i][0] && depth[p] <= boots[i][0]) { // boots must handle
            dp[j] = true; // "from" tile and "to" tile
          }
          j++;
        }
      }
      if (dp[N])
        return i;
      // System.out.println(Arrays.toString(dp) + " " + (i + 1));
    }
    return -1;
  }
}
