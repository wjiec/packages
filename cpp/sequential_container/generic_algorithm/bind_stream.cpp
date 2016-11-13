#include <iostream>
#include <algorithm>
#include <functional>
#include <string>

using namespace std;

ostream& print(ostream &os, string message);

int main(void) {
    print(cout, "Unbind function");

    auto printBind = bind(print, ref(cout), std::placeholders::_1);
    printBind("binded function");

    return 0;
}

ostream& print(ostream &os, string message) {
    return os << message << endl;
}