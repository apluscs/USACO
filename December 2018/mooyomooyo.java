package usaco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class mooyomooyo {
  public static int N, K, sz;
  public static int[][] grid;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("mooyomooyo.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mooyomooyo.out")));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      grid = new int[N][10];
      for (int i = 0; i < N; i++) {
        String line = in.readLine();
        for (int j = 0; j < 10; j++)
          grid[i][j] = line.charAt(j) - '0';
      }
      while (tetris()) // repeat tetris op until it stops itself
      for (int i = 0; i < N; i++) {
          for (int j = 0; j < 10; j++)
          out.print(grid[i][j]);
        out.println();
      }
    }
  }

  public static boolean tetris() {
    boolean found = false; // did you find something to clear?
    for (int i = 0; i < N; i++)
      for (int j = 0; j < 10; j++)
        if (grid[i][j] > 0) {
          sz = 0;
          count(i, j, grid[i][j]);
          // System.out.println(i + ", " + j + " : " + sz);
          if (sz >= K) {
            clear(i, j, grid[i][j]);
            found = true;
          }
        }
    grid = drop();
    // for (int[] g : grid) {
    // System.out.println(Arrays.toString(g));
    // }
    return found;
  }

  public static int[][] drop() {
    int[][] result = new int[N][10];
    for (int j = 0; j < 10; j++) {
      int[] dist = new int[N]; // dist[i]=#zeroes below i=#rows you need to drop
      int z = 0;
      for (int i = N - 1; i >= 0; i--) {
        if (grid[i][j] != 0)
          dist[i] = z;
        else
          z++;
      }
      for (int i = N - 1; i >= 0; i--) {
        int d = dist[i];
        result[i + d][j] = -grid[i][j];
      }
    }
    return result;
  }

  public static void clear(int i, int j, int color) { // flood fill
    if (i < 0 || i == N || j < 0 || j == 10 || grid[i][j] != color)
      return;
    grid[i][j] = 0;
    clear(i - 1, j, color);
    clear(i + 1, j, color);
    clear(i, j - 1, color);
    clear(i, j + 1, color);
  }

  public static void count(int i, int j, int color) {
    if (i < 0 || i == N || j < 0 || j == 10 || grid[i][j] != color)
      return; // pretty much same thing as clear()
    sz++;
    grid[i][j] = -color; // mark as visited
    count(i - 1, j, color);
    count(i + 1, j, color);
    count(i, j - 1, color);
    count(i, j + 1, color);
  }
}
