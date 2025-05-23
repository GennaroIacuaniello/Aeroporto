package gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel{

    private JLabel titleLabel;

    public TitlePanel (String title){

        super();

        this.setLayout(new GridBagLayout());

        titleLabel = new JLabel (title);
        this.add(titleLabel);

        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setVisible(true);

        this.setBackground(Color.CYAN);
        this.setVisible(true);

    }
}
