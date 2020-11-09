import javax.swing.*;
import java.awt.*;
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

    public MemoryGame() {
        JFrame frame = new JFrame("Test");
        JButton newButton = new JButton("NEW");
        newButton.addActionListener(this);
        newButton.setPreferredSize(new Dimension(200,100));
        JButton seenButton = new JButton("SEEN");
        seenButton.addActionListener(this);
        seenButton.setPreferredSize(new Dimension(200,100));
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 200);
        frame.add(newButton, BorderLayout.LINE_START);
        frame.add(seenButton, BorderLayout.LINE_END);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "NEW")
            System.out.println("New Button has been pushed");
        if (e.getActionCommand() == "SEEN")
            System.out.println("Seen Button has been pushed");

    }

    public static void main(String[] args) {
        MemoryGame memoryGame = new MemoryGame();
        Scanner answer = new Scanner(System.in); // scanner to read user inputs
        System.out.println("Welcome to the game!"); // initial greeting
        System.out.println("Rules: We will present you a word. If this word has been shown before, "); // begin rules
        System.out.println("type 'yes', if not, type 'no'. If you give the wrong answer 3 times, you lose."); // end rules
        System.out.println("Ready?");


    } // end main


}// end MemoryGame
