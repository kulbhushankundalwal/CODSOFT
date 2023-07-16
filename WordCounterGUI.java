import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class WordCounterGUI extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JButton countButton;
    private JLabel resultLabel;

    public WordCounterGUI() {
        // Set up the JFrame
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        textArea = new JTextArea();
        countButton = new JButton("Count Words");
        countButton.addActionListener(this);
        resultLabel = new JLabel("Word count: 0");

        // Add components to the JFrame
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(countButton);
        bottomPanel.add(resultLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == countButton) {
            String text = textArea.getText();
            int wordCount = countWords(text);
            resultLabel.setText("Word count: " + wordCount);
        }
    }

    private int countWords(String text) {
        // Split the text into words using space and punctuation as delimiters
        String[] words = text.split("[\\s\\p{Punct}]+");
        return words.length;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordCounterGUI wordCounter = new WordCounterGUI();
            wordCounter.setVisible(true);
        });
    }
}
