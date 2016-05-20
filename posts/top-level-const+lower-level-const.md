C语言虽然简单，但是重难点还是很多的，就比如让需要新手比较蛋疼的指针问题，但是深入学习之后指针才是碰到的第一个拦路虎，也是最简单的一个。比如声明问题，顶层const和底层const。今天说说后者。

以下有几个栗子
```c
int iVal = 520;
 第一个栗子
const int pVal1 = &iVal;
 第二个栗子
int const pVal2 = &iVal;
 第三个栗子
int  const pVal3 = &iVal;
 第四个栗子
int const  const pVal4 = &iVal;
```

分析
---
首先我们说说顶层const和底层const的通俗定义
`顶层const` 表示指针本身是个常量，更简单的说顶层const作用对对象本身，表示对象是一个常量
`底层const` 表示指针所指向的对象是个常量

所以有以下结论（个人总结）
---
 将const考虑成向右结合
 如果const右结合修饰的为`类型`或者``，那这个const就是一个`底层const`
 如果const右结合修饰的为`标识符`，那这个const就是一个`顶层const`

const的影响
---
 顶层const主要影响的是指向的值，我们知道有以下结论
  ```int iVal = 1314; const int ciVal = &iVal;```
 所以，顶层const表示为不能修改所指向的值，即，所指向的值为一个常量。
 底层const主要影响的是对象本身
   表示对象本身不能被修改，即，对象(指针)本身是个常量，但是可以修改指针所指向的值。

 理解以上前提为要理解指针其实可以当做一个int类型的数据，这个数据的内容是所指向对象的地址。
 比如 对象 A 的地址 = 0x123456，对象 A 的值 = ABCDEF
 指针 B 指向 对象 A
 所以指针 B 的值 = 0x123456, 指针 B 的地址 = (系统在栈或者堆中分配，因为指针本身有地址，所以可以有指向指针的指针)



栗子分析
---
第一个和第二个栗子
```c
const int pVal1 = &iVal;
 const 右结合修饰 int ，所以这个const为底层const
int const pVal2 = &iVal;
 const 右结合修饰  ,所以这个const为顶层const
```

第三个栗子
```c
int  const pVal3 = &iVal;
 这个const右结合修饰 pval3 ,修饰的是一个标识符，所以这个const为顶层const
```

第四个栗子
```c
int const  const pVal4 = &iVal;
 第一个const修饰的是  所以第一个const是底层const
 第二个const修饰的是 pVal4 所以这个const是顶层const
```
