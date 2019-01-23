import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class inflate {

  public static void main(String[] args) throws IOException {
    try (BufferedReader f = new BufferedReader(new FileReader("inflate.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("inflate.out")));) {
      StringTokenizer st = new StringTokenizer(f.readLine());
      final int M = Integer.parseInt(st.nextToken());
      final int N = Integer.parseInt(st.nextToken());
      int[][] probs = new int[N][2]; // points, minutes
      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(f.readLine());
        probs[i][0] = Integer.parseInt(st.nextToken());
        probs[i][1] = Integer.parseInt(st.nextToken());
      }
      inflate my = new inflate();
      out.println(my.inflatePoints(probs, M, N));
    }

  }

  private int inflatePoints(int[][] probs, int M, int N) {
    // https://usacosolutions.wordpress.com/2012/09/29/usaco-3-1-prog-inflate/
    // http://www.cppblog.com/master0503/articles/59449.html
    int[] maxPoints = new int[M + 1];
    for (int c = 0; c < N; c++) {

      int timeCost = probs[c][1];
      int points = probs[c][0];
      for (int t = timeCost; t <= M; t++) {
        int useThisCat = maxPoints[t - timeCost] + points; // if you choose to use this problem,
                                                           // must examine maxPoints to allot
                                                           // enough time for the problem
        maxPoints[t] = Math.max(useThisCat, maxPoints[t]);
      }
      // Notes: points increment at every a problem is run to COMPLETION; every minute in between
      // these perfect multiples of timeCost is regarded as "wasted time"
    }
    return maxPoints[M];
  }
}
