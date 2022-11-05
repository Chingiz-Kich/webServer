package kz.aitu.forMyself;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8081);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("Hello");
        out.flush();
        out.println("World");
        out.println("How are you?");
        out.close();
        socket.close();
    }
}
