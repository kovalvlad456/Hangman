import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanGame extends JFrame implements ActionListener {
    private JLabel wordLabel;
    private JLabel guessLabel;
    private JLabel resultLabel;
    private JButton[] buttons;
    private String[] words = {"JAVA", "PYTHON", "CPLUSPLUS", "JAVASCRIPT", "HTML", "CSS", "RUBY"};
    private String word;
    private int guessesLeft;
    private boolean[] guessed;
    
    public HangmanGame() {
        setTitle("Hangman Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        Font bigFont = new Font("Arial", Font.BOLD, 48);
        Font smallFont = new Font("Arial", Font.BOLD, 24);
        
        wordLabel = new JLabel();
        wordLabel.setFont(bigFont);
        wordLabel.setHorizontalAlignment(JLabel.CENTER);
        add(wordLabel, BorderLayout.NORTH);
        
        guessLabel = new JLabel("Guesses Left: ");
        guessLabel.setFont(smallFont);
        add(guessLabel, BorderLayout.WEST);
        
        resultLabel = new JLabel();
        resultLabel.setFont(bigFont);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 9));
        add(buttonPanel, BorderLayout.CENTER);
        
        buttons = new JButton[26];
        for (int i = 0; i < buttons.length; i++) {
            char letter = (char) ('A' + i);
            buttons[i] = new JButton(Character.toString(letter));
            buttons[i].setFont(smallFont);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
        
        setVisible(true);
        startGame();
    }
    
    public void startGame() {
        word = words[(int) (Math.random() * words.length)];
        guessesLeft = 6;
        guessed = new boolean[word.length()];
        for (int i = 0; i < guessed.length; i++) {
            guessed[i] = false;
        }
        updateLabels();
    }
    
    public void updateLabels() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (guessed[i]) {
                sb.append(word.charAt(i));
            } else {
                sb.append("_");
            }
        }
        wordLabel.setText(sb.toString());
        guessLabel.setText("Guesses Left: " + guessesLeft);
    }
    
    public boolean checkWin() {
        for (boolean b : guessed) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
            char letter = button.getText().charAt(0);
            boolean found = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    guessed[i] = true;
                    found = true;
                }
            }
            if (!found) {
                guessesLeft--;
            }
            updateLabels();
            if (checkWin()) {
                resultLabel.setText("You Win!");
                for (JButton b : buttons) {
                    b.setEnabled(false);
                }
            } else if (guessesLeft == 0) {
                resultLabel.setText("You Lose! The word was " + word + ".");
                for (JButton b : buttons) {
                    b.setEnabled(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        new HangmanGame();
    }
}