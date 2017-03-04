#include <iostream>
#include <vector>
#include <initializer_list>
#include <string.h>

using namespace std;

template<typename T>
class Blob {
    public:
        Blob(initializer_list<T> list) : data(list) {}
        Blob(int count, const T &el) : data(count, el) {}
        ~Blob() = default;

    public:
        void print();

    private:
        vector<T> data;
};

int main(void) {
    Blob<int> ivals{ 0, 1, 2, 3, 4, 5, 6, 7 };
    Blob<string> svals(4, "ABC");

    ivals.print();
    svals.print();

    return 0;
}

template<typename T>
void Blob<T>::print() {
//       ^^^ here
    for (auto it = data.begin(); it != data.end(); ++it) {
        cout << *it << endl;
    }
}
