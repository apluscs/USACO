package usaco;
//@formatter:off
/*
ID: the.cla1
LANG: JAVA
TASK: bigbrn
*/
//@formatter:on
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class bigbrn {
  public static int N, T;
  public static int[][] grid;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("bigbrn.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bigbrn.out")));) {
      for (int t = 1; t <= 15; t++) {
        in.readLine();
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      T = Integer.parseInt(st.nextToken());
      grid = new int[N + 1][N + 1];
      for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= N; j++) {
          grid[i][j] = -1;
        }
      }
      for (int i = 0; i < T; i++) {
        st = new StringTokenizer(in.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        grid[r][c] = 0;
      }
      int result = dp();
        out.println("Test #" + t + ": " + result);
      }
    }

  }

  public static int dp() {
    int result = 0;
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        if (grid[i][j] == 0)
          continue;
        grid[i][j] = Math.min(Math.min(grid[i - 1][j], grid[i - 1][j - 1]), grid[i][j - 1]) + 1;
        result = grid[i][j] > result ? grid[i][j] : result;
      }
    }
    // for (int[] gr : grid)
    // System.out.println(Arrays.toString(gr));
    return result;
  }
}
