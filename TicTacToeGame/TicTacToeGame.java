import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TicTacToeGame {
    private final Socket player1;
    private final Socket player2;
    private final BufferedReader input1;
    private final PrintWriter output1;
    private final BufferedReader input2;
    private final PrintWriter output2;
    private char[][] board;
    private boolean isPlayer1Turn;

    public TicTacToeGame(Socket player1, Socket player2) throws IOException {
        this.player1 = player1;
        this.player2 = player2;

        input1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
        output1 = new PrintWriter(player1.getOutputStream(), true);

        input2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
        output2 = new PrintWriter(player2.getOutputStream(), true);

        board = new char[3][3];
        isPlayer1Turn = true;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void startGame() {
        try {
            output1.println("Welcome to Tic Tac Toe! You are Player 1. Your symbol is 'X'.");
            output2.println("Welcome to Tic Tac Toe! You are Player 2. Your symbol is 'O'.");

            while (true) {
                sendBoardToPlayers();

                if (isPlayer1Turn) {
                    playTurn(player1, input1, output1, 'X');
                } else {
                    playTurn(player2, input2, output2, 'O');
                }

                if (checkWin('X')) {
                    sendBoardToPlayers();
                    output1.println("Congratulations! You win!");
                    output2.println("Player 1 wins. Better luck next time!");
                    break;
                } else if (checkWin('O')) {
                    sendBoardToPlayers();
                    output2.println("Congratulations! You win!");
                    output1.println("Player 2 wins. Better luck next time!");
                    break;
                } else if (isBoardFull()) {
                    sendBoardToPlayers();
                    output1.println("It's a tie!");
                    output2.println("It's a tie!");
                    break;
                }

                isPlayer1Turn = !isPlayer1Turn;
            }

            // Close sockets
            player1.close();
            player2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playTurn(Socket player, BufferedReader input, PrintWriter output, char symbol) throws IOException {
        output.println("Your turn. Enter row (0-2) and column (0-2) separated by a space:");

        String move = input.readLine();
        String[] moveArray = move.split(" ");

        int row = Integer.parseInt(moveArray[0]);
        int col = Integer.parseInt(moveArray[1]);

        if (isValidMove(row, col)) {
            board[row][col] = symbol;
        } else {
            output.println("Invalid move. Try again.");
            playTurn(player, input, output, symbol);
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    private boolean checkWin(char symbol) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }

        // Check diagonals
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void sendBoardToPlayers() {
        output1.println("Current board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                output1.print(board[i][j] + " ");
            }
            output1.println();
        }

        output2.println("Current board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                output2.print(board[i][j] + " ");
            }
            output2.println();
        }
    }
}

