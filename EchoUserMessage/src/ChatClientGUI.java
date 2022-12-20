import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClientGUI {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    // Set the server's IP address and port number.
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;

    private final JTextArea chatArea;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatClientGUI() {
        // Create a frame to hold the GUI elements.
        JFrame frame = new JFrame("Chat Client");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the GUI elements.
        JPanel panel = new JPanel();

        // Create a text area to display the chat messages.
        chatArea = new JTextArea(20, 45);
        chatArea.setEditable(false);
        panel.add(new JScrollPane(chatArea));

        // Create a text field and button to send messages.
        JTextField messageField = new JTextField(40);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the message from the text field.
                String message = messageField.getText();

                // Send the message to the server.
                out.println(message);

                // Clear the text field.
                messageField.setText("");
            }
        });
        panel.add(messageField);
        panel.add(sendButton);

        // Add the panel to the frame and make it visible.
        frame.add(panel);
        frame.setVisible(true);
    }

    public void connect() {
        try {
            // Create a socket to connect to the server.
            socket = new Socket(SERVER_IP, SERVER_PORT);

            // Create input and output streams to read and write data to and from the
            // server.
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Run a new thread to continuously read messages from the server.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            // Read a message from the server.
                            String message = in.readLine();
                            chatArea.append(message + "\n");

                            // Append the message to the chat area.
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClientGUI client = new ChatClientGUI();
        client.connect();
    }
}
