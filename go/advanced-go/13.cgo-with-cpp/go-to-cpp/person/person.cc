#include "person.h"

extern "C" {
    #include "person_c_api.h"
}

Person::Person(char *name, int age) {
    ref = cNewPerson(name, age);
}

Person::~Person() {
    cDeletePerson(ref);
}

const char *Person::getName() {
    return cPersonGetName(ref);
}

void Person::setName(char *name) {
    cPersonSetName(ref, name);
}

const int Person::getAge() {
    return cPersonGetAge(ref);
}

void Person::setAge(int age) {
    cPersonSetAge(ref, age);
}
