package gui;

import map.Coordinate;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.LinkedList;


public class Map extends JPanel {

    public Map(map.Map map, LinkedList<Coordinate> coordinates, LinkedList<Coordinate> processed, LinkedList<Coordinate> pending) {
        Coordinate origin = map.getOrigin();
        Coordinate destination = map.getDestination();
        Coordinate current;

        int ROWS = map.getHeigth();
        int COLUMNS = map.getWidth();

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                current = new Coordinate(row, col);
                gbc.gridx = col;
                gbc.gridy = row;

                MapCell cell = new MapCell("" + map.getType(current));
                cell.setToolTipText("(" + row + ", " + col + ") " + map.getInfo(current).toString());

                if (current.equals(origin))
                    cell.setOrigin();
                else if (current.equals(destination))
                    cell.setDestination();
                else if (coordinates.contains(current))
                    cell.setPath();
                else if (processed.contains(current))
                    cell.setProcessed();
                else if (pending.contains(current))
                    cell.setPending();


                Border border;
                if (row < ROWS - 1) {
                    if (col < COLUMNS - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < COLUMNS - 1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                cell.setBorder(border);
                add(cell, gbc);
            }
        }
    }
}
