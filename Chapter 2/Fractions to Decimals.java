/* Write a program that will accept a fraction of the form N/D, where N is the numerator and D is the denominator and print the 
decimal representation. If the decimal representation has a repeating sequence of digits, indicate the sequence by enclosing it 
in brackets. For example, 1/3 = .33333333...is denoted as 0.(3), and 41/333 = 0.123123123...is denoted as 0.(123). Use xxx.0 to 
denote an integer. Typical conversions are:

1/3     =  0.(3)
22/5    =  4.4
1/7     =  0.(142857)
2/2     =  1.0
3/8     =  0.375
45/56   =  0.803(571428)

INPUT FORMAT
A single line with two space separated integers, N and D, 1 <= N,D <= 100000.

SAMPLE INPUT (file fracdec.in)
45 56

OUTPUT FORMAT
The decimal expansion, as detailed above. If the expansion exceeds 76 characters in length, print it on multiple lines with 76 
characters per line.

SAMPLE OUTPUT (file fracdec.out)
0.803(571428)*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class fracdec {

  public static void main(String[] args) {
    try (BufferedReader f = new BufferedReader(new FileReader("fracdec.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fracdec.out")));) {
      StringTokenizer st = new StringTokenizer(f.readLine());
      int N = Integer.parseInt(st.nextToken());
      int D = Integer.parseInt(st.nextToken());
      fracdec my = new fracdec();
      String result = my.decExpand(N, D);
      while (result.length() > 76) {
        out.println(result.substring(0, 76));
        result = result.substring(76);
      }
      out.println(result);
    } catch (IOException e) {}
  }

  public String decExpand(int N, int D) {
    int[] red = reduce(N, D);
    int num = red[0];
    int den = red[1];
    String result = "";
    if (den == 1) {return num + "." + "0";}
    if (terminates(D)) {
      result += N / D + ".";
      N = (N % D) * 10;
      while (N % D != 0) {
        result += N / D + "";
        N = (N % D) * 10;
      }
      result += N / D;
      return result;
    }
    result += N / D + ".";
    StringBuilder repeat = new StringBuilder(); //after the decimal
    int[] visited = new int[1000000];
    int rem = 10 * (N % D);
    int i = 1;  //index in repeat
    while (visited[rem] == 0) { // unvisited
      visited[rem] = i++;
      repeat.append(rem / D);
      rem = 10 * (rem % D); //to be used as dividend next loop
    }
    return result + repeat.substring(0, visited[rem] - 1) + "("
        + repeat.substring(visited[rem] - 1, repeat.length()) + ")";
  }

  public boolean terminates(int D) { // true if prime factors consist only of 2s and 5s
    while (D % 2 == 0) {D /= 2;}    //factoring out all 2's
    while (D % 5 == 0) {D /= 5;}    //factoring out all 5's
    return D == 1;
  }

  public int[] reduce(int N, int D) {
    int small = N < D ? N : D;
    int gcd = 1;
    for (int g = small; g > 1; g--) {
      if (N % g == 0 && D % g == 0) {
        gcd = g;
        break;
      }
    }
    int[] result = new int[] {N / gcd, D / gcd};
    return result;
  }
}
