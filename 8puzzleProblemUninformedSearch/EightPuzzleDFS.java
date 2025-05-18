import java.util.*;

public class EightPuzzleDFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] startBoard = new int[3][3];

        System.out.println("Enter the 8-puzzle board (3x3) using 0 for the blank:");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                startBoard[i][j] = sc.nextInt();

        PuzzleState start = new PuzzleState(startBoard, null);
        dfs(start);
    }

    public static void dfs(PuzzleState start) {
        Stack<PuzzleState> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        stack.push(start);
        visited.add(start.boardToString());

        while (!stack.isEmpty()) {
            PuzzleState current = stack.pop();

            if (current.isGoal()) {
                printPath(current);
                return;
            }

            for (PuzzleState next : current.generateNextStates()) {
                String key = next.boardToString();
                if (!visited.contains(key)) {
                    visited.add(key);
                    stack.push(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static void printPath(PuzzleState goal) {
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState current = goal;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        System.out.println("Solution path (DFS):");
        for (PuzzleState state : path)
            state.printBoard();
    }
}
