package spriteview;

import java.awt.Dimension;

public class OAM {

    private final Register[] attribute;
    private final Dimension[][] dimension;

    public OAM() {

        this.attribute = new Register[4];

        for (int i = 0; i < 4; i++) {
            this.attribute[i] = new Register();
            this.attribute[i].setBigEndian(true);

        } // for

        this.dimension = new Dimension[4][4];

        dimension[0][0] = new Dimension(8, 8);
        dimension[0][1] = new Dimension(16, 16);
        dimension[0][2] = new Dimension(32, 32);
        dimension[0][3] = new Dimension(64, 64);

        dimension[1][0] = new Dimension(16, 8);
        dimension[1][1] = new Dimension(32, 8);
        dimension[1][2] = new Dimension(32, 16);
        dimension[1][3] = new Dimension(64, 32);

        dimension[2][0] = new Dimension(8, 16);
        dimension[2][1] = new Dimension(8, 32);
        dimension[2][2] = new Dimension(16, 32);
        dimension[2][3] = new Dimension(32, 64);

        dimension[3][0] = new Dimension(0, 0);
        dimension[3][1] = new Dimension(0, 0);
        dimension[3][2] = new Dimension(0, 0);
        dimension[3][3] = new Dimension(0, 0);

    } // OAM

    public Register attr(int index) {
        return attribute[index];

    } // attr

    public void setBigEndian(boolean endianness) {

        for (int i = 0; i < 4; i++) {
            attribute[i].setBigEndian(endianness);

        } // for

    } // setBigEndian

    public int row() {
        return attribute[0].getValueBetween(0, 8);

    } // getRowLocation

    public OM objectMode() {
        return OM.values()[attribute[0].getValueBetween(8, 10)];

    } // getObjectMode

    public boolean alphaBlending() {
        return attribute[0].getBit(10);

    } // alphaBlending

    public int colorDepth() {
        return (attribute[0].getBit(13)) ? 256 : 16;

    } // colorDepth

    public Shape shape() {
        return Shape.values()[attribute[0].getValueBetween(14, 16)];

    } // shape

    public int column() {
        return attribute[1].getValueBetween(0, 9);

    } // column

    public boolean horizontalFlip() {
        return attribute[1].getBit(12);

    } // horizontalFlip

    public boolean verticalFlip() {
        return attribute[1].getBit(13);

    } // verticalFlip

    public int size() {
        return attribute[1].getValueBetween(14, 16);

    } // size

    public int baseIndex() {
        return attribute[2].getValueBetween(0, 10);

    } // baseIndex

    public int priority() {
        return attribute[2].getValueBetween(10, 12);

    } // priority

    public int subPalette() {
        return attribute[2].getValueBetween(12, 16);

    } // subPalette

    public Dimension dimensions() {
        return dimension[attribute[0].getValueBetween(14, 16)][attribute[1].getValueBetween(14, 16)];

    } // dimensions

    @Override
    public String toString() {

        String output = "> OAM" + "\n";
        output += "=======================\n";
        output += "ATTR0: " + attribute[0] + "\n";
        output += "ATTR1: " + attribute[1] + "\n";
        output += "ATTR2: " + attribute[2] + "\n";
        output += "ATTR3: " + attribute[3] + "\n";
        output += "=======================\n";
        output += "Tile ID: " + this.baseIndex() + "\n";
        output += "Position: (" + this.column() + ", " + this.row() + ")\n";
        Dimension d = this.dimensions();
        output += "Dimensions: " + (int)d.getWidth() + "x" + (int)d.getHeight() + " | " + this.shape() + "\n";

        return output;

    } // toString

} // OAM