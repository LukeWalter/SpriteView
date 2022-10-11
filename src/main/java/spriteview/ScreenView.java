package spriteview;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

            ArrayList<Utilities.Triple<BufferedImage, Integer, Integer>> scaledSprites = this.split();

            for (Utilities.Triple<BufferedImage, Integer, Integer> s : scaledSprites) {

                int backgroundColor = s.a.getRGB(0, 0);
                for (int r = 0; r < s.a.getHeight(); r++) {
                    for (int c = 0; c < s.a.getWidth(); c++) {
                        if (s.a.getRGB(c, r) == backgroundColor) {
                            s.a.setRGB(c, r, 0);
                        }
                    }
                }

                g.drawImage(
                        s.a,
                        scaleIntegerByFactor(s.b % 512, displayScaleFactor),
                        scaleIntegerByFactor(s.c % 256, displayScaleFactor),
                        this
                );

            } // for

        }
    } // paintComponent

    private ArrayList<Utilities.Triple<BufferedImage, Integer, Integer>> split() {

        ArrayList<Utilities.Triple<BufferedImage, Integer, Integer>> scaledSprites = new ArrayList<>();

        int spriteColumn = sprite.column() % 512;
        int spriteRow = sprite.row() % 256;

        Dimension sd = sprite.dimensions();
        int spriteWidth = (int)sd.getWidth();
        int spriteHeight = (int)sd.getHeight();

        boolean splitHorizontally = (spriteColumn + spriteWidth > 512);
        boolean splitVertically = (spriteRow + spriteHeight > 256);

        if (splitHorizontally && splitVertically) {

            int width1 = 512 - spriteColumn;
            int width2 = spriteWidth - width1;

            int height1 = 256 - spriteRow;
            int height2 = spriteHeight - height1;

            BufferedImage scaledSprite1 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            0, 0,
                            width1, height1
                    ),
                    scaleIntegerByFactor(width1, displayScaleFactor),
                    scaleIntegerByFactor(height1, displayScaleFactor)
            );

            BufferedImage scaledSprite2 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            width1, 0,
                            width2, height1
                    ),
                    scaleIntegerByFactor(width2, displayScaleFactor),
                    scaleIntegerByFactor(height1, displayScaleFactor)
            );

            BufferedImage scaledSprite3 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            0, height1,
                            width1, height2
                    ),
                    scaleIntegerByFactor(width1, displayScaleFactor),
                    scaleIntegerByFactor(height2, displayScaleFactor)
            );

            BufferedImage scaledSprite4 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            width1, height1,
                            width2, height2
                    ),
                    scaleIntegerByFactor(width2, displayScaleFactor),
                    scaleIntegerByFactor(height2, displayScaleFactor)
            );

            scaledSprites.add(new Utilities.Triple(scaledSprite1, sprite.column(), sprite.row()));
            scaledSprites.add(new Utilities.Triple(scaledSprite2, sprite.column() + width1, sprite.row()));
            scaledSprites.add(new Utilities.Triple(scaledSprite3, sprite.column(), sprite.row() + height1));
            scaledSprites.add(new Utilities.Triple(scaledSprite4, sprite.column() + width1, sprite.row() + height1));

        } else if (splitHorizontally) {

            int width1 = 512 - spriteColumn;
            int width2 = spriteWidth - width1;

            BufferedImage scaledSprite1 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            0, 0,
                            width1, spriteToDisplay.getHeight()
                    ),
                    scaleIntegerByFactor(width1, displayScaleFactor),
                    scaleIntegerByFactor(spriteToDisplay.getHeight(), displayScaleFactor)
            );

            BufferedImage scaledSprite2 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            width1, 0,
                            width2, spriteToDisplay.getHeight()
                    ),
                    scaleIntegerByFactor(width2, displayScaleFactor),
                    scaleIntegerByFactor(spriteToDisplay.getHeight(), displayScaleFactor)
            );

            scaledSprites.add(new Utilities.Triple(scaledSprite1, sprite.column(), sprite.row()));
            scaledSprites.add(new Utilities.Triple(scaledSprite2, sprite.column() + width1, sprite.row()));

        } else if (splitVertically) {

            int height1 = 256 - spriteRow;
            int height2 = spriteHeight - height1;

            BufferedImage scaledSprite1 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            0, 0,
                            spriteToDisplay.getWidth(), height1
                    ),
                    scaleIntegerByFactor(spriteToDisplay.getWidth(), displayScaleFactor),
                    scaleIntegerByFactor(height1, displayScaleFactor)
            );

            BufferedImage scaledSprite2 = getScaledImage(
                    spriteToDisplay.getSubimage(
                            0, height1,
                            spriteToDisplay.getWidth(), height2
                    ),
                    scaleIntegerByFactor(spriteToDisplay.getWidth(), displayScaleFactor),
                    scaleIntegerByFactor(height2, displayScaleFactor)
            );

            scaledSprites.add(new Utilities.Triple(scaledSprite1, sprite.column(), sprite.row()));
            scaledSprites.add(new Utilities.Triple(scaledSprite2, sprite.column(), sprite.row() + height1));

        } else {

            BufferedImage scaledSprite = getScaledImage(
                    spriteToDisplay,
                    scaleIntegerByFactor(spriteToDisplay.getWidth(), displayScaleFactor),
                    scaleIntegerByFactor(spriteToDisplay.getHeight(), displayScaleFactor)
            );

            scaledSprites.add(new Utilities.Triple(scaledSprite, sprite.column(), sprite.row()));

        } // if
//
        return scaledSprites;

    } // split

} // ScreenView
