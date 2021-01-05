#include <assert.h>
#include <math.h>

#include <algorithm>
#include <climits>
#include <functional>
#include <iostream>
#include <numeric>
#include <queue>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#define ll long long
#define fi first
#define se second
#define mp make_pair
#define pb push_back
#define ALL(v) v.begin(), v.end()
#define FOR(a, b, c) for (int(a) = (b); (a) < (c); ++(a))
#define FORN(a, b, c) for (int(a) = (b); (a) <= (c); ++(a))
#define FORD(a, b, c) for (int(a) = (b); (a) >= (c); --(a))
#define FORSQ(a, b, c) for (int(a) = (b); (a) * (a) <= (c); ++(a))
#define FORC(a, b, c) for (char(a) = (b); (a) <= (c); ++(a))
#define FOREACH(a, b) for (auto&(a) : (b))
#define REP(i, n) FOR(i, 0, n)
#define REPN(i, n) FORN(i, 1, n)

using namespace std;

int n;
vector<vector<int>> points;

struct Solution {
  vector<int> rank;
  static bool compar(const vector<int>& a, const vector<int>& b) {
    return a[1] < b[1];
  }
  ll solve() {
    sort(points.begin(), points.end(), compar);  //sort by y
    for (int i = 0; i != n; ++i) {
      points[i][1] = i;  // y is now compressed to [0, n)
    }
    ll res = n + 1;                      // singular and all
    sort(points.begin(), points.end());  //sort by x
    STree root(0, n);
    for (int i = 0; i != n; ++i) {  // left bound
      int a = points[i][1];
      // printf("a=%d\n", a);
      if (i % 2 == 0) {
        for (int j = i + 1; j != n; ++j) {  // right bound
          int b = points[j][1], low = min(a, b), high = max(a, b);
          // printf("b=%d\n", b);
          res += (root.query(0, low - 1) + 1) * (root.query(high + 1, n) + 1);
          root.update(b, b, 1);
        }
      } else {
        root.update(a, a, -1);
        for (int j = n - 1; j != i; j--) {
          int b = points[j][1], low = min(a, b), high = max(a, b);
          res += (root.query(0, low - 1) + 1) * (root.query(high + 1, n) + 1);
          root.update(b, b, -1);
        }
      }
    }
    return res;
  }

  struct STree {
    int s, m, e, f = 0;
    STree *left = nullptr, *right = nullptr;
    STree(int s, int e) : s(s), e(e), m((s + e) / 2) {}
    int update(int from, int to, int frq) {
      if (from > to) return f;
      if (from == s && to == e) return f += frq;  // exactly this range
      if (!left) {
        left = new STree(s, m), right = new STree(m + 1, e);
      }
      return f = left->update(max(from, s), min(to, m), frq) + right->update(max(from, m + 1), min(e, to), frq);
    }
    ll query(int from, int to) {
      if (from > to) return 0;
      if (from == s && to == e) return f;  // exactly this range
      if (!left) {
        left = new STree(s, m), right = new STree(m + 1, e);
      }
      return left->query(max(from, s), min(to, m)) + right->query(max(from, m + 1), min(e, to));
    }
  };
  void TreeDelete(STree* node) {
    if (node == NULL) {
      return;
    }
    TreeDelete(node->left);   //deleting left subtree
    TreeDelete(node->right);  //deleting right subtree
    // printf("\n Deleting node: %c", node->s);
    free(node);  //deleting the node
  }
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(0);
  cin >> n;
  points.resize(n, {0, 0});
  for (int i = 0; i != n; ++i) {
    cin >> points[i][0] >> points[i][1];
  }
  Solution test;
  cout << test.solve() << endl;
}
