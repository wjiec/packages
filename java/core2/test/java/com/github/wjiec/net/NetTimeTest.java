package com.github.wjiec.net;

public class NetTimeTest {

    public static void main(String[] args) {
        System.out.println(NetTime.now("1.cn.pool.ntp.org", 5000));
        System.out.println(NetTime.now());
    }

}
