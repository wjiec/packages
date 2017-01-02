#include <iostream>
#include <memory>

using namespace std;

void process(shared_ptr<int> p);

int main(void) {
    shared_ptr<int> p(new int(100));

    process(shared_ptr<int>(p));

    std::cout << "In Function main " << p.use_count() << std::endl;
    return 0;
}

void process(shared_ptr<int> p) {
    std::cout << "In Function process " << p.use_count() << std::endl;
}