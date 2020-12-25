package com.wjiec.springaio.rpc.service;

import java.util.List;

public interface EchoService {

    <T> List<T> repeated(T obj, int count);

    <T extends Comparable<? super T>> T max(T left, T right);

}
