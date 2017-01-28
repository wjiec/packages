#include <iostream>
#include <string>
#include <memory>
#include <cstdio>

using namespace std;

class Apple {
    public:
        Apple() : name(new string) { cout << "default-constructor" << endl; check(); }
        Apple(const Apple &copy_apple) : name(new string(*copy_apple.name)) { cout << "copy-constructor" << endl; check(); }
        Apple &operator=(const Apple &copy_apple);
        void check(void);
        ~Apple() { name.reset(); cout << "destructor function" << endl; }
    private:
        shared_ptr<string> name;
};

int main(void) {
    Apple o1;
    Apple o2(o1);
    Apple o3;
    Apple o4 = o3;

    o3 = o2;

    return 0;
}

Apple &Apple::operator=(const Apple &copy_apple) {
    name = make_shared<string>(*copy_apple.name);

    cout << "assign-operator" << endl;
    check();

    return *this;
}

void Apple::check(void) {
    printf("\tAddress = %#X\n", (unsigned int)name.get());
}
