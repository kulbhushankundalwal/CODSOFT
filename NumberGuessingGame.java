import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private final int MIN_RANGE = 1;
    private final int MAX_RANGE = 100;
    private int generatedNumber;
    private int attempts;
    private int roundsWon;

    private JLabel infoLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel resultLabel;
    private JButton playAgainButton;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        infoLabel = new JLabel("Guess a number between " + MIN_RANGE + " and " + MAX_RANGE + ":");
        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        resultLabel = new JLabel();
        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);

        add(infoLabel);
        add(guessField);
        add(guessButton);
        add(resultLabel);
        add(playAgainButton);

        generatedNumber = generateRandomNumber();
        attempts = 0;
        roundsWon = 0;

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgain();
            }
        });
    }

    private int generateRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;
            if (guess == generatedNumber) {
                resultLabel.setText("Correct! You guessed the number in " + attempts + " attempts.");
                playAgainButton.setVisible(true);
                roundsWon++;
                guessButton.setEnabled(false);
            } else if (guess < generatedNumber) {
                resultLabel.setText("Too low. Try again.");
            } else {
                resultLabel.setText("Too high. Try again.");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Enter a valid number.");
        }
        guessField.setText("");
    }

    private void playAgain() {
        generatedNumber = generateRandomNumber();
        attempts = 0;
        guessButton.setEnabled(true);
        resultLabel.setText("");
        infoLabel.setText("Guess a number between " + MIN_RANGE + " and " + MAX_RANGE + ":");
        playAgainButton.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NumberGuessingGame game = new NumberGuessingGame();
                game.setVisible(true);
            }
        });
    }
}
