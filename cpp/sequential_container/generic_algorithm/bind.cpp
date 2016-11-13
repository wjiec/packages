#include <iostream>
#include <functional>
#include <algorithm>
#include <vector>

using namespace std;

bool findBiggerThat(int number, int base);

int main(void) {
    int base = 3;
    vector<int> v{ 1, -1, 2, -2, 3, -3, 4, -4, 5, -5 };

    auto pos1 = find_if(v.begin(), v.end(), [base] (int n) { return n > base; });
    for_each(pos1, v.end(), [] (const int n) { cout << n << " "; });
    cout << endl;

    auto bindFunc = bind(findBiggerThat, std::placeholders::_1, base);
    auto pos2 = find_if(v.begin(), v.end(), bindFunc);
    for_each(pos2, v.end(), [] (const int n) { cout << n << " "; });
    cout << endl;

    return 0;
}

bool findBiggerThat(int number, int base) {
    return number > base;
}