#include <stdio.h>
#include <stdlib.h>

typedef struct _node {
    int coeffcient;
    int exponent;
    struct _node *next;
} Node;

typedef Node* Poly;

void zeroPolynomial(Poly *poly);
void addNode(Poly *list, int coeffcient, int exponent);
void printPolynomial(Poly *list);
void addPolynomial(const Poly *poly1, const Poly *poly2, Poly *sum);
void multPolynomial(const Poly *poly1, const Poly *poly2, Poly *sum);
void collectPolynomial(Poly *poly);
void sortPolynomial(Poly *poly);
void deleteNode(Poly *list, Poly item);
void destory(Poly *poly);

int main(void) {
    Poly poly1, poly2, sum;

    zeroPolynomial(&poly1);
    zeroPolynomial(&poly2);
    zeroPolynomial(&sum);

    addNode(&poly1, 1, 3);
    addNode(&poly1, 2, 5);
    addNode(&poly1, 3, 10);
    addNode(&poly1, 5, 11);

    addNode(&poly2, 1, 2);
    addNode(&poly2, 2, 5);
    addNode(&poly2, 3, 9);
    addNode(&poly2, 5, 11);

    puts("AddPolynomial: ");

    addPolynomial(&poly1, &poly2, &sum);
    printPolynomial(&sum);

    puts("MulyPolunomial: ");

    destory(&sum);
    multPolynomial(&poly1, &poly2, &sum);
    printPolynomial(&sum);

    return 0;
}

void zeroPolynomial(Poly *poly) {
    *poly = NULL;
}

void addNode(Poly *list, int coeffcient, int exponent) {
    if ((*list) == NULL) { // header
        *list = (Poly)malloc(sizeof(Node));

        (*list)->coeffcient = coeffcient;
        (*list)->exponent   = exponent;
        (*list)->next       = NULL;
    } else {
        Poly el;
        Poly curr;

        el = (Poly)malloc(sizeof(Node));
        el->coeffcient = coeffcient;
        el->exponent   = exponent;
        el->next       = NULL;

        for (curr = *list; curr->next != NULL; curr = curr->next);
        curr->next = el;
    }
}

void printPolynomial(Poly *list) {
    for (Poly curr = *list; curr != NULL; curr = curr->next) {
        printf("%d*X^%d + ", curr->coeffcient, curr->exponent);
    }
    putchar('\n');
}

void addPolynomial(const Poly *poly1, const Poly *poly2, Poly *sum) {
    Poly index1 = *poly1;
    Poly index2 = *poly2;

    while ((index1 != NULL) && (index2 != NULL)) {
        if (index1->exponent == index2->exponent) {
            addNode(sum, index1->coeffcient + index2->coeffcient, index1->exponent);
            
            index1 = index1->next;
            index2 = index2->next;
        } else if (index1->exponent > index2->exponent) {
            addNode(sum, index2->coeffcient, index2->exponent);
            
            index2 = index2->next;
        } else {
            addNode(sum, index1->coeffcient, index1->exponent);

            index1 = index1->next;
        }
    }
    if (index1 != NULL) {
        for (; index1 != NULL; index1 = index1->next) {
            addNode(sum, index1->coeffcient, index1->exponent);
        }
    }
    if (index2 != NULL) {
        for (; index2 != NULL; index2 = index2->next) {
            addNode(sum, index2->coeffcient, index2->exponent);
        }
    }
    collectPolynomial(sum);
}

void multPolynomial(const Poly *poly1, const Poly *poly2, Poly *sum) {
    for (Poly line = *poly1; line != NULL; line = line->next) {
        for (Poly row = *poly2; row != NULL; row = row->next) {
            addNode(sum, (line->coeffcient) * (row->coeffcient), (line->exponent) + (row->exponent));
        }
    }
    collectPolynomial(sum);
    sortPolynomial(sum);
}

void collectPolynomial(Poly *poly) {
    for (Poly current = *poly; current->next != NULL; current = current->next) {
        for (Poly child = current->next; child != NULL;) {
            if (child->exponent == current->exponent) {
                current->coeffcient += child->coeffcient;
                deleteNode(poly, child);
            } else {
                child = child->next;
            }
        }
    }
}

void sortPolynomial(Poly *poly) {
    for (Poly index = *poly; index != NULL; index = index->next) {
        Poly min  = index;

        for (Poly item = index; item != NULL; item = item->next) {
            if (min->exponent > item->exponent) {
                min = item;
            }
        }
        
        if (min != index) {
            int temp = index->coeffcient;
            index->coeffcient = min->coeffcient;
            min->coeffcient = temp;

            temp = index->exponent;
            index->exponent = min->exponent;
            min->exponent = temp;
        }
    }
}

void deleteNode(Poly *list, Poly item) {
    for (Poly *current = list; *current;) {
        Poly entry = *current;

        if (entry == item) {
            *current = entry->next;
            free(entry);
        } else {
            current = &(entry->next);
        }
    }
}

void destory(Poly *poly) {
    Poly current = *poly;
    Poly temp;

    while (current->next != NULL) {
        temp = current->next;
        free(current);
        current = temp;
    }
    free(current);
    zeroPolynomial(poly); 
}

