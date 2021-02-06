Bolt源码系列一：Bolt主程序
----------------------

### Bolt简介

Bolt是一个使用纯Go编写的键值对形式数据库，数据库的目的是为不需要完整数据库服务（如MySQL或者Postgres）
的项目提供一个简单，快速，可靠的数据库实现（类似于SQLite）。

官方仓库：[boltdb/bolt][1]


### 关于本次源码阅读

阅读源码也是程序员的一个重要的能力纬度，如何快速阅读源码，如何去解答自己项目中遇到的一些疑惑。
同时还可以在源码阅读过程中体会高手们是如何思考的，这对于提升自己的能力也有很大的好处。


### 本章的主要目的

本篇是先总体看下Bolt的主程序，了解一些Bolt的基础概念。我们先从主程序入手，看看作者是如何使用
和检查Bolt的。


### 主程序初始化和命令行解析

在`main.go`中，主程序是非常简单明了的，首先调用`NewMain()`方法保存了标准输入/标准输出/标准错误
的引用，然后直接调用`Run`方法手动解析命令行参数（`os.Args`）。然后根据是否返回错误决定是如何退出。

```go
func main() {
	m := NewMain()
	if err := m.Run(os.Args[1:]...); err == ErrUsage {
		os.Exit(2)
	} else if err != nil {
		fmt.Println(err.Error())
		os.Exit(1)
	}
}
```

#### 关于`os.Args`

首先来看Go对该全局变量的解释
```plain
Args hold the command-line arguments, starting with the program name.
```
根据描述，`os.Args`是一个保存了命令行参数的字符串数组，并且第一个元素是程序的名字。

所以在程序中，使用切片`os.Args[1:]`将其去除程序名字后，传入`Run`方法，用于解析子命令和参数。

#### 关于退出状态

在`main`方法中判断了返回的错误，如果是`ErrUsage`则表示是错误的使用。这其实是有一套相关的规范的。
根据规范，如果程序没发生任何错误，则返回值为0。如果发生了错误，一般返回值为1。如果是命令使用错误，
如参数无效，或者子命令错误等应该返回错误码2。

相关规范：
 - [Exit Codes With Special Meanings][2]
 - [GNU Exit Status][3]

至于为什么这么设计，有很多的说法，比如“没有消息（返回0）就是最好的消息（成功）”，
“与正确只有1种，而错误有无数种相对应”，“返回0可以节省一条汇编指令”等等。

在接口设计中我们也可以考虑使用这种设计，比如在接口中一般会有业务错误码，我们就可以使用0表示
业务执行成功。大于0表示客户端请求导致的错误，小于0表示服务导致的错误。

### `Main.Run`方法逻辑

在`Run`方法中，首先简单判断了传入的命令行参数是否足够长度和是否指定了需要执行的子命令
```go
if len(args) == 0 || strings.HasPrefix(args[0], "-") {
    _, _ = fmt.Fprintln(m.Stderr, m.Usage())
    return ErrUsage
}
```

然后再根据第一个命令行参数进行相应的子命令处理。
```go
switch args[0] {
case "help":
    // 打印帮助信息
    _, _ = fmt.Fprintln(m.Stderr, m.Usage())
    return ErrUsage
case "...":
    // ...
default:
    return ErrUnknownCommand
}
```

> 扩展阅读：如果只是简单的处理命令行参数的情况下可以这么干，复杂点的话还是推荐使用第三方开发完善的
> 命令行参数解析库。简单的可以选择使用 [google/subcommands][4]，复杂的话推荐使用 [spf13/cobra][5]。
> 
> cobra不止可以提供现代化的命令行参数解析处理能力，还提供了各种高级特性，如执行期钩子方法，自动生成帮助命令，
> 在输入错误命令时提示可能想要输入的命令。


### 主程序的所有相关子命令

接下来我们来一起看所有支持的子命令。看的顺序按照switch中的执行顺序来。

#### help子命令

这个命令没啥好说的，就直接打印使用说明到标准错误中，然后直接返回`ErrUsage`。接着程序以错误码2退出执行。

```go
_, _ = fmt.Fprintln(m.Stderr, m.Usage())
return ErrUsage
```

#### bench子命令

bench子命令会稍微复杂点，该命令主要是用来做基准性能测试用的。主要是做机器性能测试和程序的性能调优使用。

