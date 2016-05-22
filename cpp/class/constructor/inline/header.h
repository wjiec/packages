#ifndef _HEADER_H_
#define _HEADER_H_

#include <iostream>

class Test {
	int appleCnt;
	char background;

	public:
		Test() = default;
		Test(int apple) : appleCnt(apple) {}
		Test(int apple, char bg) : appleCnt(apple), background(bg) {}

	std::ostream &printAll(std::ostream &o) const {
		o << appleCnt << " " << background;
		
		return o;
	}
};

#endif // HEADER.H
