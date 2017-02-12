#include <iostream>

using namespace std;

class Number {
    public:
        Number() = default;
        Number(int n) : _num(n) {}
        Number(double d) : _num(d) {}

        Number &operator++();
        Number operator++(int);

        void check(void);

    private:
        double _num = 0; // default constructor value
};

// pre increment
// return a reference
Number &Number::operator++() {
    _num += 1;

    return *this;
}

// post increment
// return an object
Number Number::operator++(int) {
    auto rst = *this;
    _num += 1;

    return rst;
}

void Number::check(void) {
    cout << _num << endl;
}

int main(void) {
    Number n1 = 100.1;
    Number n2 = 200;

    ++n1;
    n1.check();

    auto rst = n2++;
    rst.check();
    n2.check();

    auto rst_rr = std::move(rst++);
    rst_rr.check();
    rst.check();

    return 0;
}
