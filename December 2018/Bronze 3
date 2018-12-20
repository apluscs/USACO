//too long to explain. #iykyk

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

public class backforth {

  public static void main(String[] args) {
    try (BufferedReader f = new BufferedReader(new FileReader("backforth.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("backforth.out")));) {
      int[] A = new int[10];
      int[] B = new int[10];
      StringTokenizer st = new StringTokenizer(f.readLine());
      for (int i = 0; i < 10; i++) {
        A[i] = Integer.parseInt(st.nextToken());
      }
      st = new StringTokenizer(f.readLine());
      for (int i = 0; i < 10; i++) {
        B[i] = Integer.parseInt(st.nextToken());
      }

      Barn barn = new Barn(1000, A, B);
      Barn carn = new Barn(1000, B, barn); // must ensure bs & qs of carn is the same order as those
                                           // of barn
      combos = new HashSet<>();
      countCombos(barn, carn, 1); // friday is 4
      out.println(combos.size());
    } catch (IOException e) {
    }

  }

  private static HashSet<Integer> combos = null;

  private static class Barn {
    int tank;
    int[] bs, qs;

    public Barn(int tank, int[] bucks, int[] otherBarn) {
      this.tank = tank;
      HashMap<Integer, Integer> buckets = new HashMap<>();
      for (int buck : bucks)
        buckets.put(buck, buckets.getOrDefault(buck, 0) + 1);
      for (int buck : otherBarn)
        buckets.putIfAbsent(buck, 0);
      bs = new int[buckets.size()];
      qs = new int[buckets.size()];
      int ind = 0;
      for (Map.Entry<Integer, Integer> entry : buckets.entrySet()) {
        bs[ind] = entry.getKey();
        qs[ind] = entry.getValue();
        ind++;
      }
    }

    public Barn(int tank, int[] bucks, Barn first) {
      this.tank = tank;
      HashMap<Integer, Integer> buckets = new HashMap<>();
      for (int buck : bucks)
        buckets.put(buck, buckets.getOrDefault(buck, 0) + 1);
      this.bs = first.bs.clone();
      qs = new int[bs.length];
      for (int i = 0; i < bs.length; i++) { // look up frequencies of already known bucket sizes
        qs[i] = buckets.getOrDefault(bs[i], 0);
      }
    }
  }

  public static void countCombos(Barn source, Barn dest, int days) {
    if (days == 5) {
      combos.add(source.tank);
      return;
    }
    for (int i = 0; i != source.bs.length; i++) {
      int key = source.bs[i];
      if (source.qs[i] == 0)
        continue;
      source.qs[i]--;
      dest.qs[i]++;
      source.tank -= key;
      dest.tank += key;
      countCombos(dest, source, days + 1);
      dest.tank -= key;
      source.tank += key;
      source.qs[i]++;
      dest.qs[i]--;
    }
  }
}
