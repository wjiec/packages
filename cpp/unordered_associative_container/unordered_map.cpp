#include <iostream>
#include <string>
#include <unordered_map>

using namespace std;

int main(void) {
    unordered_map<string, string> um;

    um.insert({ "Hello", "World" });
    um.insert({ "NiHao", "ShiJie" });
    um.insert({ "Halo", "World" });
    um.insert({ "num_1", "Number_1" });

    for (auto &w : um) {
        cout << w.first << " " << w.second << endl;
    }

    return 0;
}