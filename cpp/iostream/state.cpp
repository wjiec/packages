#include <iostream>

int main(void) {
	std::cout << "std::cout.good() -> " << std::cout.good() << std::endl;
	std::cout << "std::cout.fail() -> " << std::cout.fail() << std::endl;
	std::cout << "std::cout.bad() -> " << std::cout.bad() << std::endl;
	std::cout << "std::cout.rdstate() -> " << std::cout.rdstate() << std::endl;

	return 0;
}
