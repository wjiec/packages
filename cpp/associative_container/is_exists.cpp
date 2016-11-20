#include <iostream>
#include <map>

using namespace std;

int main(void) {
    map<int, int> m{ {1, -1}, {2, -2}, {3, -3} };

    if (m.find(1) != m.end()) {
        cout << "1 is in map" << endl;
    } else {
        cout << "1 is not in map" << endl;
    }

    if (m.find(5) != m.end()) {
        cout << "5 is in map" << endl;
    } else {
        cout << "5 is not in map" << endl;
    }

    return 0;
}