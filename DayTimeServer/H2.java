package DayTimeServer;

import java.net.*;

public class H2 {

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress h1Address = InetAddress.getByName("127.0.0.1"); 

            // request data and time from H1
            byte[] sendData = "RequestDataFromH1".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, h1Address, 9876);

            socket.send(sendPacket);

            // receive data and time from H1
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);

            String dataFromH1 = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("H2 received data from H1: " + dataFromH1);

           
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

