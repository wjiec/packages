#ifndef HTTPD_H
#define HTTPD_H

#ifdef _WIN32
#define _CRT_SECURE_NO_WARNINGS 1
#endif

#define SERVER_NAME "Server: shttpd/1.0\r\n"

// Type: Dict
typedef struct key_value_t {
    char *key;
    char *value;
} Dict;

// Type: Http Status
typedef struct http_status_t {
    short code;
    char *description;
} HttpStatus;

// Type: Http Params
typedef struct http_params_t {
    Dict *kv;
    struct http_params_t *next;
} HttpParams;

// Type: Http Header
typedef struct http_header_t {
    HttpStatus *status;
    HttpParams *params;
} HttpHeader;

// Type: Thread Args
typedef struct thread_args_t {
    int fd;
    struct sockaddr_in *name;
} ThreadArgs;

// Type: Request Header
typedef struct request_header_t {
    enum {
        HTTP_METHOD_GET    = 0x1,
        HTTP_METHOD_POST   = 0x2,
        HTTP_METHOD_HEAD   = 0x4,
        HTTP_METHOD_UPDATE = 0x8,
        HTTP_METHOD_DELETE = 0x10
    } method;
    char *resource;
    enum {
        HTTP_1_0 = 0x1,
        HTTP_1_1 = 0x2
    } version;
} RequestHeader;

// Dict Operator, FIFO Queue
Dict *createDict(const char *key, const char *value);
void pushDict(HttpHeader *header, const char *key, const char *value);
void popDict(HttpHeader *header);
void dictDestroy(Dict * dict);

// Header Operator
HttpHeader *createHttpHeader(HttpStatus status, char *contents);

// shttpd functions
int startup(const int port);
void accpetClient(const int httpd);
void closeHttpd(const int httpd);

// http protocol
#define HTTP_VERSION_1_0 0x1
#define HTTP_VERSION_1_1 0x2

// http method
#define HTTP_METHOD_GET    0x1
#define HTTP_METHOD_POST   0x2
//#define HTTP_METHOD_HEAD   0x4
//#define HTTP_METHOD_UPDATE 0x8
//#define HTTP_METHOD_DELETE 0x10



// http status
#define HTTP_STATUS_WAIT_RESPONSE    { 000, "Wait" };
#define HTTP_STATUS_200_OK           { 200, "OK" };
#define HTTP_STATUS_400_BAD_REQUEST  { 400, "Bad Request" }
#define HTTP_STATUS_404_NOT_FOUND    { 404, "Not Found" }
#define HTTP_STATUS_500_SERVER_ERROR { 500, "Internet Server Error" }

#define HTTP_RESPONSE_FORMAT "HTTP/1.1 %d %s\r\n%s\r\n\r\n%s"

// httpd response
void ok(const int fd, HttpParams *header); // 200 OK
void notFound(const int fd, HttpParams *header); // 404 Not Found
void badRequest(const int fd, HttpParams *header); //400 Bad Request
void serverError(const int fd, HttpParams *header); // 500 Server Error

// util functions
#define READ_LINE_MAX_SIZE 64

void bzero(void *dst, size_t size);
size_t readHeader(int fd, char *buffer, size_t size);
size_t readLine(int fd, char *buffer, size_t size, bool peek);
size_t readHttpParams(int fd, HttpParams *params);
bool readHttpRequestHeader(int fd, RequestHeader *requestHeader);
size_t makeResponseHeader(const HttpHeader *header, char *buffer, size_t size);


#endif // HTTPD_H
