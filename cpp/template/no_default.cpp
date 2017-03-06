#include <iostream>
#include <vector>
using namespace std;

class NoDefault {
    public:
        NoDefault(int n) : data(n) {}

    private:
        int data;
};

// template vector<int>
template class vector<int>;
//       ^^^^^ here

// error
// candidate expects 1 argument, 0 provided
// template class vector<NoDefault>;

int main(void) {

    return 0;
}

