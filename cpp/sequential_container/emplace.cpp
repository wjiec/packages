#include <iostream>
#include <string>
#include <deque>
#include <list>

class Apple {
	public:
		Apple() = default;
		Apple(const int count, std::string banana) : pear(count), name(banana) {}
		Apple(const int count) : Apple(count, std::string("")) {}
	private:
		int pear;
		std::string name;
	public:
		friend void print(const Apple apple) {
			std::cout << apple.pear << " " << apple.name << std::endl;
		}
};
void print(const Apple apple);

int main(void) {
	std::list<Apple> apple1;
	std::deque<Apple> apple2;

	apple1.emplace_back(1);
	apple1.emplace_back(2, "apple");

	apple2.emplace_front(3);
	apple2.emplace_front(4, "banana");

	apple1.emplace(apple1.begin(), 1, "pear");
	apple2.emplace(apple2.end(), 9, "tomato");

	std::cout << "apple1:" << std::endl;
	for (auto element : apple1) {
		print(element);
	}

	std::cout << "apple2:" << std::endl;
	for (auto element : apple2) {
		print(element);
	}

	return 0;
}

