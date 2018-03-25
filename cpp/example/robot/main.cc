#include <iostream>
#include <cstdlib>
#include <fstream>
#include <vector>
#include <sstream>
#define INFINITE_LOOP -1


class Robot {
    public:
        using STEP_COUNT = int;
};


int main() {
    auto input = std::ifstream("example.in", std::ios::in);
    auto robot = new Robot();

    if (input.fail()) {
        std::cout << "read example.in error" << std::endl;
        input.close();
        delete robot;
        std::exit(1);
    }

    while (true) {
        int line, column, start_column;
        input >> line >> column >> start_column;

        if (line == 0 && column == 0 && start_column == 0) {
            break;
        }

        auto map = new char[line * column];
        for (int i = 0; i < line; ++i) {
            for (int j = 0; j < column; ++j) {
                input >> *(map + i * column + j);
            }
        }

        std::cout << map << std::endl;
    }

    return 0;
}
