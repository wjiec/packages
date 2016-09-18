#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

void elim(std::vector<std::string> &words);

int main(void) {
    std::vector<std::string> apple{ "This", "is", "an", "apple", "from", "China", "apple" };

    elim(apple);

    return 0;
}

void elim(std::vector<std::string> &words) {
    sort(words.begin(), words.end());

    auto e = unique(words.begin(), words.end());
    for (auto i = e; i < words.end(); ++i) {
        std::cout << *i << " ";
    }
    std::cout << std::endl;

    words.erase(e, words.end());
    for (auto i : words) {
        std::cout << i << " ";
    }
    std::cout << std::endl;
}

