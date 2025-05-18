import java.util.*;

public class MinimaxTicTacToe {
    static final char HUMAN = 'X';
    static final char AI = 'O';
    static final char EMPTY = '.';
    static char[] board = {
        '.', '.', '.',
        '.', '.', '.',
        '.', '.', '.'
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Tic Tac Toe with Minimax AI");
        printBoard();

        while (true) {
            // Human move
            int humanMove = -1;
            while (true) {
                System.out.print("Enter your move (1-9): ");
                humanMove = sc.nextInt() - 1;
                if (humanMove >= 0 && humanMove < 9 && board[humanMove] == EMPTY) {
                    board[humanMove] = HUMAN;
                    break;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }
            printBoard();
            if (isWinner(HUMAN)) {
                System.out.println("You win!");
                break;
            }
            if (isDraw()) {
                System.out.println("Draw!");
                break;
            }

            // AI move
            int aiMove = getBestMove();
            board[aiMove] = AI;
            System.out.println("AI played at position: " + (aiMove + 1));
            printBoard();
            if (isWinner(AI)) {
                System.out.println("AI wins!");
                break;
            }
            if (isDraw()) {
                System.out.println("Draw!");
                break;
            }
        }
    }

    static void printBoard() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + " ");
            if (i % 3 == 2) System.out.println();
        }
        System.out.println();
    }

    static boolean isWinner(char player) {
        int[][] winCombos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // cols
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };
        for (int[] line : winCombos) {
            if (board[line[0]] == player && board[line[1]] == player && board[line[2]] == player) {
                return true;
            }
        }
        return false;
    }

    static boolean isDraw() {
        for (char c : board) {
            if (c == EMPTY) return false;
        }
        return true;
    }

    static int getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                board[i] = AI;
                int score = minimax(false);
                board[i] = EMPTY;
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    static int minimax(boolean isMaximizing) {
        if (isWinner(AI)) return 10;
        if (isWinner(HUMAN)) return -10;
        if (isDraw()) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char currentPlayer = isMaximizing ? AI : HUMAN;

        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                board[i] = currentPlayer;
                int score = minimax(!isMaximizing);
                board[i] = EMPTY;
                bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
            }
        }
        return bestScore;
    }
}
