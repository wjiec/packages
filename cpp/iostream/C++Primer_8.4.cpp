#include <iostream>
#include <fstream>
#include <vector>

int main(void) {
	std::vector<std::string> buffer;
	std::ifstream in("Makefile");

	std::string buf;
	while (getline(in, buf)) {
		buffer.push_back(buf);
	}

	for (auto element : buffer) {
		std::cout << element << std::endl;
	}

	return 0;
}
