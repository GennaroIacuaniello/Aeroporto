package gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    Constraints constraints;

    private JLabel titleLabel;

    public TitlePanel(String title) {

        super();

        constraints = new Constraints();

        this.setLayout(new GridBagLayout());

        setLabel(title);

        this.setBackground(Color.CYAN);
        this.setVisible(true);
    }

    private void setLabel(String title) {
        titleLabel = new JLabel(title);

        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, 0, 50, GridBagConstraints.CENTER);

        this.add(titleLabel, constraints.getConstraints());
        titleLabel.setVisible(true);
    }

}
