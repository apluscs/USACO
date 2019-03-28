package usacoPractice;

// 2018 February Contest, Bronze Problem 3. Taming the Herd
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class taming {
  public static int N;
  public static int[] nums;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("taming.in"));
        PrintWriter out = new PrintWriter(new File("taming.out"));) {
      N = Integer.parseInt(in.readLine());
      nums = new int[N];
      StringTokenizer st = new StringTokenizer(in.readLine());
      for (int i = 0; i < N; i++)
        nums[i] = Integer.parseInt(st.nextToken());
      nums[0] = 0;
      for (int i = N - 1; i != -1; i--) {
        if (nums[i] < 1)
          continue;
        int start = nums[i];
        while (i != -1 && start != -1) {
          if (nums[i] != -1 && nums[i] != start) { // inconsistent
            out.println(-1);
            return;
          }
          nums[i--] = start--;
        }
        i++;
      }
      System.out.println(Arrays.toString(nums));
      int myst = 0, zer = 0;
      for (int i = 0; i < N; i++) {
        if (nums[i] == 0)
          zer++;
        else if (nums[i] == -1)
          myst++;
      }
      out.println(zer + " " + (zer + myst));
    }
  }
}
