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

HttpHeader *createHttpHeader(HttpStatus status, char *contents) {
    HttpHeader *header = (HttpHeader*)malloc(sizeof(HttpHeader));

    bzero(header, sizeof(HttpHeader));
    header->status = malloc(sizeof(HttpStatus));
    *(header->status) = status;

    HttpParams **params = (&header->params);
    size_t length = strlen(contents);
    char *contentsEnd = contents + length;
    char buffer[128] = { 0 };

    do {
        char *line = strstr(contents, "\r\n");
        strncpy(buffer, contents, line - contents);

        char *colon = strchr(buffer, ':');
        char *key = buffer;
        char *val = *(colon + 1) == ' ' ? (colon + 2) : (colon + 1);
        *colon = '\0';

        *params = (HttpParams*)malloc(sizeof(HttpParams));
        (*params)->kv = createDict(key, val);
        (*params)->next = NULL;
        params = &((*params)->next);

        contents = line + 2;
    } while (contents <= contentsEnd && strcmp(contents, "\r\n") != 0);

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
    printf("[SHTTPD] Client connected. socket(%d -> %s:%d)\n",
           client,
           inet_ntop(AF_INET, &remote.sin_addr, (PSTR)&buffer, 16),
           ntohs(remote.sin_port));
    createThread(client, &remote);
}

static void createThread(int fd, struct sockaddr_in *name) {
    ThreadArgs *params = NULL;

    params = (ThreadArgs*)malloc(sizeof(ThreadArgs));
    params->fd = fd;
    params->name = name;

#ifdef _WIN32
    HANDLE handle = CreateThread(NULL, 0, clientHandle, params, 0, NULL);
    WaitForSingleObject(handle, 0);
#else
    pthread_t thread;

    if (pthread_create(&thread, NULL, clientHandle, &params) != 0) {
        fprintf(stderr, "[SHTTPD] Create thread error occurs.\n");
        exit(-1);
    }
#endif
}

#ifdef _WIN32
static DWORD clientHandle(void *params) {
    ThreadArgs *tuple = (ThreadArgs*)params;

    // request header
    RequestHeader requestHeader;
    if (readHttpRequestHeader(tuple->fd, &requestHeader) == false) {
        return -1;
    }
    printf("    [SHTTPD] Request Method[%s], Resource[%s], Protocol[%s]\n",
        requestHeader.method == HTTP_METHOD_GET ? "GET" : "POST",
        requestHeader.resource,
        requestHeader.version == HTTP_VERSION_1_0 ? "HTTP/1.0" : "HTTP/1.1"
    );

//    HttpParams httpParams;
//    readHttpParams(fd, &httpParams);
//    printf("[SHTTPD] Request Params:\n");
//    for (HttpParams *entry = httpParams; entry != NULL; entry = entry->next) {
//        printf("%s -> %s\n", entry->kv->key, entry->kv->value);
//    }

    closeHttpd(tuple->fd);

    return 0;
}
#else
static void clientHandle(void *params) {

}
#endif

void closeHttpd(const int httpd) {
#ifdef _WIN32
    closesocket(httpd);
#else
    close(httpd);
#endif;
}

size_t readHttpParams(int fd, HttpParams *params) {

    return 0;
}

size_t readLine(int fd, char *buffer, size_t size, bool peek) {
    int length = 0;

    if (peek == false) {
        recv(fd, buffer, (int)size, MSG_PEEK);
        char *endl = strstr(buffer, "\r\n");

        if (endl == NULL) {
            return -1;
        } else {
            length = endl - buffer + 2; // \r\n -> 2 bytes
            bzero(buffer, size);
        }
    }

    size_t readCnt = (size_t)recv(fd, buffer, (length != 0) ? length : size, (peek) ? MSG_PEEK : 0);
    char *endl = strstr(buffer, "\r\n");

    if (endl == NULL) {
        bzero(buffer, size);
        return -1;
    }

    return readCnt;
}


bool readHttpRequestHeader(int fd, RequestHeader* requestHeader) {
    bzero(requestHeader, sizeof(RequestHeader));

    char buffer[256] = { 0 };
    readLine(fd, buffer, sizeof(buffer), false);

    char *method = buffer;
    char *resource = strchr(buffer, ' ') + 1;
    char *version = strrchr(buffer, ' ') + 1;
    char *endl = strchr(buffer, '\r');

    if (!method || !resource || !version || !endl) {
        fprintf(stderr, "[SHTTPD] Bad Request from socket(fd = %d)\n", fd);
        closeHttpd(fd);
        return false;
    }

    *(resource - 1) = '\0';
    *(version - 1) = '\0';
    *endl = '\0';

    // http method
    if (strcmp(method, "GET") == 0 || strcmp(method, "get") == 0) {
        requestHeader->method = HTTP_METHOD_GET;
    } else if (strcmp(method, "POST") == 0 || strcmp(method, "post") == 0) {
        requestHeader->method = HTTP_METHOD_POST;
    }

    // http version
    if (strcmp(version, "HTTP/1.0") == 0) {
        requestHeader->version = HTTP_VERSION_1_0;
    } else if (strcmp(version, "HTTP/1.1") == 0) {
        requestHeader->version = HTTP_VERSION_1_1;
    }

    //http resource
    requestHeader->resource = (char*)malloc(sizeof(char) * strlen(resource) + 1);
    bzero(requestHeader->resource, sizeof(char) * strlen(resource) + 1);
    strncpy(requestHeader->resource, resource, strlen(resource));

    return true;
}

size_t makeResponseHeader(const HttpHeader *header, char *buffer, size_t size) {
    return 0;
}
