package spriteview;

public class Register {

    private boolean[] bits;
    private boolean bigEndian;

    public Register() {
        this.bits = new boolean[16];

    } // Constructor

    public void setBit(int index, boolean value) {
        bits[index] = value;

    } // setBit

    public boolean getBit(int index) {
        return bits[index];

    } // getBit

    public void setBigEndian(boolean endianness) {
        bigEndian = endianness;

    } // setBigEndian

    public boolean getBigEndian() {
        return bigEndian;

    } // setBigEndian

    public short getValue() {

        short decimal = 0;

        for (int i = 0; i < bits.length; i++) {

            if (bits[i]) {
                decimal += Math.pow(2, i);

            } // if

        } // for

        return decimal;

    } // getValue

    public short getValueBetween(int start, int end) {

        short decimal = 0;

        for (int i = 0; i < end - start; i++) {

            if (bits[start + i]) {
                decimal += Math.pow(2, i);

            } // if

        } // for

        return decimal;

    } // getValueBetween

    @Override
    public String toString() {

        String output = "";

        if (bigEndian) {

            for (int i = bits.length - 1; i > -1; i--) {
                output += (bits[i]) ? "1" : "0";

            } // for

        } else {

            for (int i = 0; i < bits.length; i++) {
                output += (bits[i]) ? "1" : "0";

            } // for

        } // if

        return output;

    } // toString

} // Register
