import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class fact4 {

    public static void main(String[] args) throws IOException {
        try (Scanner f = new Scanner(new File("fact4.in")); 
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));) {
            final int K = f.nextInt();
            fact4 my = new fact4();
            out.println(my.findRightmost(K));
        }

    }

    private int findRightmost(int K) {

        int prod = 1;
        for (int next = 2; next <= K; next++) {
            prod *= next;
            while (prod % 10 == 0) {
                prod /= 10; // remove right zeros
            }
            prod %= 100000; // every power of 5 requires one more nonzero digit to store, 5^5 (<4220) is
            // the most costly case to worry about
        }
        return prod % 10;
    }
}
