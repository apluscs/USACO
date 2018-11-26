 /*An arithmetic progression is a sequence of the form a, a+b, a+2b, ..., a+nb where n=0, 1, 2, 3, ... . For this problem, a is a 
 non-negative integer and b is a positive integer.

Write a program that finds all arithmetic progressions of length n in the set S of bisquares. The set of bisquares is defined as 
the set of all integers of the form p2 + q2 (where p and q are non-negative integers).

TIME LIMIT: 5 secs

INPUT FORMAT
Line 1: 	N (3 <= N <= 25), the length of progressions for which to search
Line 2: 	M (1 <= M <= 250), an upper bound to limit the search to the bisquares with 0 <= p,q <= M.

SAMPLE INPUT (file ariprog.in)
5
7

OUTPUT FORMAT
If no sequence is found, a single line reading `NONE'. Otherwise, output one or more lines, each with two integers: the first 
element in a found sequence and the difference between consecutive elements in the same sequence. The lines should be ordered 
with smallest-difference sequences first and smallest starting number within those sequences first.

There will be no more than 10,000 sequences.

SAMPLE OUTPUT (file ariprog.out)
1 4
37 4
2 8
29 8
1 12
5 12
13 12
17 12
5 20
2 24
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ariprog {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("ariprog.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));){
			while(f.ready()){
				int length = Integer.parseInt(f.readLine());
				int max = Integer.parseInt(f.readLine());
				List<int[]> result = findProgs(length, max);
				Collections.sort(result, new Comparator<int[]>(){	//already in order of start number
					public int compare(int[] a, int[] b){
						return(a[1] - b[1]);
					}
				});
				for(int[] prog: result){
					out.println(prog[0] + " " + prog[1]);
				}
				if(result.size() == 0){
					out.println("NONE");
				}
			}
		} catch (IOException e){}
	}
	
	public static List<int[]> findProgs(int length, int max){
		List<int[]> result = new ArrayList<>();
		
		HashSet<Integer> vals = new HashSet<>();	//generate bisquares
		List<Integer> bisq = new ArrayList<>();
		for(int p = 0; p <= max; p++){
			for(int q = p; q <= max; q++){
				int bisquare = p * p + q * q;
				if(!vals.contains(bisquare)){
					bisq.add(bisquare);
				}
				vals.add(bisquare);
			}
		}
		Collections.sort(bisq);
		
		int size = bisq.size();
		int large = bisq.get(size-1);	//largest
		for(int start = 0; start < size - 2; start++){	//min length is 3, so you would never start from the third to last element
			int first = bisq.get(start);
			int j = start + 1;
			while(bisq.get(j) <= (large-first)/(length-1) + first){	//don't need to search the whole, because at a certain point you 
      //wouldn't be able to make a progression of the specified length if the diff is too big
				int diff = bisq.get(j) - first;
				int term = 0;
				while(term <= length - 1){	//search for next term in bisq
					if(!vals.contains(first + term * diff)){
						break;
					}
					term++;
				}
				if(term == length){
					result.add(new int[] {first, diff});
				}
				j++;
			}
		}
		return result;
	}
}
