#include <iostream>

class Apple {
    public:
        Apple() = default;
        Apple(const Apple &) = default;
        Apple &operator=(const Apple &) = default;
        ~Apple() = default;
    private:
        int age;
};

int main(void) {
    Apple _1;

    return 0;
}
