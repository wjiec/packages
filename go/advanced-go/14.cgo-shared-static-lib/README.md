CGO编程 - 静态库和动态库
--------------------

CGO在使用C/C++资源的时候一般有3种形式：
 * 直接使用源码
 * 链接静态库
 * 链接动态库

链接静态库和链接动态库的方式比较类似，都是通过在`LDFLAGS`选项指定要链接的库的方式来链接。


### 使用静态库

静态库因为是静态链接，最终的慕白程序并不是产生额外的运行时依赖，也不会出现动态库特有的跨运行时资源管理的错误。不过静态库对链接阶段会有一定要求：静态库一般包含了全部的源代码，里面会有大量的符号，如果不同静态库之间出现符号冲突则会导致链接的失败。

构建静态库的命令：
```bash
gcc -c -o xxx.o xxx.c yyy.c zzz.c
ar rcs libxxx.a xxx.o
```

CGO通过`#cgo`指令来配置编译和链接参数：
 * `CFLAGS`：通过`-I./include`指定头文件的检索路径
 * `LDFLAGS`：通过`-L${SRCDIR}/libdir`将静态类目录加入链接库检索目录；通过`-lxxx`表示需要链接`xxx`静态库。

如果在源码包中自带了CGO的所有源码，为了支持`go get`命令直接下载安装，可以在C语言中直接将库的源文件链接到当前包中
```c
#include "module/xxx.c"
```

如果使用的是第三方的静态库，需要先下载安装静态库，然后在`#cgo`指令中通过`CFLAGS`和`LDFLAGS`指定头文件和库文件的位置。


### 使用动态库

动态库出现的初衷为了节省内存和磁盘资源，多个进程可以共享相同的库。

构建动态库的命令：
```bash
gcc -shared -o libxxx.so xxx.c yyy.c zzz.c
```
因为动态库和静态库只是扩展名不一样，所以Go语言部分的配置和静态库完全一样。


### CGO导出静态库

CGO不仅可以使用C静态库，还可以将Go实现的函数导出为C静态库。

__注意：根据CGO文档要求，需要在main包中导出C函数。对于构建C静态库方式来说，会忽略`main`包中的`main`方法，只是简单的导出C函数。__

CGO构建静态库命令：
```bash
go build -buildmode=c-archive -o libxxx.a
```


### CGO导出动态库

CGO导出动态库的过程和静态库类似，只是简单将构建模式改为`c-shared`，输入文件名改为`libxxx.so`：
```bash
go build -buildmode=c-shared -o libxxx.so
```

### 导出非`main`包的函数

为了实现从非`main`包导出C函数，或者从多个包导出C函数，需要我们自己提供导出C函数对应的头文件。

__注意：其实这里还是从main包导出的符号。这里的做法是在一个专门的main包中引入需要导出函数的包，然后导出这个main包的函数，因为依赖关系，所以被引入的包的符号也会被导出。再配合上自己提供的头文件即可实现。__

***特别注意：多个包同时导出到全局的命名空间中，需要特别注意重名问题。***

```go
package main

import "C"
import (
	_ "module/xxx"
	_ "module/yyy"
	_ "module/zzz"
)

func main() {}

//export __go_main_export
func __go_main_export() {}``
```
