#include <iostream>

struct class_t {
	int apple = 100;

	int count() const {
	// equal to int count(const class_t * const this)
		return apple;
	}
};

int main(void) {
	struct class_t c;

	std::cout << c.count() << std::endl;

	return 0;
}
