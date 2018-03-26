#include <iostream>
#include <fstream>
#include <cstdlib>
#include <sstream>
#include <vector>
#include <algorithm>
#define INFINITE_LOOP (-1)


class Robot {
    public:
        using StepCount = int;

    public:
        template <typename T>
        StepCount walk(std::vector<T> &map, int start_column);
};


int main(int argc, char *argv[]) {
    std::ifstream input = std::ifstream("example.in");
    if (input.fail()) {
        std::cerr << "open example.in error occurs" << std::endl;
        std::exit(1);
    }

    while (true) {
        int line, column, start_column;
        input >> line >> column >> start_column;

        if (line == 0 && column == 0 && start_column == 0) {
            break;
        }

        std::vector<std::vector<char>> map;
        for (int l = 0; l < line; ++l) {
            std::vector<char> map_line;
            for (int c = 0; c < column; ++c) {
                char ch;
                input >> ch;
                map_line.push_back(ch);
            }
            map.push_back(map_line);
        }

        auto robot = new Robot;
        std::cout << robot->walk(map, start_column) << std::endl;
    }

    return 0;
}


template <typename T>
Robot::StepCount Robot::walk(std::vector<T> &map, int start_column) {
    std::vector<std::string> path;
    const int map_width = map[0].size();
    const int map_height = map.size();
    int current_line = 0;
    int current_column = start_column - 1;

    std::stringstream ss;
    while (true) {
        // ss.clear() just clean fail flag
        ss.str("");

        if (
            current_column < 0 || current_column >= map_width
            || current_line < 0 || current_line >= map_height
        ) {
            return path.size();
        }

        ss << current_line << current_column;
        if (std::find(path.begin(), path.end(), ss.str()) != path.end()) {
            return INFINITE_LOOP;
        }

        switch (map[current_line][current_column]) {
            case 'N': current_line -= 1; break;
            case 'S': current_line += 1; break;
            case 'W': current_column -= 1; break;
            case 'E': current_column += 1; break;
        }

        path.push_back(ss.str());
    }
}
