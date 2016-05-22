#include <iostream>
#include "header.h"

int main(void) {
	Test t0 = Test();
	Test t1 = Test(100);
	Test t2 = Test(100, 'A');

	t0.printAll(std::cout) << std::endl;
	t1.printAll(std::cout) << std::endl;
	t2.printAll(std::cout) << std::endl;

	return 0;
}
