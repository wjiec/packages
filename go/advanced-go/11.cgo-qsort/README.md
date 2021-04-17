CGO编程 - 封装qsort
-----------------

`qsort`是C语言中的一个高阶函数，可以对任意的数组进行自定义排序。函数签名为
```c
void qsort(void *base, size_t num, size_t size, int (*cmp)(const void *, const void *));
```

其中`base`是数组首元素的地址，`num`表示数组中元素的个数，`size`表示每个数组元素的大小，`cmp`表示比较的函数指针。

__因为C不支持闭包函数，所以这里怎么实现并发版本的qsort封装？__
