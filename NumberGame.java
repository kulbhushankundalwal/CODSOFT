// The "NumberGame" project is a Java Swing-based guessing game application where users guess a randomly generated number within a specified range and receive feedback on their guesses.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * The NumberGame class represents a GUI-based number guessing game.
 */
public class NumberGame extends JFrame {
    private final int MIN_NUMBER = 1;
    private final int MAX_NUMBER = 100;
    private final int MAX_ATTEMPTS = 5;

    private int randomNumber;
    private int attempts;
    private int score;

    private JLabel titleLabel;
    private JLabel promptLabel;
    private JTextField guessTextField;
    private JButton submitButton;
    private JLabel resultLabel;
    private JLabel scoreLabel;
    private JButton playAgainButton;

    /**
     * Constructs a NumberGame object and sets up the game GUI.
     */
    public NumberGame() {
        setTitle("Number Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(700, 600);
        setLocationRelativeTo(null);

        initializeComponents();
        addComponentsToContainer();

        randomNumber = generateRandomNumber(MIN_NUMBER, MAX_NUMBER);
        attempts = 0;
        score = 0;
        updateScoreLabel();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewRound();
            }
        });
    }

    /**
     * Initializes the GUI components of the game.
     */
    private void initializeComponents() {
        titleLabel = new JLabel("Number Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        promptLabel = new JLabel("Enter your guess between 1 and 100 :");

        guessTextField = new JTextField(10);

        submitButton = new JButton("Submit");

        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        scoreLabel = new JLabel("Score: 0");

        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
    }

    /**
     * Adds the GUI components to the container.
     */
    private void addComponentsToContainer() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(titleLabel, gbc);

        gbc.gridy = 1;
        container.add(promptLabel, gbc);

        gbc.gridy = 2;
        container.add(guessTextField, gbc);

        gbc.gridy = 3;
        container.add(submitButton, gbc);

        gbc.gridy = 4;
        container.add(resultLabel, gbc);

        gbc.gridy = 5;
        container.add(scoreLabel, gbc);

        gbc.gridy = 6;
        container.add(playAgainButton, gbc);
    }

    /**
     * Processes the user's guess and updates the game state accordingly.
     */
    private void processGuess() {
        int userGuess = Integer.parseInt(guessTextField.getText());
        attempts++;

        if (userGuess == randomNumber) {
            resultLabel.setText("Congratulations! Your guess is correct.");
            resultLabel.setForeground(Color.GREEN);
            score++;
            submitButton.setEnabled(false);
            playAgainButton.setVisible(true);
        } else if (attempts >= MAX_ATTEMPTS) {
            resultLabel.setText("Sorry, you've reached the maximum number of attempts.");
            resultLabel.setForeground(Color.RED);
            submitButton.setEnabled(false);
            playAgainButton.setVisible(true);
        } else if (userGuess < randomNumber) {
            resultLabel.setText("Too low! Try again.");
            resultLabel.setForeground(Color.BLUE);
        } else {
            resultLabel.setText("Too high! Try again.");
            resultLabel.setForeground(Color.BLUE);
        }

        updateScoreLabel();
        guessTextField.setText("");
    }

    /**
     * Updates the score label with the current score.
     */
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Starts a new round of the game.
     */
    private void startNewRound() {
        randomNumber = generateRandomNumber(MIN_NUMBER, MAX_NUMBER);
        attempts = 0;
        resultLabel.setText("");
        submitButton.setEnabled(true);
        playAgainButton.setVisible(false);
    }

    /**
     * Generates a random number within the given range.
     *
     * @param min the minimum value of the range
     * @param max the maximum value of the range
     * @return the randomly generated number
     */
    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * The main method to start the number guessing game.
     *
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGame().setVisible(true);
            }
        });
    }
}