#include <iostream>

using namespace std;

class Base {
    public:
        Base() = default;
        virtual ~Base() = default;

    public:
        void base_func(void) { cout << "base_function" << endl; }

    protected:
        void base_protected_func() { cout << "base_protected_function" << endl; }

    private:
        void base_private_func() { cout << "base_private_function" << endl; }
};

class Derived1 : public Base {
    public:
        Derived1() = default;
        ~Derived1() = default;
};

class Derived2 : protected Base {
    public:
        Derived2() = default;
        ~Derived2() = default;

    public:
        void derived2_func() { cout << "derived2_function" << endl; base_func(); base_protected_func(); }
};

class Derived3 : protected Base {
    public:
        Derived3() = default;
        ~Derived3() = default;

    public:
        // using base_func;
        using Base::base_func;
        using Base::base_protected_func;

    public:
        void derived3_func() { cout << "derived3_function" << endl; base_func(); base_protected_func(); }
};

int main(void) {
    Base b1;
    b1.base_func();
    cout << endl;

    Derived1 d1;
    d1.base_func();
    cout << endl;

    Derived2 d2;
    // 'Base' is not an accessible base of 'Derived2'
    // d2.base_func();
    d2.derived2_func();
    cout << endl;

    Derived3 d3;
    d3.derived3_func();
    d3.base_func();
    d3.base_protected_func();
    cout << endl;

    return 0;
}