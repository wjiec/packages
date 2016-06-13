#include <iostream>

class Base;

class Child {
	Base *parent;
};

class Base {
	Child child;
};

int main(void) {
	Base base;
	Child child;

	return 0;
}

