import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class range {

  public static void main(String[] args) throws IOException {
    try (BufferedReader f = new BufferedReader(new FileReader("range.in"));
        PrintWriter out = new PrintWriter(new File("range.out"));) {
      range my = new range();
      int N = Integer.parseInt(f.readLine());
      int[][] field = new int[N][N];
      for (int i = 0; i < N; i++) {
        String line = f.readLine();
        for (int j = 0; j < N; j++) {
          field[i][j] = line.charAt(j) - '0';
        }
      }
      int[] result = my.countSquares(N, field);
      for (int s = 2; s <= N; s++) {
        if (result[s] == 0) // no squares of this size, so skip
          continue;
        out.println(s + " " + result[s]);
      }
    }
  }

  private int[] countSquares(int N, int[][] field) { // field[i][j]=max size of square(bottom right
                                                     // corner is i,j) that can fit here
    int[] result = new int[N + 1];
    for (int i = 1; i < N; i++) {
      for (int j = 1; j < N; j++) {
        if (field[i][j] == 0)
          continue;
        int min = Math.min(field[i][j - 1], field[i - 1][j]);
        min = Math.min(min, field[i - 1][j - 1]);
        field[i][j] = min + 1;
      }
    }
    for (int i = 1; i < N; i++) {
      for (int j = 1; j < N; j++) {
        int max = field[i][j];
        for (int s = max; s >= 2; s--) { // any smaller square can fit here too!
          result[s]++;
        }
      }
    }
    return result;
  }
}
