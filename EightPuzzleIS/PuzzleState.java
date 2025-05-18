import java.util.*;

class PuzzleState {
    int[][] board;
    PuzzleState parent;

    PuzzleState(int[][] board, PuzzleState parent) {
        this.board = board;
        this.parent = parent;
    }

    boolean isGoal() {
        int[][] goal = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
        };

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (this.board[i][j] != goal[i][j])
                    return false;

        return true;
    }

    List<PuzzleState> generateNextStates() {
        List<PuzzleState> nextStates = new ArrayList<>();
        int zeroRow = 0, zeroCol = 0;

        // Find 0 (blank)
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }

        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1} // up, down, left, right
        };

        for (int[] dir : directions) {
            int newRow = zeroRow + dir[0];
            int newCol = zeroCol + dir[1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] newBoard = new int[3][3];
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        newBoard[i][j] = board[i][j];

                newBoard[zeroRow][zeroCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;

                nextStates.add(new PuzzleState(newBoard, this));
            }
        }

        return nextStates;
    }

    // static int getMisplacedTiles(int[][] board) {
    // int[][] goal = {
    //     {1, 2, 3},
    //     {8, 0, 4},
    //     {7, 6, 5}
    // };
    // int misplaced = 0;

    // for (int i = 0; i < 3; i++)
    //     for (int j = 0; j < 3; j++)
    //         if (board[i][j] != 0 && board[i][j] != goal[i][j])
    //             misplaced++;

    // return misplaced;
    // }


    String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board)
            for (int val : row)
                sb.append(val);
        return sb.toString();
    }

    void printBoard() {
        for (int[] row : board) {
            for (int val : row)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }
}

