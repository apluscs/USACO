/*Farmer John prides himself on having the healthiest dairy cows in the world. He knows the vitamin content for one scoop of each 
feed type and the minimum daily vitamin requirement for his cows. Help Farmer John feed the cows so they stay healthy while 
minimizing the number of scoops that a cow is fed.

Given the daily requirements of each kind of vitamin that a cow needs, identify the smallest combination of scoops of feed a cow 
can be fed in order to meet at least the minimum vitamin requirements.

Vitamins are measured in integer units. Cows can be fed at most one scoop of any feed type. It is guaranteed that a solution 
exists for all contest input data.

INPUT FORMAT
Line 1: 	integer V (1 <= V <= 25), the number of types of vitamins
Line 2: 	V integers (1 <= each one <= 1000), the minimum requirement for each of the V vitamins that a cow requires each day
Line 3: 	integer G (1 <= G <= 15), the number of types of feeds available
Lines 4..G+3: 	V integers (0 <= each one <= 1000), the amount of each vitamin that one scoop of this feed contains. The first 
line of these G lines describes feed #1; the second line describes feed #2; and so on.

SAMPLE INPUT (file holstein.in)
4
100 200 300 400
3
50   50  50  50
200 300 200 300
900 150 389 399

OUTPUT FORMAT
The output is a single line of output that contains:

    the minimum number of scoops a cow must eat, followed by:
    a SORTED list (from smallest to largest) of the feed types the cow is given 

If more than one set of feedtypes yield a minimum of scoops, choose the set with the smallest feedtype numbers.

SAMPLE OUTPUT (file holstein.out)
2 1 3
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class holstein {
	public static int[] reqs;
	public static int[][] feeds;
	public static List<Integer> toVisit = new ArrayList<>();
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("holstein.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));){
			
		while(f.ready()){
			int vitNum = Integer.parseInt(f.readLine());
			reqs = new int[vitNum];
			StringTokenizer dt = new StringTokenizer(f.readLine());
			for(int j = 0; j < vitNum; j++){
				reqs[j] = Integer.parseInt(dt.nextToken());
			}
			int feedNum = Integer.parseInt(f.readLine());
			feeds = new int[feedNum][vitNum];
			for(int i = 0; i < feedNum; i++){
				StringTokenizer st = new StringTokenizer(f.readLine());
				for(int j = 0; j < vitNum; j++){
					feeds[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			int result = minScoops();
			int feedType = 1;
			List<Integer> res = new ArrayList<>();
			while(result > 0){
				if((result & 1) == 1){	
					res.add(feedType);
				}
				result >>= 1;	feedType++;
			}
			out.print(res.size());
			Collections.sort(res);
			for(int n : res){
				out.print(" " + n);
			}
			out.println();
		}
		
		} catch (IOException e){}
	}
	
	public static int minScoops(){
		int max = 1 << feeds.length;
		
		for(int combo = 1; combo < max; combo++){
			if(satisfies(combo)){
				toVisit.add(combo);
			}
		}
		int result = 0;
		int minScoops = Integer.MAX_VALUE;	int minSum = 0;
		for(int combo: toVisit){
			int temp = combo;
			int numScoops = 0;	int feedType = 1;	int sum = 0;
			while(combo > 0){
				if((combo & 1) == 1){	//last digit in binary is next feedtype to scoop
					numScoops++;	
					sum += feedType;
				}
				combo >>= 1;	feedType++;
			}
			
			if(numScoops < minScoops){	//first selection is number of scoops
				result = temp;
				minScoops = numScoops;
				minSum = sum;
			}
			else if(numScoops == minScoops && sum < minSum){	//if two solutions have same number of scoops, choose the one with the 
      //smaller sum of feedtype numbers
				result = temp;
				minSum = sum;
			}
		}
		return result;
	}
	
	public static boolean satisfies(int combo){
		int feedType = 0;
		int[] sums = new int[reqs.length];	//sums of vitamins using this feedtype combo
		while(combo > 0){
			if((combo & 1) == 1){
				for(int j = 0; j < sums.length; j++){
					sums[j] += feeds[feedType][j];
				}
			}
			combo >>= 1; feedType++;
		}
		for(int j = 0; j < sums.length; j++){
			if(sums[j] < reqs[j]){
				return false;
			}
		}
		return true;
	}
}	
	
