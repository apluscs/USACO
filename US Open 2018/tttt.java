// 2018 US Open Contest, Bronze Problem 1. Team Tic Tac Toe

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class tttt {
  public static char[][] grid;
  public static HashSet<Integer> pairs, indivs;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("tttt.in"));
        PrintWriter out = new PrintWriter(new File("tttt.out"));) {
      grid = new char[3][3];
      pairs = new HashSet<>();
      indivs = new HashSet<>();
      for (int i = 0; i < 3; i++) {
        String line = in.readLine();
        for (int j = 0; j < 3; j++)
          grid[i][j] = line.charAt(j);
      }
      String des = "", asc = "";
      for (int i = 0; i < 3; i++) {
        String row = "", col = "";
        des = addIfUnique(des, grid[i][i]);
        asc = addIfUnique(asc, grid[i][2 - i]);
        for (int j = 0; j < 3; j++) {
          row = addIfUnique(row, grid[i][j]);
          col = addIfUnique(col, grid[j][i]);
        }
        countComponents(row);
        countComponents(col);
      }
      countComponents(des);
      countComponents(asc);
      // System.out.println("des: " + des);
      // System.out.println("asc: " + asc);
      out.println(indivs.size());
      out.println(pairs.size());
    }

  }

  public static void countComponents(String uniq) {
    if (uniq.length() == 1) {
      indivs.add(uniq.charAt(0) + 0);
    } else if (uniq.length() == 2) {
      int a = uniq.charAt(0);
      int b = uniq.charAt(1);
      pairs.add(Math.max(a, b) * 1000 + Math.min(a, b)); // ex. so AB and BA are considered same
    }
  }

  public static String addIfUnique(String temp, char c) {
    for (int i = 0; i < temp.length(); i++)
      if (c == temp.charAt(i))
        return temp;
    return temp + c;
  }
}
