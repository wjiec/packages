Go汇编语言 - 快速开始
------------------

> Plan9汇编语言是【Plan9系统上C编译器】输出的【汇编伪代码】。

Go汇编语言必须以Go包的方式组织，同时包中至少要有一个Go语言文件用于指明当前包名等基本包信息。如果Go汇编代码中定义的变量和函数要被其他Go语言代码引用，还需要在Go语言代码中将汇编语言定义的符号声明出来。


### 定义整数变量

使用`go tool compile -S dump.go`导出的知识目标文件对应的汇编，和Go汇编语言虽然相似但并不完全等价。Go汇编语言提供了`DATA`命令用于初始化包变量，语法如下
```asm
DATA symbol+offset(SB)/width, value
```
其中`symbol`为变量在汇编语言中对应的标识符，`offset`是符号开始地址的偏移量，`width`是要初始化内存的宽度大小，`value`是要初始化的值。**当前包中定义的符号`xxx`，在汇编代码中对应的是`·xxx`**

>
> 该符号前面的是一个特殊的Unicode符号（MIDDLE DOT, \\u00b7）
>

变量定义好之后需要导出以供其他代码引用，Go汇编代码提供了`GLOBL`命令用于将符号导出
```asm
GLOBL symbol(SB), width
```

### 定义字符串变量

虽然从Go语言角度看，定义字符串和整数变量的写法基本相同，但是字符串底层却有着比单个整数更复杂的数据结构。参见
```go
// unsafe/value.go
type StringHeader struct {
	Data uintptr
	Len  int
}
```

首先来看Go定义字符串生成的汇编代码
```text
/*
var Name string
var Nickname = "nickname value"
*/

go.string."nickname value" SRODATA dupok size=14
	0x0000 6e 69 63 6b 6e 61 6d 65 20 76 61 6c 75 65        nickname value
"".Name SBSS size=16
"".Nickname SDATA size=16
	0x0000 00 00 00 00 00 00 00 00 0e 00 00 00 00 00 00 00  ................
	rel 0+8 t=1 go.string."nickname value"+0
```

因为Go语言的字符串并不是值类型，而是一种只读的引用类型。该符号上可以看到有`SRODATA`标志，表示这个数据在只读内存段，`dupok`(dup/ok)表示出现多个相同标识符的数据时只保留一个就可以了。

根据以上汇编代码看，字符串变量对应的是`reflect.StringHeader`结构体类型。前8个字节对应底层真实字符串数据的指针，也就是符号`go.string."nickname value"`的地址，后8个字节对应底层真实字符串数据的有效长度（`0x0e`）。

所以在Go汇编中定义字符串需要手工构建一个`reflect.StringHeader`结构体。

注意，在使用`DATA`创建一个字符串时，会出现报错`missing Go type information for global symbol: size 8`
```text
GLOBL ·NameData(SB), $8
DATA ·NameData+0(SB)/8, $"string"

GLOBL ·Name(SB), $16
DATA ·Name+0(SB)/8, $·NameData(SB) // ptr
DATA ·Name+8(SB)/8, $5 // length
```
看报错字面意思是变量`NameData`缺少类型信息。其实真实原因是Go垃圾回收期无法判断`NameData`里是否包含指针（如果是指针则需要回收指针指向的内存），所以报错实际是指`NameData`变量没有标注是否含有指针。解决办法也很简单，要么增加没有指针的标志，要么在Go里声明该变量的类型为不包含指针（如`[8]byte`）。

或者在定义字符串时，将底层的字符串和字符串头结构定义在一起，避免引入多余的符号。
```text
GLOBL ·Name(SB), $16

DATA ·Name+0(SB)/8, $·Name+16(SB) // ptr
DATA ·Name+8(SB)/8, $5 // length
DATA ·Name+16(SB)/5, $"string"
```


### 定义函数

在汇编中定义函数，大致的实现（在函数调用时，Go语言函数完全通过栈传递调用参数和返回值）为：
```text
TEXT ·func(SB), $FRAME_SIZE-RETURN_SIZE
    // ...
    // CALL ...
    // ...
    RET
```

Go汇编同样遵循Go语言“少即是多”的哲学，只保留了最基本的特性：定义变量和全局函数。
