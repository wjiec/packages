package com.github.wjiec.net.server;

import java.io.IOException;
import java.net.Socket;

public interface HandlerInterface {

    void handle(Socket socket) throws IOException;

}
