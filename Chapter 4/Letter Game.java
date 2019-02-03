import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class lgame {

  public static int[] vals =
      {2, 5, 4, 4, 1, 6, 5, 5, 1, 7, 6, 3, 5, 2, 3, 5, 7, 2, 1, 2, 4, 6, 6, 7, 5, 7};
  public static int[] giv = new int[26];
  public static List<Pair>[] valid = new List[8];
  public static TreeSet<Pair> result = new TreeSet<>((a, b) -> {
    return a.word.compareTo(b.word);
  });
  public static int maxPts;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("lgame.in"));
        PrintWriter out = new PrintWriter(new File("lgame.out"));
        BufferedReader in2 = new BufferedReader(new FileReader("lgame.dict"));) {
      for (int i = 0; i <= 7; i++) valid[i] = new ArrayList<Pair>();
      String g = in.readLine();
      for (int i = 0; i < g.length(); i++) giv[g.charAt(i) - 'a']++;
      while (in2.ready()) {
        String word = in2.readLine();
        if (word.charAt(0) == '.') break;
        Pair p = new Pair(word);
        if (p.bad) continue;
        maxPts = Math.max(maxPts, p.pts);
        valid[word.length()].add(new Pair(word));
      }
      bestWords();
      out.println(maxPts);
      for (Pair p : result) out.println(p.word);
      }
    }

  public static void bestWords() {
    for (int i = 7; i >= 3; i--)
      for (Pair w : valid[i]) {
        judge(w); // singular
        for (int j = 3; j <= 7 - i && j <= i; j++)  // generate all valid pairs
          for (Pair v : valid[j]) {
            Pair p = new Pair(w, v);
            if (!p.bad)
            judge(p);
          }
      }
  }

  public static void judge(Pair p) {
    if (p.pts >= maxPts) 
      if (p.pts > maxPts) {
        maxPts = p.pts;
        result.clear(); // saves time by eliminating everything less than max
      }
      result.add(p); // only add if pair yields max points
  }
  
  private static class Pair {
    String word;
    int pts;
    int[] frq = new int[26];
    boolean bad; // if you can make it with given lettesr

    public Pair(String word) {
      this.word = word;
      for (char c : word.toCharArray()) {
        pts += vals[c - 'a'];
        frq[c - 'a']++;
        if (frq[c - 'a'] > giv[c - 'a']) {
          bad = true;
          break;
        }
      }
    }

    public Pair(Pair w, Pair v) { //used for making a two-part word
      this.frq = w.frq.clone();
      for (int i = 0; i < 26; i++) {
        this.frq[i] += v.frq[i];
        if (frq[i] > giv[i]) {
          bad = true;
          return;
        }
      }
      if (w.word.compareTo(v.word) > 0) this.word = v.word + " " + w.word;
      else this.word = w.word + " " + v.word; // make sure each pair is set up alphabetically
      this.pts = w.pts + v.pts;
    }
  }
}
