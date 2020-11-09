import javax.swing.*;
import java.awt.event.*;

public interface GUIInterface extends ActionListener {

    public void actionPerformed(ActionEvent e);

    public JFrame getFrame();

}
