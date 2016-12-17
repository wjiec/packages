#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct _tree_node_t {
    /**
     * default is '0'
     *
     * 0 -> symbol
     * 1 -> number
     *
     */
    int data_type : 1;
    union _element_data_t {
        char symbol;
        char number;
    } data;
    struct _tree_node_t *left;
    struct _tree_node_t *right;
} Node;
typedef Node* Tree;

int sp = -1;
Tree stack[64] = { NULL };
void push(Tree tree);
Tree pop(void);
Tree top(void);
void make(Tree *dst, const char *input);
void print_tree(Tree tree);
void clean(Tree *tree);
static void do_print_node(char symbol, Tree left, Tree right);

int main(void) {
//    char *input = "a + b * c - g / f";
    char *input = "a b c * + g f / -";
    Tree tree = NULL;

    make(&tree, input);
    print_tree(tree);
    clean(&tree);

    return 0;
}

void push(Tree tree) {
    stack[++sp] = tree;
}

Tree pop(void) {
    return sp == -1 ? NULL : stack[sp--];
}

Tree top(void) {
    return sp == -1 ? NULL : stack[sp];
}

void make(Tree *dst, const char *input) {
    for (size_t index = 0; index < strlen(input); ++index) {
        char current_symbol = input[index];
        Tree tree = (Tree)malloc(sizeof(Node));
        if (current_symbol == ' ') {
            continue;
        }
        if (current_symbol == '+' || current_symbol == '-' || current_symbol == '*' || current_symbol == '/' ) {
            tree->data_type = 0;
            if (top() == NULL) {
                puts("Runtime Error: input invalid");
                exit(1);
            }
            tree->right = pop(); // first
            if (top() == NULL) {
                puts("Runtime Error: input invalid");
                exit(1);
            }
            tree->left = pop(); // second
            tree->data.symbol = current_symbol;
        } else if (isalpha(current_symbol)) {
            tree->data_type = 1;
            tree->data.number = current_symbol;
            // the number is leaf node
            tree->left = NULL;
            tree->right = NULL;
        }
        push(tree);
    }
    if (top() == NULL) {
        puts("Runtime Error: input invalid");
        exit(1);
    }
    *dst = pop();
    if (top() != NULL) {
        puts("Runtime Error: stack is not empty");
        exit(1);
    }
}

void print_tree(Tree tree) {
    do_print_node(tree->data.symbol, tree->left, tree->right);
    printf("\n");
}

static void do_print_node(char symbol, Tree left, Tree right) {
    printf("( ");

    if (left->left && left->right) {
        do_print_node(left->data.symbol, left->left, left->right); // expression tree
    } else {
        printf("%c", left->data.number); // number node
    }

    printf(" %c ", symbol); // symbol

    if (right->left && right->right) {
        do_print_node(right->data.symbol, right->left, right->right);
    } else {
        printf("%c", right->data.number);
    }

    printf(" )");
}

void clean(Tree *tree) {
    if ((*tree)->left != NULL) {
        clean(&((*tree)->left));
    }

    if ((*tree)->right != NULL) {
        clean(&((*tree)->right));
    }

    free(*tree);
    *tree = NULL;
}
