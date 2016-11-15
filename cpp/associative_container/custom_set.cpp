#include <iostream>
#include <set>

using namespace std;

typedef struct _cust_t {
    int number;
} Cust;

bool copmare(Cust n1, Cust n2);

int main(void) {
    set<Cust, decltype(copmare)*> s(copmare);

    s.insert({1});
    s.insert({3});
    s.insert({2});
    s.insert({5});
    s.insert({4});
    s.insert({9});
    s.insert({7});
    s.insert({6});
    s.insert({8});

    for (auto u : s) {
        cout << u.number << endl;
    }

    return 0;
}

bool copmare(Cust n1, Cust n2) {
    return n1.number < n2.number;
}