#include <iostream>
#include <fstream>

int main(void) {
	std::ofstream out("text.txt", std::ios::ate); // create file or trunc file

	out << "Hello" << std::ends << "World!" << std::endl;

	out.close();
	out.open("text.txt", std::ios::app); // append to end

	out << "This is a append mode." << std::endl;

	out.close();
	return 0;
}
