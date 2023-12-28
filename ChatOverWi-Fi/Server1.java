import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server1 implements Runnable {

    private ArrayList<ConnetionHandeler> conections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server1() {
        conections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnetionHandeler handeler = new ConnetionHandeler(client);
                conections.add(handeler);
                pool.execute(handeler);
            }

        } catch (IOException e) {
            // TODO : Handle
        }
    }

    public void broadcast(String message) {
        for (ConnetionHandeler ch : conections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        done = true;
        pool.shutdown();
        if (!server.isClosed()) {
            try {
                server.close();
                for (ConnetionHandeler ch : conections) {
                    ch.shutdown();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                shutdown();
            }
        }
    }

    class ConnetionHandeler implements Runnable {

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public ConnetionHandeler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter your username: ");
                username = in.readLine();
                System.out.println(username + " Conected. ");
                broadcast(username + " joined the chat.");
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/quit ")) {
                        broadcast(username + " left the chat");
                        shutdown();
                    } else {
                        broadcast(username + " : " + message);
                    }
                }
            } catch (IOException e) {
                // TODO: handle exception
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void shutdown() {

            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                shutdown();
            }
        }
    }
    public static void main(String[] args) {
        Server1 server = new Server1();
        server.run();
    }
}
