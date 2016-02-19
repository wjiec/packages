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
    int client = 0;
    struct sockaddr_in servAddr;

    if ((server = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        fprintf(stderr, "Can't not open socket.\n"); exit(1);
    }

    memset(&servAddr, 0, sizeof(servAddr));
    servAddr.sin_family = AF_INET;
    servAddr.sin_port   = htons(9527);
    servAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    if (bind(server, (struct sockaddr*)&servAddr, sizeof(servAddr)) < 0) {
        fprintf(stderr, "Bind error"); exit(1);
    }
    if (listen(server, 10) < 0) {
        fprintf(stderr, "Listen error"); exit(1);
    }

    while (1) {
        char *buf = "Hello Socket.\n";
        struct sockaddr_in cliAddr;
        socklen_t len = sizeof(cliAddr);

        client = accept(server, (struct sockaddr*)&cliAddr, &len);
        fprintf(stderr, "Server: %s:%d Connected\n", inet_ntoa(cliAddr.sin_addr), ntohs(cliAddr.sin_port));
        write(client, buf, strlen(buf));
        close(client);
    }

    close(server);
    return 0;
}
