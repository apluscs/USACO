import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class fence6 {
  public static int N, vertNum = 1;
  public static HashMap<String, Integer> verts = new HashMap<>();
  public static int[][] paths = new int[101][101];
  
  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("fence6.in"));
        PrintWriter out = new PrintWriter(new File("fence6.out"));) {
      N = in.nextInt();
      for (int i = 0; i < 101; i++) Arrays.fill(paths[i], 10000);
      for (int f = 1; f <= N; f++) {
        int id = in.nextInt();
        int len = in.nextInt();
        int lNum = in.nextInt();
        int rNum = in.nextInt();
        int[] set1 = new int[lNum + 1];
        int[] set2 = new int[rNum + 1];
        set1[0] = set2[0] = id;
        for (int i = 1; i <= lNum; i++)  set1[i] = in.nextInt();
        for (int i = 1; i <= rNum; i++) set2[i] = in.nextInt();
        int v1 = getVertex(set1);
        int v2 = getVertex(set2);
        paths[v1][v2] = paths[v2][v1] = len;
      }
      out.println(shortCircuit());
    }
  }

  public static int shortCircuit() {
    int result = Integer.MAX_VALUE;
    for (int i = 1; i < vertNum; i++) {
      for (int j = i + 1; j < vertNum; j++) { // for every distinct pair of connected vertices
        if (paths[i][j] == 10000) continue;
        int temp = paths[i][j];
        paths[i][j] = 10000; // "remove" the path and find the shortest path between them
        int len = dijkstra(i, j);
        result = Math.min(result, len + temp); // add the path back in and it's a circuit!
        paths[i][j] = temp;
      }
    }
    return result;
  }
  public static int dijkstra(int src, int des) {
    int[] dis = new int[101];
    Arrays.fill(dis, 10000);
    dis[src] = 0;
    boolean[] vis = new boolean[101];
    PriorityQueue<int[]> toVis = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    toVis.add(new int[] {src, 0});
    while (!toVis.isEmpty()) {
      int[] curr = toVis.poll();
      int id = curr[0];
      if (id == des) return curr[1];
      if (vis[id]) continue;
      vis[id] = true;
      for (int i = 1; i < vertNum; i++) {
        if (!vis[i] && dis[i] > paths[id][i] + dis[id]) {
            dis[i] = paths[id][i] + dis[id];
            toVis.add(new int[] {i, dis[i]});
        }
      }
    }
    return 10000; // if no other path exists between source/dest, make len really big
  }
  
  public static int getVertex(int[] set) {
    Arrays.sort(set); // sort so every set of vertices has same ID
    String id = Arrays.toString(set);
    if (!verts.containsKey(id)) {
      verts.put(id, vertNum); // every vertex matched by ID's of fences at it
      return vertNum++;
    } else return verts.get(id);
  }
}
