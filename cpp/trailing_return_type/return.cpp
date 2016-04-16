#include <iostream>

using namespace std;

auto retArrayPtr() -> int(*)[5];

int main(void) {
	decltype(retArrayPtr()) a = retArrayPtr();

	for (auto item : *a) {
		cout << item << endl;
	}

	return 0;
}

auto retArrayPtr() -> int(*)[5] {
	static int arr[5] = { 1, 2, 3, 4, 5 };

	return &arr;
}

