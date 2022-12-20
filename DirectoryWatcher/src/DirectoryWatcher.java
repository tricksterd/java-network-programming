import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirectoryWatcher {
    // Set the IP address and port number for the server.
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;

    // Set the directory to watch.
    private static final String WATCH_DIRECTORY = "D:/Programming/FolderToTestJavaProject";

    public static void main(String[] args) {
        try {
            // Create a watch service to monitor the directory for changes.
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = FileSystems.getDefault().getPath(WATCH_DIRECTORY);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            // Create a socket to send messages to the clients.
            DatagramSocket socket = new DatagramSocket();

            // Run an infinite loop to watch for changes in the directory.
            while (true) {
                // Wait for a change in the directory.
                WatchKey key = watchService.take();

                // Iterate over the events that occurred.
                for (WatchEvent<?> event : key.pollEvents()) {
                    // Get the type of event that occurred.
                    WatchEvent.Kind<?> kind = event.kind();

                    // If the event was a create, delete, or modify event, send a message
                    // to the clients.
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE
                            || kind == StandardWatchEventKinds.ENTRY_DELETE
                            || kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        String message = "Directory changed: " + event.context();
                        byte[] data = message.getBytes();
                        InetAddress address = InetAddress.getByName(SERVER_IP);
                        DatagramPacket packet = new DatagramPacket(data, data.length, address, SERVER_PORT);
                        socket.send(packet);
                    }
                }

                // Reset the key.
                boolean valid = key.reset();

                // If the key is no longer valid, break out of the loop.
                if (!valid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
