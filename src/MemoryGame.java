import java.util.Scanner;

/** Public user interface for game to take place
 * Current rules; a word will appear on the screen, the user
 * must answer if this word has been seen yet or not.
 * The user has 3 lives, if the user guesses wrong
 * the game will be over. If the user can get through
 * the (amount-of-rounds here) without using their 3 lives, they win
 *
*/

 public class MemoryGame {

     public static void main(String[] args){
       Scanner answer = new Scanner(System.in); // scanner to read user inputs
       System.out.println("Welcome to the game!"); // initial greeting
       System.out.println("Rules: We will present you a word. If this word has been shown before, "); // begin rules
       System.out.println("type 'yes', if not, type 'no'. If you give the wrong answer 3 times, you lose."); // end rules
       System.out.println("Ready?"); 
         

    } // end main



 }// end MemoryGame
