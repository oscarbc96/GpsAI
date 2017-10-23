package algorithm;

import map.Coordinate;

public class State implements Comparable<State> {
    private Coordinate data;
    private double distance;

    public State(Coordinate data) {
        this.data = data;
    }

    public Coordinate getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        State aux = (State) o;
        return data.equals(aux.getData());
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String toString() {
        return data.toString();
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public int compareTo(State o) {
        if (o.getDistance() > distance)
            return -1;
        else if (o.getDistance() < distance)
            return 1;
        else
            return 0;
    }
}
