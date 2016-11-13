#include <iostream>
#include <vector>
#include <algorithm>

int main(void) {
    int number = 7;
    auto func = [&number] () mutable -> bool { if (number > 0) --number; return number == 0; };

    std::cout << number << ' ' << func() << std::endl;
    std::cout << number << ' ' << func() << std::endl;

    std::cout << std::endl;
    std::cout << number << ' ' << func() << std::endl
        << number << ' ' << func() << std::endl
        << number << ' ' << func() << std::endl
        << number << ' ' << func() << std::endl
        << number << ' ' << func() << std::endl
        << number << ' ' << func() << std::endl;

    return 0;
}