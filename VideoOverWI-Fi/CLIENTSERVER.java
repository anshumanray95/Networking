import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CLIENTSERVER {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000); // Connect to the server

            while (true) {
                // Receive image data from the server
                byte[] imageBytes = new byte[1280 * 720]; // Adjust the buffer size as needed
                socket.getInputStream().read(imageBytes);

                // Convert byte array to BufferedImage
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

                // Display the image on the client side (you may want to use a GUI library)
                displayImage(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayImage(BufferedImage image) {
        // Display the image using a JFrame and JLabel (replace this with your GUI library)
        JFrame frame = new JFrame("Video Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
