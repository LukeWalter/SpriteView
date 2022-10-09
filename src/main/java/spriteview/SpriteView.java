package spriteview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import static spriteview.Utilities.deepCopy;
import static spriteview.Utilities.getScaledImage;

public class SpriteView extends JPanel {

    OAM spriteRegister;
    BufferedImage spriteSheet;

    Point p;
    Dimension bounds;

    private static double displayScaleFactor = 1;
    private static final int MAXIMUM_DISPLAY_SCALE = 3;

    public SpriteView(OAM sprite) {
        super();

        this.setPreferredSize(new Dimension(256, 256));
        this.setBackground(Color.PINK);

        this.spriteRegister = sprite;

        try {
            File src = new File("res/donkeykong.png");
            this.spriteSheet = ImageIO.read(src);
        } catch (IOException ignored) {
        } // try

        p = new Point(0, 0);
        bounds = new Dimension(8, 8);
    } // Constructor

    public void adjustScaleDisplayFactor(double amount) {
        displayScaleFactor = Math.max(1, Math.min(displayScaleFactor + amount,
                MAXIMUM_DISPLAY_SCALE));
        this.setPreferredSize(new Dimension((int) Math.round(256 * displayScaleFactor),
                (int) Math.round(256 * displayScaleFactor)));
        updateComponent();
    }

    public void updateComponent() {

        int tileID = spriteRegister.baseIndex();
        p = new Point((tileID % 32) * 8, (tileID / 32) * 8);
        bounds = spriteRegister.dimensions();

        revalidate();
        repaint();

    } // updateComponent


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(getScaledImage(spriteSheet, (int) Math.round(256 * displayScaleFactor),
                (int) Math.round(256 * displayScaleFactor)), 0, 0, this);

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2f));

        int x = (int)(p.getX() * displayScaleFactor);
        int y = (int)(p.getY() * displayScaleFactor);

        int width = (int)(bounds.getWidth() * displayScaleFactor);
        int height = (int)(bounds.getHeight() * displayScaleFactor);

        g2d.drawLine(x, y, x, y + height);
        g2d.drawLine(x, y + height, x + width, y + height);
        g2d.drawLine(x + width, y + height, x + width, y);
        g2d.drawLine(x + width, y, x, y);

    } // paintComponent

    public BufferedImage generateScreenSprite() {

        int background = spriteSheet.getRGB(0, 0);

        if (spriteRegister.shape() == Shape.NOT_A_SHAPE || spriteRegister.objectMode() == OM.HIDE) {
            return null;

        } // if

        try {

            BufferedImage output = deepCopy(spriteSheet.getSubimage(
                    (int) p.getX(), (int) p.getY(), (int) bounds.getWidth(), (int) bounds.getHeight()
            ));

            for (int r = 0; r < output.getHeight(); r++) {
                for (int c = 0; c < output.getWidth(); c++) {

                    if (output.getRGB(c, r) == background) {
                        output.setRGB(c, r, 0);
                    } // if

                } // for

            } // for

            if (spriteRegister.horizontalFlip()) {
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-output.getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                output = op.filter(output, null);

            } // if

            if (spriteRegister.verticalFlip()) {
                AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
                tx.translate(0, -output.getHeight(null));
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                output = op.filter(output, null);

            } // if

            return output;

        } catch (RasterFormatException rfe) {
            return null;

        } // try

    } // generateScreenSprite

} // SpriteView
