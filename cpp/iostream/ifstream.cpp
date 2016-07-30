#include <iostream>
#include <string>
#include <fstream>

int main(void) {
	std::ifstream in("Makefile");
	std::string buffer;

	while (getline(in, buffer)) {
		std::cout << buffer << std::endl;
	}

	return 0;
}
