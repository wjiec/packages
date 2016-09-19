#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

int main(void) {
    vector<string> words{ "apple", "pear", "banana", "lemon", "peach", "tomato"};

    stable_sort(words.begin(), words.end(), [] (const string &s1, const string &s2) { return s1.size() < s2.size(); });

    string::size_type count = 5;
    auto wc = find_if(words.begin(), words.end(), [count] (const string &s) { return s.size() > count; });

    for_each(wc, words.end(), [](const string &s) { cout << s << " "; });
    cout << endl;

    return 0;
}

