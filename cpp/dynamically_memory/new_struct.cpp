#include <iostream>
#include <memory>
#include <new>

using namespace std;

typedef struct _data_t {
    int *pi;
    char *pc;
    double *pd;
} Data;

void data_free(Data *pd);

int main(void) {
    Data *pd = new Data;

    pd->pi = new int(1024);
    pd->pc = new char('A');
    pd->pd = new double(1.23456);

    {
        shared_ptr<Data> sp(pd, data_free);
    }

    return 0;
}

void data_free(Data *pd) {
    cout << "entry free" << endl;

    delete pd->pi;
    delete pd->pc;
    delete pd->pd;
}
