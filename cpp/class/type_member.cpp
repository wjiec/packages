#include <iostream>
#include <string>

class Apple {
	public:
		typedef std::string::size_type position;
		using count = unsigned int;
	private:
		position apple;
};

int main(void) {
	Apple::position abc = 123;
	Apple::count def = 456;

	std::cout << abc << std::endl;
	std::cout << def << std::endl;

	return 0;
}

