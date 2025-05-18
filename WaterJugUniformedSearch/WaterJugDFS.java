import java.util.*;

public class WaterJugDFS {

    public static void main(String[] args) {
        int cap4 = 4, cap3 = 3, goal = 2;
        WaterJugState start = new WaterJugState(0, 0);

        Stack<WaterJugState> stack = new Stack<>();
        Set<WaterJugState> visited = new HashSet<>();
        Map<WaterJugState, WaterJugState> parent = new HashMap<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            WaterJugState current = stack.pop();

            if (current.jug4 == goal) {
                // Print path from start to current
                List<WaterJugState> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current);
                }
                Collections.reverse(path);
                System.out.println("Solution path:");
                for (WaterJugState state : path) {
                    System.out.println(state);
                }
                return;
            }

            for (WaterJugState next : generateNextStates(current, cap4, cap3)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    parent.put(next, current);
                    stack.push(next);
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
        states.add(new WaterJugState(cap4, b));
        states.add(new WaterJugState(a, cap3));

        // Empty jugs
        states.add(new WaterJugState(0, b));
        states.add(new WaterJugState(a, 0));

        // Pour from 4 to 3
        int pour4to3 = Math.min(a, cap3 - b);
        states.add(new WaterJugState(a - pour4to3, b + pour4to3));

        // Pour from 3 to 4
        int pour3to4 = Math.min(b, cap4 - a);
        states.add(new WaterJugState(a + pour3to4, b - pour3to4));

        return states;
    }
}

