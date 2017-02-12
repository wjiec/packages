#include <iostream>
#include <string>

using namespace std;

class Student {
    friend ostream &operator<<(ostream &os, const Student &stu);
    friend istream &operator>>(istream &in, Student &stu);

    public:
        Student(istream &in) { operator>>(in, *this); }
        Student(string s = string(), int n = 0) : name(s), age(n) {}

    private:
        string name;
        int age;
};

ostream &operator<<(ostream &os, const Student &stu);
istream &operator>>(istream &in, Student &stu);

int main(void) {
    Student s1("Jack", 10);
    cout << s1 << endl;

    Student s2(cin);
    cout << s2 << endl;

    Student s3;
    cin >> s3;
    cout << s3 << endl;

    return 0;
}

ostream &operator<<(ostream &os, const Student &stu) {
    os << stu.name << " " << stu.age;

    return os;
}

istream &operator>>(istream &in, Student &stu) {
    in >> stu.name >> stu.age;

    if (in) {
        return in;
    } else {
        stu = std::move(Student());
    }

    return in;
}
