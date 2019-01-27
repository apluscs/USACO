import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class game1 {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("game1.in"));
        PrintWriter out = new PrintWriter(new File("game1.out"));) {
      game1 my = new game1();
      int N = f.nextInt();
      int[] board = new int[N + 1];
      for (int i = 1; i <= N; i++) {
        board[i] = f.nextInt();
      }
      int[] result = my.optimizeGame(board,N);
      out.println(result[0] + " " + result[1]);
    }
  }

  private int[] optimizeGame(int[] board, int N) {
    int[][] partSum = new int[N + 1][N + 1];
    int[][] scores = new int[N + 2][N + 1];
    // max additional score if row# represents (index of) leftmost number and column# represents
    // (index of) rightmost number. Extra row at bottom for indexing buffer
    for (int i = 1; i <= N; i++) {
      for (int j = i; j <= N; j++) {
        partSum[i][j] = partSum[i][j - 1] + board[j];
      }
    }
    int[] result = new int[2];
    for (int top = 1; top <= N; top++) {
      for (int i = 1; i + top - 1 <= N; i++) { // fills in diagonally but always starts from top row
        int j = i + top - 1;
        int case1 = partSum[i][j] - scores[i + 1][j]; // if you took leftmost
        int case2 = partSum[i][j] - scores[i][j - 1]; // if you took rightmost
        scores[i][j] = Math.max(case1, case2);
      }
    }
    result[0] = scores[1][N];
    result[1] = Math.min(scores[1][N - 1], scores[2][N]); // because P1's score was derived by
                                                          // making sure P2 scores as little as
                                                          // possible
    return result;
  }
}
