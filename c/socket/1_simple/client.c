#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>

int main(void) {
    int server = 0;
    char readBuf[64] = { 0 };
    struct sockaddr_in servAddr;

    if ((server = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        fprintf(stderr, "Can't not open socket.\n"); exit(1);
    }

    memset(&servAddr, 0, sizeof(servAddr));
    servAddr.sin_family = AF_INET;
    servAddr.sin_port   = htons(9527);
    if (inet_pton(AF_INET, "127.0.0.1", &servAddr.sin_addr) <= 0) {
        fprintf(stderr, "inet_pton error for %s\n", "127.0.0.1"); exit(1);
    }

    if (connect(server, (struct sockaddr*)&servAddr, sizeof(servAddr)) < 0) {
        fprintf(stderr, "Connect error\n");
    }

    for (int cnt = 0; (cnt = read(server, readBuf, 63)) > 0;) {
        readBuf[cnt] = 0;
        puts(readBuf);
    }

    close(server);
    return 0;
}
