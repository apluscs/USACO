/*Farmer John's hobby of conducting high-energy physics experiments on weekends has backfired, causing N wormholes (2 <= N <= 12, 
N even) to materialize on his farm, each located at a distinct point on the 2D map of his farm (the x,y coordinates are both 
integers).

According to his calculations, Farmer John knows that his wormholes will form N/2 connected pairs. For example, if wormholes A 
and B are connected as a pair, then any object entering wormhole A will exit wormhole B moving in the same direction, and any 
object entering wormhole B will similarly exit from wormhole A moving in the same direction. This can have rather unpleasant 
consequences.

For example, suppose there are two paired wormholes A at (1,1) and B at (3,1), and that Bessie the cow starts from position 
(2,1) moving in the +x direction. Bessie will enter wormhole B [at (3,1)], exit from A [at (1,1)], then enter B again, and so 
on, getting trapped in an infinite cycle!

   | . . . .
   | A > B .      Bessie will travel to B then
   + . . . .      A then across to B again

Farmer John knows the exact location of each wormhole on his farm. He knows that Bessie the cow always walks in the +x 
direction, although he does not remember where Bessie is currently located.

Please help Farmer John count the number of distinct pairings of the wormholes such that Bessie could possibly get trapped in an 
infinite cycle if she starts from an unlucky position. FJ doesn't know which wormhole pairs with any other wormhole, so find all 
the possibilities (i.e., all the different ways that N wormholes could be paired such that Bessie can, in some way, get in a 
cycle). Note that a loop with a smaller number of wormholes might contribute a number of different sets of pairings to the total 
count as those wormholes that are not in the loop are paired in many different ways.

INPUT FORMAT:
Line 1:	The number of wormholes, N.
Lines 2..1+N:	Each line contains two space-separated integers describing the (x,y) coordinates of a single wormhole. Each coordinate is in the range 0..1,000,000,000.

SAMPLE INPUT (file wormhole.in):

4
0 0
1 0
1 1
0 1

INPUT DETAILS:
There are 4 wormholes, forming the corners of a square.

OUTPUT FORMAT:
Line 1:	The number of distinct pairings of wormholes such that Bessie could conceivably get stuck in a cycle walking from some starting point in the +x direction.

SAMPLE OUTPUT (file wormhole.out):
2

OUTPUT DETAILS:
If we number the wormholes 1..4 as we read them from the input, then if wormhole 1 pairs with wormhole 2 and wormhole 3 pairs 
with wormhole 4, Bessie can get stuck if she starts anywhere between (0,0) and (1,0) or between (0,1) and (1,1).

   | . . . .
   4 3 . . .      Bessie will travel to B then
   1-2-.-.-.      A then across to B again

Similarly, with the same starting points, Bessie can get stuck in a cycle if the pairings are 1-3 and 2-4 (if Bessie enters WH#3 
and comes out at WH#1, she then walks to WH#2 which transports here to WH#4 which directs her towards WH#3 again for a cycle).

Only the pairings 1-4 and 2-3 allow Bessie to walk in the +x direction from any point in the 2D plane with no danger of cycling. 
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class wormhole {

	public static HashMap<Integer, Integer> partners;	//can easily look up partner indexes in pos
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader (new FileReader("wormhole.in"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
			){
		while(f.ready()){
			int total = Integer.parseInt(f.readLine());
			int[][] pos = new int[total][4];	//3rd column is curr partner, 4th column is index of immediate right wormhole
			for(int i = 0; i < total; i++){
				StringTokenizer st = new StringTokenizer(f.readLine());
				int xCoor = Integer.parseInt(st.nextToken());
				int yCoor = Integer.parseInt(st.nextToken());
				pos[i][0] = xCoor;
				pos[i][1] = yCoor;
				pos[i][2] = -1;	//all start partnerless
				pos[i][3] = -1;	//all start with unIDed right neighbors
			}
			for(int i = 0; i < total; i++){
				for(int j = 0; j < total; j++){
					if(pos[j][0] > pos[i][0] && pos[j][1] == pos[i][1]){	//j is right of i
						int currRight = pos[i][3];
						if(pos[i][3] < 0 || pos[j][0] < pos[currRight][0]){	//if i's right has not been set or j is more left than i's 
            //current right
							pos[i][3] = j;
						}
					}
				}
			}
			out.println(distinctPairs(pos));
		}
			} catch (IOException e) {}
	}
	
	public static int distinctPairs(int[][] pos){
		int i = 0;	int result = 0;
		while(i < pos.length){
			if(pos[i][2] < 0){	//find first partnerless wormhole
				break;
			}
			i++;
		}
		if(i == pos.length){	//all partners are assigned
			if(infinite(pos)){
				return 1;
			}
			return 0;
		}
		for(int j = i + 1; j < pos.length; j++){
			if(pos[j][2] < 0){
				pos[i][2] = j;
				pos[j][2] = i;
				result += distinctPairs(pos);
				pos[i][2] = -1;	//unassign partners
				pos[j][2] = -1;
			}
		}
		return result;	
	}
	
	public static boolean infinite(int[][] pos){
		for(int start = 0; start < pos.length; start++){	//starts entering a different wormhole everytime
			int loca = start;
			for(int step = 0; step < pos.length; step++){
				if(loca < 0){	//made it out
					break;
				}
				loca = pos[loca][2];	//partner's index
				loca = pos[loca][3];	//now at partner's right wormhole
				if(loca == start){	//circled back to starting wormhole
					return true;
				}
			}
		}
		return false;
	}
}
