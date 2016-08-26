#include <iostream>
#include <string>
#include <vector>
#include <list>
#include <numeric>

using namespace std;

int main(void) {
    std::vector<int> apple{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
	std::list<std::string> pear{ "This", " ", "is", " ", "a", " ", "string" };

	std::cout << accumulate(apple.cbegin(), apple.cend(), 0) << std::endl;
	std::cout << accumulate(pear.cbegin(), pear.cend(), string("")) << std::endl;

	return 0;
}
