#include <iostream>

class Apple {
	mutable int callCnt = 0;

	public:
		void cc() const {
			std::cout << "Call Count: " << callCnt++ << std::endl;
		}
};

int main(void) {
	Apple apple;
	const Apple banana;

	apple.cc();
	apple.cc();
	apple.cc();

	banana.cc();
	banana.cc();
	banana.cc();

	return 0;
}
