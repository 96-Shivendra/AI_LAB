
import java.util.*;

public class MissionariesAndCannibalsBFS {
    public static void main(String[] args) {
        bfs();
    }

    static void bfs() {
        State start = new State(3, 3, true, null);
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.isGoal()) {
                printSolution(current);
                return;
            }

            for (State next : generateSuccessors(current)) {
                if (next.isValid() && !visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static List<State> generateSuccessors(State current) {
        List<State> successors = new ArrayList<>();
        int direction = current.boatLeft ? -1 : 1;

        // All possible combinations of people moving (1 or 2 total people)
        int[][] moves = {
            {1, 0}, {2, 0}, // one or two missionaries
            {0, 1}, {0, 2}, // one or two cannibals
            {1, 1}          // one missionary and one cannibal
        };

        for (int[] move : moves) {
            int newM = current.missionariesLeft + direction * move[0];
            int newC = current.cannibalsLeft + direction * move[1];
            boolean newBoat = !current.boatLeft;

            State newState = new State(newM, newC, newBoat, current);
            if (newState.isValid()) {
                successors.add(newState);
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
