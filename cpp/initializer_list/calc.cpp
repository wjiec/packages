#include <iostream>
#include <initializer_list>

using namespace std;

int add(initializer_list<int> params);

int main(void) {
	cout << add({ 1, 2, 3, 4, 5, 6, 7, 8 }) << endl;

	return 0;
}

int add(initializer_list<int> params) {
	int sum = 0;
	for (auto item = params.begin(); item != params.end(); ++item) {
		sum += *item;
	}
	return sum;
}
