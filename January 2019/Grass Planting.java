//Note: this was not actually submitted but I haven't found reason to believe it's wrong
//January 2019 Silver

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class planting {

  public static void main(String[] args) {
    try (BufferedReader f = new BufferedReader(new FileReader("planting.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("planting.out")));) {
      int F = Integer.parseInt(f.readLine());
      List<HashSet<Integer>> immed = new ArrayList<>(); // immediate adjacency
      List<HashSet<Integer>> neighbors = new ArrayList<>(); // tracks immediately adjacent and adds
                                                            // nearly adjacent
      for (int i = 0; i < F; i++) {
        immed.add(new HashSet<Integer>());
        neighbors.add(new HashSet<Integer>());
      }
      for (int i = 1; i < F; i++) {
        StringTokenizer st = new StringTokenizer(f.readLine());
        int a = Integer.parseInt(st.nextToken()) - 1;
        int b = Integer.parseInt(st.nextToken()) - 1;
        immed.get(a).add(b);
        immed.get(b).add(a);
      }
      for (int root = 0; root < F; root++) {
        for (int n : immed.get(root)) { // for each immediate neighbors of root field
          neighbors.get(root).add(n);
          for (int nn : immed.get(n)) { // for each immediate neighbor of the immediate neighbor
            neighbors.get(root).add(nn);
          }
          neighbors.get(root).remove(root); // can't be a neighbor to yourself!
        }
      }
      HashMap<Integer, List<Integer>> frq = new HashMap<>(); // outdegree, nodes with this outdegree
      for (int i = 0; i < F; i++) {
        HashSet<Integer> adj = neighbors.get(i);
        int outD = adj.size();
        List<Integer> group = frq.get(outD);
        if (group == null) {
          frq.put(outD, group = new ArrayList<>());
        }
        group.add(i);
      }
      List<Integer> keys = new ArrayList<>(frq.keySet());
      Collections.sort(keys, Comparator.reverseOrder()); // most connected fields are planted first
      planting my = new planting();
      out.println(my.pleaseCows(frq, neighbors, keys));
    } catch (IOException e) {
    }
  }

  private int pleaseCows(HashMap<Integer, List<Integer>> frq, List<HashSet<Integer>> neighbors,
      List<Integer> keys) {
    int[] grasses = new int[neighbors.size()];
    int max = 0;
    for (int outD : keys) {
      List<Integer> roots = frq.get(outD);
      for (int root : roots) {
        findUnused(neighbors, root, grasses);
        max = Math.max(max, grasses[root]);
      }
    }
    return max;
  }

  private void findUnused(List<HashSet<Integer>> neighbors, int root, int[] grasses) {
    HashSet<Integer> used = new HashSet<>(); // grasses root can't be
    HashSet<Integer> adj = neighbors.get(root);
    for (int n : adj) {
      if (grasses[n] != 0) {
        used.add(grasses[n]);
      }
    }
    int max = 0;
    for (int u : used) {
      max = Math.max(max, u);
    }
    for (int g = 1; g < max; g++) {
      if (!used.contains(g)) {
        grasses[root] = g;
        return;
      }
    }
    grasses[root] = max + 1;
  }
}
