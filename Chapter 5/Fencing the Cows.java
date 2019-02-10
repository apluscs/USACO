import java.io.File;  
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class fc { //giftwrapping algo
  
  public static int N;
  public static double[][] nods;
  public static double midX, midY, st;
  public static double[][] hull;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("fc.in"));   //Scanner ruins runtime :(
        PrintWriter out = new PrintWriter(new File("fc.out"));) {
      st = System.currentTimeMillis();
      N = Integer.parseInt(in.readLine());
      nods = new double[N][3];
      hull = new double[N][3];
      for (int i = 0; i < N; i++) {
        StringTokenizer st = new StringTokenizer(in.readLine());
        nods[i][0] = Double.parseDouble(st.nextToken());
        nods[i][1] = Double.parseDouble(st.nextToken());
      }
      for (double[] nod : nods) {
        midX += nod[0] / N;
        midY += nod[1] / N;
      }
      for (int i = 0; i < N; i++)   //to convert to an error-free version for sorting
        nods[i][2] = 100000000 * Math.atan2((nods[i][1] - midY), (nods[i][0] - midX));
      Arrays.sort(nods, (a,b)-> (int)(a[2] - b[2]));
      double result = minHull(out);
      out.println(String.format("%.2f", result));
    }
  }

  public static double minHull(PrintWriter out) {
    hull[0] = nods[0];
    hull[1] = nods[1];
    int pos = 2;
    for (int i = 2; i < N; i++) {   //purpose: find a place for node i, may overwrite previous values
      while (pos > 1 && cross(sub(nods[i], hull[pos - 1]), sub(hull[pos - 2], hull[pos - 1])) < 0)
        pos--; // both vectors leave from pos-1 ^
      hull[pos] = nods[i];
      pos++;
    }
    int end = pos - 1; // last valid index in hull
    int start = 0; // first valid
    boolean flag = true;
    while (end - start >= 2 && flag) {
      flag = false;
      if (cross(sub(hull[start], hull[end]), sub(hull[end - 1], hull[end])) < 0) {
        end--;  //end is the middle of a concave angle, so disregard it
        flag = true;
      }
      if (cross(sub(hull[start + 1], hull[start]), sub(hull[end], hull[start])) < 0) {
        start++;    //same logic here
        flag = true;
      }
    }
    return findDist(start, end);
  }

  public static double findDist(int s, int e) {
    double result = 0;
    for (int i = s; i < e; i++) result += dist(hull[i], hull[i + 1]);
    result += dist(hull[s], hull[e]);
    return result;
  }

  public static double dist(double[] a, double[] b) {
    double dx = a[0] - b[0];
    double dy = a[1] - b[1];
    return Math.sqrt(dx * dx + dy * dy);
  }
  
  public static double[] sub(double[] a, double[] b) {
    return new double[] {a[0] - b[0], a[1] - b[1]};
  }

  public static double cross(double[] v, double[] u) {
    return v[0] * u[1] - v[1] * u[0];
  }
}
