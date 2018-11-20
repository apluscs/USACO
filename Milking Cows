Three farmers rise at 5 am each morning and head for the barn to milk three cows. The first farmer begins milking his cow at 
time 300 (measured in seconds after 5 am) and ends at time 1000. The second farmer begins at time 700 and ends at time 1200. The 
third farmer begins at time 1500 and ends at time 2100. The longest continuous time during which at least one farmer was milking 
a cow was 900 seconds (from 300 to 1200). The longest time no milking was done, between the beginning and the ending of all 
milking, was 300 seconds (1500 minus 1200).

Your job is to write a program that will examine a list of beginning and ending times for N (1 <= N <= 5000) farmers milking N 
cows and compute (in seconds):

    The longest time interval at least one cow was milked.
    The longest time interval (after milking starts) during which no cows were being milked. 

PROGRAM NAME: milk2
INPUT FORMAT
Line 1: 	The single integer, N
Lines 2..N+1: 	Two non-negative integers less than 1,000,000, respectively the starting and ending time in seconds after 0500

SAMPLE INPUT (file milk2.in)
3
300 1000
700 1200
1500 2100

OUTPUT FORMAT
A single line with two integers that represent the longest continuous time of milking and the longest idle time. 

SAMPLE OUTPUT (file milk2.out)
900 300

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

public class milk2 {

	public static void main(String[] args) {
		try( BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));){
			while(f.ready()){
				int total = Integer.parseInt(f.readLine());
				List<Interval> intervals = new ArrayList<>();
				for(int i = 0; i < total; i++){
					StringTokenizer st = new StringTokenizer(f.readLine());
					int a = Integer.parseInt(st.nextToken());
					int b = Integer.parseInt(st.nextToken());
					Interval in = new Interval(a, b);
					intervals.add(in);
					
				}
				Collections.sort(intervals);	//sorted by start index
				out.println(longest(intervals)[0] + " " + longest(intervals)[1]);
			}
		} catch (IOException e) {}

	}

	public static int[] longest(List<Interval> intervals){
		int[] result = new int[2];
		int front = intervals.get(0).start; int back = intervals.get(0).end;	
		int oneMax = back - front;	//one refers to longest time interval at least one cow was milked
		int noMax = 0;	//no refers to longest time interval (after milking starts) during which no cows were being 
		milked
		for(int i = 1; i < intervals.size(); i++){
			Interval curr = intervals.get(i);
			if(curr.start <= back){
				back = Math.max(curr.end, back);	//two overlapping intervals are merged
			}
			else{	//no overlap
				oneMax = Math.max(oneMax, back - front);	//update max
				front = curr.start;	//adjusts for a new interval chain
				noMax = Math.max(noMax, front - back);	//front on new interval, back on old interval
				back = curr.end;
			}
		}
		oneMax = Math.max(oneMax, back - front);	//accounts for last interval
		
		result[0] = oneMax;
		result[1] = noMax;
		return result;
	}
}


class Interval implements Comparable<Interval>{
	public int start;	public int end;
	public Interval(int start, int end){
		this.start = start;
		this.end = end;
	}
	
	public int compareTo(Interval o) {
	    if(o==null)
	        return -1;
	    return  start-o.start;
	}
}
