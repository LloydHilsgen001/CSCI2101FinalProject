import javax.swing.*;
import java.awt.event.*;

public interface GameInterface extends ActionListener {

    /**
     * Handles action events associated with buttons, dispays/updates points, lives, and player losers scenario.
     * If player guesses correctly, they get 1 point. If not, they loose 1 life. Once they run out of lives,
     * the game ends.
     * @param e The event that triggered actionPerformed. From the ActionListener class, which GameInterface extends.
     */
    public void actionPerformed(ActionEvent e);

    public String generateNewWord();

}
