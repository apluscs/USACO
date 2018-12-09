/*Farmer John is considering purchasing a new herd of cows. In this new herd, each mother cow gives birth to two children. The 
relationships among the cows can easily be represented by one or more binary trees with a total of N (3 <= N < 200) nodes. The 
trees have these properties:

    The degree of each node is 0 or 2. The degree is the count of the node's immediate children.
    The height of the tree is equal to K (1 < K < 100). The height is the number of nodes on the longest path from the root to 
    any leaf; a leaf is a node with no children.

How many different possible pedigree structures are there? A pedigree is different if its tree structure differs from that of 
another pedigree. Output the remainder when the total number of different possible pedigrees is divided by 9901.

INPUT FORMAT
    Line 1: Two space-separated integers, N and K. 

SAMPLE INPUT (file nocows.in)
5 3

OUTPUT FORMAT
    Line 1: One single integer number representing the number of possible pedigrees MODULO 9901. 

SAMPLE OUTPUT (file nocows.out)
2

OUTPUT DETAILS
Two possible pedigrees have 5 nodes and height equal to 3:

           @                   @      
          / \                 / \
         @   @      and      @   @
        / \                     / \
       @   @                   @   @
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class nocows {

	public static int N;
	public static int K;
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("nocows.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));){
			StringTokenizer st = new StringTokenizer (f.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			out.println(numPedigrees());
		} catch (IOException e) {}
	}
	
	public static long numPedigrees(){
		long[][] peds = new long[K + 1][N + 1];	//number of ways to make a tree of i levels and j nodes
		for(int i = 0; i <= K; i++){
			peds[i][1] = 1;	//not true, but needed for multiplication
		}
		for(int i = 2; i <= K; i++){
			for(int j = 3; j <= N; j += 2){	//no way to have an even #nodes
				for(int k = 1; k <= j - 2; k++){
					peds[i][j] += (peds[i-1][k] * peds[i-1][j-k-1]);	//double counts most 
					//possibilities because the first number is the left orientation, which can be the right
					//orientation if reflected left to right. this generates another possibility
					
					//furthermore, the column numbers don't add up to j because they do not count the top 
					//node, only the 2 subtrees stemming from that node
					peds[i][j] %= 9901;
				}
			}
		}
		return (peds[K][N] - peds[K-1][N] + 9901) % 9901;	//need to add 9901 in case it's negative
	}	
}
