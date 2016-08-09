#include <iostream>
#include <vector>
#include <array>

int main(void) {
	std::vector<int> apple;

	for (int index = 0; index < 9; ++index) {
		apple.insert(apple.begin(), index);
	}

	for (auto index : apple) {
		std::cout << index << std::ends;
	}
	std::cout << std::endl;

	std::array<int, 9> pear = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	apple.insert(apple.end(), pear.begin(), pear.end());
	for (auto index : apple) {
		std::cout << index << std::ends;
	}
	std::cout << std::endl;

	return 0;
}

