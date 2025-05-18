import java.util.Objects;

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

