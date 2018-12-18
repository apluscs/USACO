/* Farmer John went crazy and created a huge maze of fences out in a field. Happily, he left out two fence segments on the 
edges, and thus created two "exits" for the maze. Even more happily, the maze he created by this overfencing experience is a 
`perfect' maze: you can find a way out of the maze from any point inside it.

Given W (1 <= W <= 38), the width of the maze; H (1 <= H <= 100), the height of the maze; 2*H+1 lines with width 2*W+1 
characters that represent the maze in a format like that shown later - then calculate the number of steps required to exit the 
maze from the `worst' point in the maze (the point that is `farther' from either exit even when walking optimally to the closest 
exit). Of course, cows walk only parallel or perpendicular to the x-y axes; they do not walk on a diagonal. Each move to a new 
square counts as a single unit of distance (including the move "out" of the maze.

Here's what one particular W=5, H=3 maze looks like:

+-+-+-+-+-+
|         |
+-+ +-+ + +
|     | | |
+ +-+-+ + +
| |     |  
+-+ +-+-+-+

Fenceposts appear only in odd numbered rows and and odd numbered columns (as in the example). The format should be obvious and 
self explanatory. Each maze has exactly two blank walls on the outside for exiting.

INPUT FORMAT
Line 1: 	W and H, space separated
Lines 2 through 2*H+2: 	2*W+1 characters that represent the maze

SAMPLE INPUT (file maze1.in)
5 3
+-+-+-+-+-+
|         |
+-+ +-+ + +
|     | | |
+ +-+-+ + +
| |     |  
+-+ +-+-+-+

OUTPUT FORMAT
A single integer on a single output line. The integer specifies the minimal number of steps that guarantee a cow can exit the 
maze from any possible point inside the maze.

SAMPLE OUTPUT (file maze1.out)
9
The lower left-hand corner is *nine* steps from the closest exit. */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class maze1 {

  public static int W;
  public static int H;
  public static int[][][] grid; // ind 4 = min dist to exit 1, ind 5 = min dist to exit 2
  public static int[] exit1 = new int[] {-1, -1};
  public static int[] exit2 = new int[] {-1, -1};

  public static void main(String[] args) {
    try (BufferedReader f = new BufferedReader(new FileReader("maze1.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maze1.out")));) {
      StringTokenizer st = new StringTokenizer(f.readLine());
      W = Integer.parseInt(st.nextToken());
      H = Integer.parseInt(st.nextToken());
      grid = new int[H][W][6]; // n, e, s, w, exit1 dist, exit2 dist
      int i = 0;

      while (f.ready() && i < H) {
        String horz = f.readLine();
        for (int j = 0; j < W; j++) { // notes north walls
          if (horz.charAt(2 * j + 1) == '-') {
            grid[i][j][0] = 1;
          }
        }
        if (!f.ready()) {
          break;
        } // last line comes single, not in a pair
        String vert = f.readLine();
        for (int j = 0; j < W; j++) {
          if (vert.charAt(2 * j) == '|') {
            grid[i][j][3] = 1; // notes west walls
          }
        }
        if (vert.charAt(vert.length() - 1) == '|') { // notes eastmost walls
          grid[i][W - 1][1] = 1;
        }
        i++;
      }
      String horz = f.readLine();
      for (int j = 0; j < W; j++) {
        if (horz.charAt(2 * j + 1) == '-') { // notes southmost walls
          grid[i - 1][j][2] = 1;
        }
      }
      for (int k = 0; k < H; k++) {
        for (int j = 0; j < W - 1; j++) {
          if (grid[k][j + 1][3] > 0) { // east neighbor has west wall
            grid[k][j][1] = 1; // then this must have an east wall
          }
        }
      }
      for (int k = 0; k < H - 1; k++) {
        for (int j = 0; j < W; j++) {
          if (grid[k + 1][j][0] > 0) { // south neighbor has north wall
            grid[k][j][2] = 1; // then this must have an south wall
          }
        }
      } // now each cell has [n, e, s, w] noted
      findExits();
      fillDists(exit1, 4);
      fillDists(exit2, 5);
      int result = 0;
      for (int[][] s : grid) {
        for (int[] t : s) {
          int min = Math.min(t[4], t[5]); // shortest route out
          result = Math.max(result, min);
        }
      }
      out.println(result);
    } catch (IOException e) {
    }
  }

  public static void fillDists(int[] start, int exit) {
    List<int[]> toVisit = new ArrayList<>();
    toVisit.add(start);
    grid[start[0]][start[1]][exit] = 1;
    while (toVisit.isEmpty() != true) { // fills each of curr's unfilled (value = 0) neighbor
      int[] curr = toVisit.get(0);
      int row = curr[0];
      int col = curr[1];
      if (grid[row][col][0] != 1 && grid[row - 1][col][exit] == 0) { // checks for north wall
        grid[row - 1][col][exit] = grid[row][col][exit] + 1;
        toVisit.add(new int[] {row - 1, col});
      }
      if (grid[row][col][1] != 1 && grid[row][col + 1][exit] == 0) { // checks for east wall
        grid[row][col + 1][exit] = grid[row][col][exit] + 1;
        toVisit.add(new int[] {row, col + 1});
      }
      if (grid[row][col][2] != 1 && grid[row + 1][col][exit] == 0) { // checks for south wall
        grid[row + 1][col][exit] = grid[row][col][exit] + 1;
        toVisit.add(new int[] {row + 1, col});
      }
      if (grid[row][col][3] != 1 && grid[row][col - 1][exit] == 0) { // checks for west wall
        grid[row][col - 1][exit] = grid[row][col][exit] + 1;
        toVisit.add(new int[] {row, col - 1});
      }
      toVisit.remove(0);
    }
  }

  public static void findExits() {
    for (int k = 0; k < W; k++) { // if exit is on north side
      if (grid[0][k][0] == 0) {
        if (exit1[0] < 0) {
          exit1[0] = 0;
          exit1[1] = k;
        } else if (exit2[0] < 0) { // if exit1 was already found
          exit2[0] = 0;
          exit2[1] = k;
        }
        grid[0][k][0] = 1; // marks as north wall [so we don't run into trouble in fill dists]
      }
      if (grid[H - 1][k][2] == 0) { // if exit is on south side
        if (exit1[0] < 0) {
          exit1[0] = H - 1;
          exit1[1] = k;
        } else if (exit2[0] < 0) { // if exit1 was already found
          exit2[0] = H - 1;
          exit2[1] = k;
        }
        grid[H - 1][k][2] = 1; // marks as south wall
      }
    }
    for (int k = 0; k < H; k++) { // if exit is on west side
      if (grid[k][0][3] == 0) {
        if (exit1[0] < 0) {
          exit1[0] = k;
          exit1[1] = 0;
        } else if (exit2[0] < 0) { // if exit1 was already found
          exit2[0] = k;
          exit2[1] = 0;
        }
        grid[k][0][3] = 1; // marks as west wall
      }
      if (grid[k][W - 1][1] == 0) { // if exit is on east side
        if (exit1[0] < 0) {
          exit1[0] = k;
          exit1[1] = W - 1;
        } else if (exit2[0] < 0) { // if exit1 was already found
          exit2[0] = k;
          exit2[1] = W - 1;
        }
      }
      grid[k][W - 1][1] = 1; // marks as east wall
    }
  }
}
