Go的一些小知识点
-------------


* 可以在`build`命令中增加`-x`参数打印详细的编译日志。
* cgo环境下可以在`build`命令中增加`-work`参数将中间文件保留下来，或者使用`go tool cgo main.go`进行编译。
* 在`main`包里使用asm，必须使用`go build`，不能指定源文件（与cgo编译类似）
* 在使用`DATA`初始化内存时，初始化数据宽度必须是`1, 2, 4, 8`之一，因为再大的内存无法一次性用一个uint64来表示。
* X86处理器是小端字节序的。
* 在需要使用`NOPTR, RODATA`等标志位时，需要引入`#include "textflag.h"`，否则只能使用标志对应的值。
* Go汇编中，定义函数的`NOSPLIT`标志主要指示叶子函数不进行栈分裂（不会生成或包含栈分裂代码），一般用于没有其他任何函数调用的叶子函数，这样可以适当提高性能。
* Go汇编中，`WRAPPER`则表示这是一个包装函数，在`panic`或`runtime.caller`等处理函数帧的地方不会增加函数帧计数。
* Go汇编中，`NEEDCTXT`表示需要一个上下文参数，一般用于闭包函数。

### Go汇编伪寄存器

* `PC(Program counter)`：伪寄存器`PC`其实就是`IP`指令寄存器的别名
* 伪寄存器`FP(Frame pointer)`表示当前函数帧的地址，也就是第一个参数的地址。一般用来定位函数的参数和返回值。
* 伪寄存器`SB(Static Base Pointer)`表示为静态内存的开始地址。一般用于定位全局符号（变量或函数）
* 伪寄存器`SP(Stack pointer)`表示当前栈帧的底部，一般用于定位局部变量。
    * 真`SP`寄存器：对应栈的顶部（低地址），一般用于定位调用其他函数的参数和返回值。（`+0(SP)`, `+8(SP)`）
    * 伪`SP`寄存器：对应栈的底部（高地址），一般用于定位当前函数的局部变量，需要前置标识符和偏移量（`a-8(SP)`，`a-16(SP)`）


