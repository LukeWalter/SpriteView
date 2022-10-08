package spriteview;

import javax.swing.*;
import java.awt.*;

public class SpriteView extends JPanel {

    OAM sprite;

    public SpriteView(OAM sprite) {

        super();

        this.setPreferredSize(new Dimension(256, 256));
        this.setBackground(Color.PINK);

        this.sprite = sprite;

    } // Constructor

    public void updateComponent() {
        revalidate();
        repaint();

    } // updateComponent

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2f));

        int tileID = sprite.baseIndex();
        Point p = new Point((tileID % 32) * 8, (tileID / 32) * 8);
        Dimension bounds = sprite.dimensions();

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
