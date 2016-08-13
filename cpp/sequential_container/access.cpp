#include <iostream>
#include <vector>

int main(void) {
	std::vector<int> apple;

	for (int index = 0; index < 9; ++index) {
		apple.push_back(index);
	}

	std::cout << "apple.front() = " << apple.front() << std::endl;
	std::cout << "apple.back() = " << apple.back() << std::endl;
	std::cout << "apple.at(5) = " << apple.at(5) << std::endl;

	return 0;
}

