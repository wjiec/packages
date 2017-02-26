#include <iostream>

using namespace std;

class Base {
    public:
        Base() = default;
        virtual ~Base() { cout << "Base::~Base()" << endl; }
};

class Derived : public Base {
    public:
        Derived() = default;
        // automatic call Base::~Base()
        ~Derived() { cout << "Derived::~Derived()" << endl; }
};

int main(void) {
    Derived d1;

    return 0;
}
