import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class msquare {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("msquare.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));) {
      String dest = "";
      for (int i = 0; i < 8; i++) {
        dest += f.next();
      }
      msquare my = new msquare();
      String result = my.minMoves(dest);
      out.println(result.length());
      out.println(result);
    }
  }

  private String minMoves(String dest) {
    Queue<String[]> toVisit = new LinkedList<>(); // state,path
    HashMap<String, String> visited = new HashMap<>(); // each state only keeps the shortest path
    toVisit.add(new String[] {"12345678", ""}); // no path to get here
    while (!toVisit.isEmpty()) {
      int size = toVisit.size();
      for (int i = 0; i < size; i++) {
        String[] state = toVisit.poll();
        String curr = state[0];
        String path = state[1];
        if (curr.equals(dest)) {
          return path;
        }
        if (visited.containsKey(curr))
          continue;
        visited.put(curr, path);
        String A = aTrans(curr.toCharArray());
        String B = bTrans(curr);
        String C = cTrans(curr.toCharArray());
        toVisit.offer(new String[] {A, path + "A"});
        toVisit.offer(new String[] {B, path + "B"});
        toVisit.offer(new String[] {C, path + "C"});
      }
    }
    return "";
  }

  private String aTrans(char[] curr) {
    for (int i = 0; i < 4; i++) {
      swap(curr, i, 8 - i - 1);
    }
    return new String(curr);
  }

  private String bTrans(String curr) {
    String result = curr.charAt(3) + curr.substring(0, 3) + curr.substring(5, 8) + curr.charAt(4);
    return result;
  }

  private String cTrans(char[] curr) {
    swap(curr, 1, 2);
    swap(curr, 1, 5);
    swap(curr, 1, 6);
    return new String(curr);

  }

  private void swap(char[] curr, int a, int b) {
    char temp = curr[a];
    curr[a] = curr[b];
    curr[b] = temp;
  }
}
