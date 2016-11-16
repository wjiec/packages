#include <iostream>
#include <utility>
#include <map>

using namespace std;

int main(void) {
    map<char, int> m;

    m.insert({'A', 1});
    m.insert(make_pair('B', 2));
    m.insert(pair<char, int>('C', 3));
    m.insert(map<char, int>::value_type('D', 4));
    m.emplace('E', 5);

    for (auto &p : m) {
        cout << p.first << " " << p.second << endl;
    }
    cout << endl;

    return 0;
}