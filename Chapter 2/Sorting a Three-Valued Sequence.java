/* Sorting is one of the most frequently performed computational tasks. Consider the special sorting problem in which the 
records to be sorted have at most three different key values. This happens for instance when we sort medalists of a competition 
according to medal value, that is, gold medalists come first, followed by silver, and bronze medalists come last.

In this task the possible key values are the integers 1, 2 and 3. The required sorting order is non-decreasing. However, sorting 
has to be accomplished by a sequence of exchange operations. An exchange operation, defined by two position numbers p and q, 
exchanges the elements in positions p and q.

You are given a sequence of key values. Write a program that computes the minimal number of exchange operations that are 
necessary to make the sequence sorted.

INPUT FORMAT
Line 1: 	N (1 <= N <= 1000), the number of records to be sorted
Lines 2-N+1: 	A single integer from the set {1, 2, 3}

SAMPLE INPUT (file sort3.in)
9
2
2
1
3
3
3
2
3
1

OUTPUT FORMAT
A single line containing the number of exchanges required

SAMPLE OUTPUT (file sort3.out)
4
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class sort3 {

	public static void main(String[] args) {
		try(Scanner in = new Scanner(new File("sort3.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));){
		while(in.hasNext()){
			int N = in.nextInt();
			int[] records = new int[N];
			for(int i = 0; i < N; i++){
				records[i] = in.nextInt();
			}
			out.println(minExchanges(records));
		}
		
		} catch (IOException e){}

	}

	public static int minExchanges(int[] records){
		int result = 0;
		int[] frqs = new int[3];
		for(int i = 0; i < records.length; i++){
			frqs[records[i] - 1]++;	//counts number of ones, twos, threes
		}
		List<Integer> twoInOne = new ArrayList<>();	//indexes of 2's where there should be a 1
		List<Integer> oneInTwo = new ArrayList<>();
		List<Integer> twoInThree = new ArrayList<>();
		List<Integer> threeInTwo = new ArrayList<>();
		List<Integer> oneInThree = new ArrayList<>();
		List<Integer> threeInOne = new ArrayList<>();
		for(int i = 0; i < frqs[0]; i++){	//these should be 1's
			if(records[i] == 2){twoInOne.add(i);}
			else if(records[i] == 3){threeInOne.add(i);}
		}
		for(int i = frqs[0]; i < frqs[0] + frqs[1]; i++){	//these should be 2's
			if(records[i] == 1){oneInTwo.add(i);}
			else if(records[i] == 3){threeInTwo.add(i);}
		}
		for(int i = frqs[1] + frqs[0]; i < records.length; i++){	//these should be 3's
			if(records[i] == 1){oneInThree.add(i);}
			else if(records[i] == 2){twoInThree.add(i);}
		}
		result += Math.min(twoInOne.size(), oneInTwo.size()) + Math.min(oneInThree.size(), threeInOne.size()) + Math.min(twoInThree.size(), threeInTwo.size());	//counts number of simple swaps that land each number where it's supposed to be (fastest route to perfection)
		int triads = Math.abs(twoInOne.size() - oneInTwo.size());	//number of three way swaps
		result += 2 * triads;	//each threeway requires two swaps
		return result;
	}
}
