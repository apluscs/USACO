import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class kimbits {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("kimbits.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));) {
      kimbits my = new kimbits();
      final int N = f.nextInt();
      final int maxOnes = f.nextInt();
      final long I = f.nextLong();
      long result = my.zoomTo(maxOnes, I, N);
      out.println(my.toBinaryString(result, N));
    }
  }

  private long zoomTo(int maxOnes, long I, int N) { //Note: works by "jumping" to the next binary that surpasses limit maxOnes
    long start = 0;
    long count = 0;
    while (count < I) {
      int numOnes = countOnes(start);
      long next = start + fill(maxOnes - numOnes + 1);// filled becomes next "overweight" binary
      count += next - start; // doesn't count next
      start = next + 1;
    }
    return start - 2 - (count - I); // start is 2 after what the count-th element
  }

  private int countOnes(long num) {
    int result = 0;
    while (num > 0) {
      result += num % 2;
      num >>= 1;
    }
    return result;
  }

  private long fill(int numOnes) {
    long filled = 0;
    for (int i = 0; i < numOnes; i++)
      filled = (filled << 1) + 1;
    return filled;
  }

  private String toBinaryString(long num, int N) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < N; i++) {
      if (num % 2 == 1)
        sb.append("1");
      else
        sb.append("0");
      num >>= 1;
    }
    return sb.reverse().toString(); // to deal with lack of prepend method
  }
}
