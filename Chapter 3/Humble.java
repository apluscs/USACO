import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class humble {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("humble.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("humble.out")));) {
      final int K = f.nextInt();
      final int N = f.nextInt();
      int[] given = new int[K];
      for (int i = 0; i < K; i++) {
        int next = f.nextInt();
        given[i] = next;
      }
      humble my = new humble();
      out.println(my.findHumble(K, N, given));
    }
  }

  private long findHumble(int K, int N, int[] given) {
    long[] humble = new long[N + 1];
    humble[0] = 1;
    int[] pri = new int[K]; // pri[i] answers "what humble number (index) can I multiply given[i]
                            // with to produce a humble number JUST larger than the most recent
                            // humble number? Notes: saves time by ignoring the humble numbers so
                            // small that multiplying them with given[i] would give a product WAY
                            // smaller than the next larger humble number you are looking for
    for (int i = 1; i <= N; i++) {
      long min = Integer.MAX_VALUE;
      for (int k = 0; k < K; k++) {
        while (given[k] * humble[pri[k]] <= humble[i - 1]) { // bring each given's pri up to speed
          pri[k]++;
        }
        long curr = given[k] * humble[pri[k]];
        if (curr < min) {
          min = given[k] * humble[pri[k]];
        }
      }
        humble[i] = min;
      }
    return humble[N];
    }
  }
