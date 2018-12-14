/* A pair of cows is loose somewhere in the forest. Farmer John is lending his expertise to their capture. Your task is to model 
their behavior.

The chase takes place on a 10 by 10 planar grid. Squares can be empty or they can contain:

    an obstacle,
    the cows (who always travel together), or
    Farmer John. 

The cows and Farmer John can occupy the same square (when they `meet') but neither the cows nor Farmer John can share a square 
with an obstacle.

Each square is represented as follows:

    . Empty square
    * Obstacle
    C Cows
    F Farmer 

	Here is a sample grid:

*...*.....
......*...
...*...*..
..........
...*.F....
*.....*...
...*......
..C......*
...*.*....
.*.*......

The cows wander around the grid in a fixed way. Each minute, they either move forward or rotate. Normally, they move one square 
in the direction they are facing. If there is an obstacle in the way or they would leave the board by walking `forward', then 
they spend the entire minute rotating 90 degrees clockwise.

Farmer John, wise in the ways of cows, moves in exactly the same way.

The farmer and the cows can be considered to move simultaneously during each minute. If the farmer and the cows pass each other 
while moving, they are not considered to have met. The chase ends when Farmer John and the cows occupy the same square at the 
end of a minute.

Read a ten-line grid that represents the initial state of the cows, Farmer John, and obstacles. Each of the ten lines contains 
exactly ten characters using the coding above. There is guaranteed to be only one farmer and one pair of cows. The cows and 
Farmer John will not initially be on the same square.

Calculate the number of minutes until the cows and Farmer John meet. Assume both the cows and farmer begin the simulation facing 
in the `north' direction. Print 0 if they will never meet.

INPUT FORMAT
Lines 1-10: 	Ten lines of ten characters each, as explained above

SAMPLE INPUT (file ttwo.in)
*...*.....
......*...
...*...*..
..........
...*.F....
*.....*...
...*......
..C......*
...*.*....
.*.*......

OUTPUT FORMAT
A single line with the integer number of minutes until Farmer John and the cows meet. Print 0 if they will never meet.

SAMPLE OUTPUT (file ttwo.out)
49
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class ttwo {

	public static char[][] grid = new char[10][10];
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("ttwo.in"));
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ttwo.out")));){
			int john = 0;
			int cows = 0;
			for(int i = 0; i < 10; i++){
				String line = f.readLine();
				for(int j = 0; j < 10; j++){
					
					char c = line.charAt(j);
					grid[i][j] = c;
					if(c == 'F'){
						grid[i][j] = '.';	//represent as non obstacle
						john = i * 10 + j;
					}
					else if(c == 'C'){
						grid[i][j] = '.';
						cows = i * 10 + j;
					}
				}
			}
			out.println(timeChase(john, cows, 0, 0));
		} catch (IOException e) {}

	}

	public static int timeChase(int john, int cow, int johnDir, int cowDir){
		int time = 0;
		HashSet<Integer> visited = new HashSet<>();
		while(john != cow){
			time++;
			int johnX = john/10;	int johnY = john % 10;	//x is row, y is col
			int cowX = cow/10;	int cowY = cow % 10;
			int hash = johnX + johnY * 10 + johnDir * 100 + cowX * 1000 + cowY * 10000 + cowDir *100000;
			if(visited.contains(hash))	return 0;
			visited.add(hash);
			if(johnDir == 0 && (johnX == 0 || grid[johnX-1][johnY] == '*')){johnDir = 1;	//turns 90 right
			}
			else if(johnDir == 2 && (johnX == 9 || grid[johnX+1][johnY] == '*')){johnDir = 3;	//faces west
			}
			else if(johnDir == 3 && (johnY == 0 || grid[johnX][johnY-1] == '*')){johnDir = 0;	//faces north
			}
			else if(johnDir == 1 && (johnY == 9 || grid[johnX][johnY+1] == '*')){johnDir = 2;	//faces south
			}
			else{	//not a special case
				switch(johnDir){
				case 0:	john -= 10;	break;	//up
				case 1: john += 1;	break;	//right
				case 2: john += 10;	break;	//down
				case 3: john -= 1;	break;	//left
				}
			}
			if(cowDir == 0 && (cowX == 0 || grid[cowX-1][cowY] == '*')){cowDir = 1;}
			else if(cowDir == 2 && (cowX == 9 || grid[cowX+1][cowY] == '*')){cowDir = 3;}
			else if(cowDir == 3 && (cowY == 0 || grid[cowX][cowY-1] == '*')){cowDir = 0;}
			else if(cowDir == 1 && (cowY == 9 || grid[cowX][cowY+1] == '*')){cowDir = 2;}
			else{
				switch(cowDir){
				case 0:	cow -= 10;
				break;
				case 1: cow += 1;
				break;
				case 2: cow += 10;
				break;
				case 3: cow -= 1;
				break;
				}
			}
		}
		return time;
	}
}
