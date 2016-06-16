#include <iostream>

class Apple {
	public:
		Apple(int cnt, char bg) : count(cnt), background(bg) {}
		Apple(int cnt) : Apple(cnt, '*') {}
		Apple(char bg) : Apple(0, bg) {}
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

