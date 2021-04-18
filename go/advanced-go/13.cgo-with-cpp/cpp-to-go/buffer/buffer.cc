#include <string>
#include <iostream>

#include "buffer.h"


Buffer::Buffer(size_t sz) {
    buf = new std::string(sz, '\0');
}

Buffer::~Buffer() {
    delete buf;
    buf = nullptr;
}

size_t Buffer::size() {
    if (buf == nullptr) {
        return 0;
    }
    return buf->size();
}

const char *Buffer::data() {
    if (buf == nullptr) {
        return nullptr;
    }
    return buf->data();
}

std::ostream &Buffer::operator<<(std::ostream &out) {
    if (buf != nullptr) {
        for (auto i = 0; i < buf->size(); i++) {
            out << uint8_t(buf->at(i));

            if (i != 0 && i % 16 == 0) {
                out << std::endl;
            }
        }
    }
    return out;
}
