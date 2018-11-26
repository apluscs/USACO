Farmer John has three milking buckets of capacity A, B, and C liters. Each of the numbers A, B, and C is an integer from 1 
through 20, inclusive. Initially, buckets A and B are empty while bucket C is full of milk. Sometimes, FJ pours milk from one 
bucket to another until the bucket being poured into is filled or the bucket being poured is empty. Once begun, a pour must be 
completed, of course. Being thrifty, no milk may be tossed out.

Write a program to help FJ determine what amounts of milk he can leave in bucket C when he begins with three buckets as above, 
pours milk among the buckets for a while, and then notes that bucket A is empty.

INPUT FORMAT
A single line with the three integers A, B, and C.

SAMPLE INPUT (file milk3.in)
8 9 10

OUTPUT FORMAT
A single line with a sorted list of all the possible amounts of milk that can be in bucket C when bucket A is empty.

SAMPLE OUTPUT (file milk3.out)
1 2 8 9 10

SAMPLE INPUT (file milk3.in)
2 5 10

SAMPLE OUTPUT (file milk3.out)
5 6 7 8 9 10


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class milk3 {

	public static void main(String[] args) {
		try(BufferedReader f = new BufferedReader(new FileReader("milk3.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));){
		while(f.ready()){
			int[] buckets = new int[3];
			StringTokenizer st = new StringTokenizer(f.readLine());
			A = Integer.parseInt(st.nextToken());
			buckets[0] = 0;
			B = Integer.parseInt(st.nextToken());
			buckets[1] = 0;
			C = Integer.parseInt(st.nextToken());
			buckets[2] = C;
			vals = new HashSet<>();
			visited = new HashSet<>();
			
			possC(buckets);
			int[] result = new int[vals.size()];
			int j = 0;
			for(int val: vals){
				result[j] = val;
				j++;
			}
			Arrays.sort(result);
			for(int i = 0; i < result.length - 1; i++){
				out.print(result[i] + " ");
			}
			out.println(result[result.length - 1]);
		}
		} catch (IOException e){}
	}

	public static int A;
	public static int B;
	public static int C;
	public static HashSet<Integer> vals;
	public static HashSet<Integer> visited;
	
	public static void possC(int[] buckets){
		int a = buckets[0];	int b = buckets[1];	int c = buckets[2];

		if(visited.contains(a*10000 + b*100 + c)){return;}
		visited.add(a*10000 + b*100 + c);
		if(a == 0){
			vals.add(buckets[2]);
//			System.out.println("start " + buckets[2]);
		}
		int[] modBuck = new int[3];
		if(a > 0){	//a has something
			if(a + b > B){	//pouring will exceed B's limit
				modBuck = new int[] {a-(B-b), B, c};	
				possC(modBuck);
			}
			else{
				modBuck = new int[] {0, b + a, c};	//all of a to b
				possC(modBuck);
			}
//			System.out.println(Arrays.toString(modBuck));
			if(a + c > C){	//pouring will exceed C's limit
				modBuck = new int[] {a-(C-c), b, C};
				possC(modBuck);			
			}
			else{
				modBuck = new int[] {0, b, c + a};	//all of a to c
				possC(modBuck);				
			}
//			System.out.println(Arrays.toString(modBuck));
		}
		if(b > 0){
			if(a + b > A){	//pouring will exceed A's limit
				modBuck = new int[] {A, b-(A-a), c};
				possC(modBuck);
			}
			else{
				modBuck = new int[] {b + a, 0, c};	//all of b to a
				possC(modBuck);
			}
//			System.out.println(Arrays.toString(modBuck));
			if(b + c > C){	//pouring will exceed C's limit
				modBuck = new int[] {a, b-(C-c), C};
				possC(modBuck);
			}
			else{
				modBuck = new int[] {a, 0, c + b};	//all of b to c
				possC(modBuck);
			}
//			System.out.println(Arrays.toString(modBuck));
		}
		if(c > 0){
			if(c + b > B){	//pouring will exceed B's limit
				modBuck = new int[] {a, B, c-(B-b)};
				possC(modBuck);
			}
			else{	//all of c to b
				modBuck = new int[] {a, b + c, 0};
				possC(modBuck);
			}
//			System.out.println(Arrays.toString(modBuck));
			if(a + c > A){	
				modBuck = new int[] {A, b, c-(A-a)};
				possC(modBuck);
			}
			else{	//all of c to a
				modBuck = new int[] {a + c, b, 0};	
				possC(modBuck);
			}
//			System.out.println(Arrays.toString(modBuck));
		}
	}
}
