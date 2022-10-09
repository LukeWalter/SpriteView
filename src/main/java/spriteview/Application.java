package spriteview;

import javax.swing.*;
import java.awt.*;

public class Application {

    ScreenView screenView;
    SpriteView spriteView;

    public Application(int mode) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        frame.setTitle("GBA Spritesheet View -- Mode " + mode);
        frame.setMinimumSize(new Dimension(875, 575));
        frame.setPreferredSize(new Dimension(875, 575));
        frame.setResizable(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        OAM spriteRegister = new OAM();

        RegisterView rv = new RegisterView(this, spriteRegister);
        panel.add(rv);

        JPanel screen = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 50));
        screen.setBackground(Color.WHITE);

        this.spriteView = new SpriteView(spriteRegister);
        this.screenView = new ScreenView(spriteView, spriteRegister);

        JPanel scaleButtonsArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JButton reduceScale = new JButton("-");
        reduceScale.addActionListener(e -> {
            spriteView.adjustScaleDisplayFactor(-0.5);
            screenView.adjustScaleDisplayFactor(-0.5);
        });
        scaleButtonsArea.add(reduceScale);

        JButton increaseScale = new JButton("+");
        increaseScale.addActionListener(e -> {
                    spriteView.adjustScaleDisplayFactor(0.5);
                    screenView.adjustScaleDisplayFactor(0.5);
                });
        scaleButtonsArea.add(increaseScale);

        JPanel spriteViewPanel = new JPanel();
        spriteViewPanel.setLayout(new BoxLayout(spriteViewPanel, BoxLayout.PAGE_AXIS));

        JPanel screenViewPanel = new JPanel();
        screenViewPanel.setLayout(new BoxLayout(screenViewPanel, BoxLayout.PAGE_AXIS));

        spriteViewPanel.add(spriteView, BorderLayout.CENTER);
        spriteViewPanel.add(scaleButtonsArea, BorderLayout.CENTER);

        screenViewPanel.add(screenView, BorderLayout.CENTER);


        screen.add(screenViewPanel);
        screen.add(spriteViewPanel);

        frame.add(panel, BorderLayout.NORTH);

        JScrollPane scrPane = new JScrollPane(screen);
        scrPane.getVerticalScrollBar().setUnitIncrement(16);
        scrPane.getHorizontalScrollBar().setUnitIncrement(16);

        frame.add(scrPane);
        frame.pack();
        frame.setVisible(true);

    } // Constructor

    public void update() {
        spriteView.updateComponent();
        screenView.updateComponent();

    } // update

} // Application
