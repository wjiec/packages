#include <iostream>
#include <iterator>
#include <string>
#include <map>

using namespace std;

int main(void) {
    istream_iterator<string> iit(cin), eof;
    map<string, int> word_count;

    while (iit != eof) {
        auto ret = word_count.insert(make_pair(*iit++, 1));

        if (ret.second == false) {
            ret.first->second++;
        }
    }

    for (auto &w : word_count) {
        cout << w.first << " " << w.second << endl;
    }
    cout << endl;

    return 0;
}
