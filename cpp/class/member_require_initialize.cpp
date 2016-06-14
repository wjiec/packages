#include <iostream>

class Apple {
	public:
		Apple(int count) : count(count), cCount(count), rCount(count) {}
	private:
		int count;
		const int cCount;
		int &rCount;
};

/**
class BadApple {
	public:
		// error: uninitialized member Apple::cCount with const type ...
		BadApple(int cnt) {
			count = cnt;
			cCount = cnt;
			rCount = count;
		}
	private:
		int count;
		const int cCount;
		int &rCount;
};
*/
int main(void) {
	Apple apple(123);

	return 0;
}
