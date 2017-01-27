#include <iostream>
#include <memory>
#include <string>

using namespace std;

class Apple {
    public:
        Apple() : name(new string) { cout << "default constructor" << endl; }
        Apple(string s) : name(new string(s)) { cout << "one-parameter construct" << endl; }
        Apple(const Apple &apple) : name(new string(*(apple.name))) { cout << "copy constructor" << endl; }

    private:
        shared_ptr<string> name;
};

int main(void) {
    Apple obj1;
    Apple obj2(obj1);
    Apple obj3(string("hello"));

    return 0;
}