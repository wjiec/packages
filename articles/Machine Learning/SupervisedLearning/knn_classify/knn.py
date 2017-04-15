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

def auto_normal(data_set):
    # 寻找一行/列中的最小值
    #   axis：表示行(1)或者列(0)
    min_values = data_set.min(axis = 0)
    # 寻找一行/列中的最大值
    #   axis：表示行(1)或者列(0)
    max_values = data_set.max(axis = 0)
    # 计算最大值和最小值之间的差值
    diff_range = max_values - min_values
    # 归一化的数组
    normal_array = np.zeros(data_set.shape)
    # 获得所有行的数目
    row_count = data_set.shape[0]
    # 得到减去最小值之后的数据集合
    normal_array = data_set - np.tile(min_values, (row_count, 1))
    # 得到归一化的数组
    #   将 [1, 2, 3, 4, 5] 转化到 0 - 1 范围内
    #       首先获取最小值为1，最大值为5，差值为4
    #           将每个值减去这个最小值得到: [0, 1, 2, 3, 4]
    #               最后将每个值除以差值: [0, .25, .5, .75, 1]
    #   直接除以最大值是不对的，因为最小值应该转化之后为0，最大值转化之后为1
    #       所以需要先把最小值变成0，然后再除以最小值变成0后的最大值(就是最大值和最小值的差值)
    normal_array = normal_array / diff_range
    # 返回结果
    return normal_array, min_values, diff_range

def knn_classify(inX, data_set, labels, k):
    # 将输入数据集进行归一化
    data_set, min_values, diff_range = auto_normal(data_set)
    # 将将要预测值进行归一化
    inX = (inX - min_values) / diff_range
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

if __name__ == '__main__':
    print(knn_classify((1.0, 0.5), group, labels, 2))

    print(knn_classify((18, 90), np.array([
        [3, 104], [2, 100], [1, 81], [101, 10], [99, 5], [98, 2]
    ]), [ 'M', 'M', 'M', 'A', 'A', 'A'], 5))
