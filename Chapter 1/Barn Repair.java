/*It was a dark and stormy night that ripped the roof and gates off the stalls that hold Farmer John's cows. Happily, many of the 
cows were on vacation, so the barn was not completely full.

The cows spend the night in stalls that are arranged adjacent to each other in a long line. Some stalls have cows in them; some 
do not. All stalls are the same width.

Farmer John must quickly erect new boards in front of the stalls, since the doors were lost. His new lumber supplier will supply 
him boards of any length he wishes, but the supplier can only deliver a small number of total boards. Farmer John wishes to 
minimize the total length of the boards he must purchase.

Given M (1 <= M <= 50), the maximum number of boards that can be purchased; S (1 <= S <= 200), the total number of stalls; C (1 
<= C <= S) the number of cows in the stalls, and the C occupied stall numbers (1 <= stall_number <= S), calculate the minimum 
number of stalls that must be blocked in order to block all the stalls that have cows in them.

Print your answer as the total number of stalls blocked.

INPUT FORMAT
Line 1: 	M, S, and C (space separated)
Lines 2-C+1: 	Each line contains one integer, the number of an occupied stall.

SAMPLE INPUT (file barn1.in)
4 50 18
3
4
6
8
14
15
16
17
21
25
26
27
30
31
40
41
42
43

OUTPUT FORMAT
A single line with one integer that represents the total number of stalls blocked.

SAMPLE OUTPUT (file barn1.out)
25
[One minimum arrangement is one board covering stalls 3-8, one covering 14-21, one covering 25-31, and one covering 40-43.] 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
*/

public class barn1 {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("barn1.in"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));){
		while(f.ready()){
			StringTokenizer st = new StringTokenizer(f.readLine());
			int boards = Integer.parseInt(st.nextToken());
			st.nextToken();	//total number of stalls is irrelevant
			int[] cows = new int[Integer.parseInt(st.nextToken())];
			for(int i = 0; i < cows.length; i++){
				cows[i] = Integer.parseInt(f.readLine());
			}
			out.println(minBlocked(cows, boards));
		}
	} catch (FileNotFoundException e) {} catch (IOException e) {}
	}
	
	public static int minBlocked(int[] cows, int boards){
		Arrays.sort(cows);
		int result = cows[cows.length-1] - cows[0] + 1;	//starts with one giant board
		if(boards == 1){
			return result;
		}
		List<Integer> gaps = new ArrayList<Integer>();
		for(int i = 0; i < cows.length-1; i++){
			if(cows[i+1] != cows[i] + 1){
				int gapSize = cows[i+1] - cows[i];	//if nonconsecutive stalls
				gaps.add(gapSize);				
			}
		}
		Collections.sort(gaps, new Comparator<Integer>(){	//sorts based on gap size, greatest to least
			public int compare(Integer a, Integer b){
				return b - a;
			}
		});	
		for(int gap: gaps){
			result -= gap - 1;
			if(boards-- == 2){	//one less board to break
				break;	
			}
		}
		return result;
	}	
}
