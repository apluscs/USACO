import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class job {

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("job.in"));
        PrintWriter out = new PrintWriter(new File("job.out"));) {
      job my = new job();
      int N, M1, M2;
      int[] aTimes, bTimes;
      N = in.nextInt();
      M1 = in.nextInt();
      M2 = in.nextInt();
      aTimes = new int[M1];
      bTimes = new int[M2];
      for (int i = 0; i < M1; i++) {
        aTimes[i] = in.nextInt();
      }
      for (int i = 0; i < M2; i++) {
        bTimes[i] = in.nextInt();
      }
      int[] result = my.solve(N, M1, M2, aTimes, bTimes);
      out.println(result[0] + " " + result[1]);
    }
  }

  private int[] solve(int N, int M1, int M2, int[] aTimes, int[] bTimes) {
    int[] tasks = new int[N];   //tracks ending time of each task
    int[] aEnd = new int[M1];   //specific to each Machine A- tracks the time it will finish all of its tasks
    int[] bEnd = new int[M2];
    for (int t = 0; t < N; t++) {
      int ind = -1; //Question: Which machine can I delegate this task to so that the max ending time is minimal?
      int minEnd = Integer.MAX_VALUE;
      for (int a = 0; a < M1; a++) {
        if (aTimes[a] + aEnd[a] < minEnd) {
          ind = a;
          minEnd = aTimes[a] + aEnd[a];
        }
      }
      aEnd[ind] = tasks[t] = minEnd;
    }
    int aFin = tasks[N - 1]; // answer to first part
    for (int t = N - 1; t >= 0; t--) {  //Last task delegated in part A will have the latest ending time; 
      int ind = -1;                     //better give first priority for part B
      int minEnd = Integer.MAX_VALUE;
      for (int b = 0; b < M2; b++) {
        if (bTimes[b] + bEnd[b] < minEnd) {
          ind = b;
          minEnd = bTimes[b] + bEnd[b];
        }
      }
      bEnd[ind] = minEnd;
      tasks[t] += minEnd;
    }
    int result = 0;
    for (int t = 0; t < N; t++) {
      result = Math.max(result, tasks[t]);
    }
    return new int[] {aFin, result};
  }
}
