#include <iostream>
#include <forward_list>

int main(void) {
	std::forward_list<int> apple = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	std::cout << "*(++apple.before_begin()) = " << *(++apple.before_begin()) << std::endl;
	std::cout << "*(apple.insert_after(apple.begin(), 0)) = " << *(apple.insert_after(apple.begin(), 0)) << std::endl;
	std::cout << "*(apple.erase_after(apple.begin())) = " << *(apple.erase_after(apple.begin())) << std::endl;

	return 0;
}
