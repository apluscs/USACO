// 2018 December Contest, Gold  Problem 3. Teamwork 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class teamwork {
  public static int N, K;
  public static int[] sks;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("teamwork.in"));
        PrintWriter out = new PrintWriter(new File("teamwork.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      sks = new int[N + 1];
      for (int i = 1; i <= N; i++) 
        sks[i] = Integer.parseInt(in.readLine());
      int[] dp = new int[N + 1];
      for (int i = 1; i <= N; i++) {
        int max = 0;
        for (int j = i; j > 0 && i - j + 1 <= K; j--) { //test each possible place to start a new group
          max = Math.max(sks[j], max);  //max skill in the group
          int size = i - j + 1;     //of new group
          dp[i] = Math.max(dp[i], max * size + dp[j - 1]);
          // System.out.println(Arrays.toString(dp));
        }
      }
      // System.out.println(Arrays.toString(dp));
      out.println(dp[N]);
    }

  }

}
