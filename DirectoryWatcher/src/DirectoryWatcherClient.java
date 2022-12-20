import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DirectoryWatcherClient {
    // Set the IP address and port number for the server.
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            // Create a socket to receive messages from the server.
            DatagramSocket socket = new DatagramSocket(SERVER_PORT, InetAddress.getByName(SERVER_IP));

            // Run an infinite loop to receive messages from the server.
            while (true) {
                // Create a buffer to receive the message.
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Wait for a message from the server.
                socket.receive(packet);

                // Get the message from the packet.
                String message = new String(packet.getData(), 0, packet.getLength());

                // Print the message.
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
