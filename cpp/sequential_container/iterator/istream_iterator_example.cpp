#include <iostream>
#include <iterator>
#include <algorithm>

using namespace std;

int main(void) {
    istream_iterator<int> in(cin), eof;

    cout << accumulate(in, eof, 0) << endl;

    return 0;
}