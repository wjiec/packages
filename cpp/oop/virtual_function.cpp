#include <iostream>

using namespace std;

class Base {
    public:
        Base() { this->say(); }
        ~Base() { this->say(); }

    public:
        virtual void say() { cout << "Base::say()" << endl; }
};

class Derived : public Base {
    public:
        Derived() = default;
        ~Derived() = default;

    public:
        void say() { cout << "Derived::say()" << endl; }
};

int main(void) {
    Derived d;

    return 0;
}
