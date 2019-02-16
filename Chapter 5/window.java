// package usaco;
//@formatter:off
/*
ID: the.cla1
LANG: JAVA
TASK: window
*/
//@formatter:on
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class window {
  public static int top = 1, bot = 0, cPos, result;
  public static Window[] wdws = new Window[76];

  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("window.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("window.out")));
    while (in.ready()) {
      String line=in.readLine();
      int id=line.charAt(2)-'0';
//      System.out.println(id);
      char c=line.charAt(0);
//      System.out.println(c);
      switch(c){
        case 't':
          wdws[id].pos = top++;
          break;
        case 'b':
          wdws[id].pos = bot--;
          break;
        case 'd': wdws[id]=null;    break;
        case 'w': StringTokenizer st=new StringTokenizer(line.substring(4, line.length()-1),",");
          int x1 = Integer.parseInt(st.nextToken());
          int y1 = Integer.parseInt(st.nextToken());
          int x2 = Integer.parseInt(st.nextToken());
          int y2 = Integer.parseInt(st.nextToken());
          wdws[id] = new Window(x1, y1, x2, y2);
          break;
        case 's': 
          Window base = wdws[id];
          cPos = wdws[id].pos; // depth of desired window
//          System.out.println("cPos: "+cPos);
          result=0;
          int ar = (base.u - base.d) * (base.r - base.l);
          dfs(base.u, base.d, base.r, base.l, 0); // start at beginning of window array
          double res = 100.0 * result / ar;
          out.println(String.format("%.3f", res));
      }
//      wdws[id].print();
    }

    in.close();
    out.close();
  }

  public static void dfs(int u, int d, int r, int l, int ind) {
    if (ind == 76) {    //reached end of window array
      result += (u - d) * (r - l);
      // System.out.println((u - d) * (r - l));
      return;
    }
    if (wdws[ind] == null) {    //no window in this spot, try next one
      dfs(u, d, r, l, ind + 1);
      return;
    }
    Window curr = wdws[ind];
    // System.out.println("u: " + u + ", d: " + d + ", l: " + l + ", r:" + r + ", ind: " + ind);
    // curr.print();
    if (curr.pos <= cPos || curr.u <= d || curr.d >= u || curr.l >= r || curr.r <= l) {
      // System.out.println("no overlap");
      dfs(u, d, r, l, ind + 1);     //curr is beneath "base" window or it's not even covering base
      return;
    }
    if (curr.u < u) dfs(u, curr.u, r, l, ind + 1); //partial overlap: cut off what's showing and dfs on that
    if (curr.d > d) dfs(curr.d, d, r, l, ind + 1);
    int tU = Math.min(u, curr.u);   //so we don't double count visible part
    int tD = Math.max(d, curr.d);
    if (curr.r < r) dfs(tU, tD, r, curr.r, ind + 1);
    if (curr.l > l) dfs(tU, tD, curr.l, l, ind + 1);
  }
  private static class Window{
    int u, d, r, l, pos;
    Window(int x1,int y1,int x2,int y2){
      l = Math.min(x1, x2); //not always top left first, bottom right second
      r = Math.max(x1, x2);
      d = Math.min(y1, y2);
      u = Math.max(y1, y2); 
      pos=top++;
    }
    public void print(){
      System.out.println(u + " " + d + " " + l + " " + r + " " + " " + pos + " ");
    }
  }
}
