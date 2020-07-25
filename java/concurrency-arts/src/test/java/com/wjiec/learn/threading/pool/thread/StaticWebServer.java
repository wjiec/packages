package com.wjiec.learn.threading.pool.thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class StaticWebServer {

    private static final DefaultThreadPool<HttpRequestHandler> pool = new DefaultThreadPool<>();

    public static void main(String[] args) throws IOException {
        String base = System.getProperty("user.dir");
        int port = 30000 + (int)(Math.random() * 10000);
        if (args.length > 0) {
            base = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        Server server = new Server(base, port);
        System.out.printf("Server running at :%d, root = %s\n", port, base);
        server.serve();
        pool.shutdown();
    }

    static class Server {
        private String base;
        private ServerSocket socket;

        Server(String base, int port) throws IOException {
            this.base = base;
            this.socket = new ServerSocket(port);
        }

        void serve() throws IOException {
            Socket client;
            while ((client = socket.accept()) != null) {
                pool.execute(new HttpRequestHandler(base, client));
            }
            socket.close();
        }

    }

    static class HttpRequestHandler implements Runnable {

        private String base;
        private Socket socket;

        HttpRequestHandler(String base, Socket socket) {
            this.base = base;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter response = new PrintWriter(socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                String filename = Path.of(base, header.split(" ")[1]).toString();
                File file = new File(filename);
                if (!file.exists() || file.isDirectory()) {
                    response.println("HTTP/1.1 404 NotFound");
                    response.println("Server: threadpool");
                    response.println();
                    response.flush();
                    return;
                }

                if (filename.endsWith(".jpeg") || filename.endsWith(".jpg")) {
                    FileInputStream in = new FileInputStream(filename);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();

                    for (int i = in.read(); i != -1; i = in.read()) {
                        out.write(i);
                    }

                    byte[] body = out.toByteArray();
                    response.println("HTTP/1.1 200 OK");
                    response.println("Server: threadpool");
                    response.println("Content-Type: image/jpeg");
                    response.println("Content-Length: " + body.length);
                    response.println();
                    response.flush();

                    socket.getOutputStream().write(body, 0, body.length);
                    close(in);
                } else {
                    BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
                    response.println("HTTP/1.1 200 OK");
                    response.println("Server: threadpool");
                    response.println("Content-Type: text/html; charset=utf-8");

                    for (String line = fileReader.readLine(); line != null; line = fileReader.readLine()) {
                        response.println(line);
                    }

                    response.flush();
                    close(fileReader);
                }

                close(reader, response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(this.socket);
            }
        }

        private void close(Closeable... closeables) {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
