> 以下所有栗子未经特殊说明，全部实现于C++11下


初始化方式
---
在C++中有很多种初始化方式，如下栗子
```c++
string s0; // 默认初始化
string s1(s0); // 拷贝初始化
string s2("Hello World!"); // 直接初始化
string s3{"Hello World!"}; // 列表初始化
string s4 = "Hello World!"; // 拷贝初始化
string s5(10, '*'); // 直接初始化
string s6 = s0; // 拷贝初始化，等价于 s1(s0)
```

> **注意：** 列表初始化作为C++11的一部分，现在得到全面的应用。但是在这之前，使用花括号这种初始化形式只能应用于一些场合下。
> **但是如果我们使用列表初始化，初始值存在丢失的风险，那么将会直接报错**
> ```c++
> double banana = 1.23456789;
> int apple{ banana }; // 错误，丢失了部分精度
> inr orange(banana); // 成功，但是丢失了精度
> ```

重现
---
考虑以下类的声明
```c++
class Test {
    string s("Hello World")
};
```
这在编译时会发生一个错误，错误信息为
```
error: expected identifier before string constant
error: expected ‘,’ or ‘...’ before string constant
```

分析 && 结论
---
报错的原因是对类内初始值的限制，在提供类内初始值(C++11)时，不能使用直接初始化的方式。简单点说，就是不能使用圆括号**直接**进行类内初始化，其他几种方式都没问题，甚至你可以使用以下方式
```c++
class Test {
    string s = string("Hello World"); // 拷贝初始化
}
```

举一反三
---
考虑下面栗子，有哪些错误
```c++
class Base1 {
    string s;

    public:
        Base1(string &source) : s(source) {}
};

class Base2 {
    string s;

    public:
        Base2(string source) : s(source) {}
};

class Test {
    Base1 b1 = Base1("Hello World");
    Base2 b2 = Base2("Hello World");
    Base2 b3("Hello World");
};
```