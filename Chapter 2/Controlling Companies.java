 Some companies are partial owners of other companies because they have acquired part of their total shares of stock. For 
 example, Ford at one point owned 12% of Mazda. It is said that a company A controls company B if at least one of the following 
 conditions is satisfied:

    Company A = Company B
    Company A owns more than 50% of Company B
    Company A controls K (K >= 1) companies denoted C1, ..., CK with each company Ci owning xi% of company B and x1 + .... + xK 
    > 50%.

Given a list of triples (i,j,p) which denote company i owning p% of company j, calculate all the pairs (h,s) in which company h 
controls company s. There are at most 100 companies.

Write a program to read the list of triples (i,j,p) where i, j and p are positive integers all in the range (1..100) and find 
all the pairs (h,s) so that company h controls company s.

INPUT FORMAT
Line 1: 	n, the number of input triples to follow
Line 2..n+1: 	Three integers per line as a triple (i,j,p) described above.

SAMPLE INPUT (file concom.in)
3
1 2 80
2 3 80
3 1 20

OUTPUT FORMAT
List 0 or more companies that control other companies. Each line contains two integers that denote that the company whose number 
is the first integer controls the company whose number is the second integer. Order the lines in ascending order of the first 
integer (and ascending order of the second integer to break ties). Do not print that a company controls itself.

SAMPLE OUTPUT (file concom.out)
1 2
1 3
2 3
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class concom {

	public static int[][] controls;
	public static int maxn = 0;
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("concom.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));){
			Integer.parseInt(f.readLine());
			controls = new int[100][100];
			while(f.ready()){
				StringTokenizer st = new StringTokenizer(f.readLine());
				while(st.hasMoreTokens()){
					int owner = Integer.parseInt(st.nextToken());
					maxn = (owner > maxn) ? owner : maxn;	// to save time later on
					int bought = Integer.parseInt(st.nextToken());
					maxn = (bought > maxn) ? bought : maxn;
					int shares = Integer.parseInt(st.nextToken());
					controls[owner-1][bought - 1] = shares;
				}
			}
			for(int owner = 0; owner < maxn; owner++){
				 int[] c = new int[maxn];	//these refresh for every owner
				 boolean[] vis = new boolean[maxn];
				seeControls(owner, c, vis);
				for(int bought = 0; bought < maxn; bought++){
					if(c[bought] > 50 && owner != bought){	//can't own yourself!
						out.println(owner + 1 + " " + (bought + 1));
					}
				}
			}
			
		} catch (IOException e){}

	}

	public static void seeControls(int owner, int[] c, boolean[] vis) {
		vis[owner] = true;	//can only control another company once (can't double shares)
		for(int col = 0; col < maxn; col++){
			c[col] += controls[owner][col];
			if(c[col] > 50 && !vis[col]){	//if owner controls Company col, it takes col's data as its own. c[col] updates
				seeControls(col, c, vis);
			}
		}
	}	
}
