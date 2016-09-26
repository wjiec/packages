#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <ctype.h>
#include <stdbool.h>

#ifdef _WIN32

    #include <WS2tcpip.h>
    #include <WinSock2.h>
    #include <windows.h>
    #pragma comment(lib, "ws2_32.lib")

#else

    #include <netinet/in.h>
    #include <arpa/inet.h>
    #include <unistd.h>
    #include <sys/socket.h>
    #include <pthread.h>

#endif

#include "httpd.h"

static void createThread(int fd, struct sockaddr_in *name);

#ifdef _WIN32
static DWORD clientHandle(void *params);
#else
static void clientHandle(void *params);
#endif

void bzero(void *dst, size_t size) {
    memset(dst, 0, size);
}

Dict *createDict(const char *key, const char *value) {
    Dict *dict = (Dict*)malloc(sizeof(Dict));

    dict->key   = (char*)malloc((strlen(key) + 1) * sizeof(char));
    bzero(dict->key, (strlen(key) + 1) * sizeof(char));

    dict->value = (char*)malloc((strlen(value) + 1) * sizeof(char));
    bzero(dict->value, (strlen(value) + 1) * sizeof(char));

    memcpy(dict->key, key, strlen(key));
    memcpy(dict->value, value, strlen(value));

    return dict;
}

HttpHeader *createHttpHeader(HttpStatus *status, char *contents) {
    HttpHeader *header = (HttpHeader*)malloc(sizeof(HttpHeader));

    bzero(header, sizeof(HttpHeader));
    header->status = status;

    size_t length = strlen(contents);
    size_t readCnt = 0;
    char *contentsEnd = contents + length;
    char buffer[512] = { 0 };
    HttpParams *params = header->params;
    do {
        readCnt = sscanf(contents, "%s", buffer);
        contents += readCnt;

        char *colon = strchr(buffer, ':');
        if (colon != NULL) {
            char *key = buffer;
            char *val = colon + 1;
            *colon = '\0';

            params->kv = createDict(key, val);
            params->next = NULL;
        } else {
            // header ending
            if (strcmp(buffer, "\r\n\r\n") == 0) {
                break;
            }
        }
    } while (contents <= contentsEnd);
    size_t size = 0;

    return header;
}


int startup(const int port) {
#ifdef _WIN32
    WSADATA winSocket;

    if (WSAStartup(MAKEWORD(2, 2), &winSocket) != 0) {
        fprintf(stderr, "[SHTTPD] Initialize windows socket failure.\n");
        exit(-1);
    }
#endif

    int httpd = (int)socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (httpd < 0) {
        fprintf(stderr, "[SHTTPD] Create shttpd error occurs.\n");
        exit(-1);
    }

    struct sockaddr_in serverName;
    bzero(&serverName, sizeof(serverName));
    serverName.sin_family = AF_INET;
    serverName.sin_port   = htons(port);
    inet_pton(AF_INET, INADDR_ANY, &serverName.sin_addr);

    char buffer[16] = { 0 };
    if (bind(httpd, (SOCKADDR*)&serverName, sizeof(serverName)) != 0) {
        fprintf(stderr, "[SHTTPD] Server bind socket(%s:%h) failure.\n",
                inet_ntop(AF_INET, &serverName.sin_addr, (PSTR)&buffer, 16),
                ntohs(serverName.sin_port));
        exit(-1);
    }

    printf("[SHTTPD] Server on %s:%d listening\n",
           inet_ntop(AF_INET, &serverName.sin_addr, (PSTR)&buffer, 16),
           ntohs(serverName.sin_port));
    listen(httpd, 9);

    return httpd;
}

void accpetClient(const int httpd) {
    int client = 0;
    char buffer[16] = { 0 };
    struct sockaddr_in remote;
    socklen_t len = sizeof(remote);

    client = (int)accept(httpd, (SOCKADDR*)&remote, &len);
    if (client < 0) {
        fprintf(stderr, "[SHTTPD] Accept client error occurs.\n");
        exit(-1);
    }
    printf("[SHTTPD] Client connected. socket(%s:%d)\n",
           inet_ntop(AF_INET, &remote.sin_addr, (PSTR)&buffer, 16),
           ntohs(remote.sin_port));
    createThread(client, &remote);
}

#ifdef _WIN32
static DWORD clientHandle(void *params) {
    ThreadParams *tuples = params;
    HttpHeader request = { NULL, NULL };
    char buffer[512] = { 0 };

    // request header
    while (readLine(tuples->fd, buffer, sizeof(buffer))) {

    }

    return 0;
}
#else
static void clientHandle(void *params) {

}
#endif

static void createThread(int fd, struct sockaddr_in *name) {
    ThreadParams params = { fd, name };
#ifdef _WIN32
    HANDLE handle = CreateThread(NULL, 0, clientHandle, &params, 0, NULL);
    WaitForSingleObject(handle, 0);
#else
    pthread_t thread;

    if (pthread_create(&thread, NULL, clientHandle, &params) != 0) {
        fprintf(stderr, "[SHTTPD] Create thread error occurs.\n");
        exit(-1);
    }
#endif
}

void closeHttpd(const int httpd) {
#ifdef _WIN32
    closesocket(httpd);
#else
    close(httpd);
#endif;
}

size_t readLine(int fd, char *buffer, size_t size) {
    return 0;
}
