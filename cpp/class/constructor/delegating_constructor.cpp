#include <iostream>

class Apple {
	public:
		Apple(int cnt, char bg) : count(cnt), background(bg) { std::cout << "2 argvments" << std::endl; }
		Apple(int cnt) : Apple(cnt, '*') { std::cout << "1 argument for int" << std::endl; }
		Apple(char bg) : Apple(0, bg) { std::cout << "1 argument for char" << std::endl; }
	private:
		int count;
		char background;
	public:
		std::ostream &print(std::ostream &o) {
			return o << count << ' ' << background << ' ';
		}
};

int main(void) {
	Apple a1(1, 'A');
	Apple a2(2);
	Apple a3('B');

	a1.print(std::cout) << std::endl;
	a2.print(std::cout) << std::endl;
	a3.print(std::cout) << std::endl;

	return 0;
}

