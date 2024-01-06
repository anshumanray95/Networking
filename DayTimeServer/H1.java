package DayTimeServer;
import java.net.*;

public class H1 {

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress dsAddress = InetAddress.getByName("127.0.0.1"); 

            // request data and time from DS
            byte[] sendData = "RequestTime".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dsAddress, 9876);

            socket.send(sendPacket);

            // receive data and time from DS
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);

            String dateTime = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("H1 received data and time from DS: " + dateTime);

            // Close the socket
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
