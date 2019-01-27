import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class camelot {

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("camelot.in"));
        PrintWriter out = new PrintWriter(new File("camelot.out"));) {
      int[][] kni = new int[1001][2]; // kn will cap off the irrelevant rows
      int[] king = new int[2]; // row, column
      int kn = 0, R, C;
      int[][][][] m = new int[32][32][32][32]; // #moves from (a,b) to (c,d)
      int dr[] = {-2, -1, 1, 2, 2, 1, -1, -2};
      int dc[] = {1, 2, 2, 1, -1, -2, -2, -1};
      camelot my = new camelot();
      R = in.nextInt();
      C = in.nextInt();
      king[1] = in.next().charAt(0) - 'A' + 1;
      king[0] = in.nextInt();
      while (in.hasNext()) {
        kni[++kn][1] = in.next().charAt(0) - 'A' + 1;
        kni[kn][0] = in.nextInt();
      }
      for (int sr = 1; sr <= 31; sr++) {
        for (int sc = 1; sc <= 31; sc++) {
          for (int i = 1; i <= 31; i++) {
            for (int j = 1; j <= 31; j++) {
              m[sr][sc][i][j] = 1000; // large number but not that large (int overflow)
            }
          }
        }
      }
      out.println(my.winCamelot(kni, king, kn, R, C, m, dr, dc));
    }
  }

  private int winCamelot(int[][] kni, int[] king, int kn, int R, int C, int[][][][] m, int dr[],
      int dc[]) {
    for (int sr = 1; sr <= R; sr++) {
      for (int sc = 1; sc <= C; sc++) { // for each source cell
        Queue<int[]> toVisit = new LinkedList<int[]>();
        toVisit.offer(new int[] {sr, sc});
        m[sr][sc][sr][sc] = 0;
        while (!toVisit.isEmpty()) {
          int[] pos = toVisit.poll();
          for (int d = 0; d != 8; d++) // every possible move
          {
            int tr = pos[0] + dr[d];
            int tc = pos[1] + dc[d];
            if (tr > 0 && tc > 0 && tr <= R && tc <= C && m[sr][sc][tr][tc] == 1000) // hasn't
                                                                                     // visited
                                                                                   // this cell
            {
              m[sr][sc][tr][tc] = m[sr][sc][pos[0]][pos[1]] + 1;
              toVisit.offer(new int[] {tr, tc});
            }
          }
        }
      }
    }
    int l = 2; // bound of search
    int ksr = king[0] - l < 1 ? 1 : king[0] - l;
    int ksc = king[1] - l < 1 ? 1 : king[1] - l;
    int ker = king[0] + l > R ? R : king[0] + l;
    int kec = king[1] + l > C ? C : king[1] + l;
    int result = Integer.MAX_VALUE;
    for (int ar = 1; ar <= R; ar++) {
      for (int ac = 1; ac <= C; ac++) { // testing each cell as potential answer
        int kniStep = 0;
        for (int k = 1; k <= kn; k++) {
          kniStep += m[ar][ac][kni[k][0]][kni[k][1]];
        }
        int kingStep = Math.max(Math.abs(king[0] - ar), Math.abs(king[1] - ac));
        for (int pi = ksr; pi <= ker; pi++)
          for (int pj = ksc; pj <= kec; pj++) // tests each "pickup" location for the king
            for (int k = 1; k <= kn; k++) // any potential transporter
            {
              int kingToPickup = Math.max(Math.abs(king[0] - pi), Math.abs(king[1] - pj));
              int tmp = kingToPickup + m[kni[k][0]][kni[k][1]][pi][pj] + m[pi][pj][ar][ac]
                  - m[kni[k][0]][kni[k][1]][ar][ac];
              if (tmp < kingStep) {
                kingStep = tmp;
              }


            }
        if (kniStep + kingStep < result)
          result = kingStep + kniStep;
      }
    }
    return result;
  }

}
