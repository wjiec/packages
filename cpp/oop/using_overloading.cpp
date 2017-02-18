#include <iostream>

using namespace std;

class Base {
    public:
        Base() = default;
        virtual ~Base() = default;

    public:
        void func() { cout << "func()" << endl; }
        void func(int) { cout << "func(int)" << endl; }
        void func(double) { cout << "func(double)" << endl; }
        void func(char) { cout << "func(char)" << endl; }        
};

class Derived1 : public Base {
    public:
        Derived1() = default;
        ~Derived1() = default;

    public:
        void func(int) { cout << "Derived1::func(int)" << endl; }
};

class Derived2 : public Base {
    public:
        Derived2() = default;
        ~Derived2() = default;

    public:
        void func(int) { cout << "Derived1::func(int)" << endl; }
        using Base::func;
};


int main(void) {
    Base b1;
    b1.func();
    cout << endl;

    Derived1 d1;
    // // note:   candidate expects 1 argument, 0 provided
    // d1.func();
    d1.func(10);
    cout << endl;

    Derived2 d2;
    d2.func();
    d2.func(12);
    d2.func(12.1);
    d2.func('\x12');

    return 0;
}
