/*N cows show up at the airport, each with different arrival times. There are M buses to send out, each with C capacity. A cow's
waiting time is defined at (time her bus leaves) - (time she arrives). Find a way of arranging the bus rides to minimize the 
waiting time of the longest waiting cow.*/

//side note: woot woot! In contest promotion!!

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
    randomGen jen = new randomGen(1, 15, 10, 1);
    System.out.println(jen.result);
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
      out.println(maxWait(cows));
    } catch (IOException e) {
    }
  }

  public static int maxWait(int[] cows) { //binary search to converge on most strenuous yet still executable job for drivers
    int l = 0, r = cows[N - 1] - cows[0];
    while (l <= r) {
      int m = (l + r) >>> 1;
      if (helper(m, cows))
        r = m - 1;
      else
        l = m + 1;
    }
    return l;
  }

  public static boolean helper(int waittime, int[] cows) {
    int leave = cows[0] + waittime;
    int busUsed = 0; // busses sent
    int passengers = 0;
    int i = 0;
    while (i < N && busUsed < M) {
      if (cows[i] <= leave && passengers <= C) {
        // System.out.println("made it: " + cows[i] + ", " + leave);
        passengers++;
        i++;
      }
      else {
        // System.out.println(cows[i] + ", " + leave);
        busUsed++;
        passengers = 0;
        leave = cows[i] + waittime;

      }
    }
    return i == N;
  }
}
