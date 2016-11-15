#include <iostream>
#include <set>
#include <string>

using namespace std;

int main(void) {
    set<string> exs{ "Hello", "World", "C++", "Primer", "Python" };

    if (exs.find("Hello") != exs.end()) {
        cout << "Hello" << " In " << "set<string> exs" << endl;

        if (exs.find("HTML") == exs.end()) {
            cout << "HTML" << " Not In " << "set<string> exs" << endl;
        }
    } else {
        cout << "Hello" << " Not In " << "set<string> exs" << endl;
    }

    return 0;
}