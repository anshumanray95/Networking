import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running. Waiting for players...");

            // Accept two players
            Socket player1 = serverSocket.accept();
            System.out.println("Player 1 connected.");

            Socket player2 = serverSocket.accept();
            System.out.println("Player 2 connected.");

            // Create a new game
            TicTacToeGame game = new TicTacToeGame(player1, player2);

            // Start the game
            game.startGame();

            // Close the server socket
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

