package map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MapReader {
    private String filename;
    private int[] size;
    private char[][] map;
    private Coordinate[] coordinates;
    private HashMap<Character, MapCellCost> info;

    public MapReader(String filename) {
        this.filename = filename;
        this.size = parseMapSize();
        this.coordinates = parseMapCoordinates();
        this.info = parseInfo();
        this.map = parseMap();
    }

    private Stream<String> getFileContent() {
        InputStream instream = this.getClass().getResourceAsStream("maps/" + this.filename);
        return new BufferedReader(new InputStreamReader(instream)).lines();
    }

    private int[] parseMapSize() {
        String line = (String) getFileContent().toArray()[0];

        Pattern pattern = Pattern.compile(",");
        return pattern.splitAsStream(line)
                .map(Integer::valueOf)
                .mapToInt(s -> s)
                .toArray();
    }

    private Coordinate[] parseMapCoordinates() {
        String line = (String) getFileContent().skip(1).toArray()[0];

        Pattern pattern = Pattern.compile(",");
        int[] crdts = pattern.splitAsStream(line)
                .map(Integer::valueOf)
                .mapToInt(s -> s)
                .toArray();
        Coordinate[] coordinates = {new Coordinate(crdts[0], crdts[1]), new Coordinate(crdts[2], crdts[3])};
        return coordinates;
    }

    private MapCellCost parseInfoLine(String line) {
        Pattern pattern = Pattern.compile(",");
        String[] lineArr = pattern.splitAsStream(line).toArray(String[]::new);
        char id = lineArr[0].toCharArray()[0];
        int time = Integer.valueOf(lineArr[1]);
        int price = Integer.valueOf(lineArr[2]);
        return new MapCellCost(id, time, price);
    }

    private HashMap<Character, MapCellCost> parseInfo() {
        Iterator<String> stringIterator = getFileContent().skip(2).iterator();
        HashMap<Character, MapCellCost> mapInfo = new HashMap<>();
        int lines = Integer.valueOf(stringIterator.next());

        for (int i = 0; i < lines; i++) {
            MapCellCost info = parseInfoLine(stringIterator.next());
            mapInfo.put(info.getId(), info);
        }

        return mapInfo;
    }

    private char[] parseMapLine(String line) {
        char[] aux = line.toCharArray();
        if (aux.length > getMapWidth())
            return Arrays.copyOfRange(aux, 0, getMapWidth());
        else
            return aux;
    }

    private char[][] parseMap() {
        char[][] map = new char[getMapHeigth()][getMapWidth()];
        Iterator<String> stringIterator = getFileContent().skip(3 + this.info.size()).iterator();

        for (int i = 0; i < getMapHeigth(); i++) {
            map[i] = parseMapLine(stringIterator.next());
        }

        return map;
    }

    public int getMapHeigth() {
        return this.size[0];
    }

    public int getMapWidth() {
        return this.size[1];
    }

    public Coordinate getOrigin() {
        return this.coordinates[0];
    }

    public Coordinate getDestination() {
        return this.coordinates[1];
    }

    public char[][] getMap() {
        return this.map;
    }

    public HashMap<Character, MapCellCost> getInfo() {
        return this.info;
    }
}
