#include <iostream>
#include <string>
#include <new>

using namespace std;

int main(void) {
    int *pi1 = new int;
    cout << *pi1 << endl;

    int *pi2 = new int();
    cout << *pi2 << endl;

    const int *cpi = new const int(99);
    cout << *cpi << endl;

    double *pd = new double(3.1492653);
    cout << *pd << endl;

    string *ps = new string(10, 'A');
    cout << *ps << endl;

    auto pc = new auto('S');
    cout << *pc << endl;

    auto nt_pc = new (nothrow) char('H');
    cout << *nt_pc << endl;

    delete pi1;
    delete pi2;
    delete cpi;
    delete pd;
    delete ps;
    delete pc;
    delete nt_pc;

    return 0;
}