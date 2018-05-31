#include <map>
#include <iostream>
#include <memory>
#include <vector>


class Apple {
    public:
        explicit Apple(int count) : _count(count) {}
        Apple(Apple &&apple) noexcept : _count(apple._count) {
            apple._count = -1;
        }

    public:
        int _count;
};


std::ostream &operator<<(std::ostream &out, Apple &apple) {
    out << "Apple[" << "count=" << apple._count << "]";

    return out;
}


int main() {
    std::vector<Apple> s1;

    for (auto i = 0; i < 10; ++i) {
        s1.emplace_back(i);
    }

    std::vector<Apple> s2;
    std::move(s1.begin(), std::next(s1.begin(), 5), std::back_inserter(s2));

    for (auto &apple : s1) {
        std::cout << apple << std::endl;
    }

    for (auto &apple : s2) {
        std::cout << apple << std::endl;
    }
}