package com.wjiec.learn.threading;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadInfoList {

    public static ThreadInfo[] getThreadInfoList() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.dumpAllThreads(false, false);
    }

}
