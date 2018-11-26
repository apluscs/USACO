 Consider the number triangle shown below. Write a program that calculates the highest sum of numbers that can be passed on a 
 route that starts at the top and ends somewhere on the base. Each step can go either diagonally down to the left or diagonally 
 down to the right.

          7

        3   8

      8   1   0

    2   7   4   4

  4   5   2   6   5

In the sample above, the route from 7 to 3 to 8 to 7 to 5 produces the highest sum: 30.

INPUT FORMAT
The first line contains R (1 <= R <= 1000), the number of rows. Each subsequent line contains the integers for that particular 
row of the triangle. All the supplied integers are non-negative and no larger than 100.

SAMPLE INPUT (file numtri.in)
5
7
3 8
8 1 0
2 7 4 4
4 5 2 6 5

OUTPUT FORMAT
A single line containing the largest sum using the traversal specified.

SAMPLE OUTPUT (file numtri.out)
30


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class numtri {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("numtri.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));){
			while(f.ready()){
				total = Integer.parseInt(f.readLine());
				nums = new int[total][total];
				for(int i = 0; i < total; i++){
					StringTokenizer st = new StringTokenizer(f.readLine());
					int j = 0;
					while(st.hasMoreTokens()){
						nums[i][j++] = Integer.parseInt(st.nextToken());
					}
				}
				out.println(maxSum());	//start at root node
			}
		} catch (IOException e){}
	}

	public static int[][] nums;
	public static int total;
	
	public static int maxSum(){
		int result = nums[0][0];
		for(int row = 1; row < total; row++){	//BFS. Each time it goes down to a new point in the tree, it asks: "How can I get here with the most points collected?" [by examining amount accrued at two points at the previous level
			int leftBranch = 0; 
			for(int col = 0; col <= row; col++){
				int rightBranch = (col < row) ? nums[row-1][col] : 0;	//only rightmost node of each level is left without rightBranch
				nums[row][col] += Math.max(leftBranch, rightBranch);
				leftBranch = rightBranch;
			}
			
		}
		for(int last = 0; last < total; last++){	//looks over bottom row
			result = (nums[total-1][last] > result) ? nums[total-1][last] : result;
		}
		return result;
	}
}
