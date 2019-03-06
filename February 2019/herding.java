import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class herding {
  public static int N;
  public static int[] cows;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("herding.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("herding.out")));) {
      N = Integer.parseInt(in.readLine());
      cows = new int[N];
      for (int i = 0; i < N; i++)
        cows[i] = Integer.parseInt(in.readLine());
      Arrays.sort(cows);
      int min = findMin();
      int max = Math.max(cows[N - 2] - cows[0] - N + 2, cows[N - 1] - cows[1] - N + 2); 
      // maximize #gaps in between
      out.println(min);
      out.println(max); // must forgo either left/rightmost gap because first move renders that impossible to use
    }
  }

  public static int findMin() {
    if (cows[N - 2] - cows[0] == N - 2 && cows[N - 1] - cows[N - 2] > 2 // is it consecutive? is the gap >= 2?
        || cows[1] - cows[0] > 2 && cows[N - 1] - cows[1] == N - 2) // exception: default method would return 1
      return 2;
    int result = N - 1; // would never need to make more than this many moves
    for (int i = 0; i < N - 1; i++) {
      int j = i + 1;
      while (j < N && cows[j++] - cows[i] < N) {
        int temp = j - i + 1; // #cows in the range
        result = Math.min(N - temp, result);
      }
    }
    return result;
  }
}
