import java.util.*;

class WaterJugState {
    int jug4, jug3;

    WaterJugState(int jug4, int jug3) {
        this.jug4 = jug4;
        this.jug3 = jug3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WaterJugState)) return false;
        WaterJugState state = (WaterJugState) o;
        return jug4 == state.jug4 && jug3 == state.jug3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jug4, jug3);
    }

    @Override
    public String toString() {
        return "(" + jug4 + ", " + jug3 + ")";
    }
}

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
                List<WaterJugState> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current);
                }
                Collections.reverse(path);
                System.out.println("Solution path (DFS):");
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

        states.add(new WaterJugState(cap4, b));
        states.add(new WaterJugState(a, cap3));
        states.add(new WaterJugState(0, b));
        states.add(new WaterJugState(a, 0));

        int pour4to3 = Math.min(a, cap3 - b);
        states.add(new WaterJugState(a - pour4to3, b + pour4to3));

        int pour3to4 = Math.min(b, cap4 - a);
        states.add(new WaterJugState(a + pour3to4, b - pour3to4));

        return states;
    }
}
