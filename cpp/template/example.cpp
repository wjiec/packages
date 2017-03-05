#include <iostream>
#include <vector>

using namespace std;

template<typename T>
void container_dump(const T &container) {
    for (typename T::size_type index = 0; index < container.size(); ++index) {
        cout << container[index] << endl;
    }
}

int main(void) {
    vector<int> container{ 0, 1, 2, 3, 4, 5, 6, 7 };

    container_dump<decltype(container)>(container);

    return 0;
}
