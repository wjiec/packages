#include <iostream>
#include <string>

using namespace std;

class Apple {
    friend void swap(Apple &lv, Apple &rv);

    public:
        Apple(string s = string()) : name(new string(s)), age(0) {}
        Apple(const Apple &copy);
        Apple &operator=(Apple right_value);
        ~Apple();
    private:
        string *name;
        int age;
};

// declaration again
void swap(Apple &lv, Apple &rv);

int main(void) {
    Apple a1("test1");
    Apple a2("test2");

    a1 = a2;

    return 0;
}

inline Apple::Apple(const Apple &copy)
    : name(new string(*(copy.name))), age(copy.age) {

    // copy-constructor
}

// using swap
inline Apple &Apple::operator=(Apple right_value) {
    swap(*this, right_value);

    return *this;
}

inline Apple::~Apple() {
    cout << *name << " enter the destructor" << endl;
}

// only swap pointer
void swap(Apple &lv, Apple &rv) {
    using std::swap;

    swap(lv.name, rv.name);
    swap(lv.age, rv.age);
}
