#ifndef __BUFFER_INCLUDED__
#define __BUFFER_INCLUDED__

#include <string>

class Buffer {

    private:
        std::string *buf;

    public:
        explicit Buffer(size_t sz);
        ~Buffer();

    public:
        size_t size();
        const char *data();

    public:
        friend std::ostream &operator<<(std::ostream &out, Buffer &buf);
};

std::ostream &operator<<(std::ostream &out, Buffer &ref);

#endif // __BUFFER_INCLUDED__
