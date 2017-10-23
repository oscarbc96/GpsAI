package map;

import algorithm.Algorithm;
import algorithm.Heuristic;
import algorithm.Solution;
import algorithm.State;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Map {

    private String name;
    private char[][] map;
    private int width, heigth;
    private Coordinate origin, destination;
    private HashMap info;

    public Map(String map) {
        this.name = map;
        MapReader reader = new MapReader(map);
        this.map = reader.getMap();
        this.width = reader.getMapWidth();
        this.heigth = reader.getMapHeigth();
        this.origin = reader.getOrigin();
        this.destination = reader.getDestination();
        this.info = reader.getInfo();
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public Coordinate getOrigin() {
        return this.origin;
    }

    public Coordinate getDestination() {
        return this.destination;
    }

    public MapCellCost getInfo(Coordinate coordinate) {
        char c = this.getType(coordinate);
        return (MapCellCost) this.info.get(c);
    }

    public char getType(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return this.map[x][y];
    }

    public int getPrice(Coordinate coordinate) {
        char c = this.getType(coordinate);
        MapCellCost info = (MapCellCost) this.info.get(c);
        return info.getPrice();
    }

    public int getTime(Coordinate coordinate) {
        char c = this.getType(coordinate);
        MapCellCost info = (MapCellCost) this.info.get(c);
        return info.getTime();
    }

    private HashSet<State> getNeighbors(State state) {
        Coordinate coordinate = state.getData();
        int x = coordinate.getX();
        int y = coordinate.getY();
        HashSet<State> neighbours = new HashSet<>();

        if (x - 1 >= 0)
            neighbours.add(new State(new Coordinate(x - 1, y)));
        if (y + 1 < width)
            neighbours.add(new State(new Coordinate(x, y + 1)));
        if (x + 1 < heigth)
            neighbours.add(new State(new Coordinate(x + 1, y)));
        if (y - 1 >= 0)
            neighbours.add(new State(new Coordinate(x, y - 1)));

        return neighbours;
    }

    private LinkedList<Coordinate> reconstructPath(HashMap<State, State> parents) {
        LinkedList<Coordinate> steps = new LinkedList<>();
        State current = new State(destination);

        while (current != null) {
            steps.add(current.getData());
            current = parents.getOrDefault(current, null);
        }
        Collections.reverse(steps);
        return steps;
    }

    public Solution runSearch(Algorithm algorithm, Heuristic heuristic) {
        LinkedList<String> logs = new LinkedList<>();
        State startState = new State(origin);
        State endState = new State(destination);
        boolean found = false;

        HashMap<State, State> parents = new HashMap<>();
        HashSet<State> processed = new HashSet<>();
        HashMap<State, Double> distances = new HashMap<>();
        PriorityQueue<State> pending = new PriorityQueue();

        float numberOfTransports = (float) info.size();
        float sumAllPrices = (float) info.values().stream().mapToInt(x -> ((MapCellCost) x).getPrice()).sum();
        float sumAllTimes = (float) info.values().stream().mapToInt(x -> ((MapCellCost) x).getTime()).sum();

        startState.setDistance(0);
        distances.put(startState, new Double(0));
        pending.add(startState);
        State current;

        while (!found && !pending.isEmpty()) {
            current = pending.poll();
            logs.add("====");
            logs.add("Current " + current);

            if (!processed.contains(current)) {
                processed.add(current);
                if (current.equals(endState)) {
                    logs.add("Solution found !!!");
                    return new Solution(algorithm, heuristic, this, reconstructPath(parents), processed.stream().map(State::getData).collect(Collectors.toCollection(LinkedList::new)), pending.stream().map(State::getData).collect(Collectors.toCollection(LinkedList::new)), logs);
                }

                Set<State> neighbors = getNeighbors(current);
                for (State neighbor : neighbors) {
                    logs.add("---");
                    logs.add("Neighbor: " + neighbor);
                    if (!processed.contains(neighbor)) {
                        if (algorithm.equals(Algorithm.BESTFIRST) && pending.contains(neighbor))
                            continue;

                        double totalDistance, distanceG = 0, distanceH = 0;
                        double manhattanDistance = neighbor.getData().distance(destination);

                        if (algorithm.equals(Algorithm.ASTAR))
                            distanceG = current.getDistance();

                        switch (heuristic) {
                            case ECONOMIC:
                                if (algorithm.equals(Algorithm.ASTAR))
                                    distanceG += getPrice(neighbor.getData());
                                distanceH = (sumAllPrices / numberOfTransports) * manhattanDistance + getPrice(neighbor.getData());
                                break;
                            case FAST:
                                if (algorithm.equals(Algorithm.ASTAR))
                                    distanceG += getTime(neighbor.getData());
                                distanceH = (sumAllTimes / numberOfTransports) * manhattanDistance + getTime(neighbor.getData());
                                break;
                            case BESTOPTION:
                                if (algorithm.equals(Algorithm.ASTAR))
                                    distanceG += (getPrice(neighbor.getData()) + getTime(neighbor.getData())) / 2.0f;
                                double distanceH1 = (sumAllPrices / numberOfTransports) * manhattanDistance + getPrice(neighbor.getData());
                                double distanceH2 = (sumAllTimes / numberOfTransports) * manhattanDistance + getTime(neighbor.getData());
                                distanceH = (distanceH1 + distanceH2) / 2.0f;
                                break;
                        }

                        totalDistance = distanceH + distanceG;
                        logs.add("Distance: " + totalDistance);

                        if (totalDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                            if (distances.containsKey(neighbor))
                                logs.add("Shortest path found! Updating...");
                            else
                                logs.add("Adding new state...");
                            distances.put(neighbor, totalDistance);
                            neighbor.setDistance(totalDistance);
                            parents.put(neighbor, current);
                            pending.add(neighbor);
                        }
                    } else {
                        logs.add("Already processed...");
                    }
                }
            }
        }

        return null;
    }
}