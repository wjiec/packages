package com.wjiec.learn.threading;

import java.lang.management.ThreadInfo;

public class ThreadInfoListTest {

    public static void main(String[] args) {
        ThreadInfo[] threadInfos = ThreadInfoList.getThreadInfoList();
        for (var info : threadInfos) {
            System.out.printf("%d: %s\n", info.getThreadId(), info.getThreadName());
        }
    }

}
