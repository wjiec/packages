#include <stdio.h>
#include <stdlib.h>
#include "ds.h"

struct _stack_t {
    void *data;
    size_t element_size;
    size_t stack_max_size;
    size_t stack_current_size;
    void *stack_top_border;
    int stack_top_index;
};

Stack *create_stack(size_t element_size, size_t stack_size) {
    Stack *stack = (Stack*)malloc(sizeof(struct _stack_t));

    stack->stack_current_size = element_size * stack_size / 2;
    stack->data = (void*)malloc(stack->stack_current_size);
    stack->element_size = element_size;
    stack->stack_max_size = stack_size;
    stack->stack_top_border = (char*)stack->data + stack->stack_current_size;
    stack->stack_top_index = -1;

    return stack;
}

void destroy_stack(Stack *stack) {
    free(stack->data);
    stack->data = NULL;
    stack->element_size = 0;
    stack->stack_current_size = 0;
    stack->stack_max_size = 0;
    stack->stack_top_border = NULL;
    stack->stack_top_index = 0;
}

bool push(Stack *stack, void *element) {
}

void *pop(Stack *stack) {

}

void *top(Stack *stack) {

}
