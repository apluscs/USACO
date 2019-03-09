// 2018 US Open Contest, Silver Problem 3. Multiplayer Moo
//Cannot pass Test 9 (TLE)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class multimoo {
  public static int N, size, currReg = 1, size2;
  public static int[][] grid;
  public static List[] adj;
  public static boolean[] vis;
  public static Region[] regs;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("multimoo.in"));
        PrintWriter out = new PrintWriter(new File("multimoo.out"));) {
      double start = System.currentTimeMillis();
      N = Integer.parseInt(in.readLine());
      grid = new int[N][N];
      regs = new Region[62502];// will not use all of it
      for (int i = 0; i < N; i++) {
        StringTokenizer st = new StringTokenizer(in.readLine());
        for (int j = 0; j < N; j++) {
          int c = Integer.parseInt(st.nextToken());
          grid[i][j] = c;
        }
      }
      int res1 = 0;
      for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
          if (grid[i][j] >= 0) {
            size = 0;
            int col = grid[i][j];
            flood(i, j, grid[i][j]);
            regs[currReg] = new Region(size, col, currReg);
            res1 = Math.max(res1, size);
            currReg++;
          }
      System.out.println("part 1: " + (System.currentTimeMillis() - start));
      start = System.currentTimeMillis();
      int res2 = teamwork();
      out.println(res1);
      out.println(res2);
      System.out.println("part 2: " + (System.currentTimeMillis() - start));
    }

  }

  @SuppressWarnings("unchecked")
  public static int teamwork() {
    int res2 = 0;
    adj = new List[currReg]; // after we know how many regions, to save space
    for (int i = 0; i < adj.length; i++)
      adj[i] = new ArrayList<Integer>();
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++) {
        int ind = -grid[i][j]; // of region
        if (i < N - 1 && grid[i + 1][j] != ind) {
          adj[ind].add(-grid[i + 1][j]);
          adj[-grid[i + 1][j]].add(ind);
        }
        if (j < N - 1 && grid[i][j + 1] != ind) {
          adj[ind].add(-grid[i][j + 1]);
          adj[-grid[i][j + 1]].add(ind);
        }
      } // now we have a graph of regions. Each pair of colors: largest combined size?
    for (int i = 1; i < currReg; i++) {
      List<Integer> ad = adj[i];
      for (int j : ad) {
        // System.out.println(i + " " + j);
        int a = regs[i].color; // two options available
        int b = regs[j].color;
        if (a != b) {
          size2 = 0;
          vis = new boolean[currReg];
          team(i, a, b);
          res2 = Math.max(res2, size2);
          // System.out.println("Region " + i + " and region " + j + " is " + size2 + " big.");
        }
      }
    }
    return res2;
  }

  public static void team(int curr, int col1, int col2) {
    if (curr == 0 || curr == currReg || regs[curr].color != col1 && regs[curr].color != col2)
      return;
    size2 += regs[curr].size;
    vis[curr] = true;
    List<Integer> ad = adj[curr];
    for (int i : ad)
      if (!vis[i] && (regs[i].color != col1 || regs[i].color != col2))
        team(i, col1, col2);
  }

  public static void flood(int i, int j, int color) {
    if (i < 0 || j < 0 || i == N || j == N || grid[i][j] != color)
      return;
    size++;
    grid[i][j] = -currReg;
    flood(i - 1, j, color);
    flood(i + 1, j, color);
    flood(i, j - 1, color);
    flood(i, j + 1, color);
  }

  private static class Region {
    int size, color, ind;

    public Region(int size, int color, int ind) {
      this.size = size;
      this.color = color;
      this.ind = ind;
    }

    public String toString() {
      return "Region " + ind + " is " + size + " big and " + color + " color.\n";
    }
  }
}
