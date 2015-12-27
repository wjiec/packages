#include <stdio.h>
#include <stdlib.h>
#include "getopt.h"

struct option long_options[] = {
    { "uid",         required_argument, NULL, 'u' },
    { "password",    optional_argument, NULL, 'p' },
    { "help",        no_argument,       NULL, 'h' }
};
/**
 * Usage:
 *      [* argv] -u123 -p123
 *      [* argv] -u123 -p
 *      [* argv] --uid=123 --password=123
 *      [* argv] ?
 *      [* argv] --help
 */

int main(int argc, char * argv[]) {
    char opt;

    if (argc == 1) {
        printf("Usage: ...");
        exit(-1);
    }

    while ((opt = getopt_long(argc, argv, "u:p::vi?h", long_options, NULL)) != EOF) {
        switch (opt) {
            case 'u': printf("U: %s\n", optarg); break;
            case 'p': printf("P: %s\n", optarg); break;
            default: printf("Usage: ..."); break;
        }
    }

    return 0;
}