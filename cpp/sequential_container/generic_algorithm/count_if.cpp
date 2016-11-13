#include <iostream>
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

int main(void) {
    vector<string> v{ "Hello", "World", "iostream", "algorithm", "string", "math" };

    cout << count_if(v.cbegin(), v.cend(), [] (string s) -> bool {
        if (s.length() >= 6)
            return true;
        else
            return false; 
    }) << endl;

    return 0;
}