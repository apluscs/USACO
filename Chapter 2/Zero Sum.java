/*Consider the sequence of digits from 1 through N (where N=9) in increasing order: 1 2 3 ... N.

Now insert either a `+' for addition or a `-' for subtraction or a ` ' [blank] to run the digits together between each pair of 
digits (not in front of the first digit). Calculate the result that of the expression and see if you get zero.

Write a program that will find all sequences of length N that produce a zero sum.

INPUT FORMAT
A single line with the integer N (3 <= N <= 9).

SAMPLE INPUT (file zerosum.in)
7

OUTPUT FORMAT
In ASCII order, show each sequence that can create 0 sum with a `+', `-', or ` ' between each pair of numbers.

SAMPLE OUTPUT (file zerosum.out)
1+2-3+4-5-6+7
1+2-3-4+5+6-7
1-2 3+4+5+6+7
1-2 3-4 5+6 7
1-2+3+4-5+6-7
1-2-3-4-5+6+7
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class zerosum {

	public static int N;
	public static int[] digits = {1,2,3,4,5,6,7,8,9};
	public static List<char[]> result = new ArrayList<>();
	
	public static void main(String[] args) {
		try (Scanner f = new Scanner(new File("zerosum.in"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));) {
			N = f.nextInt();
			char[] opers = new char[N - 1];
			generateOperations(0, 1, 1, 1, opers);	//first num starts at 2, sum starts at 1
			for(char[] res : result){
				StringBuilder sb = new StringBuilder();
				for(int i = 1; i < N; i++){
					sb.append(i + "" + res[i-1]);
				}
				sb.append(N);
				out.println(sb.toString());
			}
		} catch (IOException e){}
	}

	public static void generateOperations(int sum, int prod, int sign, int curr, char[] combo){
		if(curr == N){	
			if(sum + sign * prod == 0){	//deals with last set of digits
				result.add(combo.clone());	//if not cloned, all previous entries have '-' as last slot. WHY?
			}	return;
		}
		char[] opers = combo.clone();
		opers[curr-1] = ' ';
		generateOperations(sum, prod * 10 + digits[curr], sign, curr + 1, opers);	//prod gets expanded every step
		opers[curr-1] = '+';
		generateOperations(sum + sign * prod, curr + 1, 1, curr + 1, opers);
		opers[curr-1] = '-';	//sign implemented when you add sign * prod
		generateOperations(sum + sign * prod, curr + 1, -1, curr + 1, opers);
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class zerosumBrutal {

	public static int N;
	public static int[] digits = {1,2,3,4,5,6,7,8,9};
	public static List<char[]> result = new ArrayList<>();
	
	public static void main(String[] args) {
		try (Scanner f = new Scanner(new File("zerosum.in"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));) {
			N = f.nextInt();
			char[] opers = new char[N - 1];
			generateOperations(1, opers);	//always N - 1 gaps
			for(char[] res : result){
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < N - 1; i++){
					sb.append(digits[i] + "" + res[i]);
				}
				sb.append(N);
				out.println(sb.toString());
			}
		} catch (IOException e){}
	}

	public static void generateOperations(int gap, char[] combo){
		char[] opers = combo.clone();
		if(gap == N){
			validate(opers);
			return;
		}
		opers[gap-1] = ' ';
		generateOperations(gap + 1, opers);
		opers[gap-1] = '+';
		generateOperations(gap + 1, opers);
		opers[gap-1] = '-';
		generateOperations(gap + 1, opers);
	}
	
	public static void validate(char[] opers){
		int sum = 0;
		for(int i = 0; i < N - 1; i++){
			int prod = digits[i];
			int start = i;
			while(i < N - 1 && opers[i] == ' '){
				prod = prod * 10 + digits[i + 1];
				i++;
			}
			if(i > start){
				if(start == 0 || opers[start - 1] == '+'){
					sum += prod;
				}
				else{
					sum -= prod;
				}
				continue;
			}
			if(i == 0 || opers[i-1] == '+'){
				sum += digits[i];
			}
			else{
				sum -= digits[i];
			}
		}
		if(opers[opers.length-1] == '-'){
			sum -= N;
		}
		else if(opers[opers.length - 1] == '+'){
			sum += N;
		}
		if(sum == 0){
			result.add(opers);
		}
	}
}
