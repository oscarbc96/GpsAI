package map;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int distance(Coordinate d) {
        int dx = Math.abs(d.getX() - this.getX());
        int dy = Math.abs(d.getY() - this.getY());
        return dx + dy;
    }

    @Override
    public boolean equals(Object o) {
        Coordinate aux = (Coordinate) o;
        return aux.x == x && aux.y == y;
    }

    @Override
    public int hashCode() {
        return (x * 31) ^ y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
