#include <iostream>
#include <string>

using namespace std;

class Apple {
    friend void swap(Apple &lv, Apple &rv);

    public:
        Apple(string s = string()) : name(new string(s)), age(0) {}
        Apple(const Apple &copy);
        Apple(Apple &&move_instance) noexcept;
        Apple &operator=(Apple right_value) noexcept;
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

    Apple a3(std::move(a1));

    Apple &&rr = std::move(a3);
    Apple rra(rr);

    return 0;
}

inline Apple::Apple(const Apple &copy)
    : name(new string(*(copy.name))), age(copy.age) {

    // copy-constructor
    cout << "copy-constructor" << endl;
}

Apple::Apple(Apple &&move_instance) noexcept
    : name(move_instance.name), age(move_instance.age) {

    cout << "move-constructor" << endl;
    move_instance.name = nullptr;
    move_instance.age = -1;
}

// using swap
inline Apple &Apple::operator=(Apple right_value) noexcept {
    swap(*this, right_value);

    return *this;
}

inline Apple::~Apple() {
    cout << (name == nullptr ? "nullptr" : *name) << " enter the destructor" << endl;

    delete name;
    age = -1;
}

// only swap pointer
void swap(Apple &lv, Apple &rv) {
    using std::swap;

    cout << "Apple::swap function" << endl;

    swap(lv.name, rv.name);
    swap(lv.age, rv.age);
}
