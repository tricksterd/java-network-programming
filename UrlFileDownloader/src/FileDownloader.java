import java.io.*;
import java.net.*;

public class FileDownloader {

    public static void download(URL url) {
        // Check if a URL was provided as an argument

        // Parse the URL from the argument

        // Open a connection to the URL and check if it points to a file
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            String contentType = connection.getContentType();
            if (contentType == null || !contentType.startsWith("application/")) {
                System.out.println("URL does not point to a file: " + url);
                return;
            }
        } catch (IOException e) {
            System.out.println("Error opening connection to URL: " + url);
            return;
        }

        // Download the file
        InputStream inputStream;
        OutputStream outputStream;
        try {
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream("downloaded_file");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            System.out
                    .println("File downloaded successfully to: " + System.getProperty("user.dir") + "/downloaded_file");
        } catch (IOException e) {
            System.out.println("Error downloading file from URL: " + url);
            return;
        }
    }
}
