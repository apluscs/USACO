/* Is Friday the 13th really an unusual event?
That is, does the 13th of the month land on a Friday less often than on any other day of the week? To answer this question, 
write a program that will compute the frequency that the 13th of each month lands on Sunday, Monday, Tuesday, Wednesday, 
Thursday, Friday, and Saturday over a given period of N years. The time period to test will be from January 1, 1900 to December 
31, 1900+N-1 for a given number of years, N. N is positive and will not exceed 400.

Note that the start year is NINETEEN HUNDRED, not NINETEEN NINETY.
There are few facts you need to know before you can solve this problem:
    January 1, 1900 was on a Monday.
    Thirty days has September, April, June, and November, all the rest have 31 except for February which has 28 except in leap 
    years when it has 29.
    Every year evenly divisible by 4 is a leap year (1992 = 4*498 so 1992 will be a leap year, but the year 1990 is not a leap 
    year)
    The rule above does not hold for century years. Century years divisible by 400 are leap years, all other are not. Thus, the 
    century years 1700, 1800, 1900 and 2100 are not leap years, but 2000 is a leap year. 
    
Do not use any built-in date functions in your computer language.
Don't just precompute the answers, either, please. 

INPUT FORMAT
One line with the integer N.

SAMPLE INPUT (file friday.in)
20

OUTPUT FORMAT
Seven space separated integers on one line. These integers represent the number of times the 13th falls on Saturday, Sunday, 
Monday, Tuesday, ..., Friday.

SAMPLE OUTPUT (file friday.out)
36 33 34 33 35 35 34
*/ 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class friday {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (BufferedReader f = new BufferedReader(new FileReader("friday.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));) {
			while(f.ready()){
				int[] result = frequency(Integer.parseInt(f.readLine()));
				for(int i = 0; i < 6; i++){
					out.print(result[i] + " ");
				}
				out.print(result[6]); //incorrect if there is an extra space
				out.println();
			}
			
		}
	}
	
	public static int[] frequency(int maxYear){
		int[] result = new int[7];
		if(maxYear < 1){
			return result;
		}
		int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int currDay = 0;	//first 13th is a Saturday
		int currMonth = 0;	int year = 1900;
		while(year-1900 < maxYear){
			int gap;
			if(currMonth == 1 && (year % 400 == 0 || year % 100 != 0 && year % 4 == 0)){
				gap = 29; 	//if it's a gap year
			}
			else {gap = months[currMonth];}	
			currMonth++; //new month
			result[currDay]++;
			currDay = (gap % 7 + currDay)% 7;	//updates which day the 13th lands on next month
			
			if(currMonth == 12){	//start of a new year
				currMonth = 0;
				year++;
			}
		}
		return result;
	}
}
