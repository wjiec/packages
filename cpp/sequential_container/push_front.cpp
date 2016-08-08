#include <iostream>
#include <deque>

int main(void) {
	std::deque<int> apple;

	for (int index = 0; index < 9; ++index) {
		apple.push_front(index);
	}

	for (auto index : apple) {
		std::cout << index << std::endl;
	}

	return 0;
}

