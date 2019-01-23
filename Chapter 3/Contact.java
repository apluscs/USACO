import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class contact {

  public static void main(String[] args) throws IOException {
    try (BufferedReader f = new BufferedReader(new FileReader("contact.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("contact.out")));) {
      StringTokenizer st = new StringTokenizer(f.readLine());
      final int A = Integer.parseInt(st.nextToken());
      final int B = Integer.parseInt(st.nextToken());
      final int N = Integer.parseInt(st.nextToken());
      StringBuilder sb = new StringBuilder();
      String line = f.readLine();
      while (line != null) {
        sb.append(line);
        line = f.readLine();
      }
      contact my = new contact();
      List<Pattern> result = my.findPatterns(sb.toString().toCharArray(), A, B);

      int printedFrqs = 0;
      String next = "";
      int currFrq = result.get(0).frq;
      int six = 0;
      int count = 0;
      while (count < result.size() && printedFrqs < N) {
        if (result.get(count).frq != currFrq) {
          out.println(currFrq + "\n" + next.trim());
          printedFrqs++;
          currFrq = result.get(count).frq;
          next = "";
          six = 0; // refreshes for every group of frequences
        }
        six++;
        next += result.get(count).pat + " ";
        if (six % 6 == 0) { // every sixth element comes with a new line after it
          next = next.trim();
          next += "\n";
        }
        count++;
      }
      if (count == result.size()) { // used all elements in result
        out.println(currFrq + "\n" + next.trim());
      }
    }
  }

  private List<Pattern> findPatterns(char[] bits, int A, int B) {
    HashMap<String, Integer> curr = new HashMap<>();
    String total = new String(bits);

    for (int s = A; s <= B; s++) {
      for (int start = 0; start + s <= total.length(); start++) {
        String pat = total.substring(start, start + s);
        curr.put(pat, curr.getOrDefault(pat, 0) + 1);
      }
    }
    ArrayList<Pattern> outputFreq = new ArrayList<>();
    for (String k : curr.keySet()) {
      outputFreq.add(new Pattern(k, curr.get(k)));
    }
    Collections.sort(outputFreq);
    return outputFreq;
  }

  private class Pattern implements Comparable<Pattern> {
    String pat;
    int frq;

    public int compareTo(Pattern other) {
      if (frq != other.frq)
        return other.frq - frq; // most frequent first
      if (pat.length() != other.pat.length())
        return pat.length() - other.pat.length();
      return pat.compareTo(other.pat); // alphabetically is the same order as bit value
    }

    public Pattern(String s, int f) {
      pat = s;
      frq = f;
    }
  }
}
