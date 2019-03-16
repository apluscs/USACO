package usacoPractice;
// 2019 January Contest, Bronze Problem 3. Guess the Animal

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class guess {
  public static int N, gtr;
  public static ArrayList[] trs;
  public static HashMap<String, Integer> map;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("guess.in"));
        PrintWriter out = new PrintWriter(new File("guess.out"));) {
      N = Integer.parseInt(in.readLine());
      gtr = 0;
      map = new HashMap<>();
      trs = new ArrayList[N];
      for (int i = 0; i < N; i++) {
        trs[i] = new ArrayList<Integer>();
        StringTokenizer st = new StringTokenizer(in.readLine());
        st.nextToken();
        int T = Integer.parseInt(st.nextToken());
        for (int t = 0; t < T; t++) {
          String trt = st.nextToken();
          if (!map.containsKey(trt))
            map.put(trt, gtr++);
          trs[i].add(map.get(trt));
        }
      }
      int res = 0;
      for (int i = 0; i < N; i++)
        for (int j = i + 1; j < N; j++) // ask about the traits in common, then ask one more to
          res = Math.max(res, overlap(trs[i], trs[j]));// differentiate
      out.println(res + 1);
    }
  }

  public static int overlap(ArrayList<Integer> A, ArrayList<Integer> B) {
    int res = 0;
    for (int a : A)
      for (int b : B)
        if (a == b) {
          res++;
          break;
        }
    return res;
  }
}
