#include <iostream>
#include <algorithm>
#include <iterator>
#include <vector>
#include <cmath>

using namespace std;

int handle1(int num) {
    return abs(num);
}

int main(void) {
    vector<int> in = { 1, -1, 2, -2, 3, -3 };
    vector<int> out1, out2;

    transform(in.begin(), in.end(), back_inserter(out1), handle1);
    for (auto n : out1) {
        cout << n << " ";
    }
    cout << endl;
    transform(in.begin(), in.end(), back_inserter(out2), [] (int n) -> int { return n > 0 ? n : -n; });
    for (auto n : out2) {
        cout << n << " ";
    }
    cout << endl;

    return 0;
}
