import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // welcome message
            System.out.println(input.readLine());

            // game loop
            while (true) {
                // Display the current board
                System.out.println(input.readLine());

                // user input for the move
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter your move (row and column separated by a space): ");
                String move = consoleInput.readLine();
                output.println(move);

                // display result of the move
                System.out.println(input.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

