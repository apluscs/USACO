// January 2019 Silver

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class perimeter {

  public static int curSize = 0, curPeri = 0;

  public static void main(String[] args) {
    try (BufferedReader f = new BufferedReader(new FileReader("perimeter.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("perimeter.out")));) {
      int N = Integer.parseInt(f.readLine());
      int[][] ices = new int[N][N];
      int i = 0;
      while (f.ready()) {
        String line = f.readLine();
        for (int j = 0; j < line.length(); j++) {
          char c = line.charAt(j);
          if (c == '#')
            ices[i][j] = -1;
          else
            ices[i][j] = -11; // no ice cream
        }
        i++;
      }
      perimeter my = new perimeter();
      int[] result = my.bestScoop(ices, N);
      out.println(result[0] + " " + result[1]);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private int[] bestScoop(int[][] nums, int N) {
    int[] result = new int[2];
    int scoop = 1;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (nums[i][j] == -1) {
          curSize = 0;
          curPeri = 0;
          unionize(nums, i, j, scoop++);
          if (curSize > result[0]) {
            result[0] = curSize;
            result[1] = curPeri;
          } else if (curSize == result[0]) {
            result[1] = Math.min(result[1], curPeri);
          }
        }
      }
    }
    return result;
  }

  private void unionize(int[][] nums, int i, int j, int scoop) {
    if (nums[i][j] == -1) { // ice cream exists here
      nums[i][j] = scoop; // id number
      curSize++; // increment area by 1 for each unit of ice cream
    } else
      return;
    if (i == 0 || nums[i - 1][j] == -11)
      curPeri++; // increment perimeter by 1 for each side exposed
    if (i == nums.length - 1 || nums[i + 1][j] == -11)
      curPeri++;// west, east, north, south neighbors
    if (j == 0 || nums[i][j - 1] == -11)
      curPeri++;
    if (j == nums.length - 1 || nums[i][j + 1] == -11)
      curPeri++;
    if (i > 0)
      unionize(nums, i - 1, j, scoop);
    if (i < nums.length - 1)
      unionize(nums, i + 1, j, scoop);
    if (j > 0)
      unionize(nums, i, j - 1, scoop);
    if (j < nums.length - 1)
      unionize(nums, i, j + 1, scoop);
  }
}

