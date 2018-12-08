/*The structure of some biological objects is represented by the sequence of their constituents, where each part is denoted by an 
uppercase letter. Biologists are interested in decomposing a long sequence into shorter sequences called primitives.

We say that a sequence S can be composed from a given set of primitives P if there is a some sequence of (possibly repeated) 
primitives from the set whose concatenation equals S. Not necessarily all primitives need be present. For instance the sequence 
ABABACABAABcan be composed from the set of primitives

	   {A, AB, BA, CA, BBC}

The first K characters of S are the prefix of S with length K. Write a program which accepts as input a set of primitives and a 
sequence of constituents and then computes the length of the longest prefix that can be composed from primitives.

INPUT FORMAT
First, the input file contains the list (length 1..200) of primitives (length 1..10) expressed as a series of space-separated 
strings of upper-case characters on one or more lines. The list of primitives is terminated by a line that contains nothing more 
than a period (`.'). No primitive appears twice in the list. Then, the input file contains a sequence S (length 1..200,000) 
expressed as one or more lines, none of which exceeds 76 letters in length. The "newlines" (line terminators) are not part of 
the string S.

SAMPLE INPUT (file prefix.in)
A AB BA CA BBC
.
ABABACABAABC

OUTPUT FORMAT
A single line containing an integer that is the length of the longest prefix that can be composed from the set P.

SAMPLE OUTPUT (file prefix.out)
11
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class prefix {

	public static HashSet<String> prims = new HashSet<>();
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("prefix.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("prefix.out")));){
		StringTokenizer st = new StringTokenizer(f.readLine());
		boolean flag = false;
		while(f.ready()){
			while(st.hasMoreTokens()){
				String prim = st.nextToken();
				if(prim.equals(".")){
					flag = true;	//used to break out of outer loop too
					break;
				}
				prims.add(prim);
			}
			if(flag){break;}
			st = new StringTokenizer(f.readLine());
		}
		StringBuilder sequence = new StringBuilder(); //this makes it much faster
		while(f.ready()){
			sequence.append(f.readLine());
		}
		out.println(longestPrefix(sequence.toString()));
		} catch (IOException e) {}
	}

	public static int longestPrefix(String sequence){
		int result = 0;
		boolean[] exists = new boolean[sequence.length() + 1];
		exists[0] = true;	//prefix of 0 length exists
		for(int i = 0; i < sequence.length(); i++){
			for(int j = 0; j < 10 && i - j > -1; j++){	//how many chars back you're jumping
				String tail = sequence.substring(i - j, i + 1);
				if(prims.contains(tail) && exists[i-j]){	//checks: is this tail a primitive? if so, does a prefix that ends right 
        //before the tail starts exist? (dp)
					exists[i + 1] = true;	//prefix of length i + 1 exists 
					continue;
				}
			}
		}
		for(int i = 0; i < exists.length; i++){
			if(exists[i]){
				result = i;
			}
		}
		return result;
	}
}
