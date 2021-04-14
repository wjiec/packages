CGO编程 - CGO的内部机制
--------------------

CGO特性主要是通过一个叫cgo的命令行工具来辅助输出Go语言和C语言之间的桥接代码。

>
> 在构建一个CGO包时增加`-work`参数可以指定输入中间生成文件的目录，并且在构建时保留中间文件。
> 
> 也可以通过手工调用`go tool cgo`命令来查看生成的中间文件。
>

cgo命令会为每个包包含CGO代码的Go文件创建2个中间文件（`xxx.cgo1.go`和`xxx.cgo2.c`）。然后会为整个包创建一个Go文件`_cgo_gotypes.go`，其中包含Go语言部分辅助代码。此外还会创建一个`_cgo_export.h`文件和一个`_cgo_export.c`文件，对应Go语言导出到C语言的类型和函数。


### Go调用C语言

在`go-to-c`中，使用命令`go tool cgo main.go`生成中间文件，生成的目录文件列表如下。
```text
_obj
├── _cgo_export.c
├── _cgo_export.h
├── _cgo_flags
├── _cgo_gotypes.go
├── _cgo_main.c
├── _cgo_.o
├── main.cgo1.go
└── main.cgo2.c
```

首先看入口文件`_cgo_main1.go`文件中调用C函数对应的纯Go方法`_Cfunc_sum`。
```go
func main() {
	fmt.Println(( /*line :12:14*/_Cfunc_sum /*line :12:18*/)(1, 2.0))
}
```

`_Cfunc_sum`该方法定义于文件`_cgo_gotypes.go`中（该文件中还包含一些CGO的辅助函数）
```go
//go:linkname _cgo_runtime_cgocall runtime.cgocall
func _cgo_runtime_cgocall(unsafe.Pointer, uintptr) int32

//go:cgo_import_static _cgo_6576b62df8e1_Cfunc_sum
//go:linkname __cgofn__cgo_6576b62df8e1_Cfunc_sum _cgo_6576b62df8e1_Cfunc_sum
var __cgofn__cgo_6576b62df8e1_Cfunc_sum byte
var _cgo_6576b62df8e1_Cfunc_sum = unsafe.Pointer(&__cgofn__cgo_6576b62df8e1_Cfunc_sum)

//go:cgo_unsafe_args
func _Cfunc_sum(p0 _Ctype_int, p1 _Ctype_float) (r1 _Ctype_double) {
	_cgo_runtime_cgocall(_cgo_6576b62df8e1_Cfunc_sum, uintptr(unsafe.Pointer(&p0)))
	if _Cgo_always_false {
		_Cgo_use(p0)
		_Cgo_use(p1)
	}
	return
}
```

__注意：这里的`_cgo_runtime_cgocall`函数被指向`runtime.cgocall`函数。__
该方法的定义于`runtime/cgocall.go`文件中，函数签名如下
```go
// fn 表示对应C语言函数的地址
// arg 表示存放C语言函数参数的结构体的地址
func cgocall(fn, arg unsafe.Pointer) int32
```

接下来，我们可以看到C函数的地址变量为`_cgo_6576b62df8e1_Cfunc_sum`，该方法会在链接的时候绑定到同名的符号上（其中`__cgofn__cgo_6576b62df8e1_Cfunc_sum`用作定位C函数地址）。

接下来就到C的桥接函数中，位于文件`main.cgo2.c`文件，内容如下
```c
void
_cgo_6576b62df8e1_Cfunc_sum(void *v)
{
	struct {
		int p0;
		float p1;
		double r;
	} __attribute__((__packed__, __gcc_struct__)) *_cgo_a = v; // 1
	char *_cgo_stktop = _cgo_topofstack(); // A
	__typeof__(_cgo_a->r) _cgo_r; // 2
	_cgo_tsan_acquire(); // B
	_cgo_r = sum(_cgo_a->p0, _cgo_a->p1); // 3
	_cgo_tsan_release(); // C
	_cgo_a = (void*)((char*)_cgo_a + (_cgo_topofstack() - _cgo_stktop)); // D
	_cgo_a->r = _cgo_r; // D
	_cgo_msan_write(&_cgo_a->r, sizeof(_cgo_a->r));
}
```

这里我们就可以很清晰的看到具体的调用流程。首先从参数指针中获得参数（1）、然后保存调用堆栈信息（A）、
声明返回值（2）、执行参数检查（B）、调用实际的C函数（3）、执行返回值检查（C）、获取需要实际返回值的地址并写入数据（D）。

Go语言和C语言有着不同的内存模型和函数调用规范。其中
 * `_cgo_topofstack`函数用于C函数调用后恢复Go调用栈
 * `_cgo_tsan_acquire`和`_cgo_tsan_release`用于扫描CGO相关函数的参数和返回值中的指针是否满足规范

