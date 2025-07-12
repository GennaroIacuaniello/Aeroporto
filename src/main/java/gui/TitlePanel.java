package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    Constraints constraints;

    private JLabel titleLabel;

    public TitlePanel(String title, Controller controller) {

        super();

        constraints = new Constraints();

        this.setLayout(new GridBagLayout());

        setTitleLabel(title);

        this.setVisible(true);
    }

    private void setTitleLabel(String title) {

        titleLabel = new JLabel("<html><h1>" + title + "</h1></html>");

        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, 0, 50, GridBagConstraints.CENTER);

        this.add(titleLabel, constraints.getConstraints());
        titleLabel.setVisible(true);
    }
}
