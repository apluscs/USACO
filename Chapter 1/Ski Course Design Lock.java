/*Farmer John has N hills on his farm (1 <= N <= 1,000), each with an integer elevation in the range 0 .. 100. In the winter, 
since there is abundant snow on these hills, FJ routinely operates a ski training camp.

Unfortunately, FJ has just found out about a new tax that will be assessed next year on farms used as ski training camps. Upon 
careful reading of the law, however, he discovers that the official definition of a ski camp requires the difference between the 
highest and lowest hill on his property to be strictly larger than 17. Therefore, if he shortens his tallest hills and adds mass 
to increase the height of his shorter hills, FJ can avoid paying the tax as long as the new difference between the highest and 
lowest hill is at most 17.

If it costs x^2 units of money to change the height of a hill by x units, what is the minimum amount of money FJ will need to 
pay? FJ can change the height of a hill only once, so the total cost for each hill is the square of the difference between its 
original and final height. FJ is only willing to change the height of each hill by an integer amount.

INPUT FORMAT:
Line 1:	The integer N.
Lines 2..1+N:	Each line contains the elevation of a single hill.

SAMPLE INPUT (file skidesign.in):
5
20
4
1
24
21

INPUT DETAILS:
FJ's farm has 5 hills, with elevations 1, 4, 20, 21, and 24.
OUTPUT FORMAT:
The minimum amount FJ needs to pay to modify the elevations of his hills so the difference between largest and smallest is at 
most 17 units.

SAMPLE OUTPUT (file skidesign.out):
18

OUTPUT DETAILS:
FJ keeps the hills of heights 4, 20, and 21 as they are. He adds mass to the hill of height 1, bringing it to height 4 (cost = 
3^2 = 9). He shortens the hill of height 24 to height 21, also at a cost of 3^2 = 9. 
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class skidesign {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("skidesign.in"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));){
			while(f.ready()){
				int length = Integer.parseInt(f.readLine());
				int[] hills = new int[length];
				for(int i = 0; i < length; i++){
					hills[i] = Integer.parseInt(f.readLine());
				}
				Arrays.sort(hills);
				out.println(minCost(hills));
			}
		} catch (IOException e) {}
	}

	public static int minCost(int[] hills){
		int result = Integer.MAX_VALUE;
		int total = hills.length;
		for(int height = hills[0]; height < hills[total-1]; height++){	//need to hit every possible window, not the just ones that 
    //include hills[] values
			int max = height + 17;	//pretend it's the min height
			int min = height - 17; 	//pretend it's the max height
			int min2 = Math.min(adjust(hills, height, max), adjust(hills, min, height));
			result = Math.min(result, min2);
		}
		return result;
	}
	
	public static int adjust(int[] hills, int min, int max){
		int result = 0;
		for(int i = 0; i < hills.length; i++){
			int diff = 0;
			if(hills[i] < min){
				diff = min - hills[i];
				result += diff * diff;
			}
			else if(hills[i] > max){
				diff = hills[i] - max;
				result += diff * diff;
			}
		}
		return result;	
	}
}
