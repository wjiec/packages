#include <iostream>
#include <string>

using namespace std;

class Apple {
	friend Apple add(const Apple &product1, const Apple &product2);

	public:
		Apple() = default;
		Apple(string name, int count) : appleName(name), appleCnt(count), revenue(count * 2.0) {}
		void print();
	private:
		string appleName;
		int appleCnt;
		double revenue;
};

int main(void) {
	Apple a1("A1", 100);
	Apple a2("A2", 200);
	Apple a3("A1", 300);
	Apple a4 = add(a1, a2);
	Apple a5 = add(a1, a3);

	a1.print();
	a2.print();
	a3.print();
	a4.print();
	a5.print();

	return 0;
}

void Apple::print() {
	cout << appleName << ": " << appleCnt << " -> " << revenue << endl;
}

Apple add(const Apple &product1, const Apple &product2) {
	if (product1.appleName == product2.appleName) {
		Apple tmp(product1.appleName, product1.appleCnt + product2.appleCnt);
		return tmp;
	} else {
		Apple tmp;
		return tmp;
	}
}

