/* A group of NP (2 ≤ NP ≤ 10) uniquely named friends has decided to exchange gifts of money. Each of these friends might or 
might not give some money to some or all of the other friends (although some might be cheap and give to no one). Likewise, each 
friend might or might not receive money from any or all of the other friends. Your goal is to deduce how much more money each 
person receives than they give.

The rules for gift-giving are potentially different than you might expect. Each person goes to the bank (or any other source of 
money) to get a certain amount of money to give and divides this money evenly among all those to whom he or she is giving a 
gift. No fractional money is available, so dividing 7 among 2 friends would be 3 each for the friends with 1 left over – that 1 
left over goes into the giver's "account". All the participants' gift accounts start at 0 and are decreased by money given and 
increased by money received.

In any group of friends, some people are more giving than others (or at least may have more acquaintances) and some people have 
more money than others.

Given:

    a group of friends, no one of whom has a name longer than 14 characters,
    the money each person in the group spends on gifts, and
    a (sub)list of friends to whom each person gives gifts,

determine how much money each person ends up with. 

INPUT FORMAT

Line #	Contents
1 	        A single integer, NP
2..         NP+1 	Line i+1 contains the name of group member i
NP+2..end 	NP groups of lines organized like this:
            The first line of each group tells the person's name who will be giving gifts.
            The second line in the group contains two numbers:

    The amount of money (in the range 0..2000) to be divided into gifts by the giver
    NGi (1 ≤ NGi ≤ NP), the number of people to whom the giver will give gifts 

If NGi is nonzero, each of the next NGi lines lists the name of a recipient of a gift; recipients are not repeated in a single 
giver's list. 

OUTPUT FORMAT

The output is NP lines, each with the name of a person followed by a single blank followed by the net gain or loss 
(final_money_value - initial_money_value) for that person. The names should be printed in the same order they appear starting on 
line 2 of the input.

All gifts are integers. Each person gives the same integer amount of money to each friend to whom any money is given, and gives 
as much as possible that meets this constraint. Any money not given is kept by the giver. 

SAMPLE INPUT (file gift1.in)

5
dave
laura
owen
vick
amr
dave
200 3
laura
owen
vick
owen
500 1
dave
amr
150 2
vick
owen
laura
0 2
amr
vick
vick
0 0

SAMPLE OUTPUT (file gift1.out)

dave 302
laura 66
owen -359
vick 141
amr -150
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class gift1 {

	public static void main(String[] args) throws IOException {
		try (BufferedReader f = new BufferedReader(new FileReader("gift1.in"));
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));) {
		
		HashMap<String, Integer> monies = new HashMap<>();
		int total = Integer.parseInt(f.readLine());
		String[] order = new String[total];
		for(int i = 0; i < total; i++){
			String currFriend = f.readLine();
			monies.put(currFriend, 0);
			order[i] = currFriend;
		}
		while(f.ready()){
			String giver = f.readLine();
			StringTokenizer st = new StringTokenizer(f.readLine());
			int withdrawn = Integer.parseInt(st.nextToken());
			int friends = Integer.parseInt(st.nextToken());
			if(friends == 0){
				continue;
			}
			int perFriend = withdrawn/friends;
			monies.put(giver, monies.get(giver) - withdrawn);	//withdraws from giver
			monies.put(giver, monies.get(giver) + withdrawn % friends);	//adds remainder to giver
			for(int i = 0; i < friends; i++){
				String currFriend = f.readLine();
				monies.put(currFriend, monies.get(currFriend) + perFriend);	//adds to each friend
			}
			
		}
		for(String fr: order){
			out.println(fr + " " + monies.get(fr));
		}
		}
	}
}
