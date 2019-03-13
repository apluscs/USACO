// 2018 US Open Contest, Bronze Problem 3. Family Tree

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class family {
  public static int N, gid;
  public static String d, u;
  public static HashMap<String, Integer> map;
  public static Node root;

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader("family.in"));
        PrintWriter out = new PrintWriter(new File("family.out"));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      map = new HashMap<>();
      root = new Node(null, -1);
      gid = 1;
      u = st.nextToken();
      d = st.nextToken();
      for (int i = 0; i < N; i++) {
        st = new StringTokenizer(in.readLine());
        String m = st.nextToken();
        String c = st.nextToken();
        if (map.containsKey(m) && map.containsKey(c)) {
          int mid = map.get(m);
          int cid = map.get(c);
          Node mom = findNode(mid);
          Node child = findNode(cid);
          mom.children.add(child);
          removeNode(child.parent, cid);    //parent should always be root
          child.parent = mom;
        } else if (map.containsKey(m)) {
          int mid = map.get(m);
          int cid = gid++; // child is new
          map.put(c, cid);
          Node mom = findNode(mid);
          Node child = new Node(mom, cid);
          mom.children.add(child);
        } else if (map.containsKey(c)) {
          int mid = gid++;  //mom is new
          int cid = map.get(c);
          Node child = findNode(cid);
          removeNode(child.parent, cid);
          Node mom = new Node(root, mid);
          map.put(m, mid);
          child.parent = mom;
          mom.children.add(child);
          root.children.add(mom);
        } else { // both are new
          int mid = gid++;
          int cid = gid++;
          map.put(m, mid);
          map.put(c, cid);
          Node mom = new Node(root, mid);
          Node child = new Node(mom, cid);
          root.children.add(mom);
          mom.children.add(child);
        }
      }
      // for (Map.Entry<String, Integer> e : map.entrySet()) {
      // System.out.println(e.getKey() + ": " + e.getValue());
      // }
      levels();
      // printTree();
      solve(out);
    }
  }

  public static void solve(PrintWriter out) {
    Node up = findNode(map.get(u));
    Node down = findNode(map.get(d));
    if (up.parent.id == down.parent.id) {
      out.println("SIBLINGS");
      return;
    }
    if (up.lev > down.lev) {
      Node temp = up;   //up should be higher up in the tree
      up = down;
      down = temp;
      String st = u;
      u = d;
      d = st;
    }
    int vert = 0;
    while (down.lev > up.lev) {
      down = down.parent;
      vert++;
    } // now at the same level
    if (down.id == up.id) { // direct descendent
      out.print(u + " is the ");
      if (vert == 1) 
        out.print("mother");
      else if (vert == 2) 
        out.print("grand-mother");
      else {
        for (int i = 2; i < vert; i++) 
          out.print("great-");
        out.print("grand-mother");
      }
      out.println(" of " + d);
      return;
    }
    if (down.parent.id != -1 && down.parent.id == up.parent.id) { // aunt
      out.print(u + " is the ");
      if (vert == 1) 
        out.print("aunt");
     else {
        for (int i = 1; i < vert; i++) 
          out.print("great-");
        out.print("aunt");
      }
      out.println(" of " + d);
      return;
    }
    while (down.id != up.id) {//finds common ancestor
      down = down.parent;
      up = up.parent;
    }
    if (down.id == -1) {    //same as up.id now
      out.println("NOT RELATED");   //root is not a cow
    } else
      out.println("COUSINS");
  }

  public static void removeNode(Node mid, int cid) {
    for (int i = 0; i < mid.children.size(); i++) 
      if (mid.children.get(i).id == cid) {
        mid.children.remove(i);
        return;
      }
  }
  public static Node findNode(int id) {
    Queue<Node> toVis = new LinkedList<>();
    toVis.add(root);
    while (!toVis.isEmpty()) {
      Node curr = toVis.poll();
      if (curr.id == id) 
        return curr;
      toVis.addAll(curr.children);
    }
    return new Node(root, -1); // should never happen
  }

  public static void levels() {
    Queue<Node> toVis = new LinkedList<>();
    toVis.add(root);
    int level = 0;
    while (!toVis.isEmpty()) {
      int size = toVis.size();
      for (int i = 0; i < size; i++) {
        Node curr = toVis.poll();
        curr.lev = level;
        toVis.addAll(curr.children);
      }
      level++;
    }
  }

  private static class Node {
    Node parent;
    List<Node> children;
    int id, lev;

    public Node(Node parent, int id) {
      this.parent = parent;
      this.id = id;
      children = new ArrayList<>();
    }

    public void printNode() {
      if (parent != null) 
        System.out.println("Parent: " + parent.id + ", I am: " + id + ", Level: " + lev);
      System.out.print("Children: ");
      for (Node kid : children) 
        System.out.print(kid.id + ", ");
      System.out.println("\n");
    }
  }

  public static void printTree() {
    Queue<Node> toVis = new LinkedList<>();
    toVis.add(root);
    while (!toVis.isEmpty()) {
      Node curr = toVis.poll();
      curr.printNode();
      toVis.addAll(curr.children);
    }
  }
}
