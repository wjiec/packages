#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>

int main(void) {
    uint16_t apple = 0x0102;
    
//  printf("%s BytesOrde\n", CPU_VENDOR_OS);
    if (*(char *)&apple == 0x01) {
        puts("Big-endian");
    } else if (*(char *)&apple == 0x02) {
        puts("Little-endian");
    } else {
        puts("Unknown");
    }
    
    return 0;
}