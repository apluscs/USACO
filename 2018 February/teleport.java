package usacoPractice;

// 2018 February Contest, Bronze Problem 1. Teleportation
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class teleport {

  public static int A, B, t1, t2;
  public static int[] sks;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("teleport.in"));
        PrintWriter out = new PrintWriter(new File("teleport.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      A = Integer.parseInt(st.nextToken());
      B = Integer.parseInt(st.nextToken());
      t1 = Integer.parseInt(st.nextToken());
      t2 = Integer.parseInt(st.nextToken());
      int direct = Math.abs(B - A);
      int p1 = Math.abs(A - t1) + Math.abs(B - t2);
      int p2 = Math.abs(A - t2) + Math.abs(B - t1);
      out.println(Math.min(p1, Math.min(p2, direct)));
    }
  }

}
