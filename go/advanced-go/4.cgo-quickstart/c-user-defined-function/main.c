#include <stdio.h>

static void sayHello(const char *str) {
    puts(str);
}


int main(int argc, char *argv[]) {
    sayHello("hello cgo");
}

