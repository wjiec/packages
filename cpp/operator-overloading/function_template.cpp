#include <iostream>
#include <functional>
#include <map>
#include <string>

using namespace std;

int add(int a, int b);

class FunctionObject {
    public:
        operator()(int a, int b) { return a * b; }
};

int main(void) {
    auto function_pointer = add;
    auto lambda_object = [] (int a, int b) -> int { return a - b; };
    auto function_objector = FunctionObject();

    map<string, function<int(int, int)>> function_table;

    function_table.insert(pair<string, function<int(int, int)>>("+", function_pointer));
    function_table.insert(pair<string, function<int(int, int)>>("-", lambda_object));
    function_table.insert(pair<string, function<int(int, int)>>("*", function_objector));

    // is ok
    cout << function_table["+"](1, 2) << endl;
    cout << function_table["-"](1, 2) << endl;
    cout << function_table["*"](1, 2) << endl;

    return 0;
}

int add(int a, int b) {
    return a + b;
}