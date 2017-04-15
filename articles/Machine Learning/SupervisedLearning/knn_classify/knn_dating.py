#!/usr/bin/env python
#
# Copyright (C) 2017 ShadowMan
#
import knn
import numpy as np
import matplotlib.pyplot as plt

def file_to_matrix():
    with open('datingTestSet.txt') as fp:
        # 获取文件中的所有行组成的列表
        lines = fp.readlines()
        # 获取总的行数
        number_of_lines = len(lines)
        # 构建一个 n * 3 的数组
        #   numpy.zeros(shape, dtype=float, order='C')
        #   返回指定形形状和类型的数组
        #       np.zeros((2, 3))  => [[0, 0, 0], [0, 0, 0]]
        data_matrix = np.zeros((number_of_lines, 3))
        # 用于储存所有的标签向量
        labels_vector = []
        # 用于映射label字符串到int
        labels_mapping = {
            'largeDoses': 3,
            'smallDoses': 2,
            'didntLike': 1
        }
        # 遍历所有行
        for index, line in enumerate(lines):
            # 去除所有行两边的空白字符 ' ', '\t', '\n', ...
            line = line.strip()
            # 将字符串以 '\t' 为间隔分开
            line_items = line.split('\t')
            # 为矩阵进行赋值
            #   因为只是二维数组，所以对其中一个数字进行索引的话就是
            #       matrix[H, L] 的方式进行，先高维，再低纬
            data_matrix[index, :] = line_items[0:3]
            # 保存对应的标签向量
            labels_vector.append(labels_mapping[line_items[-1]])
        return data_matrix, labels_vector

def paint_figure(x, y, l):
    # 新建一个绘图窗口
    figure = plt.figure()
    # 表示几行几列在第几个区域画图
    #   第一个参数是一共几行
    #   第二个参数是一共几列
    #   第三个参数是要画的图处于第几个格子，从上到下，从左到右数
    axis = figure.add_subplot(1, 1, 1)
    # 画出离散图
    #   第一个参数是 x 方向上的值
    #   第二个参数是 y 方向上的值
    #   第三个参数是每个点对应的大小
    #       可以是标量：表示所有点相同的大小
    #       可以是数组：表示每个点对应的大小
    #           如果长度不足，会循环取值，相当于模运算取到索引对应的大小
    #   第四个参数是每个点对应的颜色
    #       可以是RGB或者RGBA
    #       可以是一个表示和点数目相同的数组：其中，相同的值会赋予相同的颜色
    axis.scatter(x, y, 15 * np.array(l), np.array(l))
    # 显示所有图标
    plt.show()


if __name__ == '__main__':
    # 从文件中获取所有数据
    data_matrix, labels = file_to_matrix()
    paint_figure(data_matrix[:, 0], data_matrix[:, 1], labels)
    paint_figure(data_matrix[:, 0], data_matrix[:, 2], labels)
    paint_figure(data_matrix[:, 1], data_matrix[:, 2], labels)

    error_count = 0
    for index in range(data_matrix.shape[0]):
        knn_rst = knn.knn_classify(data_matrix[index, :], data_matrix, labels, 3)
        print("the classifier came back with {}, the real answer is {} {}".format(
            knn_rst, labels[index], 'X' if knn_rst != labels[index] else ''))

        if knn_rst != labels[index]:
            error_count += 1
    print('the total error rate is {}%'.format(error_count / data_matrix.shape[0] * 100))



















