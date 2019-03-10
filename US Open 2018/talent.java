import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class talent {
  public static int N, W;
  public static int[][] nums;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("talent.in"));
        PrintWriter out = new PrintWriter(new File("talent.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      W = Integer.parseInt(st.nextToken());
      nums = new int[N + 1][2];
      for (int i = 1; i <= N; i++) {
        st = new StringTokenizer(in.readLine());
        nums[i][1] = Integer.parseInt(st.nextToken());
        nums[i][0] = Integer.parseInt(st.nextToken());
      }
      int result = maxDensity();
      out.print(result);
    }
  }

  public static int maxDensity() {
    int result = 0;
    int[][] dp = new int[N + 1][W + 2]; // W: talent, W+1: weight
    for (int i = 1; i <= N; i++) {
      int[] max = new int[] {0, 1};// tracks best qualified group that can be added to nums[i]
      for (int j = 1; j < W; j++) { // 1 ensures no divison by 0 error
        dp[i][j] = dp[i - 1][j];
        int needed = j - nums[i][1];
        if (needed == 0) // only need you to fill this weight
          dp[i][j] = Math.max(dp[i][j], nums[i][0]);
        else if (needed > 0 && dp[i - 1][needed] > 0)
          dp[i][j] = Math.max(dp[i][j], dp[i - 1][needed] + nums[i][0]);
        if (j + nums[i][1] >= W) {
          updateMax(max, dp[i - 1][j], j); // we get to last column after j loop
        }
      }

      if (dp[i - 1][W + 1] > 0) // found a valid group
        updateMax(max, dp[i - 1][W], dp[i - 1][W + 1]); // real denom=dp[i-1][W+1], not j this time
      if (max[0] == 0)
        max[1] = 0; // not updated at all, adjust back for addition
      int[] best = findBest(nums[i], new int[] {dp[i - 1][W], dp[i - 1][W + 1]});
      int[] added = new int[] {max[0] + nums[i][0], max[1] + nums[i][1]};
      best = findBest(best, added);//3 options: nums[i], row above, max+nums[i]
      dp[i][W] = best[0];
      dp[i][W + 1] = best[1];
      if (dp[i][W] != 0) {        // update result
        double temp = (double) dp[i][W] / dp[i][W + 1] * 1000;
        result = Math.max(result, (int) temp);
      }
    }
    return result;
  }

  public static void updateMax(int[] max, int tal, int wt) {
    double mRto = (double) max[0] / max[1];
    double cRto = (double) tal / wt;
    if (cRto > mRto) {
      max[0] = tal;
      max[1] = wt;
    }
  }

  public static int[] findBest(int[] a, int[] b) { // a, b, or both? weight must >= W
    if (a[1] < W && b[1] < W)
      return new int[2];
    else if (a[1] < W && b[1] >= W)
      return b;
    else if (a[1] >= W && b[1] < W)
      return a;
    double aRto = (double) a[0] / a[1];
    double bRto = (double) b[0] / b[1];
    if (aRto > bRto)
      return a;
    return b;
  }
}
