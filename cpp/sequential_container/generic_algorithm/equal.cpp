#include <iostream>
#include <algorithm>
#include <string>
#include <vector>
#include <list>

using namespace std;

int main(void) {
    vector<string> v = { "Hello", "World", "G++", "GCC" };
    list<char *> l = { "Hello", "World", "G++", "GCC" };

    cout << equal(v.cbegin(), v.cend(), l.cbegin());

    return 0;
}

