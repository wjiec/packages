#include <iostream>

class Apple {
    private:
		static int banana;
	public:
		static int cherry;
	public:
		static int apple(int a) { banana = a; std::cout << "In Class Scope: " << a << std::endl; return a; }
};
int Apple::banana = 0;
int Apple::cherry = 0;

int apple(int a) {
	std::cout << "Global Scope: " << a << std::endl;

	return a;
}

int main(void) {
	apple(1);
	Apple::cherry = Apple::apple(5);
//	Apple::cherry = apple(5); // ?

	return 0;
}

