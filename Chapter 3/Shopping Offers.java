//Shoutout to http://www.cppblog.com/Ylemzy/articles/100170.html for helping me out with this one. Multi-dimensional DP!

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class shopping {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("shopping.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));) {
      shopping my = new shopping();
      int sell[][] = new int[104][6]; // 99+5;last column is price
      int[] encode = new int[6];
      int numOffers = f.nextInt(); // includes "bad deals" and given deals = total offers
      int toBuy[] = new int[6];
      for (int offer = 0; offer < numOffers; offer++) {
        int numProds = f.nextInt();
        for (int j = 0; j < numProds; j++) {
          int pCode = f.nextInt();  //given code
          int k = f.nextInt();  //items needed for this product for this offer
          int code = my.Encode(pCode, encode);
          sell[offer][code] = k;
        }
        sell[offer][5] = f.nextInt(); // price for this deal
      }
      int badDeals = f.nextInt();
      for (int i = 0; i < badDeals; i++) {
        int pCode = f.nextInt();
        int k = f.nextInt();
        int code = my.Encode(pCode, encode);
        sell[numOffers + i][code] = 1;
        toBuy[code] = k;
        sell[numOffers + i][5] = f.nextInt();
      }
      numOffers += badDeals;

      int result = my.saveMoney(numOffers, toBuy, sell);
      out.println(result);
    }
  }

  private int saveMoney(int numOffers, int[] toBuy, int sell[][]) {
    int dp[][][][][] = new int[6][6][6][6][6];
      int i0, i1, i2, i3, i4; // requirements: respective minimums you are looking to fill
      for (i0 = 0; i0 <= toBuy[0]; i0++) {
        for (i1 = 0; i1 <= toBuy[1]; i1++) {
          for (i2 = 0; i2 <= toBuy[2]; i2++) {
            for (i3 = 0; i3 <= toBuy[3]; i3++) {
              for (i4 = 0; i4 <= toBuy[4]; i4++) {
                for (int j = 0; j < numOffers; j++) {

                  if (i0 >= sell[j][0] && i1 >= sell[j][1] && i2 >= sell[j][2] && i3 >= sell[j][3]
                    && i4 >= sell[j][4]) { // 1. Do I have enough items to fit the offer?
                    int tmp =
                        dp[i0 - sell[j][0]][i1 - sell[j][1]][i2 - sell[j][2]][i3 - sell[j][3]][i4
                          - sell[j][4]] + sell[j][5]; // what you need without the offer + offer's
                                                      // price
                  if (dp[i0][i1][i2][i3][i4] == 0 || dp[i0][i1][i2][i3][i4] > tmp) {// 2. Do I have
                                                                                    // another
                                                                                    // choice (==0)?
                    dp[i0][i1][i2][i3][i4] = tmp; // 3. Even if there's another choice, is this
                                                  // offer a better deal?
                    }
                  }
                }
              }
            }
          }
        }
      }
      return dp[toBuy[0]][toBuy[1]][toBuy[2]][toBuy[3]][toBuy[4]];
  }

  private int Encode(int code, int[] encode) { // shrinks the given codes to something between 0-4
    for (int i = 0; i < 5; i++) {
      if (encode[i] == code)
        return i;
      else if (encode[i] == 0) {
        encode[i] = code;
        return i;
      }
    }
    return -1;
  }
}
