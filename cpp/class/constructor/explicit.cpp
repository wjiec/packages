#include <iostream>
#include <string>

class Apple {
	public:
		Apple(std::string s) : name(s) {}
	private:
		std::string name;
};

class Banana {
	public:
		explicit Banana(std::string s) : name(s) {} // explicit with a single parameter, disallowed implicit conversion
	private:
		std::string name;
};

void apple(Apple a) {}

void banana(Banana b) {}

int main(void) {
	apple(std::string("This is a apple instance"));
	
//	banana(std::string("This is a banana instance")); // error
	banana(static_cast<Banana>("This is a bananan"));
	banana(Banana("This is a banana"));

	return 0;
}

