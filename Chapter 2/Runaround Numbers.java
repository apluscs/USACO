/* Runaround numbers are integers with unique digits, none of which is zero (e.g., 81362) that also have an interesting 
property, exemplified by this demonstration:

    If you start at the left digit (8 in our number) and count that number of digits to the right (wrapping back to the first 
    digit when no digits on the right are available), you'll end up at a new digit (a number which does not end up at a new 
    digit is 
    not a Runaround Number). Consider: 8 1 3 6 2 which cycles through eight digits: 1 3 6 2 8 1 3 6 so the next digit is 6.
    Repeat this cycle (this time for the six counts designed by the `6') and you should end on a new digit: 2 8 1 3 6 2, namely   
    2.
    Repeat again (two digits this time): 8 1
    Continue again (one digit this time): 3
    One more time: 6 2 8 and you have ended up back where you started, after touching each digit once. If you don't end up back 
    where you started after touching each digit once, your number is not a Runaround number. 

Given a number M (that has anywhere from 1 through 9 digits), find and print the next runaround number higher than M, which will 
always fit into an unsigned long integer for the given test data.

INPUT FORMAT
A single line with a single integer, M

SAMPLE INPUT (file runround.in)
81361

OUTPUT FORMAT
A single line containing the next runaround number higher than the input value, M.

SAMPLE OUTPUT (file runround.out)
81362
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

public class runround {

	public static void main (String[] args) {
        try(Scanner f = new Scanner(new File("runround.in"));
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("runround.out")));){
                while(f.hasNext()){
                    int num = f.nextInt();
                    out.println(nextWrapRound(num));
                }
        }	catch (IOException e){}
    }
   
    public static long nextWrapRound(int num){
        for(int i = num + 1; i < Integer.MAX_VALUE; i++){
            if(check(i)){
                return i;
            }
        }
        return 0;
    }
   
    public static boolean check (int num){
        String n = num + "";
        int[] digits = new int[n.length()];
        HashSet<Integer> inds = new HashSet<>();
        boolean[] covered = new boolean[10];
       
        for(int k = 0; k < n.length(); k++){
            digits[k] = n.charAt(k) - '0';
            if(digits[k] == 0){
            	return false;
            }
            inds.add(k);
        }
        int j = digits[0] % digits.length;  //ind of first jump
        while(!inds.isEmpty()){
        	if(covered[digits[j]] == true){
        		return false;
        	}
        	covered[digits[j]] = true;
        	if(!inds.contains(j)){	//already visited this index
                return false;  
            }
            inds.remove(j);
            if(j == 0 && inds.isEmpty()){  //visited all other indexes, returned back to 0
                return true;
            }
            j = (digits[j] - (digits.length - j))% digits.length;  
            if(j < 0){
                j += digits.length;
            }
        }
        
        return false;
    }
}
