#include <stdio.h>

#define CALL(fun) func_##fun()

void func_message();

int main(void) {
    CALL(message);
    return 0;
}

void func_message() {
    printf("Message is called.\n");
}
