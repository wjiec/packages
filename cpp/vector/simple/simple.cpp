#include <iostream>
#include <vector>

int main(void) {
    std::vector<int> val;

    for (int i = 0; i < 10; ++i) {
        val.push_back(i);
    }

    for (auto i : val) {
        std::cout << i << std::endl;
    }

    return 0;
}
