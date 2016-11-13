#include <iostream>
#include <fstream>
#include <iterator>
#include <string>
#include <map>

using namespace std;

int main(void) {
    ifstream ifp("map.cpp");
    istream_iterator<string> it(ifp), eof;
    map<string, size_t> word_counter;

    while (it != eof) {
        word_counter[*it++] += 1;
    }

    for (auto &w : word_counter) {
        cout << w.first << " " << w.second << endl;
    }

    return 0;
}