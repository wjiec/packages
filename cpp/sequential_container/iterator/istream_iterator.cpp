#include <iostream>
#include <iterator>
#include <vector>

using namespace std;

int main(void) {
    istream_iterator<int> in(cin), eof;
    vector<int> v;

    while (in != eof) {
        v.push_back(*in++);
    }
    for (auto el : v) {
        cout << el << ' ';
    }
    cout << endl;

    // simple approach
    istream_iterator<int> ins(cin);
    vector<int> v1(ins, eof);
    for (auto el : v1) {
        cout << el << ' ';
    }
    cout << endl;

    return 0;
}