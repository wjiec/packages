#include <iostream>
#include <sstream>

int main(void) {
	std::ostringstream out;

	out << "Hello World!" << std::endl;

	std::string buffer(out.str());

	std::cout << buffer << std::endl << out.str() << std::endl;

	return 0;
}
