import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VoIPClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 9876;
    private static final int SAMPLE_RATE = 44100; 
    private static final int CHUNK_SIZE = 1024; 

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);

            // Set up audio format
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 2, true, true);
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            microphone.open(format);
            microphone.start();

            int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
            byte[] buffer = new byte[CHUNK_SIZE];  // Use a smaller buffer size

            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);

                
                for (int i = 0; i < bytesRead; i += CHUNK_SIZE) {
                    int length = Math.min(CHUNK_SIZE, bytesRead - i);
                    DatagramPacket sendPacket = new DatagramPacket(buffer, i, length, serverAddress, PORT);
                    clientSocket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
