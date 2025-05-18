import java.util.*;
class State2 {
    int missionariesLeft, cannibalsLeft;
    boolean boatLeft;

    State2(int m, int c, boolean b) {
        missionariesLeft = m;
        cannibalsLeft = c;
        boatLeft = b;
    }

    boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0 && !boatLeft;
    }

    boolean isValid() {
        int missionariesRight = 3 - missionariesLeft;
        int cannibalsRight = 3 - cannibalsLeft;

        if (missionariesLeft < 0 || cannibalsLeft < 0 ||
            missionariesRight < 0 || cannibalsRight < 0)
            return false;

        if ((missionariesLeft > 0 && missionariesLeft < cannibalsLeft) ||
            (missionariesRight > 0 && missionariesRight < cannibalsRight))
            return false;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof State) {
            State other = (State) o;
            return missionariesLeft == other.missionariesLeft &&
                   cannibalsLeft == other.cannibalsLeft &&
                   boatLeft == other.boatLeft;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionariesLeft, cannibalsLeft, boatLeft);
    }

    @Override
    public String toString() {
        return "(" + missionariesLeft + "M, " + cannibalsLeft + "C, Boat " +
               (boatLeft ? "Left" : "Right") + ")";
    }
}
class StateWithDepth {
    State2 state;
    int depth;

    StateWithDepth(State2 state, int depth) {
        this.state = state;
        this.depth = depth;
    }
}

public class MissionariesAndCannibalsDLSStack {

    public static void main(String[] args) {
        int maxDepth = 20;

        State2 start = new State2(3, 3, true);
        Stack<StateWithDepth> stack = new Stack<>();
        Set<State2> visited = new HashSet<>();
        Map<State2, State2> parent = new HashMap<>();

        stack.push(new StateWithDepth(start, 0));
        visited.add(start);

        while (!stack.isEmpty()) {
            StateWithDepth currentWD = stack.pop();
            State2 current = currentWD.state;
            int depth = currentWD.depth;

            if (current.isGoal()) {
                printPath(current, parent);
                return;
            }

            if (depth >= maxDepth) continue;

            for (State2 next : generateSuccessors(current)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    parent.put(next, current);
                    stack.push(new StateWithDepth(next, depth + 1));
                }
            }
        }

        System.out.println("No solution found within depth limit.");
    }
    static List<State2> generateSuccessors(State2 current) {
        List<State2> successors = new ArrayList<>();
        int dir = current.boatLeft ? -1 : 1;

        int[][] moves = {
            {1, 0}, {2, 0}, // missionaries
            {0, 1}, {0, 2}, // cannibals
            {1, 1}          // one of each
        };

        for (int[] move : moves) {
            int newM = current.missionariesLeft + dir * move[0];
            int newC = current.cannibalsLeft + dir * move[1];
            boolean newBoat = !current.boatLeft;

            State2 next = new State2(newM, newC, newBoat);
            if (next.isValid()) {
                successors.add(next);
            }
        }

        return successors;
    }

    static void printPath(State2 goal, Map<State2, State2> parent) {
        List<State2> path = new ArrayList<>();
        State2 current = goal;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        System.out.println("Solution path (Stack-based DLS):");
        for (State2 s : path) {
            System.out.println(s);
        }
    }
}