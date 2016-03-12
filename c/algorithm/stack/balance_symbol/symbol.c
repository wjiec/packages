#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#define STACK_MAX_LENGTH (64)

const char open[]  = { '(', '[', '{', 0 };
const char close[] = { ')', ']', '}', 0 };
char symbolStack[STACK_MAX_LENGTH] = { 0 };
int sp = -1;

bool check(char *string);
void push(char ch);
char pop(void);

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s [exp]\n", *argv); exit(1);
    }

    if (check(argv[1])) {
        printf("complete\n");
    } else {
        fprintf(stderr, "failure\n");
    }

    return 0;
}

bool check(char *string) {
    for (char *index = string; index != NULL; ++index) {
        if (*index == '\0') {
            break;
        }
        if (strchr(open, *index) != NULL) {
            push(*index);
        } else if (strchr(close, *index) != NULL) {
            char ch = pop();
            switch (ch) {
                case '(': if (*index == ')') { continue; } break;
                case '[': if (*index == ']') { continue; } break;
                case '{': if (*index == '}') { continue; } break;
                default: return false;
            }
        }
    }
    if (pop() != '\0') {
        return false;
    }

    return true;
}

void push(char ch) {
    symbolStack[++sp] = ch;
}

char pop(void) {
    if (sp == -1) {
        return '\0';
    } else {
        return symbolStack[sp--];
    }
}

