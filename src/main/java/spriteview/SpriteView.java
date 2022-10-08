package spriteview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteView extends JPanel {

    OAM spriteRegister;
    BufferedImage spriteSheet;

    public SpriteView(OAM sprite) {

        super();

        this.setPreferredSize(new Dimension(256, 256));
        this.setBackground(Color.PINK);

        this.spriteRegister = sprite;

        try {

            File src = new File("res/donkeykong.bmp");
            System.out.println(src.exists());
            this.spriteSheet = ImageIO.read(src);
            System.out.println(spriteSheet);

        } catch (IOException ioe) {


        } // try

    } // Constructor

    public void updateComponent() {
        revalidate();
        repaint();

    } // updateComponent

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(spriteSheet, 0, 0, this);

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2f));

        int tileID = spriteRegister.baseIndex();
        Point p = new Point((tileID % 32) * 8, (tileID / 32) * 8);
        Dimension bounds = spriteRegister.dimensions();

        int x = (int)(p.getX());
        int y = (int)(p.getY());

        int width = (int)(bounds.getWidth());
        int height = (int)(bounds.getHeight());

        g2d.drawLine(x, y, x, y + height);
        g2d.drawLine(x, y + height, x + width, y + height);
        g2d.drawLine(x + width, y + height, x + width, y);
        g2d.drawLine(x + width, y, x, y);

    } // paintComponent

} // SpriteView
