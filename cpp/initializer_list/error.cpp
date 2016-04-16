#include <iostream>
#include <initializer_list>
#include <string>

using namespace std;

void triggerException(initializer_list<string> lst);

int main(void) {
    triggerException( { "Fatal Error", "undefinded variable `var`", "in function.cpp line 99" } ); // use { }

    return 0;
}

void triggerException(initializer_list<string> lst) {
    for (auto beg = lst.begin(); beg != lst.end(); ++beg) {
        cout << *beg << endl;
    }
}

