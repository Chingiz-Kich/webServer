package kz.aitu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Processor {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void process() throws IOException, InterruptedException {
        // Print request that we received.
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();

        // To send response back to the client.
        PrintWriter output = new PrintWriter(socket.getOutputStream());

        // Getting request line
        String requestLine = request.getRequestLine();
        // We are returning a simple web page now.
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<html>");
        output.println("<head><title>Hello</title></head>");
        switch (requestLine) {
            case "GET / HTTP/1.1" -> output.println("<h1>This message from root directory </h1>");
            case "GET /create/itemid HTTP/1.1" -> {
                //Thread.sleep(3000);
                output.println("<h1>This message from /create/itedId</h1>");
            }
            case "GET /delete/itemid HTTP/1.1" -> {
                Thread.sleep(2000);
                output.println("<h1>This message from /delete/itedId</h1>");
            }
            case "GET /exec/params HTTP/1.1" -> {
                //Thread.sleep(1500);
                output.println("<h1>This message from /exec/params</h1>");
            }
            default -> output.println("<h1>UNDEFINED PATH</h1>");
        }
        output.println("<body><p>Hello, world!</p></body>");
        output.println("</html>");
        output.flush();

        socket.close();
    }
}
