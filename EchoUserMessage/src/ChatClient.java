import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    // Set the server's IP address and port number.
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            // Create a socket to connect to the server.
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            // Create input and output streams to read and write data to and from the
            // server.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Create a scanner to read input from the user.
            Scanner scanner = new Scanner(System.in);

            // Run an infinite loop to read and send messages to the server.
            while (true) {
                // Read a message from the user.
                String message = scanner.nextLine();

                // Send the message to the server.
                out.println(message);

                // Read a message from the server.
                String response = in.readLine();

                // Print the message to the user.
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
