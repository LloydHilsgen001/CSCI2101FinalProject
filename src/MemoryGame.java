import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.*;

/**
 * Public user interface for game to take place
 * Current rules; a word will appear on the screen, the user
 * must answer if this word has been seen yet or not.
 * The user has 3 lives, if the user guesses wrong
 * the game will be over. If the user can get through
 * the (amount-of-rounds here) without using their 3 lives, they win
 */


public class MemoryGame implements GameInterface {

    private JTextField currentWordField, pointField, livesField;
    private BagInterface<String> newWordBag, seenWordBag;
    private static final double PROBABILITY_OF_NEW_WORD = .5;
    private boolean isWordNew;
    private int numberOfPoints, numberOfLives;

    public MemoryGame() {
        numberOfLives = 3;
        numberOfPoints = 0;
        newWordBag = generateNewWordBag();
        seenWordBag = new ArrayBag<>();
        JFrame frame = generateFrame();
        frame.setVisible(true);
    }

    private BagInterface<String> generateNewWordBag() {
        BagInterface<String> bag = new ArrayBag<String>(50);
        try {
            Scanner data = new Scanner(new File("wordlist.txt"));
            while(data.hasNext())
                bag.add(data.next());
        } catch(FileNotFoundException e) {
            System.out.println("wordlist.txt not found");
        }
        return bag;
    }

    private JFrame generateFrame() {
        JFrame frame = new JFrame("Game");
        JButton newButton = new JButton("NEW");
        newButton.addActionListener(this);
        JButton seenButton = new JButton("SEEN");
        seenButton.addActionListener(this);
        currentWordField = new JTextField(generateNewWord());
        currentWordField.setEditable(false);
        currentWordField.setHorizontalAlignment(JTextField.CENTER);
        pointField = new JTextField("0 points");
        pointField.setEditable(false);
        pointField.setHorizontalAlignment(JTextField.LEFT);
        livesField = new JTextField("3 lives");
        livesField.setEditable(false);
        livesField.setHorizontalAlignment(JTextField.RIGHT);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 0;
        c.gridwidth = 2;
        frame.setSize(400, 250);
        frame.add(currentWordField, c);
        c.gridwidth = 1;
        c.gridy = 1;
        c.weightx = .5;
        c.ipady = 150;
        frame.add(newButton, c);
        c.gridx = 1;
        frame.add(seenButton, c);
        c.ipady = 0;
        c.gridy = 2;
        c.gridx = 0;
        frame.add(pointField, c);
        c.gridx = 1;
        frame.add(livesField, c);
        return frame;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "NEW")
            if(isWordNew)
                numberOfPoints++;
            else
                numberOfLives--;
        if (e.getActionCommand() == "SEEN")
            if(!isWordNew)
                numberOfPoints++;
            else
                numberOfLives--;
        currentWordField.setText(generateNewWord());
        pointField.setText(numberOfPoints + " points");
        livesField.setText(numberOfLives + " lives");
        if(numberOfLives <= 0)
            currentWordField.setText("You have lost!");
    }

    public String generateNewWord() {
        String word;
        if(Math.random() > PROBABILITY_OF_NEW_WORD || seenWordBag.isEmpty()) {
            word = newWordBag.remove();
            isWordNew = true;
        }
        else {
            word = seenWordBag.remove();
            isWordNew = false;
        }
        seenWordBag.add(word);
        return word;
    }


    public static void main(String[] args) {
        MemoryGame memoryGame = new MemoryGame();
        Scanner answer = new Scanner(System.in); // scanner to read user inputs
        System.out.println("Welcome to the game!"); // initial greeting
        System.out.println("Rules: We will present you a word. If this word has been shown before, "); // begin rules
        System.out.println("type 'yes', if not, type 'no'. If you give the wrong answer 3 times, you lose."); // end rules
        System.out.println("Ready?");


    }


}// end MemoryGame
