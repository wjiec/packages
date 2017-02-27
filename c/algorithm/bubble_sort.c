#include <stdio.h>

void bubble_sort(int array[], size_t array_length);
void array_dump(int array[], size_t array_length);
static void swap(int *x, int *y);

int main(void) {
    int array[] = { 1, 3, 8, 2, 4, 9, 6, 5, 7, 0 };

    array_dump(array, sizeof(array) / sizeof(int));
    bubble_sort(array, sizeof(array) / sizeof(int));
    array_dump(array, sizeof(array) / sizeof(int));

    return 0;
}

void bubble_sort(int array[], size_t array_length) {
    for (int ordered_index = array_length - 1; ordered_index > 0; --ordered_index) {
        for (int element = 0; element < ordered_index; ++element) {
            if (array[element] < array[element + 1]) {
                swap(array + element, array + element + 1);
            }
        }
    }
}

void array_dump(int array[], size_t array_length) {
    for (int index = 0; index < array_length; ++index) {
        printf("%d ", array[index]);
    }
    printf("\n");
}

static void swap(int *x, int *y) {
    int tmp = *x;
    *x = *y;
    *y = tmp;
}
