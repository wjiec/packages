#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main(void) {
    vector<string> v1;

    {
        vector<string> v2{"Apple", "Banana", "Pear"};

        v1 = v2;
    } // v2 and inside element is destoryed

    for (auto &el : v1) {
        cout << el << endl;
    }

    return 0;
}
