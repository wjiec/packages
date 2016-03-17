#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef int Data;
typedef struct _node {
    Data data;
    struct _node *next;
} Node;
typedef Node* List;

void createList(List *list, Data *data);
bool isEmpty(const List *list);
void addNode(List *list, Data *data);
void removeNode(List *list, Data *data);
void destoryList(List *list);
void printList(const List *list);

int main(void) {
    List list = NULL;
    Data data = 1;

    if (isEmpty(&list)) {
        createList(&list, &data);
    }

    addNode(&list, &data);
    addNode(&list, &data);
    printList(&list);

    destoryList(&list);
    printf("%d\n", isEmpty(&list) ? 1 : 0);

    return 0;
}

void createList(List *list, Data *data) {
    if (*list == NULL) {
        *list = (List)malloc(sizeof(Node));
        (*list)->data = *data;
        (*list) ->next = NULL;
    } else {
        fprintf(stderr, "List is not empty.\n");
    }
}

bool isEmpty(const List *list) {
    return *list == NULL;
}

void addNode(List *list, Data *data) {
    Node *current = *list;
    Node *item;

    for (; current->next != NULL; current = current->next);
    item = (Node*)malloc(sizeof(Node));
    item->data = *data;
    item->next = NULL;
    current->next = item;
}

void removeNode(List *list, Data *data) {
    for (Node **current = list; *current != NULL; current = &((*current)->next)) {
        if ((*current)->data == *data) {
            Node *item = *current;
            *current = (*current)->next;
            free(item);
        }
    }
}

void destoryList(List *list) {
    for (Node *item = *list, *temp; item != NULL; ) {
       temp = item->next;
       free(item);
       item = temp;
    }
    *list = NULL; // empty
}

void printList(const List *list) {
    for (Node *current = *list; current != NULL; current = current->next) {
        printf("%d ", current->data);
    }
    putchar('\n');
}

