import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VoIPServer {
    private static final int PORT = 9876;
    private static final int SAMPLE_RATE = 44100; 

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            byte[] receiveData = new byte[1024];

            AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 2, true, true);
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                byte[] audioData = receivePacket.getData();

                
                speakers.write(audioData, 0, audioData.length);
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

