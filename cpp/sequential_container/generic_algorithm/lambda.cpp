#include <iostream>
#include <algorithm>
#include <vector>

int main(void) {
    std::vector<int> apple;

    fill_n(back_inserter(apple), 10, 0);
    apple.push_back(1);

    auto lambdaObject = [] (decltype(apple) container) { for (auto e : container) std::cout << e << " "; std::cout << std::endl; };
    lambdaObject(apple);

    auto element = find(begin(apple), end(apple), 1);
    std::cout << "Found Element Count " << count(begin(apple), end(apple), 1) << std::endl;
    std::cout << "Found Element In Address " << &(*element) << ", Value " << *element << std::endl;

    return 0;
}
