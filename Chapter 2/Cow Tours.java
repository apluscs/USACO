import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class cowtour {
  public static void main(String[] args) throws IOException {
    try (BufferedReader f = new BufferedReader(new FileReader("cowtour.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowtour.out")));) {
      int N = Integer.parseInt(f.readLine());
      int[][] pastures = new int[N][2];
      double[] farthest = new double[N]; // for each pasture, what is the distance to the farthest
                                         // pasture in the same field?
      double[][] dist = new double[N][N];
      double[] diameters = new double[N];
      Arrays.fill(diameters, -1); // mark as unvisited
      for (int i = 0; i < N; i++) {
        StringTokenizer st = new StringTokenizer(f.readLine());
        pastures[i][0] = Integer.parseInt(st.nextToken());
        pastures[i][1] = Integer.parseInt(st.nextToken());
      }
      for (int i = 0; i < N; i++) {
        Arrays.fill(dist[i], -1);
        String line = f.readLine();
        for (int c = 0; c < N; c++) {
          if (line.charAt(c) == '1') {
            int xDist = pastures[c][0] - pastures[i][0];
            int yDist = pastures[c][1] - pastures[i][1];
            dist[i][c] = Math.sqrt(xDist * xDist + yDist * yDist); // mark as connected
          }
        }
      }
      cowtour my = new cowtour();
      my.floydWarshall(dist, farthest);
      my.findDiameters(dist, farthest, diameters);
      double result = my.connectFields(dist, farthest, pastures, diameters);
      out.println(String.format("%.6f", result));
    }
  }

  private void findDiameters(double[][] dist, double[] farthest, double[] diameters) {
    int N = farthest.length;
    for (int p = 0; p < N; p++) {
      double maxDiam = 0;
      if (diameters[p] != -1)
        continue;
      for (int q = 0; q < N; q++) {
        if (p != q && dist[p][q] != -1) { // examine each same-field pasture
          double farther = Math.max(farthest[q], farthest[p]);
          maxDiam = Math.max(maxDiam, farther); // of this field
        }
      }
      diameters[p] = maxDiam;
      for (int q = 0; q < N; q++) {
        if (p != q && dist[p][q] != -1) {
          diameters[q] = maxDiam;
        }
      }
    }
  }

  private double connectFields(double[][] dist, double[] farthest, int[][] pastures,
      double[] diameters) {
    int N = farthest.length;
    double minDiam = Integer.MAX_VALUE;
    for (int p = 0; p < N; p++) {
      for (int q = p + 1; q < N; q++) {
        if (p != q && dist[p][q] == -1) { // candidate for connection
          int xDist = pastures[p][0] - pastures[q][0];
          int yDist = pastures[p][1] - pastures[q][1];
          double distPQ = Math.sqrt(xDist * xDist + yDist * yDist);
          double newDiam = farthest[p] + farthest[q] + distPQ; // but sometimes the combined field's
                                                               // diameter is still the same as the
                                                               // diameter of one of the original
                                                               // field
          double existingDiam = Math.max(diameters[p], diameters[q]);
          newDiam = Math.max(existingDiam, newDiam);
          minDiam = Math.min(newDiam, minDiam);
        }
      }
    }
    return minDiam;
  }
  private void floydWarshall(double[][] dist, double[] farthest) {
    int N = farthest.length;
    for (int mid = 0; mid < N; mid++) {
      for (int a = 0; a < N; a++) {
        if (dist[mid][a] < 0) { // not connected to mid
          continue;
        }
        for (int b = 0; b < N; b++) {
          if (dist[mid][b] < 0) {
            continue;
          }
          if (dist[a][b] == -1 || dist[a][mid] + dist[b][mid] < dist[a][b]) { // a and b aren't
                                                                              // connected
            dist[a][b] = dist[a][mid] + dist[b][mid]; // found a shorter path
          }
        }
      }
    }
    for (int i = 0; i < N; i++) {
      double max = 0; // tracks farthest same-field pasture
      for (int p = 0; p < N; p++) {
        if (p != i) {
          max = Math.max(max, dist[i][p]);
        }
      }
      farthest[i] = max;
    }
  }
}
