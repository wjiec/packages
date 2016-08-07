#include <iostream>
#include <vector>

int main(void) {
	std::vector<int> apple; // empty
	
	for (int index = 0; index < 9; ++index) {
		apple.push_back(index);
		apple.emplace_back(index * index);
	}

	for (auto index : apple) {
		std::cout << index << " ";
	}
	std::cout << std::endl;

	return 0;
}

