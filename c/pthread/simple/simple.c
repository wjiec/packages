#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

void *tidFunc(void *args);

int main(void) {
    pthread_t tid;

    if (pthread_create(&tid, NULL, tidFunc, NULL) != 0) {
        fprintf(stderr, "Create thread error.\n");
        return 1;
    }

    printf("main: thread\n\tpid: %u, tid: %u(%#0x)\n", (unsigned int)getpid(), (unsigned int)pthread_self(), (unsigned int)pthread_self());
    
    sleep(1);
    return 0;
}

void *tidFunc(void *args) {
    printf("thread: thread\n\tpid: %u, tid: %u(%#0x)\n", (unsigned int)getpid(), (unsigned int)pthread_self(), (unsigned int)pthread_self());
    return NULL;
}

