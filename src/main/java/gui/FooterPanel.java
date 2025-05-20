package gui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel
{
    private JLabel label;

    public FooterPanel ()
    {
        super ();

        this.setLayout (new GridBagLayout());

        label = new JLabel ("Footer");
        this.add (label);

        label.setVisible (true);

        this.setBackground (Color.DARK_GRAY);
        this.setVisible (true);
    }
}
