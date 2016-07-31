#include <iostream>
#include <vector>

int main(void) {
	std::vector<int> apple{ 1, 2, 3, 4, 5 };

	for (auto e : apple) {
		std::cout << e << " ";
	}
	std::cout << std::endl;

	// replace
	apple = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	for (auto e : apple) {
		std::cout << e << " ";
	}
	std::cout << std::endl;

	// replace
	apple = { 11, 12, 13, 14 };
	for (auto e : apple) {
		std::cout << e << " ";
	}
	std::cout << std::endl;

	// swap
	std::vector<int> banana{ 1, 2, 3, 4, 5 };
	apple.swap(banana);
	for (auto e : apple) {
		std::cout << e << " ";
	}
	std::cout << std::endl;

	return 0;
}

