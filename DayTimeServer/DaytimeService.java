package DayTimeServer;
import java.net.*;
import java.util.Date;

public class DaytimeService {

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876); // Port for DS

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                //current date and time
                String dateTime = new Date().toString();
                byte[] sendData = dateTime.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
