#include <iostream>

using namespace std;

class Number {
    friend Number operator+(const Number &left, const Number &right);
    friend Number operator-(const Number &left, const Number &right);

    public:
        Number() = default;
        Number(int n) : _num(n) {}
        Number(double d) : _num(d) {}

        void check(void) { cout << _num << endl; }

    private:
        double _num;
};
Number operator+(const Number &left, const Number &right);


int main(void) {
    Number n1(100);
    Number n2 = 10;

    Number n3 = n1 + n2;
    n3.check();

    auto n4 = n3 - 123;
    n4.check();

    return 0;
}

Number operator+(const Number &left, const Number &right) {
    auto rst = left;

    rst._num += right._num;

    return rst;
}

Number operator-(const Number &left, const Number &right) {
    auto rst = left;

    rst._num -= right._num;

    return rst;
}