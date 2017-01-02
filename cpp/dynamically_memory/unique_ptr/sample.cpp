#include <iostream>
#include <memory>

using namespace std;

void func(unique_ptr<int> &up);
void end_int(int *p);

int main(void) {
    unique_ptr<int> up1;
    unique_ptr<int> up2(new int(100));
    unique_ptr<int, decltype(end_int)*> up3((new int(100)), end_int);
    unique_ptr<int> up4(up1.release());
    unique_ptr<int> up5;
    up5.reset(up4.release());

    auto lambda = [] (int *p) { delete p; };
    unique_ptr<int, decltype(lambda)> up6((new int(100)), lambda);

    shared_ptr<int> sp1(up5.release());
    shared_ptr<int> sp2 = sp1;

    unique_ptr<int> up7(up6.get());

    func(up1);

    return 0;
}

void end_int(int *p) {
    cout << "enter end_int" << endl;

    delete p;
}

void func(unique_ptr<int> &up) {

}