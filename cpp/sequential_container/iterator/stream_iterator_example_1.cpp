#include <iostream>
#include <fstream>
#include <iterator>
#include <algorithm>

using namespace std;

int main(void) {
    ifstream ifp("test.txt");
    ofstream ofp1("odd.txt");
    ofstream ofp2("even.txt");
    istream_iterator<int> iit(ifp), eof;
    ostream_iterator<int> oit1(ofp1, "\n"), oit2(ofp2, "\n");

    // copy_if(iit, eof, oit1, [] (int i) { return i % 2; });
    // copy_if(iit, eof, oit2, [] (int i) { return !(i % 2); });

    while (iit != eof) {
        if (*iit % 2) {
            *oit1++ = *iit++;
        } else {
            *oit2++ = *iit++;
        }
    }

    return 0;
}