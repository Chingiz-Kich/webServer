package kz.aitu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Consumer<T> extends Thread {
    private final int id;
    private final ThreadSafeQueue<T> queue;

    public Consumer(int id, ThreadSafeQueue<T> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("New consumer thread are running id: " + id);
        try {
            while (true) {
                T elem = queue.pop();

                Socket socket = (Socket) elem;
                // To read input from the client
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                // Get request
                HttpRequest request = HttpRequest.parse(input);
                Processor proc = new Processor(socket, request);
                proc.process();

                // Stop consuming if null is received.
                if (elem == null) {
                    return;
                }

                // Process element.
                System.out.println(id + ": get item: " + elem);
            }
        }
        catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
