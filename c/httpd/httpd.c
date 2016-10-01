#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <ctype.h>
#include <stdbool.h>
#include <stdarg.h>

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

HttpHeader *createHttpHeader(HttpStatus *status, HttpParams *params, char *contents) {
    HttpHeader *header = (HttpHeader*)malloc(sizeof(HttpHeader));

    bzero(header, sizeof(HttpHeader));
    header->status = malloc(sizeof(HttpStatus));
    (header->status) = status;
    (header->params) = params;
    (header->contents) = contents;

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
        fprintf(stderr, "[SHTTPD] Server bind socket(%s:%d) failure.\n",
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

    HttpParams httpParams;
    readHttpParams(tuple->fd, &httpParams);
    printf("    [SHTTPD] Request Params:\n");
    for (HttpParams *entry = &httpParams; (entry != NULL) && (entry->kv != NULL); entry = entry->next) {
        printf("        %s -> %s\n", entry->kv->key, entry->kv->value);
    }

    printf("    [SHTTPD] Request Contents:\n\n");
    for (char contents[MAX_CONTENTS_LENGTH] = { 0 }; recv(tuple->fd, contents, MAX_CONTENTS_LENGTH, 0); ) {
        puts(contents);
    }
    printf("\n");

    HttpStatus status = HTTP_STATUS_200_OK;
    HttpParams responseParams;

    createHttpParams(&responseParams, "Connection: close", "Content-Type: text/html; charset = utf-8");

    char text[MAX_RESPONSE_LENGTH] = { 0 };
    HttpHeader *response = createHttpHeader(&status, &responseParams, "<h1>Hello World<h1>");
    makeResponse(response, text, MAX_RESPONSE_LENGTH);
    httpResponse(tuple->fd, text);

    char buffer[INET_ADDRSTRLEN] = { 0 };
    printf("[SHTTPD] socket(%d -> %s:%d) closed\n",
           tuple->fd,
           inet_ntop(AF_INET, &((tuple->name)->sin_addr), (PSTR)&buffer, INET_ADDRSTRLEN),
           ntohs((tuple->name)->sin_port));
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

bool readHttpParams(int fd, HttpParams *params) {
    bzero(params, sizeof(HttpParams));

    char buffer[MAX_BUFFER_LENGTH] = { 0 };
    for (HttpParams **entry = &params; readLine(fd, buffer, MAX_BUFFER_LENGTH, 0); entry = &((*entry)->next)) {
        char *key = buffer;
        char *val = strchr(buffer, ':');

        *(val) = '\0';
        while (*val == ' ' || *val == '\0') {
            val += 1;
        }
        char *endl = strchr(val, '\r');
        *endl = '\0';

        (*entry)->kv = createDict(key, val);

        char temp[MAX_BUFFER_LENGTH] = { 0 };
        if (readLine(fd, temp, MAX_BUFFER_LENGTH, true)) {
            (*entry)->next = (HttpParams*)malloc(sizeof(HttpParams));
            bzero((*entry)->next, sizeof(HttpParams));
        } else {
            break;
        }
    }

    return true;
}

size_t readLine(int fd, char *buffer, size_t size, bool peek) {
    long long length = 0;

    if (peek == false) {
        recv(fd, buffer, (int)size, MSG_PEEK);
        char *endl = strstr(buffer, "\r\n");

        if (endl == NULL) {
            return 0;
        } else {
            length = endl - buffer + 2; // \r\n -> 2 bytes
            bzero(buffer, size);
        }
    }

    size_t readCnt = (size_t)recv(fd, buffer, (length != 0) ? (int)length : (int)size, (peek) ? MSG_PEEK : 0);
    char *endl = strstr(buffer, "\r\n");

    if (endl == NULL || (strncmp(buffer, "\r\n", 2) == 0 && readCnt == 2)) {
        bzero(buffer, size);
        return 0;
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

bool createHttpParams(HttpParams *params, ...) {
    va_list args;
    va_start(args, params);

    char buffer[MAX_BUFFER_LENGTH] = { 0 };
    for (char *kv; kv = va_arg(args, char*); ) {
        strncpy(buffer, kv, strlen(kv) + 1);

        char *key = buffer;
        char *val = strchr(buffer, ':');

        *(val) = '\0';
        while (*val == ' ' || *val == '\0') {
            val += 1;
        }
        char *endl = strchr(val, '\r');

        params->kv = createDict(key, val);
        params->next = (HttpParams*)malloc(sizeof(HttpParams));
        bzero(params->next, sizeof(HttpParams));
        params = params->next;
    }
    free(params);
    return true;
}

bool makeResponse(const HttpHeader *header, char *buffer, size_t size) {

}

bool httpResponse(int fd, char *response) {

}
