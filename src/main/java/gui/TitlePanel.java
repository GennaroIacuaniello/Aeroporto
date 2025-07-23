package gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    Constraints constraints;


    public TitlePanel(String title) {

        super();

        constraints = new Constraints();

        this.setLayout(new GridBagLayout());

        setTitleLabel(title);

        this.setVisible(true);
    }

    private void setTitleLabel(String title) {
        JLabel titleLabel;

        titleLabel = new JLabel("<html><h1>" + title + "</h1></html>");

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, 0, 50, GridBagConstraints.CENTER);

        this.add(titleLabel, constraints.getGridBagConstraints());
        titleLabel.setVisible(true);
    }
}
