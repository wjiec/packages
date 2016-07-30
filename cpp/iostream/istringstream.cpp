#include <iostream>
#include <sstream>

int main(void) {
	std::string buffer("Hello World!");
	std::istringstream in(buffer);
	std::string inBuffer;

	in >> inBuffer;

	std::cout << inBuffer << std::endl;

	return 0;
}

