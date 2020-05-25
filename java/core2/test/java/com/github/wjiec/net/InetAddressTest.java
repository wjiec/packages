package com.github.wjiec.net;

import java.io.IOException;
import java.net.InetAddress;

public class InetAddressTest {

    public static void main(String[] args) throws IOException {
        printInetAddress(InetAddress.getLocalHost());
        printInetAddress(InetAddress.getLoopbackAddress());

        InetAddress[] addresses = InetAddress.getAllByName("google.com");
        for (var address : addresses) {
            printInetAddress(address);
        }
    }

    private static void printInetAddress(InetAddress address) {
        byte[] bytes = address.getAddress();
        System.out.printf("%s: %d %d %d %d\n", address, bytes[0] & 0xff, bytes[1] & 0xff, bytes[2] & 0xff, bytes[3] & 0xff);

    }

}
