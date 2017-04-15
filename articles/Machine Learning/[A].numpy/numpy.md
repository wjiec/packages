Python - numpy
--------------

```python
from numpy import *

# 生成 4 * 4 的随机数组(array)
array = random.rand(4, 4)


# 将 4 * 4 的随机数组转化为矩阵(matrix)
matrix = mat(random.rand(4, 4))
# 在数组(array)和矩阵(matrix)上执行相同的运算会得到不同的结果


# .I 运算得到这个矩阵的逆矩阵
inverse_matrix = mat(random.rand(4, 4)).I


# 将矩阵和它的逆矩阵相乘得到单位矩阵
#   得到的单位矩阵不是一个标准的单位矩阵
#       因为计算机运算存在误差
identity_matrix = matrix * inverse_matrix
# 单位矩阵：主对角线全为1，其他全为0的矩阵
#   主对角线：从左上角到右下角的对角线


# 得到一个 4 * 4 的单位矩阵
standard_identity_matrix = eye(4)


# 得到计算机计算的误差
deviation = identity_matrix - standard_identity_matrix
```
