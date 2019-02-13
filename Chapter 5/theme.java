import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class theme {
  public static int[] nums;
  public static int N;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("theme.in"));
        PrintWriter out = new PrintWriter(new File("theme.out"));) {
      N = Integer.parseInt(in.readLine());
      nums = new int[N]; // first is 0
      StringTokenizer st = new StringTokenizer(in.readLine());
      int prev = Integer.parseInt(st.nextToken());
      for (int i = 1; i < N; i++) {
        int curr = Integer.parseInt(st.nextToken());
        if (!st.hasMoreTokens() && in.ready())
          st = new StringTokenizer(in.readLine());
        nums[i] = curr - prev; // tracks differences between notes
        prev = curr;
      }
      int result = lrSub();
      if (result < 5)
        result = 0;
      out.println(result);
    }
  }

  public static int lrSub() {
    int[] curr = new int[N]; // only need to keep last known array and curr array
    int result = 0;
    for (int i = 1; i < N; i++) {
      int[] next = new int[N]; // dp[i][j] = longest repeated substring of any nums[0->i] in the
                               // range nums[0->j]
      for (int j = i + 1; j < N; j++) {
        if (nums[i] == nums[j]) { // we can lengthen the longest substring!
          next[j] = curr[j - 1] + 1;
          if (j - next[j] == i) // overlap! Why? j=start of repeated segment; i=start of initial
                                // segment. If (i+len==j)
            next[j] = 0; // last part of initial segment = beginning of repeated segment
          result = Math.max(result, next[j]);
        }
      }
      curr = next;
    }
    return result + 1; // n intervals implies n+1 notes
  }
}
