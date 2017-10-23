package map;

public class MapCellCost {
    private char id;
    private int time;
    private int price;

    public MapCellCost(char id, int time, int price) {
        this.id = id;
        this.time = time;
        this.price = price;
    }

    public char getId() {
        return this.id;
    }

    public int getTime() {
        return this.time;
    }

    public int getPrice() {
        return this.price;
    }

    public String toString() {
        return "Time: " + time + " Price: " + price;
    }
}
