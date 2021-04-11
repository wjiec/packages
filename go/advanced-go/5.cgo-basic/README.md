CGO编程 - 基础
------------

要使用CGO特性，需要安装C/C++工具链，同时需要保证环境变量CGO_ENABLED被设置为1。

__在本地构建时CGO是默认启用的，在交叉构建时CGO是默认禁用的（如需开启需要手工配置交叉构建工具链，同时开启环境变量）。__

代码中的`import "C"`语句，表示使用了CGO特性。

注意：Go是强类型语言，所以CGO中传递的参数类型必须与声明的类型完全一致，而且传递前必须用`C`中的转换函数转换成对应的C类型，不能直接传入Go中类型的变量。

CGO会将**当前包**引用的C语言符号都放在虚拟的`"C"`包中（每个包都可以引入自己的`"C"`包，但是注意**不同Go包引入的虚拟"C"包之间的类型是不能通用的**。


### cgo语句

通过`#cgo`语句设置编译阶段和链接阶段的相关参数（主要影响`CFLAGS`，`CPPFLAGS`，`CXXFLAGS`，`FFLAGS`，`LDFLAGS`这几个编译器环境变量）。
 * 编译阶段的参数：用于定义相关宏和指定头文件检索路径
 * 链接阶段的参数：指定库文件检索路径和要链接的库文件
```cgo
// #cgo CFLAGS: -DAPP_DEBUG=1 -I./include
// #cgo LDFLAGS: -L${SRCDIR}/libs -lhello
// #include <hello.h>
```
 * `CFLAGS`主要影响C语言特有的编译选项
 * `CXXFLAGS`主要影响C++语言特有的编译选项
 * `CPPFLAGS`主要影响C和C++公有的编译选项

`#cgo`语句还支持条件选择，当满足某个操作系统或者某个CPU架构类型时，后面的编译或链接选项才生效。
```cgo
// #cgo windows CFLAGS -D__CGO_OS_WINDOWS__=1
// #cgo linux CFLAGS -D__CGO_OS_LINUX__=1
```

### build标志条件编译

build标志是在Go和CGO环境下的一种特殊注解。可以在`go build`时指定编译的标签
```bash
go build --tags="debug dev"
```

在Go源文件中的`+build`指令使用`,`表示`AND`关系，使用`(space)`表示`OR`关系
```cgo
// +build windows,!cgo darwin,386
```
如上表示只有在**windows下的非CGO环境**或**Mac下的386环境**才参与编译。
