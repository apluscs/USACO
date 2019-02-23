// package usaco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class convention {

  public static int N, M, C;

  public static void main(String[] args) {
    // randomGen jen = new randomGen(1, 15, 10, 1);
    // System.out.println(jen.result);
    try (BufferedReader f = new BufferedReader(new FileReader("convention.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("convention.out")));) {
      StringTokenizer st = new StringTokenizer(f.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      C = Integer.parseInt(st.nextToken());
      int[] cows = new int[N];
      int i = 0;
      while (f.ready()) {
        st = new StringTokenizer(f.readLine());
        while (st.hasMoreTokens()) {
          cows[i++] = Integer.parseInt(st.nextToken());
        }
      }
      Arrays.sort(cows);
      System.out.println(Arrays.toString(cows));
      out.println(maxWait(cows));
    } catch (IOException e) {
    }
  }

  public static int maxWait(int[] cows) {
    int l = 0, r = 1000000000;
    while (l <= r) {
      int m = (l + r) >>> 1;
      if (helper(m, cows))
        r = m - 1;
      else
        l = m + 1;
    }
    return l;
  }

  public static boolean helper(int wait, int[] cows) {
    int leave = cows[0] + wait;
    int bus = 1;
    int on = 0;
    for (int i = 0; i < N; i++) {
      if (on == C || cows[i] > leave) {
        bus++;
        on = 1;
        leave = cows[i] + wait;
      } else {
        on++;
      }
    }
    return bus <= M;
  }

}


