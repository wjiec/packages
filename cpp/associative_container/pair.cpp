#include <iostream>
#include <utility>
#include <vector>
#include <string>

using namespace std;

pair<string, int> process(vector<string> &v);

int main(void) {
    pair<string, string> author("Hello", "World");
    pair<string, string> words;

    words.first = "World";
    words.second = "Hello";

    cout << author.first << " " << author.second << endl;
    cout << words.first << " " << words.second << endl;

    auto fruit = make_pair("C++", "Python");
    cout << fruit.first << " " << fruit.second << endl;

    vector<string> v{"Hello"};
    auto p1 = process(v);
    cout << p1.first << " " << p1.second << endl;

    v.push_back("World");
    auto p2 = process(v);
    cout << p2.first << " " << p2.second << endl;

    return 0;
}

pair<string, int> process(vector<string> &v) {
    if (v.size() % 2) {
        return { v.front(), v.size() }; // construct
    } else {
        return pair<string, int>();
    }
}