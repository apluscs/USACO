package usaco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

//@formatter:off
/*
ID: the.cla1
LANG: JAVA
TASK: tour
*/
//@formatter:on
public class tour {

  public static int N, V;
  public static boolean[][] adj;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("tour.in"));
        PrintWriter out = new PrintWriter(new File("tour.out"));) {
      HashMap<String, Integer> map = new HashMap<>();
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      V = Integer.parseInt(st.nextToken());
      adj = new boolean[N][N];
      for (int i = 0; i < N; i++) {
        String city = in.readLine();
        map.put(city, i);
      }
      for (int i = 0; i < V; i++) {
        st = new StringTokenizer(in.readLine());
        String c1 = st.nextToken(), c2 = st.nextToken();
        int id1 = map.get(c1), id2 = map.get(c2);
        adj[id1][id2] = true;
        adj[id2][id1] = true;
      }
      int res = dp();
      out.println(res);
    }
  }

  public static int dp() { // i = where player 1 is at, j = where player 2 is at
    int[][] dp = new int[N][N];
    dp[0][0] = 1; // only situation where i and k can be the same
    for (int i = 0; i < N; i++)
      for (int j = i + 1; j < N; j++) // leaves dp[i][i] = 0, players can't be at the same city
        for (int k = 0; k < j; k++) // serves as a connecting point between i and j
          if (adj[k][j] && dp[i][k] != 0) { // if there is a path to k and an edge connecting i to k
            dp[i][j] = Math.max(dp[i][j], dp[i][k] + 1);
            dp[j][i] = dp[i][j];
          }
    // for (int[] d : dp) {
    // System.out.println(Arrays.toString(d));
    // }
    int result = 1;
    for (int i = 0; i < N; i++)
      if (adj[i][N - 1] && dp[i][N - 1] > result) // to make a full circle, i must connect to end
        result = dp[i][N - 1];
    return result;
  }
}
