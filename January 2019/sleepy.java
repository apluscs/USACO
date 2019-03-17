// 2019 January Contest, Bronze Problem 2. Sleepy Cow Sorting
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class sleepy {
  public static int N;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("sleepy.in"));
        PrintWriter out = new PrintWriter(new File("sleepy.out"));) {
      N = Integer.parseInt(in.readLine());
      StringTokenizer st = new StringTokenizer(in.readLine());
      int[] nums = new int[N];
      for (int i = 0; i < N; i++) 
        nums[i] = Integer.parseInt(st.nextToken());
      for (int i = N - 1; i > 0; i--) //only consider last decreasing subseq of length L starting at pos i
        if (nums[i - 1] > nums[i]) {    //it takes i steps to "access" it and L-1 steps to sort
          out.println(i);   //simpler: find length of longest inc subseq @ end and N-that = answer
          return;
        }
      out.println(0);   //if all are sorted
    }
  }
}
