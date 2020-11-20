import javax.swing.*;
import java.awt.event.*;

public interface GameInterface extends ActionListener {

    public void actionPerformed(ActionEvent e);

    public String generateNewWord();

}
