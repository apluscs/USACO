import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class dishes {
  public static int N, picked;
  public static int[] nums;
  public static List<Pile> counter;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("dishes.in"));
        PrintWriter out = new PrintWriter(new File("dishes.out"));) {
      N = Integer.parseInt(in.readLine());
      counter = new ArrayList<>();
      nums = new int[N];
      for (int i = 0; i < N; i++)
        nums[i] = Integer.parseInt(in.readLine());
      int res = solve();
      out.println(res);
    }
  }

  public static int solve() {
    picked = 0;
    int max = 0;
    for (int i = 0; i < N; i++) {
      // System.out.println(counter.size());
      if (nums[i] < picked)
        return i;
      if (nums[i] > max) {
        counter.add(new Pile(nums[i]));
        max = nums[i];
      }
      int j = binSearch(nums[i]);
      Pile curr = counter.get(j);
      if (nums[i] < curr.s.peek()) {
        curr.s.push(nums[i]);
      } else if (nums[i] > curr.s.peek() && nums[i] < curr.base) {
        popOff(j, nums[i]);
      }
    }
    return N;
  }

  public static int binSearch(int num) { // which pile is most fitting? this is fast search!
    int low = 0, high = counter.size() - 1;
    while (low <= high) {
      int mid = (low + high) / 2;
      Pile curr = counter.get(mid);
      if (num > curr.base && num < curr.s.peek())
        return mid;
      if (num > curr.base)
        low = mid + 1;
      else
        high = mid - 1;
    }   //at end, low and high flip. The new high's (low) base will always > num
    return low;
  }

  public static void popOff(int i, int num) {
    Pile curr = counter.get(i);
    while (curr.s.peek() < num)
      picked = curr.s.pop();
    curr.s.push(num);
  }

  public static class Pile {
    int base; // stack only allows you to see top :(
    Stack<Integer> s = new Stack<>();

    public Pile(int base) {
      this.base = base;
      s.push(base);
    }
  }
}
