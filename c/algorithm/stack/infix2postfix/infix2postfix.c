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
    for (int index = 0; infix[index] != '\0'; ++index) {
        char op = infix[index];

        if (isdigit(op) || isalpha(op)) {
            putchar(op);
        } else if (op == '+' || op == '-' || op == '*' || op == '/' || op == '(' || op == ')') {
            char topSymbol = top();
            if (topSymbol == '\0') {
                push(infix[index]);
                continue;
            }

            switch (op) {
                case '*':
                case '/':
                    if (topSymbol == '+' || topSymbol == '-') {
                        push(op);
                        continue;
                    } else if (topSymbol == '*' || topSymbol == '/') {
                        putchar(pop());
                        push(op);
                        break;
                    }
                case '+':
                case '-':
                    if (topSymbol == '*' || topSymbol == '/') {
                        while (top() != '(' || top() != '\0') {
                            printf("%c ", pop());
                        }
                        break;
                    } else if (topSymbol == '+' || topSymbol == '-') {
                        putchar(pop());
                        push(op);
                        break;
                    }
                case '(':
                    push(op); continue;
                case ')':
                    while (top() != '(') {
                        printf("%c ", pop());
                    }
                    pop(); // pop (
                    continue;
                default:
                    fprintf(stderr, "FATAL ERROR!\n");
                    exit(1);
            }
        } else if (op == ' ') {
            continue;
        }

        putchar(' ');
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

