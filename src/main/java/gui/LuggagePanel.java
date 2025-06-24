package gui;

import javax.swing.*;

public class LuggagePanel extends JPanel {

    private JLabel label;

    private Constraints constraints;

    public LuggagePanel() {

        constraints = new Constraints();

        label = new JLabel("Bagaglio");
        add(label);
        label.setVisible(true);
    }
}
