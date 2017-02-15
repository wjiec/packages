#include <iostream>
#include <string>

using namespace std;

class Person {
    public:
        Person(string s = string(), int n = 0) : name(s), age(n) {}
        virtual ~Person() = default;

        string get_name(void) const { return string(name); }
        virtual void get_info(void) const { cout << get_name() << " " << age << endl; };
    private:
        string name;
    protected:
        int age;
};

class Student : public Person {
    public:
        Student(string s = string(), int age = 0, int n = 0) : Person(s, age), score(n) {}

        void get_info(void) const override { cout << get_name() << " " << age << " " << score << endl; }
    private:
        int score;
};

int main(void) {
    Person p1("Jack", 10);
    Student s1("John", 10, 60);

    p1.get_info();
    s1.get_info();

    Person &s1_copy = s1;
    s1_copy.get_info();

    return 0;
}
