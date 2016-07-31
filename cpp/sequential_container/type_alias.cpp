#include <iostream>
#include <vector>
using namespace std;

int main(void) {
	vector<int> apple(10, 111); // instance, object
	vector<int>::iterator i = apple.begin(); // iterator
	vector<int>::const_iterator ci = apple.cbegin(); // const iterator
	vector<int>::size_type st = apple.size(); // size type
	vector<int>::difference_type dt = apple.end() - apple.begin(); // = size
	vector<int>::value_type ut = *apple.begin(); // value type
	vector<int>::reference r = *apple.begin(); // reference, = value_type&
	vector<int>::const_reference cr = *apple.cbegin(); // const reference, = const value_type&

	return 0;
}
