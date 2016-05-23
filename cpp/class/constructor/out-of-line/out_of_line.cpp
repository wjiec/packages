#include "header.h"
#include <iostream>

Test::Test(int apple, char bg) : appleCnt(apple), background(bg) {
	std::cout << "In Constructor: " << "apple = " << apple << " "
			  << "background = " << bg << std::endl;
}

