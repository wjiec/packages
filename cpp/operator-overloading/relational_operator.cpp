#include <iostream>
#include <string>

using namespace std;

class Student {
    friend bool operator==(const Student &left, const Student &right);
    friend bool operator!=(const Student &left, const Student &right);

    public:
        Student() = default;
        Student(string s = string(), int n = 0) : name(s), age(n) {}

    private:
        string name;
        int age;
};

bool operator==(const Student &left, const Student &right);
bool operator!=(const Student &left, const Student &right);

int main(void) {
    Student s1("Jack", 12);
    Student s2("Jack", 13);
    Student s3(s1);
    Student s4("John", 12);
    Student s5("John", 11);
    Student s6("John", 12);
    Student s7(s6);

    cout << (s1 == s2) << endl;
    cout << (s1 == s3) << endl;
    cout << (s3 == s4) << endl;
    cout << (s4 == s5) << endl;
    cout << (s4 == s6) << endl;
    cout << (s4 == s7) << endl;
    cout << (s6 == s7) << endl;

    cout << endl;

    cout << (s1 != s2) << endl;
    cout << (s1 != s3) << endl;
    cout << (s3 != s4) << endl;
    cout << (s4 != s5) << endl;
    cout << (s4 != s6) << endl;
    cout << (s4 != s7) << endl;
    cout << (s6 != s7) << endl;

    return 0;
}

bool operator==(const Student &left, const Student &right) {
    return (left.name == right.name) && (left.age == right.age);
}

bool operator!=(const Student &left, const Student &right) {
    return !(operator==(left, right));
}
