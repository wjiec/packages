#include <stdio.h>
#include <stdlib.h>

typedef int Data;
typedef struct _stack {
    int sp;
    int capacity;
    Data *array;
} Stack;

void create(Stack *stack, int maxCapacity);
void push(Stack *stack, Data data);
Data pop(Stack *stack);

int main(void) {
    Stack stack;

    create(&stack, 15);
    push(&stack, 1);
    push(&stack, 2);
    push(&stack, 3);

    printf("%d ", pop(&stack));
    printf("%d ", pop(&stack));
    printf("%d ", pop(&stack));
    putchar('\n');

    return 0;
}

void create(Stack *stack, int maxCapacity) {
    stack->sp = -1;
    stack->capacity = maxCapacity;
    stack->array = (Data*)malloc(sizeof(Data) * maxCapacity);
    if (stack->array == NULL) {
        fprintf(stderr, "out of mem\n"); exit(1);
    }
}

void push(Stack *stack, Data data) {
    if (stack->sp == stack->capacity - 1) {
        fprintf(stderr, "full stack\n");
    } else {
        stack->array[++stack->sp] = data;
    }
}

Data pop(Stack *stack) {
    if (stack->sp > -1) {
        return stack->array[--stack->sp];
    } else {
        fprintf(stderr, "empty stack\n");
        return -1;
    }
}
