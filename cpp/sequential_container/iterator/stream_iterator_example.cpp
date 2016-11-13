#include <iostream>
#include <fstream>
#include <iterator>
#include <vector>
#include <string>

using namespace std;

int main(void) {
    ifstream fp("Makefile", ios::in);
    istream_iterator<string> iit(fp), eof;
    ostream_iterator<string> oit(cout, "\n");
    vector<string> v(iit, eof);

    copy(v.begin(), v.end(), oit);

    return 0;
}