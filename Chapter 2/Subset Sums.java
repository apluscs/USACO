/* For many sets of consecutive integers from 1 through N (1 <= N <= 39), one can partition the set into two sets whose sums are 
identical.

For example, if N=3, one can partition the set {1, 2, 3} in one way so that the sums of both subsets are identical:

    {3} and {1,2} 

This counts as a single partitioning (i.e., reversing the order counts as the same partitioning and thus does not increase the 
count of partitions).

If N=7, there are four ways to partition the set {1, 2, 3, ... 7} so that each partition has the same sum:

    {1,6,7} and {2,3,4,5}
    {2,5,7} and {1,3,4,6}
    {3,4,7} and {1,2,5,6}
    {1,2,4,7} and {3,5,6} 

Given N, your program should print the number of ways a set containing the integers from 1 through N can be partitioned into two 
sets whose sums are identical. Print 0 if there are no such ways.

Your program must calculate the answer, not look it up from a table.

INPUT FORMAT
The input file contains a single line with a single integer representing N, as above.

SAMPLE INPUT (file subset.in)
7

OUTPUT FORMAT
The output file contains a single line with a single integer that tells how many same-sum partitions can be made from the set 
{1, 2, ..., N}. The output file should contain 0 if there are no ways to make a same-sum partition.

SAMPLE OUTPUT (file subset.out)
4
*/


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class subset {

	
	public static void main(String[] args){
		try(Scanner f = new Scanner(new File("subset.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));){
		while(f.hasNext()){
			int num = f.nextInt();
			out.println(countPartitions(num));
		}
			
		}	catch (IOException e){}
	}
	
	public static long countPartitions(int num){
		int sum = (int)((num + 1) * (double)num/2);	
		if(sum % 2 == 1){return 0;}	//cannot divide an odd number evenly into integer halves
		sum /= 2;
		
		long[] paths = new long[sum + 1];
		paths[0] = 1;	
		for(int i = 1; i <= num; i++){	//numbers available to add
			for(int j = sum; j >= i; j--){
				paths[j] += paths[j-i];	//adds #paths to j without i thrown into the mix WITH #paths to j with i thrown in
			}
		}
		return paths[sum]/2;	//we are only considering half of each solution, but the program counts both subsets of the same partition as two solutions, so we must divide by 2
	}
}