bench子命令的主体执行流程如下：
 - 解析bench子命令的命令行参数
 - 检查是否需要在执行完成之后删除数据库文件（根据work参数指定是否保留）
 - 打开或者创建数据库文件（根据path参数指定文件路径）
 - 执行写入测试（根据profile-mode参数决定是否开启运行时数据收集）
    - 执行写入测试有4种模式，分别为seq(顺序键写入)，rnd(随机键写入)，
    seq-nest（顺序的嵌入Bucket写入）rnd-nest（随机键的嵌套Bucket写入）
 - 根据profile-mode参数决定是否关闭运行时信息收集（只分析写入时的运行时数据）
 - 执行读取测试（根据profile-mode参数决定是否开启运行时数据收集）
    - 执行读取测试有2种模式，分别是正常读取和嵌套Bucket读取（根据写入是否模式是否带-nest判断）
 - 根据profile-mode参数决定是否关闭运行时信息收集（只分析读取或者分析读取写入的运行时数据）
 - 打印结果


##### 1、解析bench命令的命令行参数

在这里使用的是`flag`包中提供的`FlagSet`来解析命令行参数。
```go
var options BenchOptions

fs := flag.NewFlagSet("", flag.ContinueOnError)
fs.StringVar(&options.ProfileMode, "profile-mode", "rw", "")
// ...
fs.StringVar(&options.Path, "path", "", "")
fs.SetOutput(cmd.Stderr)
if err := fs.Parse(args); err != nil {
    return nil, err
}
```
这里将相关命令行参数与options中的属性进行了绑定，在执行Parse时，options的属性就会得到相应的值。
介于原始版本并没有提供每个属性的具体解释，我这里给出一份。
```go
type BenchOptions struct {
	// 在哪些场景下需要开启 Profiling 收集CPU, Mem, Block的动态信息
	// r - 表示在读取的时候开启
	// w - 表示在写入的时候开启
	// rw - 表示在读取和写入时都开启 (默认值)
	ProfileMode   string

	// 写入模式
	// seq - 顺序写入 (默认值)
	// rnd - 随机写入
	// seq-nest - 嵌套 Bucket 的顺序写入
	// rnd-nest - 嵌套 Bucket 的随机写入
	WriteMode     string

	// 读取模式
	// req - 顺序读取 (默认值)
	ReadMode      string

	// 操作执行次数, 表示为 Write/Read 的总键值对个数
	// 默认值为 1000
	Iterations    int

	// 表示为每次批量执行的键值对个数
	// 分为两种情况去看, 在非 nested 测试下, 表示每次批量执行操作的键值对个数,
	// 在 nested 测试下表示为嵌套 Bucket 中的键值对数量
	// 默认值为 0 (会被覆盖为与 Iterations 值相同)
	BatchSize     int

	// 键的长度
	// 默认值为 8
	KeySize       int

	// 值的长度
	// 默认值为 32
	ValueSize     int

	// Cpu Profile 文件名
	CPUProfile    string

	// 堆申请数据信息文件名
	MemProfile    string

	// 在同步上阻塞的堆栈信息的保存文件名
	BlockProfile  string

	// 暂时无用
	StatsInterval time.Duration

	// Bucket 文件填充的比例, 超过该数值将会发生文件分割
	// 默认值为0.5
	FillPercent   float64

	// 是否在每次 commit 之后执行 fsync() 方法刷数据到文件系统
	NoSync        bool

	// 是否在执行结束之后删除数据库文件
	Work          bool

	// 数据库文件的路径
	Path          string
}
```

这里需要特别注意`BatchSize`这个参数在不同`WriteMode`下会有不同的含义。`BatchSize`和`Iterations`
的值还有一个潜规则是`Iterations`需要能被`BatchSize`整除。这是因为在执行写入和读取测试时是批量进行操作，
如果不能整除，可能会出现执行写入和读取数量不对的问题。
```go
if options.BatchSize == 0 {
    options.BatchSize = options.Iterations
} else if options.Iterations%options.BatchSize != 0 {
    return nil, ErrNonDivisibleBatchSize
}
```

接下来是个骚操作的地方，如果在未指定数据库文件的情况下，会使用`ioutil.TempFile()`方法生成一个随机的
临时文件，然后在获取成功之后，关闭并删除创建好的文件，只是为了获取这个随机临时文件的文件名= =
```go
if options.Path == "" {
    f, _ := ioutil.TempFile("", "bolt-bench-")

    // 关闭临时文件, 然后删掉
    _ = f.Close()
    _ = os.Remove(f.Name())

    // 获取临时文件的路径
    options.Path = f.Name()
}
```

至此，bench子命令的参数解析完毕，接下来开始准备创建文件和执行测试。

##### 2、打开或者创建数据库文件及其准备工作

这里的准备工作比较简单，首先根据work参数决定是否需要在执行结束之后删除数据库文件。然后直接打开
path参数指定的数据库文件，同时赋值数据库的`NoSync`参数（表示是否需要在每次commit时将数据写入磁盘）即可。
```go
if options.Work {
    _, _ = fmt.Fprintf(cmd.Stdout, "work: %s\n", options.Path)
} else {
    // 执行结束之后删除文件(可能是随机文件)
    defer func() { _ = os.Remove(options.Path) }()
}

// 创建数据库文件
db, _ := bolt.Open(options.Path, 0666, nil)
db.NoSync = options.NoSync
defer func() { _ = db.Close() }()
```

