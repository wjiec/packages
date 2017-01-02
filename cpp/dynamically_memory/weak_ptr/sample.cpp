#include <iostream>
#include <memory>

using namespace std;

int main(void) {
    shared_ptr<int> sp1(new int(99));
    weak_ptr<int> wp1(sp1);

    cout << "sp1 = " << sp1 << endl;

    cout << "wp1.lock() = " << wp1.lock() << " sp1.use_count() = " << sp1.use_count() << endl;

    if (auto sp = wp1.lock()) {
        cout << "sp = " << sp << endl;
        cout << "sp.use_count() = " << sp1.use_count() << endl;
    }

    cout << "wp1.expired() = " << wp1.expired() << endl;
    cout << "wp1.expired() = " << wp1.use_count() << endl;

    return 0;
}
