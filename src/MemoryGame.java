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
 * the game will be over.
 */


public class MemoryGame implements GameInterface {

    private JTextField currentWordField, pointField, livesField; // used to display current word, points, and lives
    private BagInterface<String> newWordBag, seenWordBag; // creates bag of new words and bag of seen words
    private static final double PROBABILITY_OF_NEW_WORD = .5; // sets probablility of a new word appearing to .5
    private boolean isWordNew;
    private int numberOfPoints, numberOfLives; //tracks number of points gained and number of lives lost
    private boolean isGameOver;

    // memory game constructor
    public MemoryGame() {
        numberOfLives = 3; // user starts with 3 lives, decreases by 1 per wrong answer
        numberOfPoints = 0; // user starts with zero points, increases by 1 per correct answer
        newWordBag = generateNewWordBag(); //unseen words
        seenWordBag = new ArrayBag<>(); // contains words that have been seen
        JFrame frame = generateFrame();
        frame.setVisible(true);
    } 

    // creates bag of new words
    private BagInterface<String> generateNewWordBag() {
        BagInterface<String> bag = new ArrayBag<String>(50);
        try {
            Scanner data = new Scanner(new File("wordlist.txt"));
            while(data.hasNext())
                bag.add(data.next());
        } catch(FileNotFoundException e) { // file not found, throws error
            System.out.println("wordlist.txt not found");
        }
        return bag;
    } 
  
    /** Creates the interface the player interacts with
     * creates SEEN button, NEW button; displays current word (currentWorldField), number of points player has (pointField)
     * number of lives player has remaining (livesField), and dimensions for grids of each
     */
    private JFrame generateFrame() {
        JFrame frame = new JFrame("Game"); // window name
        // NEW and SEEN buttons
        JButton newButton = new JButton("NEW"); //user presses this button when they think the word is new
        newButton.addActionListener(this);
        JButton seenButton = new JButton("SEEN"); // user press this button when they've seen the words before
        seenButton.addActionListener(this);

        // currentWorldField: displays current word
        currentWordField = new JTextField(generateNewWord());
        currentWordField.setEditable(false); // word not editable by user
        currentWordField.setHorizontalAlignment(JTextField.CENTER); // displayed center of window

        // pointField: displays number of points
        pointField = new JTextField("0 points");
        pointField.setEditable(false); // number of points not editable by user
        pointField.setHorizontalAlignment(JTextField.LEFT); // displays points to the left in window

        // livesField: displays number of lives
        livesField = new JTextField("3 lives");
        livesField.setEditable(false); // number of lives not editable by user
        livesField.setHorizontalAlignment(JTextField.RIGHT); // displays lives to the right in window

        // grid dimensions
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 0;
        c.gridwidth = 2;

        // frame size, current world dimensions
        frame.setSize(400, 250);
        frame.add(currentWordField, c);
        c.gridwidth = 1;
        c.gridy = 1;
        c.weightx = .5;
        c.ipady = 150;

        // NEW button dimensions
        frame.add(newButton, c);
        c.gridx = 1;

        // SEEN button dimensions
        frame.add(seenButton, c);
        c.ipady = 0;
        c.gridy = 2;
        c.gridx = 0;

        // points field grid
        frame.add(pointField, c);
        c.gridx = 1;

        // lives field frame
        frame.add(livesField, c);
        return frame;
    } // end generateFrame

    /**
     * Handles action events associated with buttons, dispays/updates points, lives, and player losers scenario.
     * If player guesses correctly, they get 1 point. If not, they loose 1 life. Once they run out of lives,
     * the game ends.
     */
    public void actionPerformed(ActionEvent e) {

            // NEW button response
            if (e.getActionCommand() == "NEW") // if NEW button pressed
                if (isWordNew) // if player chose NEW and is correct
                    numberOfPoints++; // add one point
                else if (numberOfLives != 0) { // if player has at least one life left, deduct one life
                    numberOfLives--;  // one life is deducted
                } // end if (NEW scenario)

            // SEEN button response
            if (e.getActionCommand() == "SEEN") // if SEEN button pressed
                if (!isWordNew) // if player chose SEEN and is correct
                    numberOfPoints++; // add 1 point
                else if (numberOfLives != 0) { // if player has at least one life left, deduct one life
                    numberOfLives--; // deducts 1 life
                } // end if (SEEN scenario)

            currentWordField.setText(generateNewWord());
            pointField.setText(numberOfPoints + " points"); //displays number of points gained
            livesField.setText(numberOfLives + " lives"); // displays number of lives left

        if(numberOfLives <= 0) // if the number of lives are at zero, game ends
            currentWordField.setText("You have lost!"); // player loses scenario
    } // end actionPerformed

    // generates the word player sees
    public String generateNewWord() {
        String word;
        if(Math.random() > PROBABILITY_OF_NEW_WORD || seenWordBag.isEmpty()) {
            word = newWordBag.remove();
            isWordNew = true; // word is new,
        } // end if
        else {
            word = seenWordBag.remove(); // remove word from the seenWordBag
            isWordNew = false; // sets isWordNew to false, indicating word has been seen before
        } // end else
        seenWordBag.add(word); // add current word to seenWordBag
        return word;
    } // end generateNewWord

   // displays rules, runs game
    public static void main(String[] args) {
        MemoryGame memoryGame = new MemoryGame();
       Scanner answer = new Scanner(System.in); // scanner to read user inputs
       System.out.println("Welcome to the game!"); // initial greeting
       System.out.println("Rules: We will present you a word. If this word has been shown before, "); // begin rules
        System.out.println("click 'SEEN', if not click 'NEW'. If you give the wrong answer 3 times, you lose."); // end rules
        System.out.println("Ready?");
  }// end main
} //end memorygame