import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class starry {
  public static int H, W, cid = 1, cpid = 1; // height, width, curr id, curr parent id
  public static int[][] sky, grid; // cid specific to each cluster, cpid specific to each group
  public static Cluster[] clus = new Cluster[502];

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("starry.in"));
        PrintWriter out = new PrintWriter(new File("starry.out"));) {
      W = Integer.parseInt(in.readLine());
      H = Integer.parseInt(in.readLine());
      sky = new int[H][W];
      grid = new int[H][W];
      char[][] res = new char[H][W];
      for (int i = 0; i < H; i++) {
        String line = in.readLine();
        for (int j = 0; j < W; j++)
          grid[i][j] = line.charAt(j) - '0';
      }
      for (int i = 0; i < H; i++)
        for (int j = 0; j < W; j++)
          if (grid[i][j] == 1 && sky[i][j] == 0) { // found a NEW cluster
            int[] dm = flood(i, j);
            clus[cid] = new Cluster(dm, cid);
            cid++;
          }

      for (int i = 1; i < cid; i++) {
        boolean match = false;
        for (int j = 1; j < i; j++) { // compare cluster i to everything that came before it
          Cluster a = clus[i];
          Cluster b = clus[j];
          if (a.h == b.h && a.w == b.w && compare(a.norm, b.norm)) {
            clus[i].pid = clus[j].pid;
            match = true;
            break;
          } else if (a.h == b.w && a.w == b.h && compare(a.rot, b.norm)) {
            clus[i].pid = clus[j].pid;
            match = true;
            break;
          }
        }
        if (!match) clus[i].pid = cpid++;
      }
      for (int c = 1; c < cid; c++) {
        Cluster cl = clus[c];
        for (int i = 0; i < cl.h; i++)
          for (int j = 0; j < cl.w; j++)
            if (cl.norm[i][j] == 1) // star exists here, need to match to position in sky
              res[i + cl.loc[0]][j + cl.loc[1]] = (char) (cl.pid + 'a' - 1);
      } //@formatter:off
      for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++){
          if (res[i][j] != 0) out.print(res[i][j]);
          else out.print("0");
        }
        out.println();
      }
    }
  }

  public static boolean compare(int[][] a, int[][] b) { 
    int h = a.length;
    int w = a[0].length;
    boolean match = true;
    for (int i = 0; i < h; i++) for (int j = 0; j < w; j++)  // reflect both
        if (a[i][j] != b[h - i - 1][w - j - 1]) match = false;
    if (match) return true;
    match = true;   //reset for each orientation
    
    for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) // exact match
        if (a[i][j] != b[i][j]) match = false;
    if (match) return true;
    match = true;
    
    for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) // reflect x
        if (a[i][j] != b[h - i - 1][j]) match = false;
    if (match) return true;
    match = true;
    
    for (int i = 0; i < h; i++) for (int j = 0; j < w; j++)  // reflect y
      if (a[i][j] != b[i][w - j - 1]) match = false;
    if (match) return true;
    return false;
  }

  public static int[] flood(int r, int c) {
    int n = 404, s = -404, e = -404, w = 404;
    Queue<int[]> toVis = new LinkedList<>();
    toVis.add(new int[] {r, c});
    while (!toVis.isEmpty()) {
      int[] curr = toVis.poll(); 
      int i = curr[0], j = curr[1];
      if(sky[i][j]!=0) continue;
      n=Math.min(n, i);
      s=Math.max(s, i);
      e=Math.max(e, j);
      w=Math.min(w, j);
      sky[i][j] = cid;  //check 8 possible neighbors
      if (i != 0 && grid[i - 1][j] == 1)                    toVis.add(new int[] {i - 1, j});
      if (i != 0 && j != W - 1 && grid[i - 1][j + 1] == 1)  toVis.add(new int[] {i - 1, j + 1});
      if (j != W - 1 && grid[i][j + 1] == 1)                toVis.add(new int[] {i, j + 1});
      if (i != H - 1 && j != W - 1 && grid[i + 1][j + 1] == 1)  toVis.add(new int[] {i + 1, j + 1});
      if (i != H - 1 && grid[i + 1][j] == 1)                    toVis.add(new int[] {i + 1, j});
      if (i != H - 1 && j != 0 && grid[i + 1][j - 1] == 1)      toVis.add(new int[] {i + 1, j - 1});
      if (j != 0 && grid[i][j - 1] == 1)                    toVis.add(new int[] {i, j - 1});
      if (i != 0 && j != 0 && grid[i - 1][j - 1] == 1)      toVis.add(new int[] {i - 1, j - 1});
    }
    return new int[]{n,s,e,w};
  }
  
  private static class Cluster {
    int pid=-1, h,w;
    int[] loc;  //top left corner
    int id;
    int[][] norm, rot;

    public Cluster(int[] dm, int id) {  //n, s, e, w
      loc = new int[] {dm[0],dm[3]};
      this.id=id;
      h=dm[1]-dm[0]+1;
      w=dm[2]-dm[3]+1;
      norm=new int[h][w];
      rot=new int[w][h];
      for(int i=0;i<h;i++)for(int j=0;j<w;j++)
        if(sky[loc[0]+i][loc[1]+j]==id){  //ensures any irrelevant stars in middle don't get counted
          norm[i][j]=1;
          rot[j][i]=1; //rows become columns
        }
    }
  }
}
