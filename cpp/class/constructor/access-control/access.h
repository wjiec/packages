#ifndef _ACCESS_H_
#define _ACCESS_H_

#include <iostream>
#include <string>
using namespace std;

class Test {
	int apple = 0; // private
	
	public:
		Test() = default;
		Test(int appleCount, char bananaCount)
			: apple(appleCount), banana(bananaCount) {}
		ostream &printIn(ostream &o) const {
			o << apple << " " << banana << " ";

			return o;
		}

	public:
		string description = "Hello World!"; // public, dont use () to initialize, eg. string d("test");

	private:
		char banana = 0; // private
};

#endif // ACCESS.H
