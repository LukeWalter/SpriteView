package spriteview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static spriteview.Utilities.*;

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

    public Optional<BufferedImage> generateScreenSprite() {

        int background = spriteSheet.getRGB(0, 0);

        if (spriteRegister.shape() == Shape.NOT_A_SHAPE || spriteRegister.objectMode() == OM.HIDE) {
            return Optional.empty();

        } // if

        try {

            BufferedImage output = deepCopy(spriteSheet.getSubimage(
                    (int) p.getX(), (int) p.getY(), (int) bounds.getWidth(), (int) bounds.getHeight()
            ));
            
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

            return Optional.of(makeColorTransparent(output, new Color(spriteSheet.getRGB(0, 0))));

        } catch (RasterFormatException rfe) {
            return Optional.empty();

        } // try

    } // generateScreenSprite

} // SpriteView
