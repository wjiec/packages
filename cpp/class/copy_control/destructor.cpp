#include <iostream>
#include <string>
#include <memory>

using namespace std;

class Apple {
    public:
        Apple() : name(new string) { cout << "default-constructor" << endl; }
        ~Apple() { name.reset(); cout << "destructor" << endl; }
    private:
        shared_ptr<string> name;
};

int main(void) {
    Apple a1;

    return 0;
}
