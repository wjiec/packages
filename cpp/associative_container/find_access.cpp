#include <iostream>
#include <string>
#include <sstream>
#include <map>

using namespace std;

int main(void) {
    map<string, int> m;
    multimap<string, int> mm;

    for (int i = 0; i < 10; ++i) {
        ostringstream os;
        os << "Hello_" << i;
        m.insert(make_pair(os.str(), i));
        mm.insert({os.str(), i});
        mm.insert({os.str(), i});
    }

    for (auto &w : m) {
        cout << w.first << " " << w.second << endl;
    }
    cout << endl;
    for (auto &w : mm) {
        cout << w.first << " " << w.second << endl;
    }
    cout << endl;

    auto it1 = m.find("Hello_0");
    cout << "m.find('Hello_0') = " << it1->first << ", " << it1->second << endl;
    auto it2 = m.find("Hello_0");
    cout << "mm.find('Hello_0') = " << it2->first << ", " << it2->second << endl;

    cout << "m.count('Hello_0') = " << m.count("Hello_0") << endl;
    cout << "mm.count('Hello_0') = " << mm.count("Hello_0") << endl;

    

    return 0;
}