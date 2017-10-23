package algorithm;

public enum Heuristic {
    ECONOMIC("Economic"), FAST("Fast"), BESTOPTION("Best Option");

    private final String name;

    Heuristic(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
