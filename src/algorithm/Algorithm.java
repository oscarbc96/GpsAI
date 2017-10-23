package algorithm;

public enum Algorithm {
    BESTFIRST("Best-First"),
    ASTAR("A*");

    private final String name;

    Algorithm(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
