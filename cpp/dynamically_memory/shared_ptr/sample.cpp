#include <iostream>
#include <vector>
#include <string>
#include <memory>

using namespace std;

int main(void) {
    int n = 0;
    vector<string> v;
    shared_ptr<int> sp1(&n);
    shared_ptr<vector<string>> sp2(&v);

    int *ip = sp1.get();
    vector<string> *vp = sp2.get();
    cout << ip << " " << vp << endl;

    auto sp3 = make_shared<int>(1);
    cout << sp3.get() << " " << sp3.use_count() << endl;

    auto sp4 = sp3;
    cout << sp3.get() << " " << sp3.use_count() << endl;

    return 0;
}