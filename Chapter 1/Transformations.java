 /*A square pattern of size N x N (1 <= N <= 10) black and white square tiles is transformed into another square pattern. Write a 
 program that will recognize the minimum transformation that has been applied to the original pattern given the following list of p
 ossible transformations:

    #1: 90 Degree Rotation: The pattern was rotated clockwise 90 degrees.
    #2: 180 Degree Rotation: The pattern was rotated clockwise 180 degrees.
    #3: 270 Degree Rotation: The pattern was rotated clockwise 270 degrees.
    #4: Reflection: The pattern was reflected horizontally (turned into a mirror image of itself by reflecting around a vertical 
    line in the middle of the image).
    #5: Combination: The pattern was reflected horizontally and then subjected to one of the rotations (#1-#3).
    #6: No Change: The original pattern was not changed.
    #7: Invalid Transformation: The new pattern was not obtained by any of the above methods.

In the case that more than one transform could have been used, choose the one with the minimum number above. 

INPUT FORMAT
Line 1: 	A single integer, N
Line 2..N+1: 	N lines of N characters (each either `@' or `-'); this is the square before transformation
Line N+2..2*N+1: 	N lines of N characters (each either `@' or `-'); this is the square after transformation

SAMPLE INPUT (file transform.in)

3
@-@
---
@@-
@-@
@--
--@

OUTPUT FORMAT
A single line containing the number from 1 through 7 (described above) that categorizes the transformation required to change 
from the `before' representation to the `after' representation.

SAMPLE OUTPUT (file transform.out)
1
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class transform {

	public static void main(String[] args) {
		try( BufferedReader f = new BufferedReader(new FileReader("transform.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));){
			while(f.ready()){
				int size = Integer.parseInt(f.readLine());
				char[][] start = new char[size][size];
				char[][] end = new char[size][size];
				for(int i = 0; i < size; i++){
					String line = f.readLine();
					for(int j = 0; j < size; j++){
						start[i][j] = line.charAt(j);
					}
				}
				for(int i = 0; i < size; i++){
					String line = f.readLine();
					for(int j = 0; j < size; j++){
						end[i][j] = line.charAt(j);
					}
				}
				out.println(change(start, end));
			}
		} catch (FileNotFoundException e) {} catch (IOException e) {}
	}
	
	public static int change(char[][] start, char[][] end){
		if(isEqual(end, rotate(start, 90))){
			return 1;
		}
		if(isEqual(end, rotate(start, 180))){
			return 2;
		}
		if(isEqual(end, rotate(start, 270))){
			return 3;
		}
		if(isEqual(end, reflectHorz(start))){
			return 4;
		}
		if(isEqual(end, rotate(reflectHorz(start), 90)) || isEqual(end, rotate(reflectHorz(start), 180)) || isEqual(end, rotate(reflectHorz(start), 270))){
			return 5;
		}
		if(isEqual(end, start)){
			return 6;
		}
		return 7;
	}
	
	public static char[][] rotate(char[][] start, int degrees){
		int length = start.length;
		char[][] result = new char[length][length];	//if result is set equal to start, start actually gets changed too!
		if(degrees == 180){
			return reflectHorz(reflectVert(start));
		}
		else if(degrees == 90){
			for(int i = 0; i < length; i++){
				for(int j = 0; j < length; j++){
					result[i][j] = start[length-1 -j][i];
				}
			}
		}
		else if(degrees == 270){
			for(int i = 0; i < length; i++){
				for(int j = 0; j < length; j++){
					result[i][j] = start[j][length-1-i];
				}
			}
		}
		return result;
	}
	
	public static char[][] reflectVert(char[][] start){	//across x axis
		int length = start.length;
		char[][] result = new char[length][length];
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				result[i][j] = start[length-i-1][j];
			}
		}
		return result;
	}
		
	public static char[][] reflectHorz(char[][] start){	//across y axis
		int length = start.length;
		char[][] result = new char[length][length];
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				result[i][j] = start[i][length-j-1];
			}
		}
		return result;
	}
	
	public static boolean isEqual(char[][] start, char[][] end){
		for(int i = 0; i < start.length; i++){
			for(int j = 0; j < start.length; j++){
				if(start[i][j] != end[i][j]){
					return false;
				}
			}
		}
		return true;
	}
}
