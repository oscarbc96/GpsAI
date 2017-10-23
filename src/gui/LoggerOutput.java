package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class LoggerOutput extends JPanel {

    public LoggerOutput(String logs) {
        JTextArea debugTextArea = new JTextArea();
        debugTextArea.setEditable(false);
        debugTextArea.setText(logs);

        JScrollPane debugScroll = new JScrollPane(debugTextArea);
        setLayout(new BorderLayout());
        add(debugScroll, BorderLayout.CENTER);

        JTextField searchBox = new JTextField();
        searchBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            public void removeUpdate(DocumentEvent e) {
                search();
            }

            public void insertUpdate(DocumentEvent e) {
                search();
            }

            public void search() {
                String word = searchBox.getText().trim();
                debugTextArea.getHighlighter().removeAllHighlights();
                if (!word.equals("")) {
                    Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);

                    int offset = debugTextArea.getText().indexOf(word);
                    int length = word.length();

                    while (offset != -1) {
                        try {
                            debugTextArea.getHighlighter().addHighlight(offset, offset + length, painter);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        offset = debugTextArea.getText().indexOf(word, offset + 1);
                    }
                }
            }
        });

        add(searchBox, BorderLayout.NORTH);
    }
}
