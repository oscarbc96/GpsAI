package gui;

import javax.swing.*;
import java.awt.*;

public class MapCell extends JPanel {

    public MapCell(String t) {
        setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label1 = new JLabel(t);
        this.add(label1, gbc);
    }

    public void setOrigin() {
        setBackground(new Color(253, 129, 130));
    }

    public void setDestination() {
        setBackground(new Color(129, 132, 252));
    }

    public void setPath() {
        setBackground(new Color(164, 242, 137));
    }

    public void setProcessed() {
        setBackground(new Color(250, 252, 77));
    }

    public void setPending() {
        setBackground(new Color(75, 244, 232));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(30, 30);
    }
}