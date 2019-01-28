import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class fence9 {

  public static void main(String[] args) throws IOException {
    // Pick's theorem: area = #points inside + boundaryPoints/2-1
    try (Scanner in = new Scanner(new File("fence9.in"));
        PrintWriter out = new PrintWriter(new File("fence9.out"));) {
      fence9 my = new fence9();
      int n, m, p;
      n = in.nextInt();
      m = in.nextInt();
      p = in.nextInt();
      int area = p * m / 2;
      int bound = p + my.gcd(n, m) + my.gcd(Math.abs(p - n), m);
      // gcd(n,m) counts boundary pts on ascending line. gcd(Math.abs(p - n), m) counts on
      // descending line.
      out.println(area - bound / 2 + 1);
    }
  }

  private int gcd(int a, int b) { // b is never 0 because m!=0. If a==0, it returns b.
    while (b > 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

}
