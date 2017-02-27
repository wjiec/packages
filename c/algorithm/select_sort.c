#include <stdio.h>

void select_sort(int array[], size_t array_length);
void array_dump(int array[], size_t array_length);
static void swap(int *x, int *y);

int main(void) {
    int array[] = { 1, 3, 8, 2, 4, 9, 6, 5, 7, 0 };

    array_dump(array, sizeof(array) / sizeof(int));
    select_sort(array, sizeof(array) / sizeof(int));
    array_dump(array, sizeof(array) / sizeof(int));

    return 0;
}

void select_sort(int array[], size_t array_length) {
    for (int ordered_index = 0; ordered_index < array_length; ++ordered_index) {
        int max_number_index = ordered_index;

        for (int element_index = ordered_index; element_index < array_length; ++element_index) {
            if (array[element_index] > array[max_number_index]) {
                max_number_index = element_index;
            }
        }

        swap(array + ordered_index, array + max_number_index);
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
