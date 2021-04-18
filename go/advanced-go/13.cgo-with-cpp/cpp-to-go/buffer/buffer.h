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
        std::ostream &operator<<(std::ostream &out);
};

#endif // __BUFFER_INCLUDED__
