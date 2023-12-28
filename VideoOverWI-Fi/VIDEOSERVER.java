// import java.io.IOException;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.awt.image.BufferedImage;
// import java.io.ByteArrayOutputStream;
// import javax.imageio.ImageIO;

// public class VIDEOSERVER {
//     public static void main(String[] args) {
//         try {
//             ServerSocket serverSocket = new ServerSocket(5000); // Use a specific port

//             while (true) {
//                 Socket clientSocket = serverSocket.accept();
//                 System.out.println("Client connected: " + clientSocket.getInetAddress());

//                 // Simulate video capture (replace this with actual camera capture)
//                 BufferedImage image = captureVideoFrame();

//                 // Convert image to byte array
//                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                 ImageIO.write(image, "jpg", byteArrayOutputStream);
//                 byte[] imageBytes = byteArrayOutputStream.toByteArray();

//                 // Send image data to the client
//                 clientSocket.getOutputStream().write(imageBytes);

//                 clientSocket.close();
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     // Replace this method with actual video capture from a camera
//     private static BufferedImage captureVideoFrame() {
//         // Simulated video capture (replace this with actual implementation)
//         // This method should return a BufferedImage from the camera
//         return new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
//     }
// }
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VIDEOSERVER {
    private static final int PORT = 5000;
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedImage image = captureVideoFrame();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Send image data to the client
            clientSocket.getOutputStream().write(imageBytes);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Replace this method with actual video capture from a camera
    private static BufferedImage captureVideoFrame() {
        // Simulated video capture (replace this with actual implementation)
        // This method should return a BufferedImage from the camera
        return new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    }
}
