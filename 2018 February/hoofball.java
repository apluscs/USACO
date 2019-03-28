 package usacoPractice;
// 2018 February Contest, Bronze Problem 2. Hoofball 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class hoofball {
  public static int N;
  public static int[] from, frq, partner;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("hoofball.in"));
        PrintWriter out = new PrintWriter(new File("hoofball.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      if (N == 1) {
        out.println(1);
        return;
      }
      from = new int[N];
      frq = new int[N];
      st = new StringTokenizer(in.readLine());
      for (int i = 0; i < N; i++) {
        from[i] = Integer.parseInt(st.nextToken());
      }
      Arrays.sort(from);
      int res = solve();
      out.println(res);
    }
  }

  public static int solve() {
    for (int i = 0; i < N; i++) {
      frq[throwTo(i)]++;
    }
    System.out.println(Arrays.toString(frq));
    int res = 0;
    for (int i = 0; i < N; i++) {
      if (frq[i] == 0)
        res++;
      else {
        int p = throwTo(i);
        if (throwTo(p) == i && i < p && frq[p] == 1 && frq[i] == 1) {
          res++;
          System.out.println(i + " " + p);
        }
      }
    }
    return res;
  }

  public static int throwTo(int i) { // index of cow it throws to
    if (i == 0)
      return 1;
    if (i == N - 1)
      return N - 2;
    if (from[i] - from[i - 1] <= from[i + 1] - from[i])
      return i - 1;
    else
      return i + 1;

  }
}
