// January 2019 Silver
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class mountains {

  public static void main(String[] args) {
    try (BufferedReader f = new BufferedReader(new FileReader("mountains.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mountains.out")));) {
      int N = Integer.parseInt(f.readLine());
      List<int[]> peaks = new ArrayList<>();
      for (int i = 0; i < N; i++) {
        StringTokenizer st = new StringTokenizer(f.readLine());
        int[] curr = new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
        peaks.add(curr);
      }
      Collections.sort(peaks, (a, b) -> {
        int left1 = a[0] - a[1]; // sort by x-coordinate of left leg
        int left2 = b[0] - b[1];
        if (left1 != left2) { // if left leg starts at same place, take taller one
          return left1 - left2;
        }
        return (b[1] - a[1]);
      });
      mountains my = new mountains();
      out.println(my.countPeaks(peaks));
    } catch (IOException e) {
    }

  }

  private int countPeaks(List<int[]> peaks) {
    int result = peaks.size();
    int curr = 0;
    int next = curr + 1;
    while (curr < peaks.size()) {
      while (next < peaks.size() && isCovered(peaks, curr, next)) {
        next++;
        result--;
      }
      curr = next; // new curr when you find a new mountain that isn't covered
      next = curr + 1;
    }
    return result;
  }

  private boolean isCovered(List<int[]> peaks, int curr, int next) {
    int[] p = peaks.get(curr);
    int[] q = peaks.get(next);
    int exp = p[0] + p[1] - q[0]; // y-coor of mountain p at q's x coor
    if (q[1] <= exp) { // only need to check descending right leg
      return true;
    }
    return false;
  }

}
