// February 2019 Silver
// redo with shortcut (min - count cows in place, max - count gaps)

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class herding {
  public static int N, L;
  public static int[] locs;
  public static boolean[] cows;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("herding.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("herding.out")));) {
      N = Integer.parseInt(in.readLine());
      locs = new int[N];
      cows = new boolean[10001];
      for (int i = 0; i < N; i++) {
        locs[i] = Integer.parseInt(in.readLine()) - 1;
        cows[locs[i]] = true;
        L = Math.max(L, locs[i]);
      }
      int[] res = getCosts();
      out.println(res[0]);
      out.println(res[1]);
    }
  }

  public static int[] getCosts() {
    int leftCows = 0, rightCows = N;
    for (int i = 0; i < N; i++) {
      rightCows -= cows[i] ? 1 : 0;
    }
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int temp = getCost(0, N - 1, leftCows, rightCows);
    if (temp != -1) {
      min = Math.min(min, temp);
      max = Math.max(max, temp);
    }

    for (int i = N; i <= L; i++) {
      leftCows += cows[i - N] ? 1 : 0;
      rightCows -= cows[i] ? 1 : 0;
      temp = getCost(i - N + 1, i, leftCows, rightCows);
      if (temp == -1)
        continue;
      min = Math.min(min, temp);
      max = Math.max(max, temp);
    }
    return new int[] {min, max};
  }

  public static int getCost(int ldex, int rdex, int leftCows, int rightCows) {
    if (cows[ldex] && cows[rdex])
      return leftCows + rightCows;
    if (cows[ldex] && rightCows >= 2)
      return rightCows;
    if (cows[rdex] && leftCows >= 2)
      return leftCows;
    if (cows[ldex] || cows[rdex])
      return -1;// after = has no boundary
    if (leftCows == 0 || rightCows == 0)
      return -1;
    if (leftCows + rightCows == 0)
      return 0;
    if (leftCows + rightCows < 3)
      return -1;
    return leftCows + rightCows;
  }
}
