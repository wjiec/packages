Opcode
---
> Opcode是计算机指令中的一部分，用于指定要执行的操作，指令的格式和规范由处理器的指令规范指定。

> 简而言之，Opcode就是运行在Zend VM中的指令(或者成为字节码 Byte Codes)

Installation
---
```
git clone https://github.com/derickr/vld.git vld # 也可以使用其他方式获取，下面介绍
cd vld
phpize
./configure --with-php-config=/usr/local/php7/bin/php-config --enable-vld # 注意替换自己的路径
sudo make install
sudo vim /usr/local/php7/etc/php.ini
```
> 在 php.ini 增加 __extension=vld.so__ , PHP7自己编译的需要自己新建php.ini文件
> !! 注意 php.ini 中 extension_dir="/usr/local/php7/lib/php/extensions/" 设置的位置

> [PECL::Package::vld](http://pecl.php.net/package/vld)
> [Download and Installation Instructions](https://derickrethans.nl/projects.html#vld)

Problem
---
> 这是在SF的另一篇博文(面试类型)中出现的，找了一下，没找到文章。特引用其中一个例子，讲解VLD的使用
> 如果有哪位朋友找到了还请告诉我一声，我会注上说明，谢谢！

看以下栗子
```php
$apple = 1;

if (-1) {
    $apple = 2;
}

echo $apple;
```

相信很多人看到的第一反应是输出1，这不是很简单么?然而并不是，输出的是2(PHP 7.0.4 (cli)) [CodePad](http://codepad.org/WLePpxAa)

Why
---
在安装好vld扩展之后，我们马上就可以知道是为什么，首先验证下vld已经安装
```
php -m | grep vld # 看到输出一行 vld 就对了
```

我们把这个栗子写到一个test.php(文件名随意)文件中, 执行以下命令
```shell
php -dvld.active=1 test.php # 参数表示激活vld扩展
```
输出应该如下所示，
```
Finding entry points
Branch analysis from position: 0
Jump found. Position 1 = 2, Position 2 = 3
Branch analysis from position: 2
Jump found. Position 1 = -2
Branch analysis from position: 3
filename:       /home/shadowman/test/test.php
function name:  (null)
number of ops:  6
compiled vars:  !0 = $a
line     #* E I O op                           fetch          ext  return  operands
-------------------------------------------------------------------------------------
   3     0  E >   ASSIGN                                                   !0, 1
   5     1      > JMPZ                                                     -1, ->3
   6     2    >   ASSIGN                                                   !0, 2
   9     3    >   CONCAT                                           ~3      !0, '%0A'
         4        ECHO                                                     ~3
         5      > RETURN                                                   1

branch: #  0; line:     3-    5; sop:     0; eop:     1; out1:   2; out2:   3
branch: #  2; line:     6-    9; sop:     2; eop:     2; out1:   3
branch: #  3; line:     9-    9; sop:     3; eop:     5; out1:  -2
path #1: 0, 2, 3, 
path #2: 0, 3, 
2
```
重点内容为
> [Opcode Descriptions and Examples](http://jp2.php.net/manual/zh/internals2.opcodes.list.php#internals2.opcodes.list)
```
number of ops:  6 # 表示有6个操作
compiled vars:  !0 = $a # 编译之后的变量， ![number] 表示定义的变量，~[number]表示临时变量
line     #* E I O op                           fetch          ext  return  operands
-------------------------------------------------------------------------------------
   3     0  E >   ASSIGN                                                   !0, 1
# 以上表示将1这个字面值赋值给!0,即$a
   5     1      > JMPZ                                                     -1, ->3
# 重点！！以上对于 if (-1). opcode解释为如果-1为0，则跳转到3(Jump to the address if the value is zero)
   6     2    >   ASSIGN                                                   !0, 2
# 以上表示将2这个字面值赋值给!0,即$a
   9     3    >   CONCAT                                           ~3      !0, '%0A'
# 以上表示拼接 !0($a) 和 '%0A'(\n) 赋值给一个临时变量
         4        ECHO                                                     ~3
# 以上表示输出 !0($a)
         5      > RETURN                                                   1
# 以上表示返回
```
可以很简单的得知，为什么这里会有这个奇葩的"错误"。

Conclusion
---
其实vld这个扩展可以做到更多，比如纠结性能问题，可以使用这个扩展尽可能的优化你的PHP代码，或者使用这个扩展排除一些非常隐性的错误。

如上！谢谢！
