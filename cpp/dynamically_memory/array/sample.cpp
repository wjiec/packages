#include <iostream>
#include <memory>

using namespace std;

int get_array_size(void);

int main(void) {
    int *uninitialize_array = new int[get_array_size()];
    int *default_initialize_array = new int[get_array_size()]();
    int *value_initialize_array = new int[get_array_size()]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    cout << *uninitialize_array << endl;
    cout << *default_initialize_array << endl;
    cout << *value_initialize_array << endl;


    shared_ptr<int> array_sp(new int[get_array_size()], [] (int *p) { delete [] p; });
    unique_ptr<int[]> array_up(new int[get_array_size()]);

    delete [] uninitialize_array;
    delete [] default_initialize_array;
    delete [] value_initialize_array;

    return 0;
}

int get_array_size(void) {
    return 0xF;
}
