

import java.awt.*;
import javax.swing.*;
public class ArkanoidFrame extends JFrame{
    ArkanoidPanel panel;
    ArkanoidFrame(){
        super("Basic Frame");
        panel = new ArkanoidPanel();
        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ARKANOID");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
