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

int main(void) {
    Poly poly1, poly2, sum;

    zeroPolynomial(&poly1);
    zeroPolynomial(&poly2);
    zeroPolynomial(&sum);

    addNode(&poly1, 1, 2);
    addNode(&poly1, 2, 4);
    addNode(&poly1, 3, 9);
    addNode(&poly1, 5, 9);

    addNode(&poly2, 1, 2);
    addNode(&poly2, 2, 4);
    addNode(&poly2, 3, 9);
    addNode(&poly2, 5, 9);



    printPolynomial(&poly1);

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

        for (curr = *list; curr->next != NULL; curr = curr->next);
        curr->next = el;

    }
}

void printPolynomial(Poly *list) {
    for (Poly curr = *list; curr->next != NULL; curr = curr->next) {
        printf("%d %d\n", curr->coeffcient, curr->exponent);
    }
}

void addPolynomial(const Poly *poly1, const Poly *poly2, Poly *sum) {
    int index1 = 0;
    int index2 = 0;
}
