CGO编程 - 类型转换
----------------

现在CGO已经成为了C语言和GO语言双向通信的桥梁。了解两种语言类型之间的转换规则是利用好CGO特性的必要前提。


### 数值类型

在Go语言中访问C语言的符号时，一般都是通过虚拟的`"C"`包访问的，因此CGO为C语言的基础数值类型都提供了相应转换规则。

__虽然C语言中的int,short等类型的内存大小依赖于具体实现，但是在CGO中它们的内存大小是确定的。__

部分常用类型和GO中类型对应关系和大小（C语言类型 - CGO类型 - GO语言类型）：
 * `char --- C.char --- byte`
 * `signed char --- C.schar --- int8`
 * `unsigned char --- C.uchar --- uint8`
 * `(unsigned)short --- C.(u)short --- (u)int16`
 * `(unsigned)int --- C.(u)int --- (u)int32`
 * `(unsigned)long --- C.(u)long --- (u)int32`
 * `(unsigned)long long int --- C.(u)longlong --- (u)int64`
 * `float --- C.float --- float32`
 * `double --- C.double --- float64`
 * `size_t --- C.size_t --- uint`

在`_cgo_export.h`中，每个基本的Go数值类型都定义了相应的C语言类型：`GoInt`, `GoUint8`, `GoFloat32`

__为了提高C语言的可移植性，更好的做法是使用C99标准引入的stdint.h头文件中定义的数值类型。__


### Go字符串和切片

`_cgo_export.h`中卫Go语言的字符串、切片、字典、接口和通道等特有的数据类型都生成了对应的C语言类型。
```c
typedef struct { const char *p; ptrdiff_t n; } _GoString_;
typedef _GoString_ GoString;
typedef void *GoMap;
typedef void *GoChan;
typedef struct { void *t; void *v; } GoInterface;
typedef struct { void *data; GoInt len; GoInt cap; } GoSlice;
```

__因为Go并未对除了字符串和切片之外的类型提供相应的辅助函数，导致我们无法保持这些类型在GO语言特有内存模型下的内存指针，所以它们在C语言环境下并无使用价值。__

在C中可以通过以下两个函数获取字符串结构中的长度和指针信息：
```c
size_t _GoStringLen(_GoString_ s);
const char *_GoStringPtr(_GoString_ s);
```


### 结构体、联合和枚举类型

C语言中的结构体、联合、枚举类型不能作为匿名成员被嵌入到Go语言的结构体中。

在Go语言中我们可以通过`C.struct_xxx`来访问C语言中定义的`struct xxx`结构体类型。如果结构体的成员名字真好是Go语言的关键字，则在成员名开头添加下划线来访问。
```go
package main
/*
struct _node_t {
    int type;
    float value;
};
*/
import "C"
import "fmt"

func main() {
	var node C.struct__node_t
    fmt.Println(node._type)
    fmt.Println(node.value)
}
```

__如果真好存在一个成员正好是以下划线和Go关键字命名的，那以Go关键词命名的成员将无法访问。__
```cgo
/*
struct _node_t {
    int type;
    float _type; // forbidden member 'type' access
};
*/
import "C"
```

结构体的内存布局按照C语言的通用对齐规则（在32位的Go语言环境下按照32位对齐），对于指定了特殊对齐规则的结构体则无法在CGO中访问。
```c
#pragma pack(1)
struct _node_t {
    uint8_t flags : 4;
    uint8_t syn : 1;
    uint8_t fin : 1;
    // auto 2b padding
}
```






