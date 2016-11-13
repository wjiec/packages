#include <iostream>
#include <iterator>
#include <algorithm>
#include <vector>

using namespace std;

int main(void) {
    ostream_iterator<int> out(cout, " - ");
    std::vector<int> v{ 1, 2, 4, 5, 6, 7, 8, 9 };

    for (auto el : v) {
        *out++ = el;
    }
    cout << endl;

    // or simple
    reverse(v.begin(), v.end());
    copy(v.begin(), v.end(), out);
    cout << endl;

    return 0;
}