import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    // Set the port number.
    private static final int PORT = 1234;

    // Set the maximum number of clients that can be connected at the same time.
    private static final int MAX_CLIENTS = 10;

    // Create a list to store the threads for each client.
    private static List<ClientThread> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Create a server socket to listen for incoming connections.
            ServerSocket serverSocket = new ServerSocket(PORT);

            // Run an infinite loop to accept and handle incoming connections.
            while (true) {
                if (clients.size() < MAX_CLIENTS) {
                    // Accept a connection from a client.
                    Socket clientSocket = serverSocket.accept();

                    // Create a new thread for the client.
                    ClientThread clientThread = new ClientThread(clientSocket);
                    clientThread.start();

                    // Add the client thread to the list of connected clients.
                    clients.add(clientThread);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class to represent a thread for a client.
    private static class ClientThread extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Create input and output streams to read and write data to and from the
                // client.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Run an infinite loop to read and handle messages from the client.
                while (true) {
                    // Read a message from the client.
                    String message = in.readLine();

                    // If the message is null, the client has disconnected.
                    if (message == null) {
                        break;
                    }

                    // Broadcast the message to all other connected clients.
                    for (ClientThread client : clients) {
                        if (client != this) {
                            client.out.println(message);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Remove the client from the list of connected clients.
                clients.remove(this);

                // Close the input and output streams and the socket.
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
