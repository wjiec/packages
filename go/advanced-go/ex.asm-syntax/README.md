Go汇编 - 语法
------------

[doc](https://golang.org/doc/asm)

 * Global data symbols are defined by a sequence of initializing `DATA` directives followed by a `GLOBL` directive.
 * Adding `<>` to the name, as in `foo<>(SB)`, makes the name visible only in the current source file, like a top-level `static` declaration in a C file. 


### `DATA`

`DATA` directive initializes a section of the corresponding memory. **The memory not explicitly initialized is zeroed**. The general form of the DATA directive is
```text
DATA	symbol+offset(SB)/width, value
```
which initializes the symbol memory at the given offset and width with the given value. **The `DATA` directives for a given symbol must be written with increasing offsets**.


### `GLOBL`

The `GLOBL` directive declares a symbol to be global. The arguments are **optional** flags and the **size of the data** being declared as a global, which will have initial value all zeros unless a `DATA` directive has initialized it. The `GLOBL` directive must follow any corresponding DATA directives.
```text
GLOBL	symbol, [flags], width
```

