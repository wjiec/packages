#include <iostream>

using namespace std;

template<typename T>
bool small_than(const T &lv, const T &rv) {
    if (lv < rv) {
        return true;
    }
    return false;
}

template<typename Type>
bool bigger_than(const Type &lv, const Type &rv) {
    if (lv > rv) {
        return true;
    }
    return false;
}

int main(void) {
    cout << small_than(1, 2) << endl;
    cout << small_than(2, 1) << endl;

    cout << bigger_than(1, 2) << endl;
    cout << bigger_than(2, 1) << endl;

    return 0;
}
