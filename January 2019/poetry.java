// 2019 January Contest, Gold Problem 1. Cow Poetry
/* 1)dp[k-word.length]=#ways to make a line ending with a word of that length
 * 2)sum (all these ways for every word in a class) = #ways to make a line of that class
 * 3)sum^(frq of rhyme type)=#ways to use that class for that rhyme type
 * 4)sum(all these powers for every class)=#ways to implement that rhyme type
 * 5)multiply(the sums for every rhyme type)=#ways to fill all rhyme types=#ways to write poem
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class poetry {
  public static HashMap<Integer, Integer> map;
  public static int N, M, K, MOD = 1000000007;
  public static int[][] words;
  public static int[] frqs;
  public static long[] dp;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("poetry.in"));
        PrintWriter out = new PrintWriter(new File("poetry.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      map = new HashMap<>();
      words = new int[N][2];
      frqs = new int[26];
      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(in.readLine());
        int l = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        words[i][0] = l;
        words[i][1] = c;
      }
      for (int i = 0; i < M; i++) 
        frqs[in.readLine().charAt(0) - 'A']++;
      makeLines();
      long res = 1;

      for (int f : frqs) {
        if (f == 0)
          continue;
        long sum = 0;
        for (int c : map.values()) {
          sum += exp(c, f); //ex. 3 lines of type A, 7 ways to make a line A with line 1
          //= 7^3 ways to fill all A's with class 1
          sum %= MOD;   //consider all possible classes
        }
        res = (sum * res) % MOD;    //ex. sum(A)*sum(B)*....
      }
      out.println(res);
    }
  }

  public static int exp(int base, int pow) {
    if (pow == 0)
      return 1;
    if (pow == 1)
      return base;
    long r = exp(base, pow / 2);
    long res = (r * r) % MOD;
    if (pow % 2 == 1)
      res = (res * base) % MOD;
    return (int) res;
  }
  public static void makeLines() {
    dp = new long[K + 1];
    dp[0] = 1;
    for (int i = 1; i <= K; i++) 
      for (int j = 0; j < N; j++) {
        int l = words[j][0];
        if (i - l >= 0) {
          dp[i] += dp[i - l];
          dp[i] %= MOD;
        }
      }
    for (int[] w : words) {
      int c = w[1];
      int l = w[0];
      long add = dp[K - l] + map.getOrDefault(c, 0);
      map.put(c, (int) (add % MOD));    //value=#ways to make a line of that class c
    }
  }
}
