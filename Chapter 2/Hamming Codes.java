/* Given N, B, and D: Find a set of N codewords (1 <= N <= 64), each of length B bits (1 <= B <= 8), such that each of the 
 codewords is at least Hamming distance of D (1 <= D <= 7) away from each of the other codewords.

The Hamming distance between a pair of codewords is the number of binary bits that differ in their binary notation. Consider the 
two codewords 0x554 and 0x234 and their differences (0x554 means the hexadecimal number with hex digits 5, 5, and 4)(a hex digit 
requires four bits):

           0x554 = 0101 0101 0100
           0x234 = 0010 0011 0100
Bit differences:   -XXX -XX- ----

Since five bits were different, the Hamming distance is 5.

INPUT FORMAT
N, B, D on a single line

SAMPLE INPUT (file hamming.in)
16 7 3

OUTPUT FORMAT
N codewords, sorted, in decimal, ten per line. In the case of multiple solutions, your program should output the solution which, 
if interpreted as a base 2^B integer, would have the least value.

SAMPLE OUTPUT (file hamming.out)
0 7 25 30 42 45 51 52 75 76
82 85 97 102 120 127
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class hamming {

	public static int N, B, D;
	public static int[][] dists;
	public static List<Integer> result = new ArrayList<>();
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("hamming.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));){
		
		while(f.ready()){
			StringTokenizer st = new StringTokenizer(f.readLine());
			N = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			generateDists();
			generateCode();
			for(int i = 1; i < result.size(); i++){
				if(i % 10 == 0){
					out.println(result.get(i-1));
					continue;
				}
				out.print(result.get(i-1) + " ");
			}
			out.println(result.get(result.size()-1));
		}
		} catch (IOException e){}
	} 
	
	public static void generateCode(){	//complete search
		int previous = 0;
		for(int i = 0; i < N; i++){	//number of codes needed
			for(int j = previous; j < 1 << B; j++){
				boolean flag = true;
				for(int n: result){	//checks if it's far enough from previously derived values
					if(dists[j][n] < D){
						flag = false;
						break;
					}
				}
				if(flag){
					previous = j;
					result.add(j);
					break;	//move to next i value
				}
			}
		}
	}

	public static void generateDists(){	//hamming distances
		int max = 1 << B;
		dists = new int[max][max];
		for(int i = 0; i < max; i++){
			for(int j = 0; j < max; j++){
				int diff = i ^ j;
				while(diff > 0){
					if((diff & 1) == 1){
						dists[i][j]++;
					}
					diff >>= 1;
				}
			}
		}
	}
}
