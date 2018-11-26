/*The Merry Milk Makers company buys milk from farmers, packages it into attractive 1- and 2-Unit bottles, and then sells that 
milk to grocery stores so we can each start our day with delicious cereal and milk.

Since milk packaging is such a difficult business in which to make money, it is important to keep the costs as low as possible. 
Help Merry Milk Makers purchase the farmers' milk in the cheapest possible manner. The MMM company has an extraordinarily 
talented marketing department and knows precisely how much milk they need each day to package for their customers.

The company has contracts with several farmers from whom they may purchase milk, and each farmer has a (potentially) different 
price at which they sell milk to the packing plant. Of course, a herd of cows can only produce so much milk each day, so the 
farmers already know how much milk they will have available.

Each day, Merry Milk Makers can purchase an integer number of units of milk from each farmer, a number that is always less than 
or equal to the farmer's limit (and might be the entire production from that farmer, none of the production, or any integer in 
between).

Given:

    The Merry Milk Makers' daily requirement of milk
    The cost per unit for milk from each farmer
    The amount of milk available from each farmer 

calculate the minimum amount of money that Merry Milk Makers must spend to meet their daily need for milk.

Note: The total milk produced per day by the farmers will always be sufficient to meet the demands of the Merry Milk Makers even 
if the prices are high.

INPUT FORMAT
Line 1: 	Two integers, N and M.
The first value, N, (0 <= N <= 2,000,000) is the amount of milk that Merry Milk Makers wants per day.
The second, M, (0 <= M <= 5,000) is the number of farmers that they may buy from.
Lines 2 through M+1: 	The next M lines each contain two integers: Pi and Ai.
Pi (0 <= Pi <= 1,000) is price in cents that farmer i charges.
Ai (0 <= Ai <= 2,000,000) is the amount of milk that farmer i can sell to Merry Milk Makers per day.

SAMPLE INPUT (file milk.in)
100 5
5 20
9 40
3 10
8 80
6 30

INPUT EXPLANATION
100 5 -- MMM wants 100 units of milk from 5 farmers
5 20 -- Farmer 1 says, "I can sell you 20 units at 5 cents per unit"
9 40 etc.
3 10 -- Farmer 3 says, "I can sell you 10 units at 3 cents per unit"
8 80 etc.
6 30 -- Farmer 5 says, "I can sell you 30 units at 6 cents per unit"

OUTPUT FORMAT
A single line with a single integer that is the minimum cost that Merry Milk Makers must pay for one day's milk.

SAMPLE OUTPUT (file milk.out)
630

OUTPUT EXPLANATION
Here's how the MMM company spent only 630 cents to purchase 100 units of milk:
Price
per unit 	Units
available 	Units
bought 	Price *
# units 	Total cost 	Notes
5 	20 	20 	5*20 	100
9 	40 	0 			      Bought no milk from farmer 2
3 	10 	10 	3*10 	30
8 	80 	40 	8*40 	320 	Did not buy all 80 units!
6 	30 	30 	6*30 	180
Total	180 	100 		630 	Cheapest total cost
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class milk {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("milk.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));){
			while(f.ready()){
				StringTokenizer st = new StringTokenizer(f.readLine());
				int needed = Integer.parseInt(st.nextToken());
				int farmers = Integer.parseInt(st.nextToken());
				List<int[]> matrix = new ArrayList<int[]>();
				for(int i = 0; i < farmers; i++){
					StringTokenizer dt = new StringTokenizer(f.readLine());
					int[] datum = {Integer.parseInt(dt.nextToken()), Integer.parseInt(dt.nextToken())};	//converts farmer data into array
					matrix.add(datum);
				}
				out.println(minCost(matrix, needed));
			}
		} catch (FileNotFoundException e) {} catch (IOException e) {}
	}
	
	public static int minCost(List<int[]> matrix, int needed){
		int result = 0;
		Collections.sort(matrix, new Comparator<int[]>(){	//sorts based on price
	           public int compare(int[] a, int[] b){
	               return a[0] - b[0];
	           }
	        });
		for(int[] datum: matrix){
			needed -= datum[1];
			result += datum[0] * datum[1];	//adds cost
			if(needed < 0){
				 return result += needed * datum[0];	//if we subtracted too much, we'll add some back. Here we are adding a negative (needed)
			}
		}
		return result;
	}
}
