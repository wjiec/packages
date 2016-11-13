#include <iostream>
#include <iterator>
#include <algorithm>
#include <vector>

using namespace std;

int main(void) {
    vector<int> v{ 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    for_each(v.crbegin(), v.crend(), [] (int n) { cout << n << endl; });
    cout << endl;

    // output bigger that 5 all element

    // wrong
    auto it1 = find_if(v.crbegin(), v.crend(), [] (int n) { return n < 5; });
    for_each(v.crbegin(), it1, [] (int n) { cout << n << endl; });
    cout << endl;

    // true
    for_each(it1.base(), v.cend(), [] (int n) { cout << n << endl; });

    return 0;
}