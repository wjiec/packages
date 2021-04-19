#ifndef __PERSON_INCLUDED__
#define __PERSON_INCLUDED__

extern "C" {
    #include "person_c_api.h"
}


class Person {

    private:
        RefAddr ref;

    public:
        Person(char *name, int age);
        ~Person();

    public:
        const char *getName();
        void setName(char *name);

        const int getAge();
        void setAge(int age);

    public:
        RefAddr getRef() { return ref; }

};


#endif // __PERSON_INCLUDED__