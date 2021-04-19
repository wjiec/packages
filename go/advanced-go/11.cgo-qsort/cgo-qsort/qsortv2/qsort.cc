#include <cstdlib>
#include <iostream>
#include <functional>

extern "C" {
    #include "qsort.h"
}

typedef int (callback_func_t)(void *, void*);
typedef int (*callback_func_p)(void *, void*);
typedef int (*qsort_cmp_func_t)(const void*, const void*);

extern void doSort(void *base, size_t count, size_t size, void *fn) {
    auto cb = std::bind(cgoCall, fn, std::placeholders::_1, std::placeholders::_2);
    auto ff = std::function<callback_func_t>(cb);

    auto l = 1, r = 2;
    std::cout << cb(&l, &r) << " " << cb(&r, &l) << std::endl;
    if (ff.target<std::_Bind<std::_Mem_fn<int(*)(void*,void*)>>>() == nullptr) {
        std::cout << "what fuck that" << std::endl;
    }

    std::qsort(base, count, size, nullptr);
}
