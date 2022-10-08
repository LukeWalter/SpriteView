package spriteview;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {

    private int current;
    private OAM sprite;
    private RegisterButton[] buttons;
    private JLabel title;

    public RegisterView() {

        super();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titleArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
        titleArea.setBackground(Color.LIGHT_GRAY);
        JPanel attribute = new JPanel();
        attribute.setBackground(Color.DARK_GRAY);
        JPanel categories = new JPanel();
        categories.setLayout(new BoxLayout(categories, BoxLayout.X_AXIS));
        categories.setBackground(Color.GREEN);

        this.current = 0;
        JButton prev = new JButton("<<");
        prev.addActionListener(e -> decrementCurrent());
        JButton next = new JButton(">>");
        next.addActionListener(e -> incrementCurrent());

        this.title = new JLabel();
        this.title.setForeground(Color.BLACK);
        this.title.setFont(new Font("Helvetica", Font.PLAIN, 30));
        updateTitle();

        titleArea.add(prev);
        titleArea.add(this.title);
        titleArea.add(next);

        this.sprite = new OAM();
        this.buttons = new RegisterButton[16];

        for (int i = this.buttons.length - 1; i >= 0; i--) {
            this.buttons[i] = new RegisterButton(this, i);
            attribute.add(this.buttons[i]);

        } // for

        this.add(titleArea);
        this.add(attribute);
        this.add(categories);

    } // Constructor

    public void reverseEndian() {

        for (int i = 0; i < buttons.length / 2; i++) {
            swapButtons(i, buttons.length - 1 - i);

        } // for

    } // reverseEndian

    private void swapButtons(int a, int b) {
        RegisterButton temp = buttons[a];
        buttons[a] = buttons[b];
        buttons[b] = temp;

    } // swap

    public void updateComponent() {

        updateTitle();
        updateOAM();
        System.out.println(sprite);
        revalidate();
        repaint();

    } // updateComponent

    private void updateTitle() {
        title.setText("Attribute " + current);

    } // updateText

    private void updateOAM() {

        Register reg = sprite.attr(current);

        for (int i = 0; i < 16; i++) {
            reg.setBit(i, buttons[i].isOn());

        } // for

//        System.out.println(sprite);

    } // updateRegister

    private void incrementCurrent() {

        current = (current + 5) % 4;
        System.out.println(current);
        for (int i = 0; i < 16; i++) {

            RegisterButton b = buttons[i];

            b.setOn(sprite.attr(current).getBit(i));
            b.setText((b.isOn()) ? "1" : "0");

        } // for

        updateComponent();

    } // incrementCurrent

    private void decrementCurrent() {

        current = (current + 3) % 4;

        for (int i = 0; i < 16; i++) {

            RegisterButton b = buttons[i];

            b.setOn(sprite.attr(current).getBit(i));
            b.setText((b.isOn()) ? "1" : "0");

        } // for

        updateComponent();

    } // incrementCurrent

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    } // paintComponent

} // RegisterView
