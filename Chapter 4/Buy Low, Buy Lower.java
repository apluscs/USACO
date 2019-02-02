import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class buylow {
  public static int[] pr = new int[5001];
  public static Pair[] best = new Pair[5001];
  public static int N;

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("buylow.in"));
        PrintWriter out = new PrintWriter(new File("buylow.out"));) {
      buylow my = new buylow();
      N = in.nextInt();
      for (int i = 0; i < N; i++) {
        pr[i] = in.nextInt();
      }
      my.findLDS();
      out.print(best[N].len - 1 + " ");
      for (int b : best[N].big) {
        out.print(b);
      }
      out.println();
    }
  }

  private void findLDS() {
    for (int i = 0; i <= N; i++) {
      best[i] = new Pair();
      for (int j = 0; j < i; j++) {
        if (pr[j] > pr[i])
          best[i].len = Math.max(best[i].len, best[j].len + 1);
      }
      if (best[i].len == 1)
        best[i].big.add(1);
      else {
        HashSet<Integer> z = new HashSet<>();
        for (int k = i - 1; k >= 0; k--) { // must go in reverse order because last elements will
                                           // contain largest s value
          if (best[i].len == best[k].len + 1 && pr[k] > pr[i] && !z.contains(pr[k])) { 
            z.add(pr[k]);// an unencountered "parent" to i
            best[i].big = add(best[i].big, best[k].big);
          }
        }
      }
    }
  }

  private List<Integer> add(List<Integer> a, List<Integer> b) {
    int sizeAB = Math.max(a.size(), b.size());
    for (int i = a.size(); i < sizeAB; i++) a.add(0, 0); // fill with 0's so we don't have to deal with null pointers
    for (int i = b.size(); i < sizeAB; i++) b.add(0, 0);
    List<Integer> C = new ArrayList<>();
    int carry = 0;
    for (int pos = sizeAB - 1; pos >= 0; pos--) { // start from units digit -> highest order
      int sum = a.get(pos) + b.get(pos) + carry;
      C.add(0, sum % 10); // always add to front
      carry = sum / 10;
    }
    if (carry != 0)
      C.add(0, carry); // overflow
    return C;
  }

  private static class Pair {
    int len;
    List<Integer> big;
    Pair() {
      len = 1; // maximum length of LDS ending at index i
      big = new ArrayList<>(); // number of instances, recorded as a bigNum
    }
  }
}
