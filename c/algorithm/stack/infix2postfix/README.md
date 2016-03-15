infix => postfix
---
```shell
infix2postfix 'a + b * c + ( d * e + f ) * g'
# a b c * + d e * f + g * +
```

op > top   ==>  push
top >= op  ==>  put(pop)
op == '('  ==>  push('(')
op == ')'  ==>  loop push => '('
