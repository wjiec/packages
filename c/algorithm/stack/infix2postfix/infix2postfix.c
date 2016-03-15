#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

char symbolStack[64] = { 0 };
int sp = -1;

void transition(char *infix);
void push(char ch);
char pop(void);
char top(void);

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s [exp]\n", *argv);
        return 1;
    }
    transition(argv[1]);
    return 0;
}

void transition(char *infix) {
    for (char *current = infix; *current != '\0'; ++current) {
        char op = *current;
        char tp = top();

        if (isdigit(op) || isalpha(op)) {
            printf("%c ", op);
            continue;
        }

        if (op != '+' && op != '-' && op != '*' && op != '/' && op != '(' && op != ')' && op != ' ') {
            fprintf(stderr, "`%c` cannot defined.\n", op);
            exit(1);
        }
        if (op == ' ') {
            continue;
        }
        if (tp == '\0') {
            push(op);
            continue;
        }

        switch(op) {
            case '(':
                push(op);
                break;
            case '*':
            case '/':
                if (tp == '*' || tp == '/') {
                    do {
                        printf("%c ", pop());
                        tp = top();
                    } while (tp == '*' || tp == '/');
                }
                push(op);
                break;
            case '+':
            case '-':
                while (tp != '(' && tp != '\0') {
                    printf("%c ", pop());
                    tp = top();
                }
                push(op);
                break;
            case ')':
                while (tp != '(' && tp != '\0') {
                    printf("%c ", pop());
                    tp = top();
                }
                pop(); // pop (
                break;
            default:
                fprintf(stderr, "FATAL ERROR.\n");
                exit(1);
        }
    }
    while (top() != '\0') {
        printf("%c ", pop());
    }
    putchar('\n');
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

char top(void) {
    if (sp == -1) {
        return '\0';
    } else {
        return symbolStack[sp];
    }
}

