import java.io.*;
import java.net.*;

public class FileDownloaderConsole {

    public static void main(String[] args) {
        // Check if a URL was provided as an argument
        if (args.length == 0) {
            System.out.println("Please provide a URL to download a file from as an argument.");
            return;
        }

        // Parse the URL from the argument
        URL url;
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL provided: " + args[0]);
            return;
        }

        // Open a connection to the URL and check if it points to a file
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            String contentType = connection.getContentType();
            if (contentType == null || !contentType.startsWith("application/")) {
                System.out.println("URL does not point to a file: " + args[0]);
                return;
            }
        } catch (IOException e) {
            System.out.println("Error opening connection to URL: " + args[0]);
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
            System.out.println("Error downloading file from URL: " + args[0]);
            return;
        }
    }
}