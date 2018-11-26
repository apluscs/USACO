/* You have a necklace of N red, white, or blue beads (3<=N<=350) some of which are red, others blue, and others white, arranged 
at random. Here are two examples for n=29:

                1 2                               1 2
            r b b r                           b r r b
          r         b                       b         b
         r           r                     b           r
        r             r                   w             r
       b               r                 w               w
      b                 b               r                 r
      b                 b               b                 b
      b                 b               r                 b
       r               r                 b               r
        b             r                   r             r
         b           r                     r           r
           r       r                         r       b
             r b r                             r r w
            Figure A                         Figure B

  r red bead
                        b blue bead
                        w white bead

The beads considered first and second in the text that follows have been marked in the picture.

The configuration in Figure A may be represented as a string of b's and r's, where b represents a blue bead and r represents a 
red one, as follows: brbrrrbbbrrrrrbrrbbrbbbbrrrrb .

Suppose you are to break the necklace at some point, lay it out straight, and then collect beads of the same color from one end 
until you reach a bead of a different color, and do the same for the other end (which might not be of the same color as the 
beads collected before this).

Determine the point where the necklace should be broken so that the most number of beads can be collected.
Example

For example, for the necklace in Figure A, 8 beads can be collected, with the breaking point either between bead 9 and bead 10 
or else between bead 24 and bead 25.

In some necklaces, white beads had been included as shown in Figure B above. When collecting beads, a white bead that is 
encountered may be treated as either red or blue and then painted with the desired color. The string that represents this 
configuration can include any of the three symbols r, b and w.

Write a program to determine the largest number of beads that can be collected from a supplied necklace. 

INPUT FORMAT
Line 1: 	N, the number of beads
Line 2: 	a string of N characters, each of which is r, b, or w
SAMPLE INPUT (file beads.in)

29
wwwbbrwrbrbrrbrbrwrwwrbwrwrrb

OUTPUT FORMAT
A single line containing the maximum of number of beads that can be collected from the supplied necklace.
SAMPLE OUTPUT (file beads.out)

11

OUTPUT EXPLANATION
Consider two copies of the beads (kind of like being able to runaround the ends). The string of 11 is marked. 

 Two necklace copies joined here
                             v
wwwbbrwrbrbrrbrbrwrwwrbwrwrrb|wwwbbrwrbrbrrbrbrwrwwrbwrwrrb
                       ******|*****
                       rrrrrb|bbbbb  <-- assignments
                   5xr .....#|#####  6xb

                        5+6 = 11 total
                       */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class beads {

	public static void main(String[] args) throws IOException {
		try (BufferedReader f = new BufferedReader(new FileReader("beads.in"));
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));) {
		while(f.ready()){
			f.readLine();
			String line = f.readLine();
			char[] beads = (line).toCharArray();
			out.println(maxContinuous(beads));
		}
	}

	}
	
	public static int maxContinuous(char[] beads){
		if(beads.length <= 3){
			return beads.length;
		}
		int result = 0;
		char bwr = beads[0];	//tracks current bead color
		ArrayList<Integer> startInd = new ArrayList<>();
		HashMap <Integer, Integer> whiteInd = whiteChains(beads); //tracks end and start indices of white chains
		ArrayList<Character> color = new ArrayList<>();	//will vertically align with startInd
		int start = 0; int end = 0;
		for(int i = 0; i < beads.length; i++){
			if(beads[i] != 'w'){	//find first "real color"
				start = i;
				end = i + 1;
				bwr = beads[i];
				break;
			}
		}
		while(end < beads.length){
			char b = beads[end];
			if(b != bwr && b != 'w'){	//color change
				color.add(bwr);
				startInd.add(start);
				bwr = b;
				start = end;
			}
			end++;	//if elongation is permitted
		}
		startInd.add(start);
		color.add(bwr);	//adds leftovers
		if(color.size() == 1){
			return beads.length;
		}
		if(bwr != color.get(0)){	//if last is same color as front, it will skip this step (ignore the front when looping around)
			startInd.add(startInd.get(0) + beads.length);
			color.add(color.get(0));
		}
		startInd.add(startInd.get(1) + beads.length);
		color.add(color.get(1));
		
		for(int i = 0; i < color.size() - 2; i++){
			int total = startInd.get(i + 2) - startInd.get(i);
			if(whiteInd.containsValue(startInd.get(i)-1)){	//if white immediately precedes
				total += startInd.get(i) - whiteInd.get(startInd.get(i)-1);	//length of white chain
			}
			result = Math.max(result, total);
		}
		return result;
}
	
	public static HashMap<Integer, Integer> whiteChains(char[] beads){
		HashMap <Integer, Integer> whiteInd = new HashMap<>();
		int wStart = 0;	int wEnd = 0;	boolean foundStart = false;
		while(wEnd < beads.length){
			char b = beads[wEnd];
			if(b == 'w' && !foundStart){	//found start of w-chain
				wStart = wEnd;
				foundStart = true;
			}
			else if(b != 'w' && foundStart){
				whiteInd.put( wEnd-1, wStart);	//end index is key
				foundStart = false;
			}
			wEnd++;
		}
		return whiteInd;
	}
}
