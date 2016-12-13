> 在编程的世界中数据结构和算法总是形影不离, 难舍难分的.
>
> 栈作为一种常见的数据结构(*抽象数据类型*)在程序的世界中有非常的意义.

栈
===
> 在计算机科学中,栈是一种抽象数据类型(`ADT / Abstract Data Type`),用作数据的集合表示.栈有两个主要的操作
> * `push`用于将元素推入栈中
> * `pop` 用于将元素从栈顶部弹出

简单来说,栈就是一个后入先出(`LIFO / Last In First Out`)的队列.现实生活中叠盘子就是一个形象的栈,新盘子只能在顶部堆叠进去,而抽盘子是从顶部一个个抽走.

栈的应用
===
栈在计算机中有非常广泛的应用,比如说函数的调用堆栈.谈点更实在的应用的话,栈可以非常方便的用来做平衡符号, 表达式求值和语法解析.
今天的重点是**通过栈实现一个中缀表达式到后缀表达式的转换**,为之后的**构建表达式树**做铺垫.
>  使用栈来实现平衡符号其实非常简单
> * 遇到 `'(', '[', '{'`就将符号推入栈中
> * 遇到 `')', ']', '}'`就弹出栈中的一个元素,查看是否匹配
> * 处理完所有数据之后栈应该为空.
> 
> 这个算法的时间复杂度为`O(N)`并且这个算法是在线的.

后缀表达式
=========
常见的表达式如`a + b * c + g / f`在计算这个表达式时,我们必须明确记住操作符的优先级, `+, -`的优先级小于`*, /`所以表达式处理上就会比较复杂.如果我们换种思路,将中缀表达式转换为后缀表达式,那处理就会简单很多.

这种记法其实就是将我们口头上的说法搬到纸上.
* b和c相乘 => `b c *`(A1)
* a与b和c相乘的结果相加 => `a (A1) +`(A2)
* g被f除 => `g f /`(A3)
* 前面的结果与后面的结果相加 => `A2 A3 +`
* 将`A2 A3 +`展开之后就得到了`a b c * + g f / +`

最后展开的结果就是中缀表达式`a + b * c + g / f`转换成后缀表达式的结果`a b c * + g f / +`,这种记法叫做后缀记法或者逆波兰记法.
计算这个表达式的值的简单方法就是使用栈.
* 当遇到一个操作数时就将这个操作数推入栈中
* 当遇到一个操作符时就从栈中弹出两个操作数, 并将这个操作符运用于弹出的两个操作数上

这种方法显而易见的一个大优点就是没有必要知道任何优先级规则.而且这个算法的时间复杂度是O(N), 而且是在线的.

那么问题来了,如何将中缀表达式转换成后缀表达式呢?

中缀 => 后缀
================
显然,我们也可以通过栈来进行转换.记住以下几个规则
* 当遇到一个操作数时,将这个操作数输出
* 当遇到一个操作符时,**将栈中大于等于该符号优先级的操作符弹出**(并输出).**然后将该操作符压入栈中**
* 当遇到特殊操作符开括号(`'(', '[', '{'`)时,**将该符号压入栈中.但是把从这个符号开始当做一个新栈**(可以考虑递归的调用栈)
* 当遇到特殊操作符闭括号(`')', ']', '}'`)时,将**最新**的那个栈中的元素**全部弹出**(并输出, 但是不输出弹出的开括号)

规则就这么多,是不是感觉很难理解?我们来看个栗子~
```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h> // 用于判断字符类型

int sp = -1; // 栈顶指针, 始终指向栈顶, 空栈时指向栈底(0)之下
char symbol_stack[64] = { 0 }; // 显然,我们需要个符号栈来存放操作符

// 定义几个栈操作
void push(char op); // 将符号 op 推入栈中
char pop(void); // 弹出栈顶元素
char top(void); // 查看栈顶的元素

int main(void) {
    // 定义一个输入表达式
    char *input = "a + b * c - g / f";
    
    // 开始处理输入表达式
    // 我们只进行四则运算的转换
    for (int index = 0; index < strlen(input); ++index) {
        char current_character = input[index];
        char stack_top_character = top();

        // 如果是操作数,那就直接输出
        if (isdigit(current_character) || isalpha(current_character)) {
            printf("%c ", current_character);
            continue;
        }

        // 如果不是操作数,并且不是 '+', '-', '*', '/', '(', ')', ' ' 那就报错
        if (current_character != '+' && current_character != '-' &&
            current_character != '*' && current_character != '/' &&
            current_character != '(' && current_character != ')' &&
            current_character != ' ') {
            fprintf(stderr, "Unknown symbol `%c`\n", current_character);
            exit(1);
        }

        // 跳过空格和结束标志
        if (current_character == ' ' || current_character == '\0') {
            continue;
        }

        switch (current_character) {
            // 特殊操作符 (
            case '(':
                push(current_character);
                break;
            // 特殊操作符 )
            case ')':
                // 弹出新栈的所有元素, 或者直到为空栈
                while (stack_top_character != '(' && stack_top_character != '\0') {
                    printf("%c ", stack_top_character);
                    stack_top_character = pop();
                }
                pop(); // 弹出多余的 (
                break;
            case '*':
            case '/':
                // 除了 '(' ')' 之外, * / 具有最高的优先级, 除非栈中不可能有比 * / 更大的优先级
                // 所以只能弹出优先级相等的操作符(本身)
                while (stack_top_character == '*' || stack_top_character == '/') {
                    printf("%c ", pop());
                    stack_top_character = top();
                }
                // 执行完之后压入当前的符号
                push(current_character);
                break;
            case '+':
            case '-':
                // + - 具有最低的优先级, 所以弹出所有的操作符,除非是代表新栈的 (
                while (stack_top_character != '(' && stack_top_character != '\0') {
                    printf("%c ", pop());
                    stack_top_character = top();
                }
                // 执行完之后压入当前的符号
                push(current_character);
                break;
        }
    }
    // 最后执行清空所有栈
    while (top() != '\0') {
        printf("%c ", pop());
    }

    return 0;
}

// 入栈操作定义
void push(char op) {
    symbol_stack[++sp] = op;
}
// 出栈操作定义
char pop(void) {
    return symbol_stack[sp--];
}
// 查看操作定义
char top(void) {
    return sp == -1 ? '\0' : symbol_stack[sp];
}

```
看我代码是不是感觉就一目了然了呢?如果还是不懂的话,那是我的锅-^-(面壁思过).其实自己再纸上演算一下就好啦~
接下来就是使用转换出的后缀表达式构建表达式树了,其实也是用栈的方式啦~