package gui;

import javax.swing.*;

public class StatusBar extends JPanel {

    public StatusBar(String status) {
        JLabel statusLabel = new JLabel(status);
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(statusLabel);

//        JScrollPane labelScroll = new JScrollPane(statusLabel);
//        setLayout(new BorderLayout());
//        add(labelScroll, BorderLayout.CENTER);
    }
}
