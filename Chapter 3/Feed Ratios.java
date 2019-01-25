import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ratios {
  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("ratios.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));) {
      int[][] feeds = new int[3][3];
      int[] goal = new int[3];
      for (int i = 0; i < 3; i++) {
        goal[i] = f.nextInt();
      }
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          feeds[i][j] = f.nextInt();
        }
      }
      ratios my = new ratios();
      int[] result = my.matchGoal(feeds, goal);
      if (result.length == 0) {
        out.println("NONE");
      } else {
        out.println(result[0] + " " + result[1] + " " + result[2] + " " + result[3]);
      }

    }
  }

  private int[] matchGoal(int[][] feeds, int[] goal) {
    check(feeds, goal, new int[] {99, 99, 99});
    for (int k = 0; k < 100; k++) {
      for (int j = 0; j < 100; j++) {
        for (int m = 0; m < 100; m++) {
          int[] rat = new int[] {k, j, m, 0};
          if (check(feeds, goal, rat) != -1) { // multiplier
            rat[3] = check(feeds, goal, rat);
            return rat;
          }
        }
      }
    }
    return new int[0];
  }

  private int check(int[][] feeds, int[] goal, int[] rat) {
    int[] total = new int[3];
    for (int t = 0; t < 3; t++) {
      for (int f = 0; f < 3; f++) {
        total[t] += rat[f] * feeds[f][t];
      }
      if (goal[t] != 0 && total[t] == 0 || goal[t] == 0 && total[t] != 0
          || goal[t] != 0 && total[t] % goal[t] != 0) { // tricky case with zeroes: if one is 0 and
                                                        // the other is not, it fails. If both are
                                                        // 0, it works. If both are nonzeroes and
                                                        // total/goal is not an integer, it breaks.
        return -1;
      }
    }
    if (total[0] * goal[1] != total[1] * goal[0] || total[1] * goal[2] != goal[1] * total[2]
        || total[0] * goal[2] != total[2] * goal[0]) { // checks if total's ratios matches the goal
      return -1;
    }
    if (total[0] != 0) // finds first nonzero multiplier
      return total[0] / goal[0];
    else if (total[1] != 0)
      return total[1] / goal[1];
    else if (total[2] != 0)
      return total[2] / goal[2];
    return 0;
  }
}
