package usaco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
ID: the.cla1
LANG: JAVA
TASK: friday
*/

public class friday {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (BufferedReader f = new BufferedReader(new FileReader("friday.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));) {
			while(f.ready()){
				int[] result = frequency(Integer.parseInt(f.readLine()));
				for(int i = 0; i < 6; i++){
					out.print(result[i] + " ");
				}
				out.print(result[6]);
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
