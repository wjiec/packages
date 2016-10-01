#ifndef HTTPD_H
#define HTTPD_H

#ifdef _WIN32
#define _CRT_SECURE_NO_WARNINGS 1
#endif

#define SERVER_NAME "Server: shttpd/1.0\r\n"

#define MAX_BUFFER_LENGTH 512
#define MAX_CONTENTS_LENGTH 1024
#define MAX_RESPONSE_LENGTH 4096

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
    char *contents;
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
HttpHeader *createHttpHeader(HttpStatus *status, HttpParams *params, char *contents);

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

// util functions
#define READ_LINE_MAX_SIZE 64
#define bzero(dst, size) memset((dst), 0, (size))

bool createHttpParams(HttpParams *params, ...);
size_t readHeader(int fd, char *buffer, size_t size);
size_t readLine(int fd, char *buffer, size_t size, bool peek);
bool readHttpParams(int fd, HttpParams *params);
bool readHttpRequestHeader(int fd, RequestHeader *requestHeader);
bool makeResponse(const HttpHeader *header, char *buffer, size_t size);
bool httpResponse(int fd, char *response);


#endif // HTTPD_H
