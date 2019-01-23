import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class stamps {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("stamps.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("stamps.out")));) {
      final int limit = f.nextInt();
      final int N = f.nextInt();
      int[] stamps = new int[N];
      for (int i = 0; i < N; i++) {
        stamps[i] = f.nextInt();
      }
      stamps my = new stamps();
      int result = my.maxContiguousVal(stamps, limit);
      out.println(result);
    }
  }

  private int maxContiguousVal(int[] stamps, int limit) {
    int[] minWays = new int[2000001]; // 10000 x 200
    for (int v = 1; v < 2000001; v++) {
      int minStamps = Integer.MAX_VALUE;
      for (int st : stamps) {
        if (v - st >= 0) {
          minStamps = Math.min(minStamps, minWays[v - st]); //minimum #stamps needed to make this value, working 
          //backwards from stamps[] (considers stamp st being last stamp used)
        }
      }
      if (minStamps + 1 > limit || minStamps == Integer.MAX_VALUE) { 
        return v - 1;// unable to make this value (v) or takes too many stamps
      }
      minWays[v] = minStamps + 1;
    }
    return 404;
  }
}
