// 2019 January Contest, Bronze Problem 2. Sleepy Cow Sorting (first print)
 //2019 January Contest, Gold Problem 2. Sleepy Cow Sorting 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class sleepy {
  public static int N, k;
  public static int[] fenwick;
  
  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("sleepy.in"));
        PrintWriter out = new PrintWriter(new File("sleepy.out"));) {
      N = Integer.parseInt(in.readLine());
      fenwick = new int[N + 1];
      StringTokenizer st = new StringTokenizer(in.readLine());
      int[] nums = new int[N];
      for (int i = 0; i < N; i++)
        nums[i] = Integer.parseInt(st.nextToken());
      int res = 0;
      for (int i = N - 1; i > 0; i--) { // last decreasing subseq of length L starting at pos i
        update(nums[i]);
        if (nums[i - 1] > nums[i]) { // it takes i steps to "access" it and L-1 steps to sort
          res = i; // simpler: find length of longest inc subseq @ end and N-that = answer
          break;
        }
      }
      out.println(res); // where unsorted head ends (pos "0" in tail array)
      k = res;
      for (int i = 0; i < res; i++) {
        int less = getSum(nums[i]); // number of cows need to jump over in the unsorted segment
        k--; // moves one back each time
        update(nums[i]);
        out.print(k + less);
        if (i != res - 1) 
          out.print(' ');
      }
      out.println();
    }
  }

  public static int getSum(int num) {
    int res = 0;
    while (num != 0) {
      res += fenwick[num];
      num -= (num & -num);
    }
    return res;
  }

  public static void update(int num) {
    while (num <= N) {
      fenwick[num]++;
      num += (num & -num);
    }
  }
}
