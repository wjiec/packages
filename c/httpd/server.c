#include <stdio.h>
#include <stdbool.h>
#include "httpd.h"

int main(void) {
    int httpd = startup(9999);

    while (true) {
        accpetClient(httpd);
    }

    closeHttpd(httpd);
    return 0;
}

