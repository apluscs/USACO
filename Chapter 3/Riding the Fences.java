import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class fence {
  private static int maxPost = 0;
  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("fence.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence.out")));) {
      final int F = in.nextInt();
      int[][] fences = new int[501][501];
      int[] degree = new int[501];
      int oddDeg = 1;

      for (int i = 0; i < F; i++) {
        int a = in.nextInt();
        int b = in.nextInt();
        fences[a][b]++;
        fences[b][a]++; // may be >1 fence connecting two intersections
        degree[a]++;
        degree[b]++;
        maxPost = a > maxPost ? a : maxPost;
        maxPost = b > maxPost ? b : maxPost;
      }
      for (int i = 1; i <= 500; i++) {
        if (degree[i] % 2 == 1) {
          oddDeg = i;
          break;
        }
      }
      fence my = new fence();
      my.findEulerPath(oddDeg, fences, degree);
      for (int i = F; i >= 0; i--)
        out.println(path[i]);
    }
  }

  private static int[] path = new int[1025];    
  private static int count = 0;

  private void findEulerPath(int curr, int[][] fences, int[] degree) {
    if (degree[curr] == 0) {
      path[count++] = curr;
      return;
    }
    for (int i = 1; i <= maxPost; i++) { // must be linear traversal (not random) to ensure smallest result
      if (fences[i][curr] > 0) {
        fences[i][curr]--;
        fences[curr][i]--;
        degree[i]--;
        degree[curr]--;
        findEulerPath(i, fences, degree);
      }
    }
    path[count++] = curr;
  }
}
