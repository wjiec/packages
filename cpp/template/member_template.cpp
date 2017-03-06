#include <iostream>
#include <memory>
using namespace std;

class Deleter {
    public:
        template<typename T>
        void operator()(T *ptr) const;
};

int main(void) {
    unique_ptr<int, Deleter> up(new int, Deleter());

    return 0;
}

template<typename T>
void Deleter::operator()(T *ptr) const {
    cout << "deleting ptr " << ptr << endl;

    delete ptr;
}

