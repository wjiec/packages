#include <iostream>
#include <string>
#include <vector>
#include <initializer_list>

using namespace std;

class StringVec {
    public:
        StringVec() : data(vector<string>()) {}
        StringVec(initializer_list<string> sl) : data(vector<string>(sl)) {}

        string &operator[](const int index);
        const string &operator[](const int index) const;

    private:
        vector<string> data;
};

int main(void) {
    StringVec s1;
    StringVec s2{ "Hello", "World", "C++", "Programmer" };

    // cout << s1[0] << endl;

    for (auto i = 0; i < 4; ++i) {
        cout << s2[i] << " ";
    }
    cout << endl;

    return 0;
}

string &StringVec::operator[](const int index) {
    if (index < 0 || index >= (int)data.size()) {
        throw out_of_range("the index out of range");
    }
    return data[index];
}

const string &StringVec::operator[](const int index) const {
    if (index < 0 || index >= (int)data.size()) {
        throw out_of_range("the index out of range");
    }
    return data[index];
}
