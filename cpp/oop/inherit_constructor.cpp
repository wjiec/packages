#include <iostream>

using namespace std;

class Base {
    public:
        Base(int n = 0, double d = 0.0) : number(n), d_val(d) {}

    public:
        inline void check(void);

    private:
        int number;
        double d_val;
};

class Derived : public Base {
    public:
        using Base::Base;
};

int main(void) {
    Derived d1;
    d1.check();

    Derived d2(11);
    d2.check();

    Derived d3(11.0);
    d3.check();

    Derived d4(10, 11.0);
    d4.check();

    return 0;
}

inline void Base::check(void) {
    cout << number << " " << d_val << endl;
}
