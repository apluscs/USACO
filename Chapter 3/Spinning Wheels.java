import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class spin {
  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("spin.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));) {
    int[][][] wedges = new int[5][5][2];
    int[] numWedges = new int[5]; //so we know to ignore the nonexistent wedges in wedges[][][]
    int[] speeds = new int[5];
    for (int i = 0; i < 5; i++) {
      speeds[i] = f.nextInt();
      int numWed = f.nextInt();
      numWedges[i] = numWed;
      for (int j = 0; j < numWed; j++) {
        wedges[i][j][0] = f.nextInt();
        wedges[i][j][1] = f.nextInt();
      }
    }
    spin my = new spin();
    int result = my.timeGap(wedges, speeds, numWedges);
    if (result != -1) {
      out.println(result);
    } else {
      out.println("none");
      }
    }
  }

  private int timeGap(int[][][] wedges, int[] speeds, int[] numWedges) {
    for (int t = 0; t < 360; t++) { // every360 seconds is the same for the set of wheels
      int[] open = new int[360];
      for (int whe = 0; whe < 5; whe++) {
        for (int wed = 0; wed < numWedges[whe]; wed++) { // for each wedge
          int start = t * speeds[whe] + wedges[whe][wed][0];
          for (int slice = start; slice <= wedges[whe][wed][1] + start; slice++) { // for each angle
                                                                                  // of wedge
            if (++open[slice % 360] == 5) { // must increase first
              System.out.println(t);
              return t;
            }
          }
        }
      }
    }
    return -1;
  }
}
