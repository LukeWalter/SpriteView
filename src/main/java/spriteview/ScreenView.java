package spriteview;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static spriteview.Utilities.getScaledImage;

public class ScreenView extends JPanel {

    SpriteView sv;
    BufferedImage spriteToDisplay;
    OAM sprite;

    private static double displayScaleFactor = 1;
    private static final int MAXIMUM_DISPLAY_SCALE = 3;

    public ScreenView(SpriteView sv, OAM sprite) {

        super();

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(240, 160));

        this.sv = sv;
        this.sprite = sprite;
        this.spriteToDisplay = sv.generateScreenSprite();

    } // Constructor

    public void adjustScaleDisplayFactor(double amount) {
        displayScaleFactor = Math.max(1, Math.min(displayScaleFactor + amount,
                MAXIMUM_DISPLAY_SCALE));
        this.setPreferredSize(new Dimension((int) Math.round(240 * displayScaleFactor),
                (int) Math.round(160 * displayScaleFactor)));
        updateComponent();
    }

    protected void updateComponent() {

        this.spriteToDisplay = sv.generateScreenSprite();

        revalidate();
        repaint();

    } // updateComponent

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage scaledSprite = getScaledImage(spriteToDisplay,
                (int) Math.round(spriteToDisplay.getWidth() * displayScaleFactor),
                (int) Math.round(spriteToDisplay.getHeight() * displayScaleFactor));

        int backgroundColor = scaledSprite.getRGB(0, 0);
        for (int r = 0; r < scaledSprite.getHeight(); r++) {
            for (int c = 0; c < scaledSprite.getWidth(); c++) {
                if (scaledSprite.getRGB(c, r) == backgroundColor) {
                    scaledSprite.setRGB(c, r, 0);
                }
            }
        }

        g.drawImage(scaledSprite, sprite.column(), sprite.row(), this);
    } // paintComponent

} // ScreenView
