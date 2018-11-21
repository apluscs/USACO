Palindromes are numbers that read the same forwards as backwards. The number 12321 is a typical palindrome.

Given a number base B (2 <= B <= 20 base 10), print all the integers N (1 <= N <= 300 base 10) such that the square of N is 
palindromic when expressed in base B; also print the value of that palindromic square. Use the letters 'A', 'B', and so on to 
represent the digits 10, 11, and so on.

Print both the number and its square in base B.

INPUT FORMAT
A single line with B, the base (specified in base 10).

SAMPLE INPUT (file palsquare.in)
10

OUTPUT FORMAT
Lines with two integers represented in base B. The first integer is the number whose square is palindromic; the second integer 
is the square itself. NOTE WELL THAT BOTH INTEGERS ARE IN BASE B!

SAMPLE OUTPUT (file palsquare.out)
1 1
2 4
3 9
11 121
22 484
26 676
101 10201
111 12321
121 14641
202 40804
212 44944
264 69696


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class palsquare {

	public static void main(String[] args) {
		try(Scanner in = new Scanner(new File("palsquare.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));){
		while(in.hasNext()){
			for(String s: findPalindromes(in.nextInt())){
				out.println(s);
			}
		}
		} catch (IOException e) {}
	}
	
	public static List<String> findPalindromes(int base){
		List<String> result = new ArrayList<>();
		for(int i = 1; i <= 300; i++){
			String converted = convert(i*i, base);
			if(isPalindrome(converted)){
				result.add(convert(i, base) + " " + converted);
			}
		}
		return result;
	}
	
	public static String convert(long num, int base){
		if(base == 10){return "" + num;}
		if(num == 1){return "1";}
		String key = "0123456789ABCDEFGHIJ";
		long temp = num;
		long multiplier = 1;
		while(temp/multiplier > 0){	//multiplier is largest power of said base that "fits" under num
			multiplier *= base;
		}
		multiplier /= base;
		temp = num;	
		String result = "";
		
		while(multiplier > 0){
			result += key.charAt((int)(temp/multiplier));
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
