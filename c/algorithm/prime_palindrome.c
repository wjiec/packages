#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdbool.h>

#define MAX_SEARCH_NUMBER 10240
#define VALUE(x) (x * 2 + 1)
#define INDEX(x) ((x - 1) / 2)

static bool is_palindrome_number(int number);

int main(void) {
    // all is odd-number
    // index => (index * 2 + 1)
    // index(0) => number(1)
    // index(1) => number(3)
    // if value is 0, that is prime-number
    bool _global_prime_table[MAX_SEARCH_NUMBER] = { 0 };

    for (size_t i = 1; VALUE(i) < sqrt(VALUE(MAX_SEARCH_NUMBER)); ++i) {
        if (_global_prime_table[i] == 0) {
            for (size_t t = 3; t * VALUE(i) < VALUE(MAX_SEARCH_NUMBER); t += 2) {
                _global_prime_table[INDEX(t * VALUE(i))] = 1;
            }
        }
    }

    for (size_t i = 0, out_cnt = 0; i < MAX_SEARCH_NUMBER; ++i) {
        if (_global_prime_table[i] == 0) {
            if (is_palindrome_number(VALUE(i))) {
                printf("%8d ", VALUE(i));
                out_cnt += 1;

                if (out_cnt % 8 == 0) {
                    puts("");
                }
            }
        }
    }

    return 0;
}

static bool is_palindrome_number(int number) {
    if (number < 10) {
        return true;
    }

    char to_string[16] = { 0 };
    sprintf(to_string, "%d", number);
    
    for (size_t i = 0; i < strlen(to_string) / 2; ++i) {
        if (to_string[i] != to_string[strlen(to_string) - 1 - i]) {
            return false;
        }
    }

    return true;
}
