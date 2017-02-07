#include <iostream>
#include <string>

using namespace std;

class Apple {
    public:
        Apple(string s = string(), int n = 0);
        Apple(const Apple &copy_instance);
        Apple(Apple &&move_instance) noexcept;
        Apple &operator=(const Apple &copy_instance);
        Apple &operator=(Apple &&move_instance) noexcept;
        ~Apple();

        void print(void);

    private:
        string *name;
        int age;
};

Apple test_return_1(Apple a);
Apple test_return_2(Apple &a);
Apple test_return_3(Apple &&a);
Apple test_return_3_rr(Apple &&a);
Apple &test_return_4(Apple a);
Apple &test_return_5(Apple &a);
Apple &test_return_6(Apple &&a);
Apple &&test_return_7(Apple a);
Apple &&test_return_8(Apple &a);
Apple &&test_return_9(Apple &&a);

int main(void) {
    // Apple a1("Apple 1", 1);
    // Apple a2(a1);
    // Apple a3("Apple 2", 2);
    // Apple a4 = a3;
    // Apple rr(std::move(a4));

    Apple r("return test", 0);

    cout << endl << "Apple test_return_1(Apple a);" << endl;
    Apple r1 = test_return_1(r);

    cout << endl << "Apple test_return_2(Apple &a);" << endl;
    Apple r2 = test_return_2(r);

    cout << endl << "Apple test_return_3(Apple &&a);" << endl;
    Apple r3 = test_return_3(std::move(r));
    r3.print();

    cout << endl << "Apple test_return_3_rr(Apple &&a);" << endl;
    Apple r4 = test_return_3_rr(std::move(r));
    r4.print();

    // error
    /*
    cout << endl << "Apple &test_return_4(Apple a);" << endl;
    Apple r5 = test_return_4(r);
    r5.print();
    */

    cout << endl << "Apple &test_return_5(Apple &a);" << endl;
    Apple r6 = test_return_5(r); // copy
    r6.print();

    cout << endl << "Apple &test_return_6(Apple &&a);" << endl;
    Apple r7 = test_return_6(std::move(r)); // copy
    r7.print();

    cout << endl << "Apple &&test_return_7(Apple a);" << endl;
    Apple r8 = test_return_7(r); // copy
    r8.print();

    cout << endl << "Apple &&test_return_8(Apple &a);" << endl;
    Apple r9 = test_return_8(r); // move
    r9.print();

    // error
    /*
    cout << endl << "Apple &&test_return_9(Apple &&a);" << endl;
    Apple r10 = test_return_9(std::move(r)); // move
    r10.print();
    */

    cout << "[DEBUG] r.print()" << endl;
    r.print(); // because test_return_8 move-construtcor occurs

    cout << endl;
    return 0;
}

Apple::Apple(string s, int n)
    : name(new string(s)), age(n) {

    // default constructor
    cout << "[DEBUG] default constructor args = ( " << s << ", " << n << " )" << endl;
}

Apple::Apple(const Apple &copy_instance)
    : name(new string(*(copy_instance.name))), age(copy_instance.age) {

    // copy-constructor
    cout << "[DEBUG] copy constructor args = Apple( " << *name << ", " << age << " )" << endl;

    // modify
    *name += " copy";
    age += 1;
}

Apple::Apple(Apple &&move_instance) noexcept
    : name(move_instance.name), age(move_instance.age) {

    // move-comstructor
    cout << "[DEBUG] move constructor args = Apple( " << *name << ", " << age << " )" << endl;

    // flag
    *name += " move";

    // modify move_instance
    move_instance.name = nullptr;
    move_instance.age = -1;
}

Apple &Apple::operator=(const Apple &copy_instance) {
    cout << "[DEBUG] Apple &Apple::operator=(const Apple &copy_instance)" << endl;
    cout << "\targs = Apple( " << *(copy_instance.name) << ", " << copy_instance.age << " )" << endl;

    auto tmp = copy_instance;

    // destroy
    delete name;

    // copy
    name = new string(*(tmp.name));
    age = tmp.age;

    return *this;
}

Apple &Apple::operator=(Apple &&move_instance) noexcept {
    cout << "[DEBUG] Apple &Apple::operator=(Apple &&move_instance)" << endl;
    cout << "\targs = Apple( " << *(move_instance.name) << ", " << move_instance.age << " )" << endl;

    if (&move_instance != this) {
        // destroy
        delete name;

        // move
        name = move_instance.name;
        age = move_instance.age;

        // modify move_instance
        move_instance.name = nullptr;
        move_instance.age = -1;
    }

    return *this;
}

Apple::~Apple() {
    cout << "[DEBUG] destructor this = Apple( " << ((name == nullptr) ? "nullptr" : *name) << ", " << age << " )" << endl;

    // if pointer is nullptr, no action occurs
    delete name;
    age = -1;
}

void Apple::print(void) {
    cout << "\t( " << (name == nullptr ? "nullptr" : *name) << ", " << age << " )" << endl;
}

Apple test_return_1(Apple a) {
    Apple tmp = a;

    return tmp;
}

Apple test_return_2(Apple &a) {
    auto tmp = a;

    return tmp;
}

Apple test_return_3(Apple &&a) {
    auto tmp(a); // copy

    return tmp;
}

Apple test_return_3_rr(Apple &&a) {
    auto tmp = a; // copy

    return std::move(tmp); // move | tmp -> anonymous_variable
}

// error
Apple &test_return_4(Apple a) {
    return a;
}

Apple &test_return_5(Apple &a) {
    return a;
}

Apple &test_return_6(Apple &&a) {
    return a;
}

// rvalue-reference == reference
Apple &&test_return_7(Apple a) { // copy
    return std::move(a); // move & a.destructor
}

Apple &&test_return_8(Apple &a) {
    return std::move(a);
}

// error rvalue-reference == reference
Apple &&test_return_9(Apple &&a) {
    // a is lvalue
    return std::move(a);
}
