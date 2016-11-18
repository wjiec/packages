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

    cout << "foreach map" << endl;
    for (auto &w : m) {
        cout << w.first << " " << w.second << endl;
    }
    cout << endl << "foreach multimap" << endl;
    for (auto &w : mm) {
        cout << w.first << " " << w.second << endl;
    }

    cout << endl << "map.find()" << endl;
    auto it1 = m.find("Hello_0");
    cout << "m.find('Hello_0') = " << it1->first << ", " << it1->second << endl;
    cout << endl << "multimap.find()" << endl;
    auto it2 = m.find("Hello_0");
    cout << "mm.find('Hello_0') = " << it2->first << ", " << it2->second << endl;

    cout << endl << "map.count()" << endl;
    cout << "m.count('Hello_0') = " << m.count("Hello_0") << endl;
    
    cout << endl << "multimap.count()" << endl;
    cout << "mm.count('Hello_0') = " << mm.count("Hello_0") << endl;

    cout << endl << "map.lower_bound()" << endl;
    auto it3 = m.lower_bound("Hello_5");
    while (it3 != m.end()) {
        cout << it3->first << " " << it3->second << endl;
        it3++;
    }

    cout << endl << "multimap.lower_bound()" << endl;
    auto it4 = mm.lower_bound("Hello_5");
    while (it4 != mm.end()) {
        cout << it4->first << " " << it4->second << endl;
        it4++;
    }

    cout << endl << "map.upper_bound()" << endl;
    auto it5 = m.upper_bound("Hello_5");
    while (it5 != m.end()) {
        cout << it5->first << " " << it5->second << endl;
        it5++;
    }

    cout << endl << "multimap.upper_bound()" << endl;
    auto it6 = mm.upper_bound("Hello_5");
    while (it6 != mm.end()) {
        cout << it6->first << " " << it6->second << endl;
        it6++;
    }

    cout << endl << "map.equal_range()" << endl;
    auto pair1 = m.equal_range("Hello_5");
    cout << "equal 'Hello_5' " << "( " << pair1.first->first << ", " << pair1.first->second << " )"
         << " ( " << pair1.second->first << ", " << pair1.second->second << " )" << endl;

    cout << endl << "multimap.equal_range()" << endl;
    auto pair2 = mm.equal_range("Hello_5");
    cout << "equal 'Hello_5' " << "( " << pair2.first->first << ", " << pair2.first->second << " )"
         << " ( " << pair2.second->first << ", " << pair2.second->second << " )" << endl;

    return 0;
}
