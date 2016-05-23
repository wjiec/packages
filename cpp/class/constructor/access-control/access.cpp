#include <iostream>
#include "access.h"

int main(void) {
	Test t(100, '*');

	t.printIn(std::cout) << t.description << std::endl;

	return 0;
}
