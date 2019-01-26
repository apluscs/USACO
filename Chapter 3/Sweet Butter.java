// Note: this one took me much longer than I would have wanted. What I learned? the Comparator used
// in a PQ should not be based off some values that keep changing. If they are, store the current
// value in an int[] or something and copy that int[] in. Of course, the PQ might accumulate
// multiple elements associated with the changing value, but it will process the least element
// first, and all the others can be ignored by checking visitation status before processing. Bottom
// line is Don't change the elements in a PQ. That's just gonna screw stuff up. Secondly: HashSet 
//takes up a considerable amount of time more than a simple boolean[]. So go simple
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class butter {

  private static int N, C, P;
  private static ArrayList<int[]>[] adj;
  private static int[] locations;

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("butter.in"));
        PrintWriter out = new PrintWriter(new File("butter.out"));) {
      N = in.nextInt();
      P = in.nextInt();
      C = in.nextInt();
      locations = new int[N];
      for (int i = 0; i < N; i++) {
        locations[i] = in.nextInt();
      }
      adj = new ArrayList[P + 1];
      for (int i = 0; i <= P; i++) {
        adj[i] = new ArrayList<int[]>();
      }

      for (int i = 0; i < C; i++) {
        int from = in.nextInt(), to = in.nextInt(), dist = in.nextInt();
        adj[from].add(new int[] {to, dist});
        adj[to].add(new int[] {from, dist});
      }
      int[][] distTo = paths();
      int ans = Integer.MAX_VALUE;
      for (int i = 1; i <= P; i++) {
        int total = 0;
        for (int location : locations) // add cows' walking distance
          total += distTo[i][location];
        ans = Math.min(total, ans);
      }
      out.println(ans);
    }
  }

  private static int[][] paths() {
    int[][] distTo = new int[P + 1][P + 1];

    for (int i = 0; i <= P; i++) {
      for (int j = 0; j <= P; j++) {
        distTo[i][j] = (i != j) ? Integer.MAX_VALUE : 0;
      }
    }
    for (int source : locations) {  //only need to worry about cows' distances to every pasture
      PriorityQueue<int[]> toVisit = new PriorityQueue<int[]>(new Comparator<int[]>() {
        public int compare(int[] one, int[] two) {
          return one[1] - two[1];
        }
      });
      int count = 0;
      boolean[] visited = new boolean[P + 1];
      toVisit.add(new int[] {source, 0});
      while (count < P) {
        int[] next = toVisit.remove();
        int current = next[0], dist = next[1]; // distance from source to current
        if (visited[current])
          continue;
        distTo[source][current] = distTo[current][source] = dist; // two pieces of info, set both
        count++;
        visited[current] = true;
        for (int[] node : adj[current]) {
          int neighbor = node[0], nextDist = node[1] + dist;
          if (!visited[neighbor])
            toVisit.add(new int[] {neighbor, nextDist});
        }
      }
    }
    return distTo;
  }
}
