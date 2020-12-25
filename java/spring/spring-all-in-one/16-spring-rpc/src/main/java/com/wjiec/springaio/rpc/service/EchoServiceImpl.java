package com.wjiec.springaio.rpc.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EchoServiceImpl implements EchoService {

    @Override
    public <T> List<T> repeated(T obj, int count) {
        List<T> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(obj);
        }

        return list;
    }

    @Override
    public <T extends Comparable<? super T>> T max(T left, T right) {
        return left.compareTo(right) >= 0 ? left : right;
    }

}
