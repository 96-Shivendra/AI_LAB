import java.util.*;

public class WaterJugBFS {

    static void solveWaterJugBFS() {
        int jug4Capacity = 4;
        int jug3Capacity = 3;
        int goal = 2;

        Queue<WaterJugState> queue = new LinkedList<>();
        Set<WaterJugState> visited = new HashSet<>();
        Map<WaterJugState, WaterJugState> parent = new HashMap<>();

        WaterJugState start = new WaterJugState(0, 0);
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            WaterJugState current = queue.poll();

            if (current.jug4 == goal) {
                printPath(current, parent);
                return;
            }

            // Generate possible next states
            List<WaterJugState> nextStates = generateNextStates(current, jug4Capacity, jug3Capacity);

            for (WaterJugState next : nextStates) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    parent.put(next, current);
                    queue.add(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static List<WaterJugState> generateNextStates(WaterJugState current, int cap4, int cap3) {
        List<WaterJugState> states = new ArrayList<>();

        int a = current.jug4;
        int b = current.jug3;

        // Fill jugs
        states.add(new WaterJugState(cap4, b)); // Fill 4-gallon
        states.add(new WaterJugState(a, cap3)); // Fill 3-gallon

        // Empty jugs
        states.add(new WaterJugState(0, b)); // Empty 4-gallon
        states.add(new WaterJugState(a, 0)); // Empty 3-gallon

        // Pour from 4 to 3
        int pour4to3 = Math.min(a, cap3 - b);
        states.add(new WaterJugState(a - pour4to3, b + pour4to3));

        // Pour from 3 to 4
        int pour3to4 = Math.min(b, cap4 - a);
        states.add(new WaterJugState(a + pour3to4, b - pour3to4));

        return states;
    }

    static void printPath(WaterJugState goal, Map<WaterJugState, WaterJugState> parent) {
        List<WaterJugState> path = new ArrayList<>();
        WaterJugState current = goal;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        System.out.println("Solution path:");
        for (WaterJugState state : path) {
            System.out.println(state);
        }
    }

    public static void main(String[] args) {
        solveWaterJugBFS();
    }
}
