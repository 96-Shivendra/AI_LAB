import java.util.*;

public class EightPuzzleAStar {

    static class AStarNode {
        PuzzleState state;
        int g; // cost so far
        int f; // total estimated cost = g + h

        AStarNode(PuzzleState state, int g, int f) {
            this.state = state;
            this.g = g;
            this.f = f;
        }
    }

    public static void main(String[] args) {
        int[][] startBoard = {
            {2, 8, 3},
            {1, 6, 4},
            {7, 0, 5}
        };

        PuzzleState startState = new PuzzleState(startBoard, null);
        solve(startState);
    }

    static void solve(PuzzleState startState) {
        PriorityQueue<AStarNode> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<String> closed = new HashSet<>();

        int startH = manhattanDistance(startState.board);
        open.add(new AStarNode(startState, 0, startH));

        while (!open.isEmpty()) {
            AStarNode currentNode = open.poll();
            PuzzleState currentState = currentNode.state;

            if (currentState.isGoal()) {
                printPath(currentState);
                return;
            }

            String key = currentState.boardToString();
            if (closed.contains(key)) continue;
            closed.add(key);

            for (PuzzleState neighbor : currentState.generateNextStates()) {
                if (!closed.contains(neighbor.boardToString())) {
                    int g = currentNode.g + 1;
                    int h = manhattanDistance(neighbor.board);
                    open.add(new AStarNode(neighbor, g, g + h));
                }
            }
        }

        System.out.println("No solution found.");
    }

    static int manhattanDistance(int[][] board) {
        int[][] goalPos = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
        };

        Map<Integer, int[]> goalMap = new HashMap<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                goalMap.put(goalPos[i][j], new int[]{i, j});

        int distance = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                int val = board[i][j];
                if (val != 0) {
                    int[] goal = goalMap.get(val);
                    distance += Math.abs(i - goal[0]) + Math.abs(j - goal[1]);
                }
            }

        return distance;
    }

    static void printPath(PuzzleState state) {
        Stack<PuzzleState> path = new Stack<>();
        while (state != null) {
            path.push(state);
            state = state.parent;
        }

        int step = 0;
        while (!path.isEmpty()) {
            System.out.println("Step " + step++);
            path.pop().printBoard();
        }
    }
}
