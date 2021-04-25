Go的一些小知识点
-------------


* 可以在`build`命令中增加`-x`参数打印详细的编译日志。
* cgo环境下可以在`build`命令中增加`-work`参数将中间文件保留下来，或者使用`go tool cgo main.go`进行编译。
* 在`main`包里使用asm，必须使用`go build`，不能指定源文件（与cgo编译类似）
