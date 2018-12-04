/* A certain book's prefaces are numbered in upper case Roman numerals. Traditional Roman numeral values use a single letter to 
represent a certain subset of decimal numbers. Here is the standard set:

        I   1     L   50    M  1000
        V   5     C  100
        X  10     D  500

As many as three of the same marks that represent 10n may be placed consecutively to form other numbers:

    III is 3
    CCC is 300 

Marks that have the value 5x10n are never used consecutively.

Generally (with the exception of the next rule), marks are connected together and written in descending order to form even more 
numbers:
CCLXVIII = 100+100+50+10+5+1+1+1 = 268

Sometimes, a mark that represents 10^n is placed before a mark of one of the two next higher values (I before V or X; X before L 
or C; etc.). In this case, the value of the smaller mark is SUBTRACTED from the mark it precedes:

    IV = 4
    IX = 9
    XL = 40 

This compound mark forms a unit and may not be combined to make another compound mark (e.g., IXL is wrong for 39; XXXIX is 
correct).

Compound marks like XD, IC, and XM are not legal, since the smaller mark is too much smaller than the larger one. For XD (wrong 
for 490), one would use CDXC; for IC (wrong for 99), one would use XCIX; for XM (wrong for 990), one would use CMXC. 90 is 
expressed XC and not LXL, since L followed by X connotes that successive marks are X or smaller (probably, anyway).

Given N (1 <= N < 3,500), the number of pages in the preface of a book, calculate and print the number of I's, V's, etc. (in 
order from lowest to highest) required to typeset all the page numbers (in Roman numerals) from 1 through N. Do not print 
letters that do not appear in the page numbers specified.

If N = 5, then the page numbers are: I, II, III, IV, V. The total number of I's is 7 and the total number of V's is 2.

INPUT FORMAT
A single line containing the integer N.

SAMPLE INPUT (file preface.in)
5

OUTPUT FORMAT
The output lines specify, in ascending order of Roman numeral letters, the letter, a single space, and the number of times that 
letter appears on preface page numbers. Stop printing letter totals after printing the highest value letter used to form preface 
numbers in the specified set.

SAMPLE OUTPUT (file preface.out)
I 7
V 2
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class preface {

	public static int[] result = new int[7];	// I V X L C D M	
	public static HashMap<Character, Integer> map = new HashMap<>();
	
	public static void main(String[] args) {
		try(Scanner f = new Scanner(new File("preface.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("preface.out")));){
		while(f.hasNext()){
			int num = f.nextInt();
			for(int i = 1; i <= num; i++){
				generateLetters(i);
			}
			if(map.containsKey('I')){out.println("I " + map.get('I'));}
			if(map.containsKey('V')){out.println("V " + map.get('V'));}
			if(map.containsKey('X')){out.println("X " + map.get('X'));}
			if(map.containsKey('L')){out.println("L " + map.get('L'));}
			if(map.containsKey('C')){out.println("C " + map.get('C'));}
			if(map.containsKey('D')){out.println("D " + map.get('D'));}
			if(map.containsKey('M')){out.println("M " + map.get('M'));}
		}
		
		} catch (IOException e){}
	}

	
	public static void generateLetters(int num){
		char[] ones = new char[] {'I', 'V', 'X'};	char[] tens = new char[] {'X', 'L', 'C'};
		char[] hunds = new char[] {'C', 'D', 'M'};
		
		int divisor = 10*10*10;	int power = 3;
		while(divisor > 0){
			int temp = num/divisor;
			if(temp == 0){
				divisor /= 10;	power--;	continue;
			}	
			char[] useThis = hunds;	//for the sake of compilation
			switch(power){
				case 3:		
					for(int i = 0; i < temp; i++){	
						map.put('M', map.getOrDefault('M', 0) + 1);
					} 
					num %= divisor;	divisor /= 10;	power--;	continue;
				case 2:	useThis = hunds;	break;
				case 1:	useThis = tens;		break;
				case 0:	useThis = ones;		break;
			}
			if(temp == 4){
				map.put(useThis[0], map.getOrDefault(useThis[0], 0) + 1);
				map.put(useThis[1], map.getOrDefault(useThis[1], 0) + 1);
			}
			else if(temp == 9){
				map.put(useThis[0], map.getOrDefault(useThis[0], 0) + 1);
				map.put(useThis[2], map.getOrDefault(useThis[2], 0) + 1);
			}
			else if(temp >= 5){	//5, 6, 7, 8
				map.put(useThis[1], map.getOrDefault(useThis[1], 0) + 1);
				for(int i = 5; i < temp; i++){
					map.put(useThis[0], map.getOrDefault(useThis[0], 0) + 1);
				}
			}
			else{	//1, 2, 3, 4
				for(int i = 0; i < temp; i++){
					map.put(useThis[0], map.getOrDefault(useThis[0], 0) + 1);
				}
			}
			num %= divisor;	divisor /= 10;	power--;
		}
	}
}
