import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class race3 {
  public static boolean[][] adj = new boolean[51][51];
  public static int N = -1;
  public static List<Integer> unavoid = new ArrayList<>();
  public static List<Integer> splits = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("race3.in"));
        PrintWriter out = new PrintWriter(new File("race3.out"));) {
      while (in.hasNext()) {
        N++;
        StringTokenizer st = new StringTokenizer(in.nextLine());
        while (st.hasMoreTokens()) {
          int to = Integer.parseInt(st.nextToken());
          if (to == -2 || to == -1)
            break;
          adj[N][to] = true;
        }
      }
      N--;
      findUnavoid();
      for (int u : unavoid) {
        BitSet[] acc = findAccess(u);
        if (acc.length == 1) continue;  //no points in common!
        if (acc[0].cardinality() + acc[1].cardinality() == N + 1) 
          splits.add(u);    //together, all points must be accessible
      }
      out.print(unavoid.size());
      for (int i = 0; i < unavoid.size(); i++)  out.print(" " + unavoid.get(i));
      out.print("\n" + splits.size());
      for (int i = 0; i < splits.size(); i++)   out.print(" " + splits.get(i));
      out.println();
    }
  }

  public static BitSet[] findAccess(int u) {
    Queue<Integer> toVis = new LinkedList<>();
    BitSet vis = new BitSet();
    toVis.add(0);
    vis.set(0);
    while (!toVis.isEmpty()) {
      int curr = toVis.poll();
      if (curr == u)    continue;   //don't add neighbors; you've reached end1
      for (int i = 0; i <= N; i++)
        if (adj[curr][i] && !vis.get(i)) { // unvisited and visitable
          toVis.add(i);
          vis.set(i);
        }
    }
    vis.set(u, false);  //so it isn't flagged later

    BitSet vis2 = new BitSet();
    toVis.add(u);
    vis2.set(u);
    while (!toVis.isEmpty()) {
      int curr = toVis.poll();
      if (vis.get(curr)) return new BitSet[] {vis}; // only one to flag this u as not a splitting pint
      // System.out.println("able to visit " + curr + " if " + u + " is the split");
      for (int i = 0; i <= N; i++)
        if (adj[curr][i] && !vis2.get(i)) { // unvisited and visitable
          toVis.add(i);
          vis2.set(i);
        }
    }
    return new BitSet[] {vis, vis2};
  }

  public static void findUnavoid() {
    for (int i = 1; i < N; i++) 
      if (!reachEnd(i)) unavoid.add(i); //must be present if you want to reach the end
  }

  public static boolean reachEnd(int gone) {
    Queue<Integer> toVis = new LinkedList<>();
    toVis.add(0);
    boolean[] vis = new boolean[N + 1];
    while (!toVis.isEmpty()) {
      int curr = toVis.poll();
      vis[curr] = true;
      for (int i = 1; i <= N; i++) {
        if (i != gone && adj[curr][i] && !vis[i]) {
          toVis.add(i);
          if (i == N)   //you've reached the end!
            return true;
        }
      }
    }
    return false;   //you can't reach the end if node "gone" is gone.
  }
}
