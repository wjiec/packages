#include <iostream>
#include <iterator>
#include <algorithm>
#include <vector>
#include <list>

using namespace std;

int main(void) {
    vector<int> v = { 1, 2, 3, 4, 5, 6 };
    list<int> empl;
    vector<int> empv1, empv2;

    cout << "front_inserter" << ' ';
    copy(v.begin(), v.end(), front_inserter(empl));
    for (auto el : empl) {
        cout << el << ' ';
    }
    cout << endl;

    cout << "back_inserter" << ' ';
    copy(v.begin(), v.end(), back_inserter(empv1));
    for (auto el : empv1) {
        cout << el << ' ';
    }
    cout << endl;

    cout << "inserter" << ' ';
    copy(v.begin(), v.end(), inserter(empv2, empv2.begin()));
    for (auto el : empv2) {
        cout << el << ' ';
    }
    cout << endl;

    return 0;
}
