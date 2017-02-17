#include <iostream>
#include <string>

using namespace std;

class Base {
    public:
        // default-constructor
        Base(int pub = 0, int prot = 0, int priv = 0);
        // copy-constructor
        Base(const Base &) = default;
        // move-constructor
        Base(Base &&) = default;
        // copy-assign operator
        Base &operator=(const Base &) = default;
        // move-assign operator
        Base &operator=(Base &&) = default;
        // default-destructor
        virtual ~Base() = default;

    public:
        // publish method
        virtual void debug(void) const;
        // common method
        void print(void) const;

    public:
        int public_member;

    protected:
        int protected_member;

    private:
        int private_member;
};

class Derived1 : public Base {
    public:
        // default-constructor
        Derived1(string s = string(), int n = 0, bool man = true);
        // copy-constructor
        Derived1(const Derived1 &) = default;
        // move-constructor
        Derived1(Derived1 &&) = default;
        // copy-assign operator
        Derived1 &operator=(const Derived1 &) = default;
        // move-assign operator
        Derived1 &operator=(Derived1 &&) = default;

    public:
        // is ok
        void debug(void) const override;
        // override?
        // error: 'void Derived1::print() const' marked 'override', but does not override
        void print(void) const;

    protected:
        void protected_func(void) const;

    public:
        string name;

    protected:
        int age;

    private:
        bool is_man = true;
};

class Derived11 : public Derived1 {
    public:
        // default-constructor
        Derived11() { cout << "Derived11::Derived11() constructor" << endl; };
        // copy-constructor
        Derived11(const Derived11 &) = default;
        // move-constructor
        Derived11(Derived11 &&) = default;
        // copy-assign operator
        Derived11 &operator=(const Derived11 &) = default;
        // move-assign operator
        Derived11 &operator=(Derived11 &&) = default;

    public:
        void debug(void) const override;
};

void func1(Base &instance);

int main(void) {
    Base b1;
    b1.debug();
    b1.print();
    cout << endl;

    Derived1 d1;
    d1.debug();
    d1.print();
    cout << endl;

    func1(b1);
    cout << endl;

    func1(d1);
    cout << endl;

    Derived11 d11;
    // 'void Derived1::protected_func() const' is protected
    // d11.protected_func();
    func1(d11);
    cout << endl;

    Derived1 &dr = d1;
    dr.print();
    dr.Base::print();

    return 0;
}

// default-constructor initialize
Base::Base(int pub, int prot, int priv) :
    public_member(pub), protected_member(prot), private_member(priv) {

    // default-constructor body
    cout << "Base::Base() constructor" << endl;
}

void Base::debug(void) const {
    cout << "[BASE_DEBUG] " << public_member
        << " " << protected_member
        << " " << private_member
        << endl;
}

void Base::print(void) const {
    cout << "BASE::print()" << endl;
}

// Derived1 default-constructor
Derived1::Derived1(string s, int n, bool man) :
    Base(0, 0, 0), name(s), age(n), is_man(man) {

    // default-constructor body
    cout << "Derived1::Derived1() constructor" << endl;
}

void Derived1::debug(void) const {
    cout << "[Derived1_DEBUG] " << name
        << " " << age
        << " " << is_man
        << endl;
}

void Derived1::print(void) const {
    cout << "Derived1::print()" << endl;
}

void Derived1::protected_func(void) const {
    cout << "Derived1::protected_func" << endl;
}

void Derived11::debug(void) const {
    cout << "[Derived11_DEBUG] " << endl;
}

void func1(Base &instance) {
    // dynamic type bind
    instance.debug();
    // static type bind
    instance.print();
}

