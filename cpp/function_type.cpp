#include <iostream>

int func(int apple);

int (*pf)(int) = func;

typedef int (*func_p)(int); // pointer
typedef int func_t(int); // function

typedef decltype(func) func_t1; // function
typedef decltype(func) *func_p1; // pointer

int main(void) {
	func_p f1 = func;
	func_t *f2 = func;
	func_t1 *f3 = func;
	func_p1 f4 = func;

	std::cout << f1(1) << ' '
			  << f2(2) << ' '
			  << f3(3) << ' '
			  << f4(4) << std::endl;

	return 0;
}

int func(int apple) {
	return apple * 9;
}
