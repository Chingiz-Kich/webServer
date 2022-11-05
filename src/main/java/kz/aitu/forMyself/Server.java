package kz.aitu.forMyself;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        Socket input = serverSocket.accept();
        Scanner in = new Scanner(input.getInputStream());
        PrintWriter out = new PrintWriter(input.getOutputStream());
        while (in.hasNext()) {
            System.out.println(in.nextLine());
            Scanner message = new Scanner(System.in);
            message.nextLine();
            out.println(message);
        }
        in.close();
        input.close();
        serverSocket.close();
    }
}
