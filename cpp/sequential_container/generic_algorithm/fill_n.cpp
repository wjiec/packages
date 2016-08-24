#include <iostream>
#include <vector>

int main(void) {
    std::vector<int> apple(10, 0);

    for (auto element : apple) {
        std::cout << element << " ";
    }
    std::cout << std::endl;

    fill_n(begin(apple), apple.size(), 9);
    for (auto element : apple) {
        std::cout << element << " ";
    }
    std::cout << std::endl;

    return 0;
}
