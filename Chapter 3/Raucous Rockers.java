import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class rockers {

  public static void main(String[] args) throws IOException {
    try (Scanner f = new Scanner(new File("rockers.in"));
        PrintWriter out = new PrintWriter(new File("rockers.out"));) {
      rockers My = new rockers();
      int N = f.nextInt();
      int T = f.nextInt();
      int M = f.nextInt();
      int[] songs = new int[N + 1];
      for (int i = 1; i <= N; i++) {
        songs[i] = f.nextInt();
      }
      out.println(My.maxSongs(songs, N, T, M));
    }
  }

  private int maxSongs(int[] songs, int N, int T, int M) {
    int dp[][] = new int[N + 1][1 + M * T]; // songs as rows, Minutes/disk as cols
    for (int i = 1; i <= N; ++i) {
      for (int j = 1; j <= M * T; j++) {
        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // keep everything To left and up
        int space = j % T;
        if (space == 0)
          space += T;
        if (space >= songs[i]) { // This song can fit on a disk
          dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - songs[i]] + 1);
        }
      }
    }
    return dp[N][M * T];
  }
  
  private int maxSongsDFS(int[] songs, int N, int T, int M) { //slower and tougher :/
    int result = 0;
    for (int i = 0; i < 1 << N; i++) {
      int com = i; // binary represents combination
      int numDisks = 0, numSongs = 0, so = 1, used = 0;
      while (com > 0) {
        if (songs[so] > T) {
          so++;
          com >>= 1;
          break;
        }
        if ((com & 1) == 1) {
          numSongs++;
          used += songs[so];
          if (used > T) { // disk overflow
            used = songs[so];
            numDisks++;
          }
        }
        so++; // song index
        com >>= 1;
      }
      if (used > 0)
        numDisks++; // need to count last disk only partially filled
      if (numDisks > M)
        continue;
      result = Math.max(numSongs, result);
    }
    return result;
  }
}
