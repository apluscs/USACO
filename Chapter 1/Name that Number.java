/* Among the large Wisconsin cattle ranchers, it is customary to brand cows with serial numbers to please the Accounting 
 Department. The cow hands don't appreciate the advantage of this filing system, though, and wish to call the members of their 
 herd by a pleasing name rather than saying, "C'mon, #4734, get along."

Help the poor cowhands out by writing a program that will translate the brand serial number of a cow into possible names 
uniquely associated with that serial number. Since the cow hands all have cellular saddle phones these days, use the standard 
Touch-Tone(R) telephone keypad mapping to get from numbers to letters (except for "Q" and "Z"):

          2: A,B,C     5: J,K,L    8: T,U,V
          3: D,E,F     6: M,N,O    9: W,X,Y
          4: G,H,I     7: P,R,S

Acceptable names for cattle are provided to you in a file named "dict.txt", which contains a list of fewer than 5,000 acceptable 
cattle names (all letters capitalized). Take a cow's brand number and report which of all the possible words to which that 
number maps are in the given dictionary which is supplied as dict.txt in the grading environment (and is sorted into ascending 
order).

For instance, the brand number 4734 produces all the following names:

GPDG GPDH GPDI GPEG GPEH GPEI GPFG GPFH GPFI GRDG GRDH GRDI
GREG GREH GREI GRFG GRFH GRFI GSDG GSDH GSDI GSEG GSEH GSEI
GSFG GSFH GSFI HPDG HPDH HPDI HPEG HPEH HPEI HPFG HPFH HPFI
HRDG HRDH HRDI HREG HREH HREI HRFG HRFH HRFI HSDG HSDH HSDI
HSEG HSEH HSEI HSFG HSFH HSFI IPDG IPDH IPDI IPEG IPEH IPEI
IPFG IPFH IPFI IRDG IRDH IRDI IREG IREH IREI IRFG IRFH IRFI
ISDG ISDH ISDI ISEG ISEH ISEI ISFG ISFH ISFI

As it happens, the only one of these 81 names that is in the list of valid names is "GREG".

Write a program that is given the brand number of a cow and prints all the valid names that can be generated from that brand 
number or ``NONE'' if there are no valid names. Serial numbers can be as many as a dozen digits long. 

INPUT FORMAT
A single line with a number from 1 through 12 digits in length.

SAMPLE INPUT (file namenum.in)
4734

OUTPUT FORMAT
A list of valid names that can be generated from the input, one per line, in ascending alphabetical order.

SAMPLE OUTPUT (file namenum.out)
GREG*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class namenum {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("namenum.in"));
		BufferedReader g = new BufferedReader(new FileReader("dict.txt"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
		){
			ArrayList<HashSet<String>> abc = new ArrayList<>();	//hashsets are divided by first letter. sadly, alphabetical order is not preserved
			for(int i = 0; i < 26; i++){
				abc.add(new HashSet<String>());
			}
			while(g.ready()){
				String name = g.readLine();
				abc.get(name.charAt(0)-65).add(name);	//adds name
			}
			while(f.ready()){
				String identity = f.readLine();
				for(String s: nameCow(identity, abc)){
					out.println(s);
				}
			}
		} catch (FileNotFoundException e) {} catch (IOException e) {}
	}
	
	public static List<String> nameCow(String identity, List<HashSet<String>> abc){
		List<String> result = new ArrayList<>();
		char[] id = identity.toCharArray();
		char initial = id[0];
		char[] possible;
		if(initial == '2'){possible = new char[] {'A','B','C'};}  //limits search range to 3/26 of original based on first number given. Not necessary, but I did it anyways
		else if(initial == '3'){possible = new char[] {'D','E','F'};}
		else if(initial == '4'){possible = new char[] {'G','H','I'};}
		else if(initial == '5'){possible = new char[] {'J','K','L'};}
		else if(initial == '6'){possible = new char[] {'M','N','O'};}
		else if(initial == '7'){possible = new char[] {'P','R','S'};}
		else if(initial == '8'){possible = new char[] {'T','U','V'};}
		else{possible = new char[] {'W','X','Y'};}
		
		for(char c: possible){	//ex. all "A" names
			for(String s: abc.get(c-65)){	//"ADAM"
				if(s.length() != id.length){
					continue;
				}
				String corrId = "";
				for(char d: s.toCharArray()){	//"A"
					switch(d){
						case 'A': case 'B': case 'C': corrId += 2;	break;
						case 'D': case 'E': case 'F': corrId += 3;	break;
						case 'G': case 'H': case 'I': corrId += 4;	break;
						case 'J': case 'K': case 'L': corrId += 5;	break;
						case 'M': case 'N': case 'O': corrId += 6;	break;
						case 'P': case 'R': case 'S': corrId += 7;	break;
						case 'T': case 'U': case 'V': corrId += 8;	break;
						case 'W': case 'X': case 'Y': corrId += 9;	break;
					}
				}
			if(corrId.equals(identity)){result.add(s);}
			}
		}
		if(result.size() == 0){result.add("NONE");}
		Collections.sort(result);	//to alphabetize
		return result;
	}
}
