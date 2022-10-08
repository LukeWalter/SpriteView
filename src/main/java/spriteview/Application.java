package spriteview;

import javax.swing.*;
import java.awt.*;

public class Application {

    public Application(int mode) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        frame.setTitle("GBA Spritesheet View -- Mode " + mode);
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        RegisterView rv = new RegisterView();
        panel.add(rv);

        JPanel screen = new JPanel(new FlowLayout());
        screen.setBackground(Color.WHITE);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(screen, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

    } // Constructor

} // Application
