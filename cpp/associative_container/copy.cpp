#include <iostream>
#include <algorithm>
#include <vector>
#include <set>

using namespace std;

int main(void) {
    vector<int> v;
    set<int> s;

    for (int i = 0; i < 10; ++i) {
        v.push_back(i);
    }

    copy(v.begin(), v.end(), inserter(s, s.end()));
    for (auto &p : s) {
        cout << p << " ";
    }
    cout << endl;

    s.clear();
    cout << "s.clear(); s.size() = " << s.size() << endl;

    // wrong s not 'push_back' member method
    // copy(s.begin(), s.end(), back_inserter(s));
    // for (auto &p : s) {
    //     cout << p << endl;
    // }
    // cout << endl;

    return 0;
}
