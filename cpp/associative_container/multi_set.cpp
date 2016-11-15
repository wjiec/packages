#include <iostream>
#include <vector>
#include <set>

using namespace std;

int main(void) {
    vector<int> v;

    for (int i = 0; i < 10; ++i) {
        v.push_back(i);
        v.push_back(i);
    }

    set<int> s(v.begin(), v.end());
    multiset<int> ms(v.begin(), v.end());

    cout << "v.size() = " << v.size() << endl;
    cout << "s.size() = " << s.size() << endl;
    cout << "ms.size() = " << ms.size() << endl;
}