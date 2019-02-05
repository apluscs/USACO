import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class shuttle {

  public static int N;
  public static String init = "", goal = "";
  public static String result = "99 ";

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("shuttle.in"));
        PrintWriter out = new PrintWriter(new File("shuttle.out"));) {
      N = in.nextInt();
      String b = "", w = "";
      for (int i = 0; i < N; i++) {
        b += 'B';
        w += 'W';
      }
      init = w + " " + b;
      goal = b + " " + w;
      minMoves();
      result = result.trim();
      int twen = 0;
      int sp = result.indexOf(' ');
      while (result.indexOf(' ') != -1) {
        out.print(result.substring(0, sp));
        twen++;
        if (twen == 20) {
          out.println();
          twen = 0;
        } else
          out.print(' ');
        result = result.substring(sp + 1);
        sp = result.indexOf(' ');
      }
      out.println(result);
    }
  }
  public static void easySol(PrintWriter out) {
    int loc = N;
    for (int lv = 2; lv <= N + 1; lv++) {
      if (lv % 2 == 0) {    //alternating pattern of left and right, each run longer than last
        for (int i = 0; i < lv; i++) {
          out.print(loc + " ");
          loc += 2;
        }
        loc--;
      } else {
        for (int i = 0; i < lv; i++) {
          out.print(loc + " ");
          loc -= 2;
        }
        loc++;
      }
    }
    if (loc == 0) {
      loc = 2;
    } else {    //get loc back in bounds
      loc = 2 * N;
    }
    for (int lv = N; lv >= 1; lv--) {
      if (lv % 2 == 0) {    //now each run is smaller than the last
        for (int i = 0; i < lv; i++) {
          out.print(loc + " ");
          loc += 2;
        }
        loc -= 3;
      } else {
        for (int i = 0; i < lv; i++) {
          out.print(loc + " ");
          loc -= 2;
        }
        loc += 3;
      }
    }
  }
  
  public static void minMoves() {
    Queue<String[]> toVis = new LinkedList<>();
    HashSet<String> vis = new HashSet<>();
    toVis.add(new String[] {init, ""});
    while (!toVis.isEmpty()) {
      int size = toVis.size();
      for (int i = 0; i < size; i++) { // reviews each path at this length
        String[] curr = toVis.poll();
        vis.add(curr[0]);
        if (curr[0].equals(goal))
          judge(curr[1]);
        List<String[]> moves = genMoves(curr[0], curr[1]);
        for (String[] m : moves)
          if (!vis.contains(m[0]))
            toVis.add(m);
      }
      if (!result.equals("99 "))    //found a solution!
        break;  //can safely do so because we searched each path of this length
    }
  }

  public static List<String[]> genMoves(String state, String path) {
    int sp = state.indexOf(' ');
    char[] ste = state.toCharArray();
    List<String[]> result = new ArrayList<>();
    boolean foundJump = false;
    if (sp > 1 && ste[sp - 1] == 'B' && ste[sp - 2] == 'W') { //jump right
      String m3 = swap(ste.clone(), sp - 2, sp);
      result.add(new String[] {m3, path + (sp - 2 + 1) + " "});
      foundJump = true;
    }
    if (sp < 2 * N - 1 && ste[sp + 1] == 'W' && ste[sp + 2] == 'B') { //jump left
      String m4 = swap(ste.clone(), sp + 2, sp);
      result.add(new String[] {m4, path + (sp + 2 + 1) + " "});
      foundJump = true;
    }
    if (foundJump) return result;   //this makes it run even faster! My rules: jump if you can
    if (sp != 0 && ste[sp - 1] == 'W') {        //and black only goes left, white only goes right
      String m1 = swap(ste.clone(), sp - 1, sp);
      result.add(new String[] {m1, path + (sp - 1 + 1) + " "}); //slide right
    }
    if (sp != 2 * N && ste[sp + 1] == 'B') {
      String m2 = swap(ste.clone(), sp + 1, sp);
      result.add(new String[] {m2, path + (sp + 1 + 1) + " "}); //slide left
    }
    return result;
  }

  public static String swap(char[] s, int a, int b) {
    char temp = s[a];
    s[a] = s[b];
    s[b] = temp;
    return new String(s);
  }

  public static void judge(String path) {
    // System.out.println("candidate: " + path);
    int pStart = Integer.parseInt(path.substring(0, path.indexOf(' ')));    //compares first integer of each string. 
    int rStart = Integer.parseInt(result.substring(0, result.indexOf(' ')));    //luckily for me, there were no ties :D
    if (pStart < rStart)    //this may seem clumsy, but the alternative would be to store paths in int[], which
      result = path;    //would be very difficult to check if .equals(goal)
  }
}
