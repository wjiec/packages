Go汇编 - 威力及例子
----------------

汇编语言的真正威力来自两个维度：
 * 突破框架限制，实现看似不可能的任务
 * 突破指令限制，通过高级指令挖掘挖掘极致的性能

比如可以使用汇编执行系统调用（需要根据操作遵循不同的调用规范）。还可以直接调用C函数（注意C语言的调用约定，比如cdecl调用约定通过栈传递参数，通过AX寄存器返回结果）。


### 例子：获取GoroutineID

Go语言可以没有提供goid的原因是为了避免被滥用。有两种方法可以获取到GoroutineID：
 * 通过纯Go的`runtime.Stack`方法获取调用栈信息后解析字符串获得。
 * 从g结构中获取，每个运行的Goroutine结构的g指针保存在当前运行Goroutine的系统线程的局部存储TLS中。

可以使用runtime包中定义的`get_tls`宏获取g指针。
```text
get_tls(CX)
MOVQ g(CX), AX
```

从g结构（参考runtime/runtime2.go中g结构的定义）中获取`goid`字段，需要使用偏移量的方式获取。


### Delve调试器

Go语言支持的调试器有`GDB`, `LLDB`, `Delve`。