完整的调用链路如下：
```text
main() [main.cgo1.go]
    -> _Cfunc_sum(p0 _Ctype_int, p1 _Ctype_float) (r1 _Ctype_double) [_cgo_gotypes.go]
        -> _cgo_runtime_cgocall(_cgo_6576b62df8e1_Cfunc_sum, uintptr(unsafe.Pointer(&p0))) [runtime/cgocall.go cgocall]
            -> _cgo_6576b62df8e1_Cfunc_sum(void *v) [main.cgo2.c]
                -> sum(_cgo_a->p0, _cgo_a->p1) [_cgo_.o]
            <- _cgo_a->r = _cgo_r; [main.cgo2.c]
        <- errno := asmcgocall(fn, arg) [runtime/cgocall.go cgocall]
    <- return (r1 _Ctype_double) [_cgo_gotypes.go]
<-
```

### C调用Go函数

为了在C语言中使用Go函数，我们需要先将Go代码编译为一个C静态库。此时会生成`main.a`静态库文件（包含导出函数的实现）和`main.h`头文件（包含导出函数的声明）。
```bash
go build -buildmode=c-archive -o main.a main.go
```

我们同时使用cgo命令生成中将文件，文件列表如下
```text
_obj
├── _cgo_export.c
├── _cgo_export.h
├── _cgo_flags
├── _cgo_gotypes.go
├── _cgo_main.c
├── _cgo_.o
├── main.cgo1.go
└── main.cgo2.c
```

其中大多数文件都是类似的内容，因为是从C调用Go语言，这里直接看`_cgo_export.c`中关于`sum`函数的实现。
```c
double sum(int p0, float p1)
{
	__SIZE_TYPE__ _cgo_ctxt = _cgo_wait_runtime_init_done(); // A
	struct {
		int p0;
		float p1;
		double r0;
	} __attribute__((__packed__, __gcc_struct__)) a; // 1
	a.p0 = p0; // 1
	a.p1 = p1; // 1
	_cgo_tsan_release(); // B
	crosscall2(_cgoexp_16940316f0f0_sum, &a, 16, _cgo_ctxt); // C
	_cgo_tsan_acquire(); // D
	_cgo_release_context(_cgo_ctxt); // E
	return a.r0; // 2
}
```

这里与之前类似，这里是初始化Go运行时环境（A）、将参数和返回值打包到同一段内存（结构体）（1）、调用Go方法实现（C）、返回结果值（2）。

其中从C调用Go函数的具体实现在`runtime/asm_[amd64].s`文件中（该方法适用汇编语言实现，需要根据不同架构找到不同的具体实现，这里以amd64为例）。函数签名为
```go
// fn 是中间代理函数的指针
// a 是参数和返回值的结构体指针
func crosscall2(fn func(a unsafe.Pointer, n int32, ctxt uintptr), a unsafe.Pointer, n int32, ctxt uintptr)
```

这里实际调用的中间函数为`_cgoexp_16940316f0f0_sum`，该方法定义在`_cgo_gotypes.go`文件中。
```go
//go:linkname _cgo_runtime_cgocallback runtime.cgocallback
func _cgo_runtime_cgocallback(unsafe.Pointer, unsafe.Pointer, uintptr, uintptr)

//go:cgo_export_dynamic sum
//go:linkname _cgoexp_16940316f0f0_sum _cgoexp_16940316f0f0_sum
//go:cgo_export_static _cgoexp_16940316f0f0_sum
//go:nosplit
//go:norace
func _cgoexp_16940316f0f0_sum(a unsafe.Pointer, n int32, ctxt uintptr) {
	fn := _cgoexpwrap_16940316f0f0_sum
	_cgo_runtime_cgocallback(**(**unsafe.Pointer)(unsafe.Pointer(&fn)), a, uintptr(n), ctxt);
}

func _cgoexpwrap_16940316f0f0_sum(p0 _Ctype_int, p1 _Ctype_float) (r0 _Ctype_double) {
	return sum(p0, p1)
}
```

这里可以看到`_cgoexp_16940316f0f0_sum`该函数将代理函数`_cgoexpwrap_16940316f0f0_sum`的地址传递给了`_cgo_runtime_cgocallback`方法，并由该方法完成C语言到Go语言的回调工作。

其中`_cgo_runtime_cgocallback`函数实际会在运行时调用`runtime.cgocallback`函数（汇编实现）
```go
func cgocallback(fn, frame unsafe.Pointer, framesize, ctxt uintptr)
```

完整的调用链路如下：
```text
sum(x, y) [?.c]
    -> crosscall2(_cgoexp_16940316f0f0_sum, &a, 16, _cgo_ctxt) [_cgo_export.c]
        -> [...asm...]
            -> _cgoexp_16940316f0f0_sum(a unsafe.Pointer, n int32, ctxt uintptr) [_cgo_gotypes.go]
                -> _cgo_runtime_cgocallback(**(**unsafe.Pointer)(unsafe.Pointer(&fn)), a, uintptr(n), ctxt) [_cgo_gotypes.go]
                    -> [...asm...]
                        -> _cgoexpwrap_16940316f0f0_sum(p0 _Ctype_int, p1 _Ctype_float) (r0 _Ctype_double) [_cgo_gotypes.go]
                            -> sum(p0, p1) [main.go]
                        <- [...in memory(pointer)...]
                    <- ...
                <- ...
            <- ...
        <- ...
    <- return a.r0
<-
```
