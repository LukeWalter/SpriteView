package spriteview;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {

    private final Application app;

    private int currentRegister;
    private final OAM sprite;
    private final RegisterButton[] buttons;
    private final JLabel title;

    private final JPanel categories;

    public RegisterView(Application app, OAM sprite) {

        super();
        this.app = app;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titleArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
        titleArea.setBackground(Color.LIGHT_GRAY);
        JPanel attribute = new JPanel();
        attribute.setBackground(Color.BLACK);
        categories = new JPanel();
        categories.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        categories.setBackground(Color.BLACK);
        this.addRegInfo(0);

        this.currentRegister = 0;
        JButton prev = new JButton("<<");
        prev.addActionListener(e -> decrementCurrent());
        JButton next = new JButton(">>");
        next.addActionListener(e -> incrementCurrent());

        this.title = new JLabel();
        this.title.setForeground(Color.BLACK);
        this.title.setFont(new Font("Helvetica", Font.PLAIN, 30));
        this.updateTitle();

        titleArea.add(prev);
        titleArea.add(this.title);
        titleArea.add(next);

        this.sprite = sprite;
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
            this.swapButtons(i, buttons.length - 1 - i);

        } // for

    } // reverseEndian

    private void swapButtons(int a, int b) {
        RegisterButton temp = buttons[a];
        buttons[a] = buttons[b];
        buttons[b] = temp;

    } // swap

    public void updateComponent() {

        this.updateTitle();
        this.updateOAM();

        this.revalidate();
        this.repaint();

    } // updateComponent

    private void updateTitle() {
        title.setText("Attribute " + currentRegister);

    } // updateText

    private void updateOAM() {

        Register reg = sprite.attr(currentRegister);

        for (int i = 0; i < 16; i++) {
            reg.setBit(i, buttons[i].isOn());

        } // for

        app.update();

    } // updateRegister

    private void incrementCurrent() {

        currentRegister = (currentRegister + 5) % 4;

        for (int i = 0; i < 16; i++) {

            RegisterButton b = buttons[i];

            b.setOn(sprite.attr(currentRegister).getBit(i));
            b.setText((b.isOn()) ? "1" : "0");

        } // for

        this.addRegInfo(currentRegister);
        this.updateComponent();

    } // incrementCurrent

    private void decrementCurrent() {

        currentRegister = (currentRegister + 3) % 4;

        for (int i = 0; i < 16; i++) {

            RegisterButton b = buttons[i];

            b.setOn(sprite.attr(currentRegister).getBit(i));
            b.setText((b.isOn()) ? "1" : "0");

        } // for

        this.addRegInfo(currentRegister);
        this.updateComponent();

    } // decrementCurrent

    public void addRegInfo(int register) {

        categories.removeAll();

        switch (register) {

            case 0:
                categories.add(this.generateAttrLabel("Shape", 100, Color.BLUE, Color.BLACK));
                categories.add(this.generateAttrLabel("256", 50, Color.GREEN, Color.BLACK));
                categories.add(this.generateAttrLabel("", 105, Color.BLACK, Color.BLACK));
                categories.add(this.generateAttrLabel("α", 50, Color.GREEN, Color.BLACK));
                categories.add(this.generateAttrLabel("OM", 105, Color.BLUE, Color.BLACK));
                categories.add(this.generateAttrLabel("Row Location", 410, Color.GREEN, Color.BLACK));
                break;
            case 1:
                categories.add(this.generateAttrLabel("Size", 100, Color.GREEN, Color.BLACK));
                categories.add(this.generateAttrLabel("Flip", 100, Color.BLUE, Color.BLACK));
                categories.add(this.generateAttrLabel("", 165, Color.BLACK, Color.BLACK));
                categories.add(this.generateAttrLabel("Column Location", 460, Color.GREEN, Color.BLACK));
                break;
            case 2:
                categories.add(this.generateAttrLabel("Sub-Palette", 200, Color.GREEN, Color.BLACK));
                categories.add(this.generateAttrLabel("Priority", 110, Color.BLUE, Color.BLACK));
                categories.add(this.generateAttrLabel("Tile ID", 510, Color.GREEN, Color.BLACK));
                break;
            case 3:
                categories.add(this.generateAttrLabel("Leave this space alone!", 875, Color.BLACK, Color.WHITE));
                break;
            default:
                break;

        } // switch

    } // addRegInfo

    private JPanel generateAttrLabel(String text, int width, Color bkgdColor, Color textColor) {

        JPanel attrPanel = new JPanel();
        attrPanel.setPreferredSize(new Dimension(width, 36));
        attrPanel.setBackground(bkgdColor);
        JLabel attrLabel = new JLabel(text);
        attrLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        attrLabel.setForeground(textColor);
        attrPanel.add(attrLabel);
        return attrPanel;

    } // generateAttrLabel

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    } // paintComponent

} // RegisterView
