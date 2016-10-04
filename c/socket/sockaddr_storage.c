#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int main(void) {
    union {
        struct sockaddr_storage address;
        char bytes[sizeof(struct sockaddr_storage)];
    } un;
   
    memset(&un, 0, sizeof(struct sockaddr_storage));
    un.address.ss_family = AF_INET;

    for (int offset = 0; offset < sizeof(struct sockaddr_storage); ++offset) {
        printf("%#.2X ", un.bytes[offset] & 0xff);
    }
    printf("\n");

    return 0;
}

