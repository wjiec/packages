#include <iostream>
#include <string>

using namespace std;

inline const string &shorterString(const string &s1, const string &s2);

int main(void) {
	cout << shorterString("Hello World!", "C/C++") << endl;

	return 0;
}

inline const string &shorterString(const string &s1, const string &s2) {
	return (s1.size() > s2.size()) ? s2 : s1;
}

