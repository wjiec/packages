#include <iostream>
#include <set>
#include <map>
#include <string>

using namespace std;

int main(void) {
    multimap<string, int> mm;
    multiset<string> ms;

    mm.insert({"Hello", 5});
    mm.insert({"Hello", 4});
    for (auto &m : mm) {
        cout << m.first << " " << m.second << endl;
    }
    cout << mm.find("Hello")->second << endl;

    ms.insert("Hello");
    ms.insert("Hello");
    for (auto &s : ms) {
        cout << s << endl;
    }

    return 0;
}