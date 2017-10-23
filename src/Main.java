import algorithm.Algorithm;
import algorithm.Heuristic;
import algorithm.Solution;
import map.Map;

import java.io.File;

public class Main {

    private static void runAllMaps() {
        File folder = new File("src/map/maps/");
        for (File file : folder.listFiles()) {
            if (!file.isDirectory() &&  file.getName().toLowerCase().endsWith(".txt")) {
                String filename = file.getName();
                Map map = new Map(filename);
                runAllAlgorithm(map);
            }
        }
    }

    private static void runAllAlgorithm(Map map) {
        for (Algorithm alg : Algorithm.values()) {
            for (Heuristic heur : Heuristic.values()) {
                Solution solution = map.runSearch(alg, heur);
                solution.runGUI();
                System.out.println(solution);
                solution.saveToImage();
            }
        }
    }

    public static void main(String[] args) {
        runAllMaps();
//        default | test1 | test2 | test3 | test4
//        Map map = new Map("default.txt");
//        runAllAlgorithm(map);

    }
}
