#include <iostream>
#include <functional>
#include <vector>


class Apple {
    public:
        Apple() = default;
        explicit Apple(int count) : _count(count) {}

    public:
        inline int get_apple_count(int additional_count) const {
            return _count + additional_count;
        }

    public:
        int _count;
};


int main(int argc, char *argv[]) {
    Apple *apple = new Apple(20);

    auto func_1 = std::mem_fun(&Apple::get_apple_count);
    std::cout << func_1(apple, 10) << std::endl;

    auto func_2 = std::bind(func_1, apple, std::placeholders::_1);
    std::cout << func_2(100) << std::endl;

    auto func_3 = apple->get_apple_count;
    std::cout << (apple->*func_3)(123) << std::endl;

    int Apple:: *offset = &Apple::_count;
    apple->*offset = 100;
    std::cout << apple->_count << std::endl;

//
//
//    int Apple::*offset = 1;
//
//    std::cout << apple.*offset(100) << std::endl;

    return 0;
}
