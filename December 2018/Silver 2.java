/*Many cows are waiting to each grass, and they are given in order of seniority (oldest listed first). Only one cow can eat the 
grass at a time, and she takes all the time she wants (eating time is given as well). Arrival times are given as well. If one 
cow is done eating and others are in line, the oldest cow in line gets to eat next. Find the waiting time (start of eating time) 
- (arrival time) of the longest waiting cow. If a cow B shows up just as a cow A is finished eating, cow B is considered 
"waiting", and her seniority is in contest with those already waiting.*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class convention2 {

  public static void main(String[] args) {
    try (BufferedReader f = new BufferedReader(new FileReader("convention2.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("convention2.out")));) {
      StringTokenizer st = new StringTokenizer(f.readLine());
      int[][] cows = new int[Integer.parseInt(st.nextToken())][5];

      for (int i = 0; i < cows.length; i++) {
        st = new StringTokenizer(f.readLine());
        cows[i][1] = Integer.parseInt(st.nextToken());
        cows[i][2] = Integer.parseInt(st.nextToken());
        cows[i][0] = i; // seniority
      }
      Arrays.sort(cows, (a, b) -> {
        return a[1] - b[1];
      });
      out.println(maxWait(cows));
    } catch (IOException e) {
    }
  }

  private static class SeniorityComp implements Comparator<int[]> {
    @Override
    public int compare(int[] cow1, int[] cow2) {
      return cow1[0] - cow2[0];
    }
  }

  public static int maxWait(int[][] cows) {
    final int N = cows.length;
    int ind = 0, time = -1, res = -1;
    PriorityQueue<int[]> pq = new PriorityQueue<>(new SeniorityComp());
    while (pq.isEmpty() != true || ind != N) {
      if (pq.isEmpty()) // ind must not equal to N
        time = Math.max(cows[ind][1], time);
      while (ind != N && cows[ind][1] <= time)
        pq.offer(cows[ind++]);
      // at this time, pq is definitely not empty
      int[] lucky = pq.poll();
      res = Math.max(res, time - lucky[1]);
      time += lucky[2]; // the time lucky finished eating
      // if the pq is empty but ind!=N, we need to pick up the next guy
    }
    return res;
  }
}
