import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class sort {

  public static int[][] nums;
  public static int N;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("sort.in"));
        PrintWriter out = new PrintWriter(new File("sort.out"));) {
      N = Integer.parseInt(in.readLine());
      nums = new int[N][2];
      for (int i = 0; i < N; i++) {
        nums[i][0] = Integer.parseInt(in.readLine());
        nums[i][1] = i; // original pos
      }
      Arrays.sort(nums, (a, b) -> {
        if (a[0] != b[0]) 
          return a[0] - b[0];
        return a[1] - b[1];
      });
      int result = 0;
      for (int i = 0; i < N; i++) 
        result = Math.max(result, nums[i][1] - i);
      out.println(result + 1);
    }
  }
}
