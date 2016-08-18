#include <iostream>
#include <vector>
#include <string>

int main(void) {
    std::string apple("This is an apple");

    std::cout << apple.find("apple") << std::endl;

    auto pos = apple.find("Apple");
    if (pos == std::string::npos) {
        std::cout << "Apple not found" << std::endl;
    }

    return 0;
}
