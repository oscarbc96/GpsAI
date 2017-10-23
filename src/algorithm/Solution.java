package algorithm;

import gui.LoggerOutput;
import gui.StatusBar;
import map.Coordinate;
import map.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Solution {

    private Algorithm algorithm;
    private Heuristic heuristic;
    private Map map;
    private LinkedList<Coordinate> path;
    private LinkedList<String> logs;
    private HashMap<String, Integer> statistics;
    private LinkedList<Coordinate> processedNodes;
    private LinkedList<Coordinate> pendingNodes;


    public Solution(Algorithm algorithm, Heuristic heuristic, Map map, LinkedList<Coordinate> path, LinkedList processedNodes, LinkedList pendingNodes, LinkedList<String> logs) {
        this.algorithm = algorithm;
        this.heuristic = heuristic;
        this.map = map;
        this.path = path;
        this.processedNodes = processedNodes;
        this.pendingNodes = pendingNodes;
        this.logs = logs;
        this.statistics = new HashMap<>();

        this.statistics.put("processedNodes", processedNodes.size());
        this.statistics.put("price", path.stream().skip(1).mapToInt(x -> map.getPrice(x)).sum());
        this.statistics.put("time", path.stream().skip(1).mapToInt(x -> map.getTime(x)).sum());
    }

    public void runGUI() {
        JFrame frame = new JFrame(getTitle());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Map start
        frame.add(new gui.Map(map, path, processedNodes, pendingNodes));
        // Map end

        // Status bar start
        StatusBar status = new StatusBar(getStatistics());
        status.setPreferredSize(new Dimension(frame.getWidth(), 25));
        frame.add(status, BorderLayout.SOUTH);
        // Status bar end

        // Sidebar start
        LoggerOutput logs = new LoggerOutput(String.join("\n", this.logs));
        logs.setPreferredSize(new Dimension(320, frame.getHeight()));
        frame.add(logs, BorderLayout.EAST);
        // Sidebar end

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void saveToImage() {
        JFrame frame = new JFrame(getTitle());
        frame.add(new gui.Map(map, path, processedNodes, pendingNodes));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.pack();

        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        frame.getContentPane().printAll(g);
        g.dispose();

        try {
            ImageIO.write(image, "png", new File("src/map/maps/output/" + getTitle() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTitle() {
        StringBuilder str = new StringBuilder();
        str.append(map.getName());
        str.append(" ");
        str.append(algorithm);
        str.append(" ");
        str.append(heuristic);
        return str.toString();
    }

    private String getStatistics() {
        StringBuilder str = new StringBuilder();
        str.append(" ");
        str.append("Processed nodes: ");
        str.append(statistics.getOrDefault("processedNodes", 0));
        str.append(" ");
        str.append("Time: ");
        str.append(statistics.getOrDefault("time", 0));
        str.append(" ");
        str.append("Price: ");
        str.append(statistics.getOrDefault("price", 0));

        return str.toString();
    }

    @Override
    public String toString() {
        return getTitle() + " " + String.join(" -> ", path.stream().map(x -> x.toString()).collect(Collectors.toList()));
    }
}
