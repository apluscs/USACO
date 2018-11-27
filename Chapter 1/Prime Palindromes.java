/*The number 151 is a prime palindrome because it is both a prime number and a palindrome (it is the same number when read
forward as backward). Write a program that finds all prime palindromes in the range of two supplied numbers a and b (5 <= a < b 
<= 100,000,000); both a and b are considered to be within the range .

INPUT FORMAT
Line 1: 	Two integers, a and b

SAMPLE INPUT (file pprime.in)
5 500

OUTPUT FORMAT
The list of palindromic primes in numerical order, one per line.

SAMPLE OUTPUT (file pprime.out)
5
7
11
101
131
151
181
191
313
353
373
383
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class pprime {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("pprime.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));){
			n = 0;
			nums = new int[12000];
			generateAll();	//gets all at once, then print only the ones that fit
			screenPrimes();
			while(f.ready()){
				StringTokenizer st = new StringTokenizer(f.readLine());
				int start = Integer.parseInt(st.nextToken());
				int end = Integer.parseInt(st.nextToken());
				for(int i = 0; i < nums.length; i++){
					if(nums[i] >= start && nums[i] <= end){
						out.println(nums[i]);
					}
				}
			}
		} catch (IOException e){}

	}
	
	public static int[] nums;
	public static int n;

	public static void generateAll(){	//first generate all palindromes
		for(int a = 1; a <= 9; a += 2){
			nums[n++] = a;
		}
		for(int a = 1; a <= 9; a += 2){
			int two = a * 10 + a;
			nums[n++] = two;
		}
		for(int a = 1; a <= 9; a += 2){	//three digit
			for(int b = 0; b <= 9; b++){
				int three = a * 100 + a + b * 10;
				nums[n++] = three;
			}
		}
		for(int a = 1; a <= 9; a += 2){		//four digit
			for(int b = 0; b <= 9; b++){
				int four = a * 1000 + a + b * 100 + b * 10;
				nums[n++] = four;
			}
		}
		for(int a = 1; a <= 9; a += 2){	//five digit
			for(int b = 0; b <= 9; b++){
				for(int c = 0; c <= 9; c++){
					nums[n++] = a * 10000 + a + b * 1000 + b * 10 + c * 100;
				}
			}
		}
		for(int a = 1; a <= 9; a += 2){		//six digit
			for(int b = 0; b <= 9; b++){
				for(int c = 0; c <= 9; c++){
					nums[n++] = a*100000 + a + b * 10000 + b * 10 + c * 1000 + c* 100;
				}
			}
		}
		for(int a = 1; a <= 9; a += 2){	//seven digit
			for(int b = 0; b <= 9; b++){
				for(int c = 0; c <= 9; c++){
					for(int d = 0; d <= 9; d++){
						nums[n++] = a*1000000 + a + b * 100000 + b * 10 + c * 10000 + c * 100 + d * 1000;;
					}
				}
			}
		}
		for(int a = 5; a <= 9; a += 2){	//if a != 0, it will be the last digit. Thus, cannot be even
			for(int b = 0; b <= 9; b++){
				for(int c = 0; c <= 9; c++){
					for(int d = 0; d <= 9; d++){
						nums[n++] = a*10000000 + a + b * 1000000 + b * 10 + c * 100000 + c * 100 + d * 10000 + d * 1000;;
					}
				}
			}
		}
	}
	
	public static void screenPrimes(){
		for(int i = 0; i < nums.length; i++){
			nums[i] = (isPrime(nums[i])) ? nums[i] : 0;
		}
	}
	
	public static boolean isPrime(int num){	
		for(int i = 3; i <= Math.sqrt(num); i++){ //already filtered out all evens
			if(num % i == 0){
				return false;
			}
		}
		return true;
	}
}
