// 2019 January Contest, Bronze Problem 1. Shell Game

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class shell {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("shell.in"));
        PrintWriter out = new PrintWriter(new File("shell.out"));) {
      int[] result = new int[4];
      int[] shells = new int[] {0, 1, 2, 3};
      int N = f.nextInt();
      for (int i = 1; i <= N; i++) {
        int s1 = f.nextInt();
        int s2 = f.nextInt();
        int g = f.nextInt();
        int temp = shells[s1];
        shells[s1] = shells[s2];
        shells[s2] = temp; // simulate all 3 possible locations and pretend Elsie guessed right for
        result[shells[g]]++;                           // each location
      }
      int max = Math.max(result[1], result[2]);
      out.println(Math.max(max, result[3]));
    }

  }

}
