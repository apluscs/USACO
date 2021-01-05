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
vector<vector<int>> adj;

struct Solution {
  vector<int> rank;
  int res = 0;
  int solve() {
    dfs(0, -1);
    return res;
  }
  void dfs(int curr, int par) {
    int x = adj[curr].size() - (par == -1 ? 0 : 1);  // number of children
    res += ceil(log2(x + 1)) + x;
    // printf("curr=%d, par=%d, x=%d, res=%d\n", curr, par, x, res);
    for (auto kid : adj[curr]) {
      if (kid == par) continue;
      dfs(kid, curr);
    }
  }
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(0);
  cin >> n;
  int a, b;
  adj.resize(n);
  for (int i = 1; i != n; ++i) {
    cin >> a >> b;
    a--, b--;
    adj[a].push_back(b);
    adj[b].push_back(a);
  }
  Solution test;
  cout << test.solve() << endl;
}
