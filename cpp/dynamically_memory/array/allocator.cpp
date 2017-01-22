#include <iostream>
#include <string>
#include <memory>

using namespace std;

int main(void) {
    allocator<string> alloc = allocator<string>();
    string *mem = alloc.allocate(10);
    string *index = mem;

    alloc.construct(index++); // empty string
    alloc.construct(index++, 10, 'A'); // AAAAAAAAAA
    alloc.construct(index++, "ABCDEFG"); // ABCDEFG
    alloc.construct(index++, string("Hello World")); // Hello World

    for (auto current = mem; current != index; ++current) {
        cout << *current << endl;
    }

    while (index != mem) {
        alloc.destroy(--index);
    }
    alloc.deallocate(mem, 10);

    return 0;
}
