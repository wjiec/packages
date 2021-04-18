#include <iostream>

#include "buffer.h"

extern "C" {

#include "buffer_c_api.h"

}

struct buffer_t {
    Buffer *internal;
};

extern CBuffer newBuffer(size_t sz) {
    auto buf = new buffer_t;
    buf->internal = new Buffer(sz);

    return buf;
}

extern void deleteBuffer(CBuffer buf) {
    delete buf->internal;
    buf->internal = nullptr;
}

extern size_t bufferSize(CBuffer buf) {
    if (buf->internal == nullptr) {
        return 0;
    }
    return buf->internal->size();
}

extern const char *bufferData(CBuffer buf) {
    if (buf->internal == nullptr) {
        return nullptr;
    }
    return buf->internal->data();
}

extern void printBuffer(CBuffer buf) {
    if (buf->internal != nullptr) {
        std::cout << buf << std::endl;
    }
}