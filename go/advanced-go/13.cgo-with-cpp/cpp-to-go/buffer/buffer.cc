#include <string>
#include <iostream>
#include <iomanip>

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

std::ostream &operator<<(std::ostream &out, Buffer &ref) {
    if (ref.buf != nullptr) {
        for (auto i = 0; i < ref.size(); i++) {
            out << std::hex << std::setw(2) << std::setfill('0') << (int)(ref.buf->at(i));

            if (i != 0 && (i + 1) % 64 == 0) {
                out << std::endl;
            } else {
                out << " ";
            }
        }
    }
    return out;
}
