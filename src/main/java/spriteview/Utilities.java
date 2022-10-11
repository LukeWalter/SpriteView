package spriteview;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

public class Utilities {

    public static class Triple<A, B, C> {

        public A a;
        public B b;
        public C c;

        public Triple(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;

        } // Constructor

    } // Triple

    /**
     * Returns a BufferedImage of the original image scaled to the passed in width and height.
     * @param image Source image to be scaled
     * @param width New width
     * @param height New height
     * @return A BufferedImage that is the scaled version of the original image
     */
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(
                image,
                new BufferedImage(width, height, image.getType()));
    }

    /**
     * @param bi The buffered image to copy
     * @return A deep copy of the passed in image
     */
    // Credit to user1050755!
    // https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);

    } // deepCopy

    /**
     * Use this because Java sucks (or I'm stupid, either is possible)
     * @param value The value to scale
     * @param scaleFactor The scale factor to use
     * @return The scaled integer, rounded if the scaling resulted in a decimal value
     */
    public static int scaleIntegerByFactor(int value, double scaleFactor) {
        return (int) Math.round(value * scaleFactor);
    }

    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {System.out.println("Hi");
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public final int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return imageToBufferedImage(Toolkit.getDefaultToolkit().createImage(ip),
                im.getWidth(), im.getHeight());
    }

    public static BufferedImage imageToBufferedImage(Image image, int width, int height)
    {
        BufferedImage dest = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return dest;
    }
}
