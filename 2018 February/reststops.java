package usacoPractice;

// 2018 February Contest, Silver Problem 1. Rest Stops
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class reststops {
  public static int N, L, rF, rB;
  public static int[][] stops;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("reststops.in"));
        PrintWriter out = new PrintWriter(new File("reststops.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      L = Integer.parseInt(st.nextToken());
      N = Integer.parseInt(st.nextToken());
      rF = Integer.parseInt(st.nextToken());
      rB = Integer.parseInt(st.nextToken());
      stops = new int[N][2];
      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(in.readLine());
        stops[i][0] = Integer.parseInt(st.nextToken());
        stops[i][1] = Integer.parseInt(st.nextToken());
      }
      long res = solve();
      out.println(res);
    }
  }

  public static long solve() {
    long res = 0;
    Stack<Integer> st = new Stack<>(); // stores indexes of "best value" stops
    Arrays.sort(stops, (a, b) -> (a[0] - b[0])); // left to right
    for (int i = N - 1; i != -1; i--) {
      if (st.isEmpty() || stops[st.peek()][1] < stops[i][1]) {
        st.push(i);
      } // Bessie exploits best value stop until John catches up, then moves on to next best
    }
    int prev = 0; // pos of last stop used
    while (!st.isEmpty()) {
      int[] curr = stops[st.pop()];
      res += (rF - rB) * (curr[0] - prev) * (long) curr[1];
      prev = curr[0];
    }
    return res;
  }
}
