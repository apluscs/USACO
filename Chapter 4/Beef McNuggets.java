import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class nuggets {
  // as i--> infinite, makable sizes come in increments of GCD. So if GCD==1, all sizes become
  // makable after a certain point Why? If int sn represents smallest size, you need sn consecutive
  // numbers before you can start making all of them. Example: sizes 3 and 10. After you make
  // 18,19,20, everything becomes makable. The "gaps" in between makable numbers shrink over time.
  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("nuggets.in"));
        PrintWriter out = new PrintWriter(new File("nuggets.out"));) {
      int N = f.nextInt();
      int[] sizes = new int[N];
      for (int i = 0; i < N; i++) {
        sizes[i] = f.nextInt();
      }
      int gcd = sizes[0]; // becomes GCD of all sizes
      for (int s : sizes) {
        gcd = gcd(s, gcd);
      }
      if (gcd != 1) {
        out.println(0); 
        return;
      } 
      out.println(lastImpossible(sizes));
    }
  }

  public static int lastImpossible(int[] sizes) {
    int max = 0; // if 1 appears in sizes, this returns 0
    boolean[] dp = new boolean[1 << 16 + 258];
    dp[0] = true;
    for (int i = 0; i <= 65536; i++) { // Worst case: 255,256. You get 255 numbers in a row ~< 256^2
      if (!dp[i]) {
        max = i;
      } else {
        for (int s : sizes) {
          dp[i + s] = true;
        }
      }
    }
      return max;
  }

  public static int gcd(int a, int b) {
    while (b > 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }
}
