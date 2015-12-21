#include <stdio.h>

union byteorder {
	short apple;
	char banana[sizeof(short)];
};

int main(void) {
	union byteorder pear;
	
	pear.apple = 0x0102;
	if (pear.banana[0] == 0x01) {
		puts("Big-endian");
	} else if (pear.banana[0] == 0x02) {
		puts("Little-endian");
	} else {
		puts("UNKNOWN");
	}
	
	return 0;
}
