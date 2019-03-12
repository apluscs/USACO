//2018 US Open Contest, Bronze Problem 2. Milking Order 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class milkorder {
  public static int N, M, K;
  public static int[] nums, order;
  public static boolean[] isFixed;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("milkorder.in"));
        PrintWriter out = new PrintWriter(new File("milkorder.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      K = Integer.parseInt(st.nextToken());
      nums = new int[N + 1];
      isFixed = new boolean[N + 1];
      order = new int[M];
      st = new StringTokenizer(in.readLine());
      for (int i = 0; i < M; i++) 
        order[i] = Integer.parseInt(st.nextToken());
      for (int i = 0; i < K; i++) {
        st = new StringTokenizer(in.readLine());
        int cow = Integer.parseInt(st.nextToken());
        int pos = Integer.parseInt(st.nextToken());
        nums[pos] = cow;
        isFixed[cow] = true;
      }
      System.out.println(Arrays.toString(nums));
      for (int i = 1; i <= N; i++) 
        if (nums[i] == 0) {
          int[] temp = nums.clone();
          temp[i] = 1;
          isFixed[1] = true;
          if (doable(temp)) {
            out.println(i);
            return;
          }
          isFixed[1] = false;
        }
      out.println(N);
    }
  }

  public static boolean doable(int[] nums) {
    int oi = 0; // index in order
    for (int i = 1; i <= N; i++) {
      if (oi == M)
        return true;
      int next = order[oi];
      if (nums[i] == 0 && !isFixed[next]) {
        nums[i] = next; // if not fixed, place ASAP
        oi++;
      } else if (nums[i] == next)  // if fixed, must wait until seeing it before moving on
        oi++;
    }
    return oi == M; // can you place everything in order[]? without running out of room
  }
}
