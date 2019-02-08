import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class frameup {
  public static Frame[] frms = new Frame[26];
  public static int[][] view;
  public static int H, W, N;
  public static List<String> result = new ArrayList<>();
  public static StringBuilder sb = new StringBuilder();
  public static int[] hash = new int[26];
  public static int[] rHash = new int[26];

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("frameup.in"));
        PrintWriter out = new PrintWriter(new File("frameup.out"));) {
      H = in.nextInt();
      W = in.nextInt();
      view = new int[H][W];
      Arrays.fill(hash, -1);
      for (int i = 0; i < 26; i++) frms[i] = new Frame();
      for (int i = 0; i < H; i++) {
        String line = in.next();
        for (int j = 0; j < W; j++) {
          int ch = line.charAt(j) - 'A';
          if (ch < 0) continue; // period
          if (hash[ch] == -1) { // unencountered character
            rHash[N] = ch; // if given "A" what was the original letter?
            hash[ch] = N++; // ex. A to U
          }
          int c = hash[ch];
          view[i][j] = c;
          frms[c].nor = Math.min(frms[c].nor, i); // finds northmost boundary
          frms[c].ea = Math.max(frms[c].ea, j);
          frms[c].sou = Math.max(frms[c].sou, i);
          frms[c].wes = Math.min(frms[c].wes, j);
        }
      }
      for (int f = 0; f < N; f++) { // finds interruptions
        Frame cur = frms[f];
        for (int j = cur.wes; j <= cur.ea; j++) {
          if (view[cur.nor][j] != f)
            cur.set.add(view[cur.nor][j]);
          if (view[cur.sou][j] != f)
            cur.set.add(view[cur.sou][j]);
        }
        for (int i = cur.nor; i <= cur.sou; i++) {
          if (view[i][cur.wes] != f)
            cur.set.add(view[i][cur.wes]);
          if (view[i][cur.ea] != f)
            cur.set.add(view[i][cur.ea]);
        }
      }
      for (int i = 0; i < N; i++) frms[i].lev = frms[i].set.size(); // frontmost frames at level 0
      dfs();
      Collections.sort(result);
      for (String r : result) out.println(r);
    }
  }

  public static void dfs() {
    if (sb.length() == N) {
      result.add(new StringBuilder(sb).reverse().toString());
      return;
    }
    for (int i = 0; i < N; i++) {
      if (frms[i].lev == 0) {
        frms[i].lev--;
        sb.append((char) (rHash[i] + 'A'));
        for (int j = 0; j < N; j++)
          if (frms[j].set.contains(i))
            frms[j].lev--;
        dfs();
        sb.deleteCharAt(sb.length() - 1); // reverse everything above
        for (int j = 0; j < N; j++)
          if (frms[j].set.contains(i))
            frms[j].lev++;
        frms[i].lev++;
      }
    }
  }

  private static class Frame {
    int nor, ea, wes, sou;
    HashSet<Integer> set;
    int lev;

    public Frame() {
      set = new HashSet<>();
      nor = wes = 404;
      ea = sou = -404;
    }
  }
}
