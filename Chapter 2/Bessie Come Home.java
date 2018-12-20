/*It's dinner time, and the cows are out in their separate pastures. Farmer John rings the bell so they will start walking to 
the barn. Your job is to figure out which one cow gets to the barn first (the supplied test data will always have exactly one 
fastest cow).

Between milkings, each cow is located in her own pasture, though some pastures have no cows in them. Each pasture is connected 
by a path to one or more other pastures (potentially including itself). Sometimes, two (potentially self-same) pastures are 
connected by more than one path. One or more of the pastures has a path to the barn. Thus, all cows have a path to the barn and 
they always know the shortest path. Of course, cows can go either direction on a path and they all walk at the same speed.

The pastures are labeled `a'..`z' and `A'..`Y'. One cow is in each pasture labeled with a capital letter. No cow is in a pasture 
labeled with a lower case letter. The barn's label is `Z'; no cows are in the barn, though.

INPUT FORMAT
Line 1: 	Integer P (1 <= P <= 10000) the number of paths that interconnect the pastures (and the barn)
Line 2..P+1: 	Space separated, two letters and an integer: the names of the interconnected pastures/barn and the distance 
between them (1 <= distance <= 1000)

SAMPLE INPUT (file comehome.in)
5
A d 6
B d 3
C e 9
d Z 8
e Z 3

OUTPUT FORMAT
A single line containing two items: the capital letter name of the pasture of the cow that arrives first back at the barn, the length of the path followed by that cow.

SAMPLE OUTPUT (file comehome.out)
B 11
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class comehome { //standard Dijkstra algorithm

  private static void shortestPath(int[][] pastures, long[] cows) {
    Arrays.fill(cows, Integer.MAX_VALUE);
    cows[25] = 0;
    boolean[] visited = new boolean[52];
    int current = 25;
    while (current != -1) {
      visited[current] = true;
      for (int a = 0; a < 52; a++) {
        long dist = cows[current] + pastures[a][current]; // dist must be long in case it overflows, becomes negative
        if (dist < cows[a]) {
            cows[a] = dist;
          }
      }
      current = nextField(visited, cows);
    }
  }

  private static int nextField(boolean[] visited, long[] distTo) {
    long min = Integer.MAX_VALUE;
    int next = -1;
    for (int i = 0; i < 52; i++) {
      if (!visited[i] && distTo[i] < min) {
        min = distTo[i];
        next = i;
      }
    }
    return next;
  }

  public static void main(String[] args) throws IOException {
    try (BufferedReader f = new BufferedReader(new FileReader("comehome.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("comehome.out")));) {
      f.readLine();
      int[][] paths = new int[52][52]; // weighted adjacency matrix
      long[] cows = new long[52]; // tracks shortest distance to barn
      for (int i = 0; i < cows.length; i++) {
        cows[i] = Integer.MAX_VALUE;
        Arrays.fill(paths[i], Integer.MAX_VALUE);
      }
      while (f.ready()) {
        StringTokenizer st = new StringTokenizer(f.readLine());
        char past = st.nextToken().charAt(0);
        char qast = st.nextToken().charAt(0);
        int weight = Integer.parseInt(st.nextToken());
        int ind1;
        int ind2;
        if (past > 'Z') { // lowercase
          ind1 = past - 'a' + 26; // starts at ind 26
        } else { // uppercase
          ind1 = past - 'A';
        }
        if (qast > 'Z') { // lowercase
          ind2 = qast - 'a' + 26;
        } else { // uppercase
          ind2 = qast - 'A';
        }
        paths[ind1][ind2] = Math.min(weight, paths[ind1][ind2]);
        paths[ind2][ind1] = Math.min(weight, paths[ind2][ind1]);
      }

      shortestPath(paths, cows);
      long[] result = new long[2];
      result[1] = Integer.MAX_VALUE;
      for (int i = 0; i < 25; i++) {
        if (cows[i] < result[1]) {
          result[0] = i;
          result[1] = cows[i];
        }
      }
      out.println((char) (result[0] + 'A') + " " + result[1]);
    }
  }
}
