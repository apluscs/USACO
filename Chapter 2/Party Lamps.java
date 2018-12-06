/*To brighten up the gala dinner of the IOI'98 we have a set of N (10 <= N <= 100) colored lamps numbered from 1 to N.

The lamps are connected to four buttons:

    Button 1: When this button is pressed, all the lamps change their state: those that are ON are turned OFF and those that are 
    OFF are turned ON.
    Button 2: Changes the state of all the odd numbered lamps.
    Button 3: Changes the state of all the even numbered lamps.
    Button 4: Changes the state of the lamps whose number is of the form 3xK+1 (with K>=0), i.e., 1,4,7,...

A counter C records the total number of button presses.

When the party starts, all the lamps are ON and the counter C is set to zero.

You are given the value of counter C (0 <= C <= 10000) and the final state of some of the lamps after some operations have been 
executed. Write a program to determine all the possible final configurations of the N lamps that are consistent with the given 
information, without repetitions.

INPUT FORMAT
No lamp will be listed twice in the input.
Line 1: 	N
Line 2: 	Final value of C
Line 3: 	Some lamp numbers ON in the final configuration, separated by one space and terminated by the integer -1.
Line 4: 	Some lamp numbers OFF in the final configuration, separated by one space and terminated by the integer -1.

SAMPLE INPUT (file lamps.in)
10
1
-1
7 -1

In this case, there are 10 lamps and only one button has been pressed. Lamp 7 is OFF in the final configuration.

OUTPUT FORMAT
Lines with all the possible final configurations (without repetitions) of all the lamps. Each line has N characters, where the 
first character represents the state of lamp 1 and the last character represents the state of lamp N. A 0 (zero) stands for a 
lamp that is OFF, and a 1 (one) stands for a lamp that is ON. The lines must be ordered from least to largest (as binary 
numbers).

If there are no possible configurations, output a single line with the single word `IMPOSSIBLE'

SAMPLE OUTPUT (file lamps.out)
0000000000
0101010101
0110110110

In this case, there are three possible final configurations:
    All lamps are OFF
    Lamps 1, 3, 5, 7, 9 are OFF and lamps 2, 4, 6, 8, 10 are ON.
    Lamps 1, 4, 7, 10 are OFF and lamps 2, 3, 5, 6, 8, 9 are ON.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class lamps {

	public static HashSet<Integer> on = new HashSet<>();
	public static HashSet<Integer> off = new HashSet<>();
	public static int N;
	public static int C;
	public static List<int[]> result = new ArrayList<>();
	public static List<String> result2 = new ArrayList<>();
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("lamps.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));){
		while(f.ready()){
			N = Integer.parseInt(f.readLine());
			C = Integer.parseInt(f.readLine());
			StringTokenizer st = new StringTokenizer(f.readLine());
			while(st.hasMoreTokens()){
				int next = Integer.parseInt(st.nextToken());
				if(next != -1 && next % 6 != 0){
					on.add(next % 6);
				}
				else if(next % 6 == 0){on.add(6);}	//must adjust for this special case
			}
			st = new StringTokenizer(f.readLine());
			while(st.hasMoreTokens()){
				int next = Integer.parseInt(st.nextToken());
				if(next != -1 && next % 6 != 0){
					off.add(next % 6);
				}
				else if(next % 6 == 0){off.add(6);}	//on and off will be used to check validity
			}
			
			int[] state = new int[6];	//lamp patterns come in loops of 6 (lcd of 2 and 3)
			Arrays.fill(state, 1);
			generateCombos(state);
			
			if(valid(state)){result.add(state);}	//zero changes
			if(C == 2){
				for(int[] poss : twoChange){
					if(valid(poss)){result.add(poss);}
				}
			}
			else if(C == 1){
				result.clear();	//remove zero changes
				for(int[] poss : oneChange){
					if(valid(poss)){result.add(poss);}
				}
			}
			
			if(C % 2 == 0 && C > 2){	//traverse two and four change
				for(int[] poss : twoChange){	//if number of lamps is even, at least two buttons must cancel out each other, leaving an 
        //even number of net buttons pressed
					if(valid(poss)){result.add(poss);}
				}
				if(valid(fourChange)){result.add(fourChange);}
			}
			else if(C > 2){	//traverse one and three change
				for(int[] poss : oneChange){	//if number of lamps is odd, at least two buttons must cancel out each other, leaving an 
        //odd number of net buttons pressed
					if(valid(poss)){result.add(poss);}
				}
				for(int[] poss : threeChange){
					if(valid(poss)){result.add(poss);}
				}
			}
			for(int[] combo: result){	//converts int[]s to Strings
				String next = "";
				for(int c : combo){
					next += c + "";
				}
				result2.add(next);
				
			}
			Collections.sort(result2);
			for(int i = 0; i < result2.size(); i++){
				String combo = result2.get(i);
				for(int t = 1; t < N/6; t++){
					combo += combo.substring(0, 6);	//loops it 
				}
				for(int t = 0; t < N % 6; t++){	//adds the leftovers
					combo += combo.charAt(t);
				}
				result2.remove(i);
				result2.add(i, combo);	//replaced with the looped version
			}
			if(result.size() == 0){
				out.println("IMPOSSIBLE");
			}
			for(String s : result2){
				out.println(s);
			}
		}
		} catch (IOException e) {}
	}
	
	public static List<int[]> oneChange = new ArrayList<>();
	public static List<int[]> twoChange = new ArrayList<>();
	public static List<int[]> threeChange = new ArrayList<>();
	public static int[] fourChange = new int[6];

	public static void generateCombos(int[] state){
		oneChange.add(but1(state));
		oneChange.add(but2(state));
		oneChange.add(but3(state));
		oneChange.add(but4(state));
		twoChange.add(but2(oneChange.get(0)));	//buts 1 and 2
		twoChange.add(but3(oneChange.get(0)));	//buts 1 and 3
		twoChange.add(but4(oneChange.get(0)));	//buts 1 and 4
		twoChange.add(but3(oneChange.get(1)));	//buts 2 and 3
		twoChange.add(but4(oneChange.get(1)));	//buts 2 and 4
		twoChange.add(but4(oneChange.get(2)));	//buts 3 and 4
		threeChange.add(but3(twoChange.get(0)));	//buts 3 and 1 and 2
		threeChange.add(but4(twoChange.get(1)));	//buts 4 and 1 and 3
		threeChange.add(but4(twoChange.get(0)));	//buts 4 and 1 and 2
		threeChange.add(but4(twoChange.get(3)));	//buts 3 and 4 and 2
		fourChange = but4(threeChange.get(0));	//buts 1, 2, 3, 4
	}
	
	public static boolean valid(int[] state){
		for(int i : on){
			if(state[i-1] == 0){return false;}
		}
		for(int i : off){
			if(state[i-1] == 1){return false;}
		}
		return true;
	}
	
	public static int[] but1(int[] state){
		int[] result = state.clone();
		for(int i = 0; i < state.length; i++){
			if(result[i] == 0){result[i] = 1;}
			else{result[i] = 0;}
		}
		return result;
	}
	
	public static int[] but2(int[] state){
		int[] result = state.clone();
		for(int i = 0; i < state.length; i += 2){
			if(result[i] == 0){result[i] = 1;}
			else{result[i] = 0;}
		}
		return result;
	}
		
	public static int[] but3(int[] state){
		int[] result = state.clone();
		for(int i = 1; i < state.length; i += 2){
			if(result[i] == 0){result[i] = 1;}
			else{result[i] = 0;}
		}
		return result;
	}
	
	public static int[] but4(int[] state){
		int[] result = state.clone();
		for(int i = 0; i < state.length; i += 3){
			if(result[i] == 0){result[i] = 1;}
			else{result[i] = 0;}
		}
		return result;
	}
}
