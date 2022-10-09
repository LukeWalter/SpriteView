package spriteview;

import javax.swing.*;
import java.awt.*;

public class RegisterButton extends JButton {

    private final int index;
    private boolean on;

    public RegisterButton(RegisterView view, int index) {

        super("0");
        this.index = index;

        this.setFont(new Font("Helvetica", Font.PLAIN, 30));
        this.setMargin(new java.awt.Insets(5, 12, 5, 12));

        this.addActionListener(e -> {
            on = !on;
            setText((on) ? "1" : "0");
            view.updateComponent();

        });

    } // RegisterButton

    public int getIndex() {
        return index;

    } // getIndex

    public void setOn(boolean on) {
        this.on = on;

    } // setOn

    public boolean isOn() {
        return on;

    } // isOn

} // RegisterButton
