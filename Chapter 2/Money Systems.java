/* The cows have not only created their own government but they have chosen to create their own money system. In their own 
rebellious way, they are curious about values of coinage. Traditionally, coins come in values like 1, 5, 10, 20 or 25, 50, and 
100 units, sometimes with a 2 unit coin thrown in for good measure.

The cows want to know how many different ways it is possible to dispense a certain amount of money using various coin systems. 
For instance, using a system of {1, 2, 5, 10, ...} it is possible to create 18 units several different ways, including: 18x1, 
9x2, 8x2+2x1, 3x5+2+1, and many others.

Write a program to compute how many ways to construct a given amount of money using supplied coinage. It is guaranteed that the 
total will fit into both a signed long long (C/C++) and Int64 (Free Pascal).

INPUT FORMAT
The number of coins in the system is V (1 <= V <= 25).
The amount money to construct is N (1 <= N <= 10,000).
Line 1: 	Two integers, V and N
Lines 2..: 	V integers that represent the available coins (no particular number of integers per line)

SAMPLE INPUT (file money.in)
3 10
1 2 5

OUTPUT FORMAT
A single line containing the total number of ways to construct N money units using V coins.

SAMPLE OUTPUT (file money.out)
10
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class money {

	public static int V, N;
	public static int[] coins;
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("money.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("money.out")));){
			StringTokenizer st = new StringTokenizer(f.readLine());
			V = Integer.parseInt(st.nextToken());
			coins = new int[V];
			N = Integer.parseInt(st.nextToken());
			int i = 0;
			while(f.ready()){
				StringTokenizer dt = new StringTokenizer(f.readLine());
				while(dt.hasMoreTokens()){
					coins[i++] = Integer.parseInt(dt.nextToken());
				}
			}
			out.println(countWays2());
		}	catch (IOException e){}

	}

	public static long countWays2(){	//smaller space
		long[] dp = new long[N + 1];	//each entry is number of ways to produce this value using coins at row and rows above
		for(int i = coins[0]; i <= N; i += coins[0]){
			dp[i] = 1;
		}
		dp[0] = 1;
		for(int i = 1; i < V; i++){
			for(int j = coins[i]; j <= N; j++){
				dp[j] += dp[j-coins[i]];
			}
		}
		return dp[N];
	}
	
	public static long countWays(){
		long[][] dp = new long[V][N + 1];	//each entry is number of ways to produce this value using coins at row and rows above
		for(int i = coins[0]; i <= N; i += coins[0]){
			dp[0][i] = 1;
		}
		dp[0][0] = 1;
		for(int i = 1; i < V; i++){
			for(int j = 0; j < coins[i] && j <= N; j++){
				dp[i][j] = dp[i-1][j];	//when this row's value is too large, must rely solely on smaller coins
			}
			for(int j = coins[i]; j <= N; j++){
				dp[i][j] = dp[i-1][j] + dp[i][j-coins[i]];
			}
		}
		return dp[V-1][N];
	}
}
