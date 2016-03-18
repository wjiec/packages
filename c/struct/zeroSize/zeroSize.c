#include <stdio.h>

typedef struct _node {
    int apple[0];
} Node;

typedef struct _avaliable {
    int apple[1];
} Avaliable;

int main(void) {
    printf("sizeof Node =  %lu\n", sizeof(Node));
    printf("sizeof Avaliable = %lu\n", sizeof(Avaliable));

    return 0;
}

