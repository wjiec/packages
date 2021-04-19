#include <iostream>
#include "person.h"

extern "C" {
    #include "person_c_api.h"
}

static Person *p = nullptr;

void testPerson1() {
    p = new Person((char*)"hello from cgo", 18);
    std::cout << "C1: person.name" << " = " << p->getName() << std::endl;
    std::cout << "C1: person.age" << " = " << p->getAge() << std::endl;
    std::cout << "C1: person.ref" << " = " << p->getRef() << std::endl;
}

void testPerson2() {
    p->setName((char*)"hello from cgo again");
    p->setAge(21);

    std::cout << "C2: person.name" << " = " << p->getName() << std::endl;
    std::cout << "C2: person.age" << " = " << p->getAge() << std::endl;
    std::cout << "C2: person.ref" << " = " << p->getRef() << std::endl;
}

void testPerson3() {
    std::cout << "C3: person.name" << " = " << p->getName() << std::endl;
    std::cout << "C3: person.age" << " = " << p->getAge() << std::endl;
    std::cout << "C3: person.ref" << " = " << p->getRef() << std::endl;
}

void testPerson4() {
    delete p;
}

