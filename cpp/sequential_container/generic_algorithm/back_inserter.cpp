#include <iostream>
#include <vector>

int main(void) {
    std::vector<int> apple;

    std::cout << "apple.empty() : " << apple.empty() << std::endl;

    fill_n(back_inserter(apple), 9, 9);
    for (auto element : apple) {
        std::cout << element << " ";
    }
    std::cout << std::endl;

    return 0;
}
