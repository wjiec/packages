#include <iostream>
#include <string>
#include <vector>

class Apple {
	public:
		Apple() = default; // default function
		Apple(int number, char ch) : array(number, 123), name(number, 'A') {}
	private:
		std::vector<int> array;
		std::string name;

	public:
		void variableDump();
};

inline void Apple::variableDump() {
	for (auto ch : name) {
		std::cout << ch << " ";
	}
	std::cout << std::endl;

	for (auto n : array) {
		std::cout << n << "-";
	}
	std::cout << std::endl;
}

int main(void) {
	Apple a1(12, 'A');

	a1.variableDump();

	return 0;
}

