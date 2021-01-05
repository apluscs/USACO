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
vector<vector<int>> cows;

struct Solution {
  vector<int> res;
  vector<vector<int>> adj;
  struct Stop {
    int x, y, time;
    Stop(int x, int y, int time) : x(x), y(y), time(time) {}
    bool operator<(const Stop& a) const {
      return time < a.time;
    }
  };
  struct compar {
    bool operator()(const vector<int>& a, const vector<int>& b) {
      return a[2] > b[2];
    }
  };
  vector<int> solve() {
    vector<vector<Stop>> points(n);  // points[i] = all possible meeting points for cow i
    vector<vector<int>> meets(n);
    adj.resize(n);  // adj[i] = all cows stopped by cow i
    for (int i = 0; i != n; i++) {
      auto a = cows[i];         // should go north
      if (a[0] == 1) continue;  // going east
      for (int j = 0; j != n; j++) {
        if (j == i) continue;
        auto b = cows[j];         // should go east
        if (b[0] == 0) continue;  // going north
        if (a[1] > b[1] && a[2] < b[2]) {
          points[i].push_back(Stop(a[1], b[2], b[2] - a[2]));
          points[j].push_back(Stop(a[1], b[2], a[1] - b[1]));
        }
      }
    }
    priority_queue<vector<int>, vector<vector<int>>, compar> pq;  // cow, index in points[i], time
    for (int i = 0; i != n; i++) {
      if (points[i].empty()) continue;
      sort(points[i].begin(), points[i].end());  //by time until arrival
      pq.push({i, 0, points[i][0].time});
    }
    unordered_map<string, vector<int>> last_seen;  // values are {time, cow}
    while (!pq.empty()) {
      auto curr = pq.top();
      pq.pop();
      int cow = curr[0], k = curr[1], time = curr[2];
      auto loc = to_string(points[cow][k].x) + "," + to_string(points[cow][k].y);
      // printf("cow=%d, k=%d, time=%d, loc=%s\n", cow, k, time, loc.c_str());
      if (last_seen.count(loc)) {
        auto prev = last_seen[loc];
        if (prev[1] < time) {
          adj[prev[0]].push_back(cow);
          continue;
        }
      }
      last_seen[loc] = {cow, time};
      if (++k >= points[cow].size()) continue;  // no more points to go
      pq.push({cow, k, points[cow][k].time});
    }
    // print(adj);
    res.resize(n);
    for (int i = 0; i != n; ++i) {
      if (res[i] == 0 && !adj[i].empty()) {
        dfs(i);
      }
    }
    return res;
  }
  int dfs(int i) {
    // printf("i=%d, x=%d\n", i, i);
    if (adj[i].empty()) return 0;
    if (res[i] > 0) return res[i];
    int x = 0;
    for (auto nei : adj[i]) {
      x += 1 + dfs(nei);
    }
    res[i] = x;
    return x;
  }
  void print(vector<int>& nums) {
    for (auto num : nums) cout << num << " ";
    cout << endl;
  }

  void print(vector<vector<int>>& nums) {
    for (auto& row : nums) print(row);
  }
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(0);
  cin >> n;
  cows.resize(n, {0, 0, 0});
  for (int i = 0; i != n; ++i) {
    string east;
    cin >> east >> cows[i][1] >> cows[i][2];
    cows[i][0] = east == "E";  // north = 0, east = 1
  }
  Solution test;
  auto res = test.solve();
  for (auto r : res) cout << r << endl;
}
