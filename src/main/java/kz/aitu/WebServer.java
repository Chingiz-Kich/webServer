package kz.aitu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple web server.
 */
public class WebServer extends Thread{


    public static void main(String[] args) {
        // Port number for http request
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
        // The maximum queue length for incoming connection
        /** Queue length was increased for multithreading **/
        int queueLength = args.length > 2 ? Integer.parseInt(args[2]) : 5000;

        try (ServerSocket serverSocket = new ServerSocket(port, queueLength)) {
            System.out.println("Web Server is starting up, listening at port " + port + ".");
            System.out.println("You can access http://localhost:" + port + " now.");

            while (true) {
                // Make the server socket wait for the next client request
                Socket socket = serverSocket.accept();

                /** This new Worker.class was added for multithreading **/
                Worker worker = new Worker(socket);
                /** Socket after accepting connection and creating Worker.class that process request itself
                 * We will start() Worker.class instance to run it on separate thread**/
                worker.start();

                /** This part was changed for multithreading **/
/*                // Make the server socket wait for the next client request
                Socket socket = serverSocket.accept();

                System.out.println("Got connection!");

                // To read input from the client
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                try {
                    // Get request
                    HttpRequest request = HttpRequest.parse(input);

                    // Process request
                    Processor proc = new Processor(socket, request);
                    proc.process();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            System.out.println("Server has been shutdown!");
        }
    }
}
