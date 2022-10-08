package spriteview;

import javax.swing.*;
import java.awt.*;

public class Application {

    ScreenView scv;
    SpriteView spv;

    public Application(int mode) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        frame.setTitle("GBA Spritesheet View -- Mode " + mode);
        frame.setPreferredSize(new Dimension(875, 500));
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        OAM spriteRegister = new OAM();

        RegisterView rv = new RegisterView(this, spriteRegister);
        panel.add(rv);

        JPanel screen = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 50));
        screen.setBackground(Color.WHITE);

        this.spv = new SpriteView(spriteRegister);
        this.scv = new ScreenView(spv, spriteRegister);

        screen.add(scv, BorderLayout.CENTER);
        screen.add(spv, BorderLayout.CENTER);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(screen, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

    } // Constructor

    public void update() {
        spv.updateComponent();
        scv.updateComponent();

    } // update

} // Application
