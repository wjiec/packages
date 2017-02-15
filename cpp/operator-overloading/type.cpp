#include <iostream>

using namespace std;

class Student {
    public:
        Student(int s = 0) : score(s) {}

        operator bool() { return score >= 60; }
    private:
        int score;
};

int main(void) {
    Student s1(100);
    Student s2(59);

    cout << static_cast<bool>(s1) << endl;
    cout << static_cast<bool>(s2) << endl;

    return 0;
}
