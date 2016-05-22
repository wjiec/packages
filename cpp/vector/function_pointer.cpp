#include <iostream>
#include <vector>

using namespace std;

using fp_t1 = int (*)(int, int);
using f_t1  = int (int, int);

int func(int a, int b) {
	return 0;
}

typedef decltype(func) * fp_t2; // decltype return 'function' type, not 'function pointer'

int main(void) {
	vector<fp_t1> fps1;
	vector<f_t1*> fps2;
	vector<fp_t2> fps3;

	return 0;
}

