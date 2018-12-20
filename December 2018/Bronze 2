/*There are three buckets, each with different capacities and initial fluid amounts. Farmer John pours the contents of Bucket 
One into Bucket Two, Two into Three, Three into One, and so on for 100 pours. He pours as much as he can from the source to 
destined bucket before the latter overflows. Find the final state of the buckets.*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class mixmilk {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("mixmilk.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mixmilk.out")));){
			int[][] buckets = new int[2][3];
			StringTokenizer first = new StringTokenizer(f.readLine());
			StringTokenizer second = new StringTokenizer(f.readLine());
			StringTokenizer third = new StringTokenizer(f.readLine());
			buckets[1][0] = Integer.parseInt(first.nextToken());	
			buckets[0][0] = Integer.parseInt(first.nextToken());	//first row is curr amount
			buckets[1][1] = Integer.parseInt(second.nextToken());
			buckets[0][1] = Integer.parseInt(second.nextToken());
			buckets[1][2] = Integer.parseInt(third.nextToken());
			buckets[0][2] = Integer.parseInt(third.nextToken());
			int[] result = pourHundred(buckets);
			out.println(result[0]);
			out.println(result[1]);
			out.println(result[2]);
		} catch (IOException e) {}

	}

	public static int[] pourHundred(int[][] buckets){
		for(int i = 1; i <= 100; i++){
			if(i % 3 == 1){	//first to second
				pour(buckets, 0, 1);
			} else if(i % 3 == 2){
				pour(buckets, 1, 2);
			} else{
				pour(buckets, 2, 0);
			}
		}
		return new int[] {buckets[0][0], buckets[0][1], buckets[0][2]};
	}
	
	public static void pour(int[][] buckets, int from, int to){
		if(buckets[0][from] + buckets[0][to] > buckets[1][to]){
			buckets[0][from] = buckets[0][from] - (buckets[1][to] - buckets[0][to]);
			buckets[0][to] = buckets[1][to];	//to max cap
			
		} else{
			buckets[0][to] += buckets[0][from];
			buckets[0][from] = 0;
		}
	}
}
