package spriteview;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static spriteview.Utilities.getScaledImage;
import static spriteview.Utilities.scaleIntegerByFactor;

public class ScreenView extends JPanel {

    SpriteView spriteView;
    BufferedImage spriteToDisplay;
    OAM sprite;

    private static double displayScaleFactor = 1;
    private static final int MAXIMUM_DISPLAY_SCALE = 3;


    /**
     * We could use this to show some sort of indication for when a sprite isn't being shown
     * to help clarify what is happening. Maybe like a red X?
     */
    private BufferedImage imageShownWhenSpriteIsNotVisible;

    public ScreenView(SpriteView sv, OAM sprite) {

        super();

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(240, 160));

        this.spriteView = sv;
        this.sprite = sprite;
        this.imageShownWhenSpriteIsNotVisible
                = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        this.spriteToDisplay = spriteView.generateScreenSprite()
                .orElse(imageShownWhenSpriteIsNotVisible);

    } // Constructor

    public void adjustScaleDisplayFactor(double amount) {
        displayScaleFactor = Math.max(1, Math.min(displayScaleFactor + amount,
                MAXIMUM_DISPLAY_SCALE));
        this.setPreferredSize(new Dimension((int) Math.round(240 * displayScaleFactor),
                (int) Math.round(160 * displayScaleFactor)));
        updateComponent();
    }

    protected void updateComponent() {

        this.spriteToDisplay = spriteView.generateScreenSprite()
                .orElse(imageShownWhenSpriteIsNotVisible);

        revalidate();
        repaint();

    } // updateComponent

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (spriteToDisplay != null) {
            BufferedImage scaledSprite = getScaledImage(spriteToDisplay,
                    scaleIntegerByFactor(spriteToDisplay.getWidth(), displayScaleFactor),
                    scaleIntegerByFactor(spriteToDisplay.getHeight(), displayScaleFactor));

            int backgroundColor = scaledSprite.getRGB(0, 0);
            for (int r = 0; r < scaledSprite.getHeight(); r++) {
                for (int c = 0; c < scaledSprite.getWidth(); c++) {
                    if (scaledSprite.getRGB(c, r) == backgroundColor) {
                        scaledSprite.setRGB(c, r, 0);
                    }
                }
            }

            g.drawImage(scaledSprite, scaleIntegerByFactor(sprite.column(), displayScaleFactor),
                    scaleIntegerByFactor(sprite.row(), displayScaleFactor), this);
        }
    } // paintComponent

} // ScreenView
