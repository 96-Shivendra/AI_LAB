import java.util.*;

public class EightPuzzleBestFirst {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] startBoard = new int[3][3];

        System.out.println("Enter the 8-puzzle board (3x3) using 0 for the blank:");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                startBoard[i][j] = sc.nextInt();

        PuzzleState start = new PuzzleState(startBoard, null);
        bestFirstSearch(start);
    }

    static void bestFirstSearch(PuzzleState start) {
        PriorityQueue<PuzzleState> pq = new PriorityQueue<>(Comparator.comparingInt(s -> getMisplacedTiles(s.board)));
        Set<String> visited = new HashSet<>();

        pq.offer(start);
        visited.add(start.boardToString());

        while (!pq.isEmpty()) {
            PuzzleState current = pq.poll();

            if (current.isGoal()) {
                printPath(current);
                return;
            }

            for (PuzzleState next : current.generateNextStates()) {
                String key = next.boardToString();
                if (!visited.contains(key)) {
                    visited.add(key);
                    pq.offer(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static int getMisplacedTiles(int[][] board) {
        int[][] goal = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
        };
        int misplaced = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] != 0 && board[i][j] != goal[i][j])
                    misplaced++;

        return misplaced;
    }

    static void printPath(PuzzleState goal) {
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState current = goal;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        System.out.println("Solution path (Best-First Search):");
        for (PuzzleState state : path)
            state.printBoard();
    }
}
