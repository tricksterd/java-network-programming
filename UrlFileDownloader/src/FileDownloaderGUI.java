import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileDownloaderGUI {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 200;

    public static void main(String[] args) {
        // Create a frame to hold the GUI elements
        JFrame frame = new JFrame("File Downloader");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the GUI elements
        JPanel panel = new JPanel();

        // Create a label and text field for the URL
        JLabel urlLabel = new JLabel("URL:");
        JTextField urlField = new JTextField(30);
        panel.add(urlLabel);
        panel.add(urlField);

        // Create a button to start the download
        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the URL from the text field
                String urlString = urlField.getText();
                URL url;
                try {
                    url = new URL(urlString);
                } catch (MalformedURLException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid URL provided: " + urlString);
                    return;
                }

                // Start the download
                FileDownloader.download(url);
            }
        });
        panel.add(downloadButton);

        // Add the panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }
}
