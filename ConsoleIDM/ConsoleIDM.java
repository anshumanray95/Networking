import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsoleIDM {

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter the file URL: ");
            String fileURL = reader.readLine();

            System.out.print("Enter the directory to save the file: ");
            String saveDir = reader.readLine();

            downloadFile(fileURL, saveDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);

        File file = new File(saveDir + File.separator + fileName);
        long existingFileSize = 0;

        if (file.exists()) {
            existingFileSize = file.length();
            System.out.println("Resuming download from " + existingFileSize + " bytes");
            connection.setRequestProperty("Range", "bytes=" + existingFileSize + "-");
        }

        try (InputStream inputStream = connection.getInputStream();
             RandomAccessFile outputStream = new RandomAccessFile(file, "rw")) {

            outputStream.seek(existingFileSize);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long totalBytesRead = existingFileSize;

            System.out.println("Downloading: " + fileName);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                printProgress(totalBytesRead, connection.getContentLength());
            }

            System.out.println("\nDownload completed!");

        } catch (IOException e) {
            System.err.println("Download failed: " + e.getMessage());
        } finally {
            connection.disconnect();
        }
    }

    private static void printProgress(long bytesRead, long totalBytes) {
        double progress = (double) bytesRead / totalBytes * 100;
        System.out.printf("\rProgress: %.2f%%", progress);
    }
}

