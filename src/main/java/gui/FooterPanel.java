package gui;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {
    private JLabel label;
    private Constraints constraints;

    public FooterPanel() {

        super();

        this.setLayout(new GridBagLayout());
        constraints = new Constraints();

        label = new JLabel("Footer");
        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(label, constraints.getConstraints());

        label.setVisible(true);

        this.setBackground(Color.GRAY);
        this.setVisible(true);
    }

}