##### 3、开始执行性能测试和收集执行过程中的运行时数据

因为需要先写入之后才能执行读取，所以先执行写入测试。在写入测试时，会根据`ProfileMode`是否是`w`或者`rw`
来决定是否开启运行时数据收集（主要为CPU，堆创建对象信息，发生阻塞时的堆栈信息）。
接下来就直接根据`WriteMode`来决定如何写入数据。

`seq`和`rnd`主要体现在键的生成上是顺序的还是随机的。这里主要影响的是键hash过程中的复杂程度。
```go
// 在一个Bucket中写入 Iterations 个键值对, 每次批量写入 BatchSize 个键值对
func (cmd *BenchCommand) runWritesWithSource(db *bolt.DB, options *BenchOptions, results *BenchResults, keySource func() uint32) error {
	// ...
	key := make([]byte, options.KeySize)
    binary.BigEndian.PutUint32(key, keySource())
	// ...
}

// seq
var i = uint32(0)
cmd.runWritesWithSource(db, options, results, func() uint32 { i++; return i })

// rnd
r := rand.New(rand.NewSource(time.Now().UnixNano()))
return cmd.runWritesWithSource(db, options, results, func() uint32 { return r.Uint32() })
```

而是否`nest`则决定是否采用嵌套Bucket的方式进行写入。
```go
// 非nest
func (cmd *BenchCommand) runWritesWithSource(db *bolt.DB, options *BenchOptions, results *BenchResults, keySource func() uint32) error {
	// ...
	b, _ := tx.CreateBucketIfNotExists(benchBucketName)
	// ...
	_ = b.Put(key, value)
	// ...
	return nil
}

// nest
func (cmd *BenchCommand) runWritesNestedWithSource(db *bolt.DB, options *BenchOptions, results *BenchResults, keySource func() uint32) error {
    // ...
    top, _ := tx.CreateBucketIfNotExists(benchBucketName)
    // ... seq or rnd
    name := make([]byte, options.KeySize)
    binary.BigEndian.PutUint32(name, keySource())
    // ...
    b, err := top.CreateBucketIfNotExists(name)
    // ...
    _ = b.Put(key, value)
    // ...
	return nil
}

```

有趣的一点是，在这里代码其实是有错误的\[doge\]。官方不管是否nest都直接调用了非nest的方法，
实际上nest的方法是已经提供了的。

在写入测试完成之后，接下来就是读取的相关测试。因为读取不能使用随机的方式，所以对于读取只做是否
nest的判断。
```go
switch options.ReadMode {
case "seq":
    switch options.WriteMode {
    case "seq-nest", "rnd-nest":
        err = cmd.runReadsSequentialNested(db, options, results)
    default:
        err = cmd.runReadsSequential(db, options, results)
    }
default:
    return fmt.Errorf("invalid read mode: %s", options.ReadMode)
}
```

我觉得这个`ReadMode`的参数就很多余，直接根据`WriteMode`走不同的判断就可以了。
需要注意的是，因为数据量可能过少（默认写入1000条数据），读取的时候需要重复执行直到时间达到1秒
以上才可以退出测试。

读取相关的代码我就不贴出来了，其实就是根据是否nest遍历数据库元素，然后检查数量是否正确就可以。

##### 4、收集运行时数据和打印结果

在写入/读取测试的开头都有根据`ProfileMode`进行判断是否开启收集运行时数据的逻辑。得到的数据可以
用于分析在写入、读取或者写入+读取环境下的性能瓶颈和资源使用情况。

需要注意的是，需要在命令行参数中提供`cpuprofile`、`memprofile`，`blockprofile`才会进行相应
的数据收集工作，否则是不会执行操作的。

最后就是根据执行的测试输出测试结果。就是简单的计算下每次操作需要多少时间以及每秒钟能执行多少次操作。
```go
_, _ = fmt.Fprintf(os.Stderr, "# Write\t%v\t(%v/op)\t(%v op/sec)\n",
    results.WriteDuration, results.WriteOpDuration(), results.WriteOpsPerSecond())

_, _ = fmt.Fprintf(os.Stderr, "# Read\t%v\t(%v/op)\t(%v op/sec)\n",
    results.ReadDuration, results.ReadOpDuration(), results.ReadOpsPerSecond())
```




 [1]: https://github.com/boltdb/bolt
 [2]: https://tldp.org/LDP/abs/html/exitcodes.html
 [3]: https://www.gnu.org/software/bash/manual/html_node/Exit-Status.html
 [4]: https://github.com/google/subcommands
 [5]: https://github.com/spf13/cobra
 
