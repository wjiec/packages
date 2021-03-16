package org.laboys.better.spring.core.util;

import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.core.bean.copier.CopyOptions;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyUtil {

    private PropertyUtil() {}

    /**
     * 流对象的泛型属性拷贝
     */
    public static <T, R> List<R> copy(Stream<T> src, Supplier<R> supplier) {
        return src.map((item) -> copy(item, supplier)).collect(Collectors.toList());
    }

    /**
     * 列表对象的泛型属性拷贝
     */
    public static <T, R> List<R> copy(List<T> src, Supplier<R> supplier) {
        return copy(src.stream(), supplier);
    }

    /**
     * 自定义拷贝方法进行属性拷贝
     */
    public static <T, R> List<R> copy(Stream<T> src, Function<T, R> function) {
        return src.map(function).collect(Collectors.toList());
    }

    /**
     * 自定义拷贝方法进行属性拷贝
     */
    public static <T, R> List<R> copy(List<T> src, Function<T, R> function) {
        return copy(src.stream(), function);
    }

    /**
     * 对象的泛型属性拷贝
     */
    public static <T, R> R copy(T src, Supplier<R> supplier) {
        R data = supplier.get();
        return BeanCopier.create(src, data, CopyOptions.create()).copy();
    }

}
