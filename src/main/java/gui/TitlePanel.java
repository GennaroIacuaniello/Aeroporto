package gui;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel
{
    private JLabel titleLabel;

    public TitlePanel (String title)
    {
        super ();

        this.setLayout(new FlowLayout ());

        titleLabel = new JLabel (title);
        this.add (titleLabel);

        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setVisible (true);

        this.setBackground(Color.CYAN);
        this.setSize(1920, 500);
        this.setVisible (true);

    }
}
