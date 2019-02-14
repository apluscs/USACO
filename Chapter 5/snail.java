// package usaco;
//@formatter:off
/*
ID: the.cla1
LANG: JAVA
TASK: snail
*/
//@formatter:on
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class snail {
  public static int[][] grid;
  public static int N, result;
  public static int[] dr = new int[] {0, 1, 0, -1};
  public static int[] dc = new int[] {1, 0, -1, 0};

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("snail.in"));
        PrintWriter out = new PrintWriter(new File("snail.out"));) {
      // double start = System.currentTimeMillis();
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      int B = Integer.parseInt(st.nextToken());
      grid = new int[N][N];
      for (int i = 0; i < B; i++) {
        String bar = in.readLine();
        int c = bar.charAt(0) - 'A';
        int r = Integer.parseInt(bar.substring(1)) - 1;
        grid[r][c] = 2;
      }
      dfs(0, 0, 1, 1);
      dfs(0, 0, 0, 1);
      out.println(result);
      // out.println(System.currentTimeMillis() - start);
    }
  }

  public static void dfs(int sr, int sc, int d, int len) {
    // print();
    result = Math.max(result, len);
    grid[sr][sc] = 1;
    int r = sr + dr[d];
    int c = sc + dc[d];
    if (out(r, c) || grid[r][c] == 2) {
      int d1 = (d + 1) % 4;
      if (!out(sr + dr[d1], sc + dc[d1]) && grid[sr + dr[d1]][sc + dc[d1]] == 0) {
        dfs(sr + dr[d1], sc + dc[d1], d1, len + 1);
      }
      int d2 = (d + 3) % 4;
      if (!out(sr + dr[d2], sc + dc[d2]) && grid[sr + dr[d2]][sc + dc[d2]] == 0) {
        dfs(sr + dr[d2], sc + dc[d2], d2, len + 1);
      }
    } else if (grid[r][c] == 0) {
      dfs(r, c, d, len + 1);
    }
    grid[sr][sc] = 0;
  }

  public static boolean out(int r, int c) {
    return r < 0 || r >= N || c < 0 || c >= N;
  }

  public static void print() {
    for (int[] g : grid) {
      System.out.println(Arrays.toString(g));
    }
    System.out.println();
  }
}
