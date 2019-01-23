import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class agrinet {

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("agrinet.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("agrinet.out")));) {
      int N = in.nextInt();
      int[][] costs = new int[N][N];
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          int c = in.nextInt();
          costs[i][j] = c;
        }
      }
      agrinet my = new agrinet();
      int result = my.minWires(costs);
      out.println(result);
    }
  }

  private int minWires(int[][] costs) {
    final int N = costs.length;
    PriorityQueue<int[]> toVisit = new PriorityQueue<>((a, b) -> {
      return a[0] - b[0]; //
    });
    int[][] farms = new int[N][2]; // ez access of neighbors based on neighbors' IDs; distToTree, #id
    for (int i = 0; i < N; i++) {
      farms[i][0] = Integer.MAX_VALUE;
    }
    int[] lucky = new int[] {0, 0};
    farms[0][1] = 0;
    BitSet visited = new BitSet(N);
    toVisit.offer(lucky);

    int result = 0;
    while (visited.cardinality() != N) {
      int[] farm = toVisit.poll(); // closest to existing tree
      if (visited.get(farm[1]))
        continue;
      visited.set(farm[1]);
      result += farm[0];
      relaxNeighbors(farm[1], costs, farms, toVisit, visited);
    }
    return result;
  }

  private void relaxNeighbors(int now, int[][] costs, int[][] farms, PriorityQueue<int[]> toVisit,
      BitSet visited) {
    for (int next = 0; next < costs.length; next++) {
      if (visited.get(next) || costs[now][next] == 0) // visited next or there's no path to next
        continue;
      if (farms[next][0] > costs[now][next]) {
        farms[next][0] = costs[now][next];
        toVisit.add(new int[] {costs[now][next], next});    //only add if farm "now" carves a shorter path
      }
    }
  }
}
