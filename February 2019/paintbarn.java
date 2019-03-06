// package usaco;

// February 2019 Silver
// redo with sweepline
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class paintbarn {
  public static int N, K;
  public static int[][] grid;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("paintbarn.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("paintbarn.out")));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      grid = new int[1003][1003]; // to allows for i-1,j-1 in fillFast
      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(in.readLine());
        int x1 = Integer.parseInt(st.nextToken()) + 1;
        int y1 = Integer.parseInt(st.nextToken()) + 1;
        int x2 = Integer.parseInt(st.nextToken()) + 1;
        int y2 = Integer.parseInt(st.nextToken()) + 1;
        // fill(x1, y1, x2, y2);
        fillFast(x1, y1, x2, y2);
      }
      int result = sweepFast();
      out.println(result);
    }
  }

  public static int sweepFast() {
    int result = 0;
    for (int i = 1; i <= 1001; i++) { // think of as upper right corner
      for (int j = 1; j <= 1001; j++) {
        grid[i][j] -= grid[i - 1][j - 1];
        grid[i + 1][j] += grid[i][j];
        grid[i][j + 1] += grid[i][j];
        if (grid[i][j] == K)
          result++;
      }
    }
    return result;
  }

  public static void fillFast(int x1, int y1, int x2, int y2) {
    grid[x1][y1]++;
    grid[x1][y2]--;
    grid[x2][y1]--;
    grid[x2][y2]++;
  }

  public static int sweep() {
    int result = 0;
    for (int i = 1; i <= 1001; i++)
      for (int j = 1; j <= 1001; j++) {
        grid[i][j + 1] += grid[i][j]; // update next value
        if (grid[i][j] == K)
          result++;
      }
    return result;
  }
  public static void fill(int x1, int y1, int x2, int y2) {
    for (int i = x1; i < x2; i++) {
      grid[i][y1]++;
      grid[i][y2]--;
    }
  }
}