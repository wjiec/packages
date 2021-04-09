CGO编程 - 快速入门
----------------

只要通过以下代码就可以启用CGO特性，即使没有任何和CGO相关的代码，也没有调用CGO的相关函数，但是`go build`命令会在编译和链接阶段启动gcc编译器，这已经是一个网站的CGO程序了。

__没有释放使用`C.CString()`创建的C语言字符串会导致内存泄露__


### CGO编译注意事项

__在使用块注释时，只能使用`/**/`，中间不能多加任何星号！__ 如下所示
```cgo
// 错误，因为开头有2个星号
/**
#include <stdio.h>
// ...
 */

// 错误，因为末尾有2个星号
/*
#include <stdio.h>
// ...
**/
```

__在使用外部C源文件定义C方法时，必须以目录形式编译__ 如下所示
```bash
# ls
hello.cc
GO111MODULE=off go build .

# !! wrong command !!
go build main.go
# 
# undefined reference to `xxx' 
#
```
这里的`GO111MODULE=off`依情况而定，因为没初始化`go.mod`所以这里需要关闭Module模式，不然会出现报错。

注意：绝对不能使用`go build xxx.go`形式编译，这会导致go编译器无法找到在C文件中的方法定义。
```text
$ go help build
usage: go build [-o output] [-i] [build flags] [packages]

Build compiles the packages named by the import paths,
along with their dependencies, but it does not install the results.

If the arguments to build are a list of .go files, build treats
them as a list of source files specifying a single package.

...
```


