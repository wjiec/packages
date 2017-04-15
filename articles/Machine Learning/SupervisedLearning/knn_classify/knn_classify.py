#!/usr/bin/env python
#
# Copyright (C) 2017 ShadowMan
#
import operator
import numpy as np

group = np.array([
    [1.0, 1.1],
    [1.0, 1.0],
    [0.0, 0.0],
    [0.0, 0.1]
])
labels = ['A', 'A', 'B','B']


def knn_classify(inX, data_set, labels, k):
    # shape 或者该 array/matrix 的大小, 结果为 [行数, 列数]
    data_set_row_size = data_set.shape[0]  # >>> 4
    # 扩展 array/matrix
    # 第二个参数如果是 int, 那就在列方向上重复 第一个参数 n 次
    #   [[1,2],[3,4]] 2  ->  [[1,2,1,2],[3,4,3,4]]
    # 第二个参数是元组，那就在列方向上重复 t[0] 次，在行方向上重复 t[1] 次
    #   [[1,2],[3,4]] (2, 3)  ->  [[1,2,1,2],[3,4,3,4],[1,2,1,2],[3,4,3,4],[1,2,1,2],[3,4,3,4]]
    extra_array = np.tile(inX, (data_set_row_size, 1))  # >>> [[1.0, 0.9], [1.0, 0.9], [1.0, 0.9], [1.0, 0.9]]
    # 将扩展的输入与每个数据进行对比，计算距离
    #   上面已经将输入扩展为和原有数据集相同的行数
    #       所以直接将 扩展的数据数组 - 已有数据数组  =>  输入数据与原有每条数据对应的差值
    # [[1.0, 1.1], [1.0, 1.0], [0.0, 0.0], [0.0, 0.1]]
    # [[1.0, 0.9], [1.0, 0.9], [1.0, 0.9], [1.0, 0.9]]
    #--------------------------------------------------
    # [[0.0, 0.2], [0.0, 0.1], [-1., -.9], [-1., -.8]]
    difference_array = extra_array - data_set  # >>> [[0.0, 0.2], [0.0, 0.1], [-1., -.9], [-1., -.8]]
    # 计算差值的平方
    square_difference_array = difference_array ** 2  # >>> [[0.0, 0.04], [0.0, 0.01], [1.0, 0.81], [1.0, 0.64]]
    # 计算差值平方和
    #   axis = 1  =>  同行相加，[[1,2],[3,4]]  =>  [3,7]
    #   axis = 0  ->  同列相加，[[1,2],[3,4]]  =>  [4,6]
    # 所以这是将每个数据行的差值的平方加起来
    square_difference_matrix_sum = square_difference_array.sum(axis=1)  # >>> [0.04, 0.01, 1.81, 1.64]
    # 计算距离：将每个平方和开根号
    # 计算距离的公式为：sqrt( sum( ((X1 - X2) ** 2) + ((Y1 - Y2) ** 2) ) )
    #  其实就是在坐标轴上计算两点距离，即计算三角形第三条边
    distances = square_difference_matrix_sum ** 0.5  # >>> [0.2, 0.1, 1.3453624, 1.28062485]
    # 根据距离进行排序
    #   返回值是一个数组，数组里是根据输入数组的值从小到大排序的索引
    #       np.array([2, 1, 3])  =>  [1, 0, 2]
    #           最小的值的索引是1，其次是0，最后是2
    sorted_distances = distances.argsort()  # >>> [1, 0, 3, 2]
    # 用于储存前k个最佳匹配的标签
    #   label => occurs_count
    vote_labels = {}
    for i in range(k):
        # 获取前 i 个最佳匹配的标签
        #   sorted_distances[i]  =>  第 i 个最近距离的标签的索引
        label = labels[sorted_distances[i]]
        # 设置最佳匹配对应的标签的出现次数
        vote_labels[label] = vote_labels.get(label, 0) + 1
    # 根据标签出现次数进行投票
    #   operator.itemgetter(1)  <===>  lambda el: el[1]
    sorted_vote_labels = sorted(vote_labels.items(), key=operator.itemgetter(1), reverse=True)
    # 获取最佳的标签
    return sorted_vote_labels[0][0]


print(knn_classify((0.5, 0.5), group, labels, 2))
print(knn_classify((0.55, 0.55), group, labels, 2))
