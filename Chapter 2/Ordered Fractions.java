/* Consider the set of all reduced fractions between 0 and 1 inclusive with denominators less than or equal to N.

Here is the set when N = 5:

0/1 1/5 1/4 1/3 2/5 1/2 3/5 2/3 3/4 4/5 1/1

Write a program that, given an integer N between 1 and 160 inclusive, prints the fractions in order of increasing magnitude.

INPUT FORMAT
One line with a single integer N.

SAMPLE INPUT (file frac1.in)
5

OUTPUT FORMAT
One fraction per line, sorted in order of magnitude.

SAMPLE OUTPUT (file frac1.out)
0/1
1/5
1/4
1/3
2/5
1/2
3/5
2/3
3/4
4/5
1/1
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class frac1 {

	public static void main(String[] args) {
		try(Scanner in = new Scanner(new File("frac1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));){
			while(in.hasNext()){
				fracs.clear();	
				fracs.add(new int[]{0,1});
				genFrac(in.nextInt());
				Collections.sort(fracs, (a, b) -> {
					double first = (double)a[0]/a[1];
					double second = (double)b[0]/b[1];
					if(first > second){return 1;}
					return -1;
				});
				for(int[] i : fracs){
					out.println(i[0] + "/" + i[1]);
				}
			}
		} catch (IOException e){}

	}
	
	public static List<int[]> fracs = new ArrayList<>();
	
	public static void genFrac(int N){
		for(int num = 1; num <= N; num++){
			for(int den = 1; den <= N; den++){
				if(num > den){
					continue;
				}
				if(num == 1 || den == 1 || gcd(num, den) == 1){	//ensures they are relatively prime
					fracs.add(new int[] {num, den});
				}
			}
		}
	}
	
	public static int gcd(int a, int b){
		int max = Math.max(a,b);
		int min = Math.min(a, b);
		int remainder = max % min;
		if(remainder == 1){return 1;}
		if(remainder == 0){return min;}
		return gcd(min, remainder);
	}
}
