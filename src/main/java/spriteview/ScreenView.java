package spriteview;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenView extends JPanel {

    SpriteView sv;
    BufferedImage image;
    OAM sprite;

    public ScreenView(SpriteView sv, OAM sprite) {

        super();

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(240, 160));

        this.sv = sv;
        this.sprite = sprite;
        this.image = sv.generateScreenSprite();

    } // Constructor

    protected void updateComponent() {

        this.image = sv.generateScreenSprite();

        revalidate();
        repaint();

    } // updateComponent

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, sprite.column(), sprite.row(), this);

    } // paintComponent

} // ScreenView
