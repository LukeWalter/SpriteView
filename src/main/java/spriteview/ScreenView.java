package spriteview;

import javax.swing.*;
import java.awt.*;

public class ScreenView extends JPanel {

    Image sprite;

    public ScreenView() {

        super();

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(240, 160));

    } // Constructor

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;



    } // paintComponent

} // ScreenView
