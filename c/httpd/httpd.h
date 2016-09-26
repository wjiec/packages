#ifndef HTTPD_H
#define HTTPD_H

#ifdef _WIN32
#define _CRT_SECURE_NO_WARNINGS
#endif

#define SERVER_NAME "Server: shttpd/1.1\r\n"

typedef struct key_value_t {
    char *key;
    char *value;
} Dict;

typedef struct http_status_t {
    short code;
    char *description;
} HttpStatus;

typedef struct http_params_t {
    Dict *kv;
    struct http_params_t *next;
} HttpParams;

typedef struct http_header_t {
    HttpStatus *status;
    HttpParams *params;
} HttpHeader;

typedef struct thread_params_t {
    int fd;
    struct sockaddr_in *name;
} ThreadParams;

// Dict Operator, FIFO Queue
Dict *createDict(const char *key, const char *value);
void pushDict(HttpHeader *header, const char *key, const char *value);
void popDict(HttpHeader *header);
void dictDestroy(Dict * dict);

// Header Operator
HttpHeader *createHttpHeader(HttpStatus *status, char *contents);

// shttpd functions
int startup(const int port);
void accpetClient(const int httpd);
void closeHttpd(const int httpd);

// http status
#define STATUS_200_OK           { 200, "OK" };
#define STATUS_400_BAD_REQUEST  { 400, "Bad Request" }
#define STATUS_404_NOT_FOUND    { 404, "Not Found" }
#define STATUS_500_SERVER_ERROR { 500, "Internet Server Error" }

#define HTTP_RESPONSE_FORMAT "HTTP/1.1 %d %s\r\n%s"

// httpd response
void ok(const int fd, HttpParams *header); // 200 OK
void notFound(const int fd, HttpParams *header); // 404 Not Found
void badRequest(const int fd, HttpParams *header); //400 Bad Request
void serverError(const int fd, HttpParams *header); // 500 Server Error


// util functions
void bzero(void *dst, size_t size);
size_t readLine(int fd, char *buffer, size_t size);


#endif // HTTPD_H
