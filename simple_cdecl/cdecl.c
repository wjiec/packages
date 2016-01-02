#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

#define STACKLEN 64

enum token_type { IDENTIFIER, QUALIFIER, TYPE };

struct token {
    enum token_type type;
    char string[64];
};

int SP = -1;
struct token stack[64]; // 4KB
struct token current = { 0 };

#define push(t) stack[++SP] = (t)
#define pop stack[SP--]

static enum token_type classifyString(void);
static void readToFirstIdentifier(void);
static void nextToken(void);
static void DealWithDeclarator(void);
static void dealWithArrays(void);
static void dealWithFunctionArgs(void);
static void dealWithPointers(void);


int main(int argc, char *argv[]) {
    readToFirstIdentifier();
    DealWithDeclarator();
    getchar(); getchar(); getchar(); getchar();
    return 0;
}

static enum token_type classifyString(void) {
    char *s = current.string;

    if (!strcmp(s, "const")) {
        strncpy(s, "Read-Only", 10);
        return QUALIFIER;
    }

    if (!strcmp(s, "volatile")) return QUALIFIER;
    if (!strcmp(s, "void"))     return TYPE;
    if (!strcmp(s, "char"))     return TYPE;
    if (!strcmp(s, "short"))    return TYPE;
    if (!strcmp(s, "int"))      return TYPE;
    if (!strcmp(s, "long"))     return TYPE;
    if (!strcmp(s, "signed"))   return TYPE;
    if (!strcmp(s, "unsigned")) return TYPE;
    if (!strcmp(s, "float"))    return TYPE;
    if (!strcmp(s, "double"))   return TYPE;
    if (!strcmp(s, "struct"))   return TYPE;
    if (!strcmp(s, "union"))    return TYPE;
    if (!strcmp(s, "enum"))     return TYPE;
    return IDENTIFIER;
}

static void readToFirstIdentifier(void) {
    while (nextToken(), current.type != IDENTIFIER) {
        push(current);
    }

    if (current.type == IDENTIFIER) {
        printf("%s is ", current.string);
    } else {
        fprintf(stderr, "No Identifier."); exit(1);
    }
}

static void nextToken(void) {
    char *p = current.string;

    while ((*p = getchar()) == ' ');

    if (isalnum(*p)) {
        while ((*(++p) = getchar()), isalnum(*p));
        ungetc(*p, stdin);
        *p = 0;
        current.type = classifyString();
        return;
    }
    if (*p == '*') {
        strncpy(current.string, "pointer to ", 10);
        current.type = '*';
        return;
    }

    current.string[1] = 0;
    current.type      = current.string[0];
}

static void DealWithDeclarator(void) {
    nextToken();
    switch (current.type) {
        case '[': dealWithArrays(); break;
        case '(': dealWithFunctionArgs(); break;
    }
    dealWithPointers();

    while (SP > -1) {
        if (stack[SP].type == '(') {
            pop;
            nextToken();
            DealWithDeclarator();
        } else {
            printf("%s ", pop.string);
        }
    }
}

static void dealWithArrays(void) {
    while (current.type == '[') {
        printf("array ");
        
        if (nextToken(), isdigit(current.string[0])) {
            printf("0..%d ", atoi(current.string) - 1);
            nextToken(); // ']'
        }
        nextToken(); // next token == '['
        printf("of ");
    }
}

static void dealWithFunctionArgs(void) {
    while (current.type != ')') {
        nextToken();
    }
    nextToken();
    printf("function returning ");
}

static void dealWithPointers(void) {
    while (stack[SP].type == '(') {
        printf("%s ", pop.string);
    }
}
