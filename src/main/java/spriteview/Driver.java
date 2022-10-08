package spriteview;

import javax.swing.*;

public class Driver {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { Application app = new Application(4); });

    } // main

} // Driver
