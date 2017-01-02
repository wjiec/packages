#include <iostream>
#include <string>
#include <vector>
#include <initializer_list>
#include <memory>

using namespace std;

class StrBlob {
    
    private:
        shared_ptr<vector<string>> data;

    public:
        // definition
        typedef vector<string>::size_type size_type;

        // constructor
        StrBlob() : data(make_shared<vector<string>>()) { }
        StrBlob(initializer_list<string> sl) : data(make_shared<vector<string>>(sl)) {}

        // access
        string &front(void) const;
        string &back(void) const;

        // iterator
        vector<string>::iterator begin() const { return data->begin(); }
        vector<string>::iterator end() const { return data->end(); }

        // push and pop
        void push_back(const string s) { data->push_back(s); }
        void pop_back();

        // operator
        size_type size(void) const { return data->size(); }
        bool empty(void) const { return data->empty(); }

    private:
        void check(size_type index, const string &msg) const;
};

void StrBlob::check(size_type index, const string &msg) const {
    if (index > size()) {
        throw out_of_range(msg);
    }
}

string &StrBlob::front(void) const {
    check(0, "front on empty StrBlob");

    return data->front();
}

string &StrBlob::back(void) const {
    check(0, "back on empty StrBlob");

    return data->back();
}

void StrBlob::pop_back() {
    check(0, "pop_back on empty StrBlob");

    data->pop_back();
}


int main(void) {
    StrBlob sb1;

    {
        StrBlob sb2{ "Apple", "Pear", "Banana" };
        sb1 = sb2;
    }

    for (auto &el : sb1) {
        cout << el << " ";
    }
    cout << endl;

    StrBlob sb3 = sb1; // Sharing the same chunk of memory
    sb1.push_back("Orange");
    for (auto &el : sb3) {
        cout << el << " ";
    }
    cout << endl;

    return 0;
}
