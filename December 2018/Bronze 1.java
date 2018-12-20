/*Many cows are being milked at different times, which are given as intervals. Each cow needs at least one bucket to hold its 
milk. Help Farmer John find the maximum number of buckets needed at once.*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class blist {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("blist.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("blist.out")));){
			int[][] milkings = new int[Integer.parseInt(f.readLine())][3];
			int maxTime = 0;
			for(int i = 0; i < milkings.length; i++){
				StringTokenizer st = new StringTokenizer(f.readLine());
				milkings[i][0] = Integer.parseInt(st.nextToken());
				maxTime = (milkings[i][0] > maxTime) ? milkings[i][0] : maxTime;
				milkings[i][1] = Integer.parseInt(st.nextToken());
				maxTime = (milkings[i][1] > maxTime) ? milkings[i][1] : maxTime;
				milkings[i][2] = Integer.parseInt(st.nextToken());
			}
			out.println(partitionTime(milkings, maxTime));
		} catch (IOException e) {}

	}

	public static int partitionTime(int[][] milkings, int maxTime){
		int result = 0;
		for(int t = 1; t <= maxTime; t++){	//iterate over every second
			int buckNeeded = 0;
			for(int m = 0; m < milkings.length; m++){
				int[] curr = milkings[m];
				if(t >= curr[0] && t <= curr[1]){	//fits in the time frame
					buckNeeded += curr[2];
				}
			}
			result = Math.max(result, buckNeeded);
		}
		return result;
	}
}
