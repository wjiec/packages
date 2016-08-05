#include <iostream>
#include <array>

int main(void) {
	std::array<int, 9> apple = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	for (auto iter = apple.begin(); iter != apple.end(); ++iter) {
		std::cout << *iter << " " << std::endl;
	}

	std::array<int, 9> pear(apple);
	for (auto iter = pear.begin(); iter != pear.end(); ++iter) {
		std::cout << *iter << " " << std::endl;
	}

	return 0;
}

