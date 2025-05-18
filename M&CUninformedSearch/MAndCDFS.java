import java.util.*;

public class MAndCDFS {
    public static void main(String[] args) {
        dfs();
    }

    static void dfs() {
        State start = new State(3, 3, true, null);
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            State current = stack.pop();

            if (current.isGoal()) {
                printSolution(current);
                return;
            }

            for (State next : generateSuccessors(current)) {
                if (next.isValid() && !visited.contains(next)) {
                    visited.add(next);
                    stack.push(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static List<State> generateSuccessors(State current) {
        List<State> successors = new ArrayList<>();
        int direction = current.boatLeft ? -1 : 1;

        int[][] moves = {
            {1, 0}, {2, 0}, // missionaries
            {0, 1}, {0, 2}, // cannibals
            {1, 1}          // one of each
        };

        for (int[] move : moves) {
            int newM = current.missionariesLeft + direction * move[0];
            int newC = current.cannibalsLeft + direction * move[1];
            boolean newBoat = !current.boatLeft;

            State nextState = new State(newM, newC, newBoat, current);
            if (nextState.isValid()) {
                successors.add(nextState);
            }
        }

        return successors;
    }

    static void printSolution(State goal) {
        List<State> path = new ArrayList<>();
        State current = goal;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);

        System.out.println("Solution path:");
        for (State s : path) {
            System.out.println(s);
        }
    }
}
