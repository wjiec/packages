package com.github.wjiec.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket server;

    public Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public void serve(HandlerInterface handler) {
        while (true) {
            try {
                Socket socket = server.accept();
                new Thread(() -> {
                    try {
                        handler.handle(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
