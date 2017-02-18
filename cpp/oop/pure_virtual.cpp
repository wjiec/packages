#include <iostream>

class Base {
    public:
        Base() = default;
        virtual ~Base() = default;

    public:
        virtual auto debug() const & -> int = 0;
};

class Derived1 : public Base {
    public:
        Derived1() = default;

    public:
        int debug() const & override { std::cout << "Overrided" << std::endl; return 1; }
};

class Deriver2 : public Base {
    public:
        Deriver2() = default;
};

int main(void) {
    Derived1 d1;
    d1.debug();

    // error: cannot declare variable 'd2' to be of abstract type 'Deriver2'
    // because the following virtual functions are pure within 'Deriver2'
    Deriver2 d2;
    // Derived2 is abstract base class

    return 0;
}
