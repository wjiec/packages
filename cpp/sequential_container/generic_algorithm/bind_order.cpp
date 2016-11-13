#include <iostream>
#include <algorithm>
#include <functional>
#include <vector>

using namespace std;

bool shortSort(int x, int y);

int main(void) {
    std::vector<int> v{ 1, 3, 2, 4, 6, 5, 7, 9, 8, 0 };

    sort(v.begin(), v.end(), shortSort);
    for (auto n : v) {
        cout << n << ' ';
    }
    cout << endl;

    sort(v.begin(), v.end(), bind(shortSort, std::placeholders::_2, std::placeholders::_1));
    for (auto n : v) {
        cout << n << ' ';
    }
    cout << endl;

    return 0;
}

bool shortSort(int x, int y) {
    return x > y;
}