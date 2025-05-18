import java.util.*;

class State {
    int missionariesLeft, cannibalsLeft;
    boolean boatLeft; // true = boat on left side
    State parent;

    State(int m, int c, boolean b, State p) {
        missionariesLeft = m;
        cannibalsLeft = c;
        boatLeft = b;
        parent = p;
    }

    boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0 && !boatLeft;
    }

    boolean isValid() {
        int missionariesRight = 3 - missionariesLeft;
        int cannibalsRight = 3 - cannibalsLeft;

        if (missionariesLeft < 0 || cannibalsLeft < 0 ||
            missionariesRight < 0 || cannibalsRight < 0) return false;

        if ((missionariesLeft > 0 && missionariesLeft < cannibalsLeft) ||
            (missionariesRight > 0 && missionariesRight < cannibalsRight)) return false;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof State) {
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
