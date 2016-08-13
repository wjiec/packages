#include <iostream>
#include <deque>
#include <vector>

int main(void) {
	std::vector<int> apple;
	std::deque<int> pear;

	for (int index = 0; index < 9; ++index) {
		apple.push_back(index);
		pear.push_back(index);
	}

	apple.pop_back();
	pear.pop_front();

	for (int index = 0; index < 8; ++index) {
		std::cout << apple[index] << " " << pear.at(index) << std::endl;
	}

	apple.erase(apple.begin());
	pear.erase(pear.end());

	for (int index = 0; index < 7; ++index) {
		std::cout << apple[index] << " " << pear.at(index) << std::endl;
	}

	apple.clear();
	pear.clear();
	std::cout << apple.empty() << " " << pear.empty() << std::endl;

	return 0;
}
