import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class heritage {

  public static String postOrd = "";

  public static void main(String[] args) throws IOException {
    try (Scanner in = new Scanner(new File("heritage.in"));
        PrintWriter out = new PrintWriter(new File("heritage.out"));) {
      heritage my = new heritage();
      String inOrd = in.next();
      String preOrd = in.next();
      my.genPost(inOrd, preOrd);
      out.println(postOrd);
    }
  }

  private void genPost(String inOrd, String preOrd) {
    if (preOrd.length() == 0)
      return;
    char root = preOrd.charAt(0);
    int rootPos = inOrd.indexOf(root);
    String inLeft = inOrd.substring(0, rootPos); // rootPos aligns with right boundary of preLeft;
                                                 // left and root are just swapped
    String preLeft = preOrd.substring(1, rootPos + 1);
    genPost(inLeft, preLeft);

    String inRight = inOrd.substring(rootPos + 1);
    String preRight = preOrd.substring(rootPos + 1);
    genPost(inRight, preRight);
    postOrd += root; // root comes last
  }
}
