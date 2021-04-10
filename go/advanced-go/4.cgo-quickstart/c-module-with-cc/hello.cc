#include <iostream>

extern "C" {
    #include "hello.h"
}

extern void sayHello(const char *str) {
    std::cout << "hello " << str << std::endl;
}
