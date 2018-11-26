 /*A number that reads the same from right to left as when read from left to right is called a palindrome. The number 12321 is a 
 palindrome; the number 77778 is not. Of course, palindromes have neither leading nor trailing zeroes, so 0220 is not a p
 alindrome.

The number 21 (base 10) is not palindrome in base 10, but the number 21 (base 10) is, in fact, a palindrome in base 2 (10101).

Write a program that reads two numbers (expressed in base 10):

    N (1 <= N <= 15)
    S (0 < S < 10000) 

and then finds and prints (in base 10) the first N numbers strictly greater than S that are palindromic when written in two or 
more number bases (2 <= base <= 10).

Solutions to this problem do not require manipulating integers larger than the standard 32 bits.

PROGRAM NAME: dualpal

INPUT FORMAT
A single line with space separated integers N and S.

SAMPLE INPUT (file dualpal.in)
3 25

OUTPUT FORMAT
N lines, each with a base 10 number that is palindromic when expressed in at least two of the bases 2..10. The numbers should be 
listed in order from smallest to largest.

SAMPLE OUTPUT (file dualpal.out)
26
27
28
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class dualpal {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("dualpal.in"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));){
			
		while(f.ready()){
			StringTokenizer st = new StringTokenizer(f.readLine());
			int total = Integer.parseInt(st.nextToken());
			int min = Integer.parseInt(st.nextToken());
			for(int n: findDuals(total, min)){
				out.println(n);
			}
		}		

		} catch (FileNotFoundException e) {} catch (IOException e) {}		
	}
	
	public static int[] findDuals(int total, int min){
		int[] result = new int[total];	int ind = 0;
		int poss = min + 1;	//a possible dual pal
		while(ind < total){
			int two = 0;
			for(int base = 2; base < 11; base++){	//checks all the bases
				String converted = convert(poss, base);
				if(isPalindrome(converted + "")){
					two++;
				}
				if(two == 2){
					result[ind++] = poss;
					break;
				}
			}
			poss++;
		}
		return result;
	}
	
	public static String convert(int num, int base){
		if(base == 10){return "" + num;}
		if(num == 1){return "1";}
		long temp = num;
		long multiplier = 1;
		while(temp/multiplier > 0){	//multiplier is largest power of said base that "fits" under num
			multiplier *= base;
		}
		multiplier /= base;
		temp = num;	
		String result = "";
		
		while(multiplier > 0){
			result += (int)(temp/multiplier);
			temp %= multiplier;
			multiplier /= base;
		}
		return result;
	}
	
	public static boolean isPalindrome(String num){	
		int length = num.length();
		for(int i = 0; i < length/2; i++){
			if(num.charAt(i) != num.charAt(length - i - 1)){
				return false;
			}
		}
		return true;
	}
}
