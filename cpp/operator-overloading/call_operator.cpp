#include <iostream>

using namespace std;

struct Add {
    int operator()(const int n1, const int n2) {
        return n1 + n2;
    }
};

int main(void) {
    auto add_instance = Add();

    cout << add_instance(1, 2) << endl;

    return 0;
}
