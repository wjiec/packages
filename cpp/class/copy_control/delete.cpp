#include <iostream>

class World {
    public:
        World() = default;
        ~World() = default;
        // ~World() = delete;

        World(const World &) = delete;
        World &operator=(const World &) = delete;
    private:
        int age;
};

int main(void) {
    World _1;
    // World _2(_1);
    // World _3 = _1;
    
    // World _4;
    // _4 = _1;

    return 0;
}
