import java.util.*;
public class WaterJugDLS {

    static List<WaterJugState> path = new ArrayList<>();
    static Set<WaterJugState> visited = new HashSet<>();
    static int maxDepth = 15;  // You can change this limit

    public static void main(String[] args) {
        WaterJugState start = new WaterJugState(0, 0);
        int goal = 2;

        boolean found = dls(start, goal, 4, 3, 0);

        if (found) {
            Collections.reverse(path); // Reverse the collected path
            System.out.println("Solution path (Depth-Limited Search):");
            for (WaterJugState state : path) {
                System.out.println(state);
            }
        } else {
            System.out.println("No solution found within depth limit.");
        }
    }

    static boolean dls(WaterJugState current, int goal, int cap4, int cap3, int depth) {
        if (depth > maxDepth) return false;

        if (current.jug4 == goal) {
            path.add(current);
            return true;
        }

        visited.add(current);

        for (WaterJugState next : generateNextStates(current, cap4, cap3)) {
            if (!visited.contains(next)) {
                visited.add(next);
                if (dls(next, goal, cap4, cap3, depth + 1)) {
                    path.add(current);  // Add to path if solution found
                    return true;
                }
            }
        }

        return false;
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
