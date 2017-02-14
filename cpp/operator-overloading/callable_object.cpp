#include <iostream>
#include <string>
#include <map>

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

    cout << function_pointer(1, 2) << endl;
    cout << lambda_object(1, 2) << endl;
    cout << function_objector(1, 2) << endl;

    map<string, int(*)(int, int)> function_table;
    function_table.insert(pair<string, int(*)(int, int)>("+", function_pointer));
    // error
    // function_table.insert(pair<string, int(*)(int, int)>("*", function_objector));

    cout << function_table["+"](10, 20) << endl;

    return 0;
}

int add(int a, int b) {
    return a + b;
}
