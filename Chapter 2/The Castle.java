/* In a stroke of luck almost beyond imagination, Farmer John was sent a ticket to the Irish Sweepstakes (really a lottery) for 
his birthday. This ticket turned out to have only the winning number for the lottery! Farmer John won a fabulous castle in the 
Irish countryside.

Bragging rights being what they are in Wisconsin, Farmer John wished to tell his cows all about the castle. He wanted to know 
how many rooms it has and how big the largest room was. In fact, he wants to take out a single wall to make an even bigger room.

Your task is to help Farmer John know the exact room count and sizes.

The castle floorplan is divided into M (wide) by N (1 <=M,N<=50) square modules. Each such module can have between zero and four 
walls. Castles always have walls on their "outer edges" to keep out the wind and rain.

Consider this annotated floorplan of a castle:

     1   2   3   4   5   6   7
   #############################
 1 #   |   #   |   #   |   |   #
   #####---#####---#---#####---#   
 2 #   #   |   #   #   #   #   #
   #---#####---#####---#####---#
 3 #   |   |   #   #   #   #   #   
   #---#########---#####---#---#
 4 # ->#   |   |   |   |   #   #   
   ############################# 

#  = Wall     -,|  = No wall
-> = Points to the wall to remove to
     make the largest possible new room

By way of example, this castle sits on a 7 x 4 base. A "room" includes any set of connected "squares" in the floor plan. This 
floorplan contains five rooms (whose sizes are 9, 7, 3, 1, and 8 in no particular order).

Removing the wall marked by the arrow merges a pair of rooms to make the largest possible room that can be made by removing a 
single wall.

The castle always has at least two rooms and always has a wall that can be removed.

INPUT FORMAT

The map is stored in the form of numbers, one number for each module ("room"), M numbers on each of N lines to describe the 
floorplan. The input order corresponds to the numbering in the example diagram above.

Each module descriptive-number tells which of the four walls exist and is the sum of up to four integers:

    1: wall to the west
    2: wall to the north
    4: wall to the east
    8: wall to the south

Inner walls are defined twice; a wall to the south in module 1,1 is also indicated as a wall to the north in module 2,1.
Line 1: 	Two space-separated integers: M and N
Line 2..: 	M x N integers, several per line.

SAMPLE INPUT (file castle.in)
7 4
11 6 11 6 3 10 6
7 9 6 13 5 15 5
1 10 12 7 13 7 5
13 11 10 8 10 12 13

OUTPUT FORMAT
The output contains several lines:
Line 1: 	The number of rooms the castle has.
Line 2: 	The size of the largest room
Line 3: 	The size of the largest room creatable by removing one wall
Line 4: 	The single wall to remove to make the largest room possible

Choose the optimal wall to remove from the set of optimal walls by choosing the module farthest to the west (and then, if still 
tied, farthest to the south). If still tied, choose 'N' before 'E'. Name that wall by naming the module that borders it on 
either the west or south, along with a direction of N or E giving the location of the wall with respect to the module.

SAMPLE OUTPUT (file castle.out)
5
9
16
4 1 E

INPUT DETAILS
First, the map is partitioned like below. Note that walls not on the outside borders are doubled:

     1    2    3    4    5    6    7
   ####|####|####|####|####|####|#####
 1 #   |   #|#   |   #|#   |    |    #
   ####|   #|####|   #|#   |####|    #
  -----|----|----|----|----|----|-----
   ####|#   |####|#  #|#  #|####|#   #
 2 #  #|#   |   #|#  #|#  #|#  #|#   #
   #  #|####|   #|####|#  #|####|#   #
  -----|----|----|----|----|----|-----
   #   |####|   #|####|#  #|####|#   #
 3 #   |    |   #|#  #|#  #|#  #|#   #
   #   |####|####|#  #|####|#  #|#   #
  -----|----|----|----|----|----|-----
   #  #|####|####|    |####|   #|#   #
 4 #  #|#   |    |    |    |   #|#   #
   ####|####|####|####|####|####|#####

Let's talk about the squares with a (row, column) notation such that the lower right corner is denoted (4, 7).

The input will have four lines, each with 7 numbers. Each number describes one 'room'. >Walls further toward the top are 
'north', towards the bottom are 'south', towards the left are 'west', and towards the right are 'east'.

Consider square (1,1) which has three walls: north, south, and west. To encode those three walls, we consult the chart:

    1: wall to the west
    2: wall to the north
    4: wall to the east
    8: wall to the south

and sum the numbers for north (2), south (8), and west (1). 2 + 8 + 1 = 11, so this room is encoded as 11.

The next room to the right (1,2) has walls on the north (2) and east (4) and thus is encoded as 2 +4 = 6.

The next room to the right (1,3) is the same as (1,1) and thus encodes as 11.

Room (1,4) is the same as (1,2) and thus encodes as 6.

Room (1,5) has rooms on the west (1) and north (2) and thus encodes as 1 + 2 = 3.

Room (1,6) has rooms on the north (2) and south (8) and thus encodes as 2 + 8 = 10.

Room (1,7) is the same as room (1,2) and thus encodes as 6.

This same method continues for rooms (2,1) through (4,7). 
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

public class castle {

	public static int[][][] modules;
	public static int M;
	public static int N;
	public static int curRoom;		public static int curSize;
	public static List<int[]> walls = new ArrayList<>();
	public static HashSet<Integer> visited = new HashSet<>();
	
	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("castle.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));){
		while(f.ready()){
			int id = 0;	curRoom = 1;
			StringTokenizer st = new StringTokenizer(f.readLine());
			M = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			modules = new int[N][M][7];	//top, right, down, left, id#, room#, roomSize
			for(int r = 0; r < N; r++){
				StringTokenizer dt = new StringTokenizer(f.readLine());
				for(int c = 0; c < M; c++){
					int wall = Integer.parseInt(dt.nextToken());
					if((wall & 2) == 2){
						modules[r][c][0] = 1;
					}
					if((wall & 4) == 4){
						modules[r][c][1] = 1;
					}
					if((wall & 8) == 8){
						modules[r][c][2] = 1;
					}
					if((wall & 1) == 1){
						modules[r][c][3] = 1;
					}
					modules[r][c][4] = id++;
				}
			}
			int maxSize = 0;
			for(int r = 0; r < N; r++){
				for(int c = 0; c < M; c++){
					if(!visited.contains(modules[r][c][4])){	//only calls on new chains of rooms
						curSize = 0;
						floodFill(r, c);
						curRoom++;	//starts new room #
						maxSize = Math.max(curSize, maxSize);
						setSize(r, c);
					}
				}
			}	curRoom--;
			out.println(curRoom);
			out.println(maxSize);
			out.println(removeWall());
			int[] result = oneWall();
			out.print(result[0] + " " + result[1] + " ");
			if(result[2] == 0){
				out.println("E");
			}
			else{
				out.println("N");
			}
		}
		} catch (IOException e) {}

	}

	public static int[] oneWall(){
		Collections.sort(walls, (a, b) -> {
			return a[1] - b[1];
		});
		int mostWest;
		mostWest = walls.get(0)[1];
		for(int w = 1; w < walls.size(); w++){
			if(walls.get(w)[1] != mostWest){	//eliminating those not farthest  west
				walls.remove(w--);
			}
		}
		Collections.sort(walls, (a, b) -> {
			return b[0] - a[0];
		});
		int[] result = walls.get(0);
		if(walls.size() > 1 && walls.get(1)[0] == walls.get(0)[0] && walls.get(1)[1] == walls.get(0)[1] && walls.get(1)[2] == 1){
			result = walls.get(1);	//special case: winning module can be broken up or right
		}
		result[0]++;	result[1]++;	//adjust indexes
		return result;
	}
	
	public static int removeWall(){
		int maxSize = 1;
		for(int row = N - 1; row >= 0; row--){
			for(int col = 0; col < M; col++){
				int breakRight = 0;	int breakUp = 0;
				if(col != M - 1 && modules[row][col][5] != modules[row][col + 1][5]){
					breakRight = modules[row][col + 1][6] + modules[row][col][6];
				}
				if(row != 0 && modules[row][col][5] != modules[row - 1][col][5]){
					breakUp = modules[row][col][6] + modules[row - 1][col][6];
				}
				if(breakRight == maxSize){	
					int[] possibility = {row, col, 0};	//0 for right, 1 for up
					walls.add(possibility);
				}
				else if(breakRight > maxSize){
					walls.clear();	//previous possibilities are not legit anymore
					maxSize = breakRight;
					int[] possibility = {row, col, 0};
					walls.add(possibility);
				}
				if(breakUp == maxSize){	
					int[] possibility = {row, col, 1};
					walls.add(possibility);
				}
				else if(breakUp > maxSize){
					walls.clear();
					maxSize = breakUp;
					int[] possibility = {row, col, 1};
					walls.add(possibility);
				}
			}
		}
		return maxSize;
	}
	
	public static void floodFill(int row, int col){
		if(visited.contains(modules[row][col][4])){	  //checking id#
			return;
		}
		visited.add(modules[row][col][4]);
		curSize++;
		if(modules[row][col][0] == 0){floodFill(row - 1, col);} //calling floodFill to appropriate neighbors
		if(modules[row][col][1] == 0){floodFill(row, col + 1); }
		if(modules[row][col][2] == 0){floodFill(row + 1, col); }
		if(modules[row][col][3] == 0){floodFill(row, col - 1); }
		modules[row][col][5] = curRoom;
	}
	
	public static void setSize(int row, int col){
		if(modules[row][col][6] > 0){	//already set this room's size if it's > 0
			return;
		}
		modules[row][col][6] = curSize; 
		if(modules[row][col][0] == 0){setSize(row - 1, col);} 
		if(modules[row][col][1] == 0){setSize(row, col + 1); }
		if(modules[row][col][2] == 0){setSize(row + 1, col); }
		if(modules[row][col][3] == 0){setSize(row, col - 1); }
	}
}
