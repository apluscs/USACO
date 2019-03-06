// package usaco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class revegetate {
  public static List[] same, diff;
  public static int[] fields;
  public static int N, M;
  public static boolean impossible;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("revegetate.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("revegetate.out")));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      same = new List[100001];
      diff = new List[100001];
      fields = new int[100001];
      // Arrays.fill(fields, -1); // mark as unused
      for (int i = 1; i <= N; i++) {
        same[i] = new ArrayList<Integer>();
        diff[i] = new ArrayList<Integer>();
      }
      for (int i = 0; i < M; i++) {
        st = new StringTokenizer(in.readLine());
        char c = st.nextToken().charAt(0);
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        // fields[a] = fields[b] = 0;
        if (c == 'S') {
          same[a].add(b);
          same[b].add(a);
        } else {
          diff[a].add(b);
          diff[b].add(a);
        }
      }
      int res = solve();
      if (!impossible) {
//        out.println(res + " " + impossible);
        StringBuilder sb = new StringBuilder();
        sb.append('1');
        for (int i = 0; i < res; i++) {
          sb.append('0');
        }
        out.println(sb.toString());
      }
      else
      out.println('0');
    }
  }

  public static void visit(int f, int grass) {
    fields[f] = grass;
    List<Integer> ss = same[f];
    for (int s : ss) {
      if (fields[s] == 3 - grass)
        impossible = true;
      if (fields[s] == 0)
        visit(s, grass);
    }
    List<Integer> dd = diff[f];
    for (int d : dd) {
      if (fields[d] == grass)
        impossible = true;
      if (fields[d] == 0)
        visit(d, 3 - grass);
    }
  }

  public static int solve() {
    int group = 0;
    // System.out.println(Arrays.toString(fields));
    for (int i = 1; i <= N; i++) {
      if (fields[i] == 0) {
//        System.out.println(group);
        visit(i, 1);
        group++;
    }
  }
    return group;
  }

}