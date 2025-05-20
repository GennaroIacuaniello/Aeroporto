package gui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel
{
    private JLabel label;

    public UserPanel (User user)
    {
        super ();

        this.setLayout (new GridBagLayout ());

        label = new JLabel ("Ciao, " + user.get_username());
        this.add (label);

        label.setVisible (true);

        this.setBackground (Color.GRAY);
        this.setVisible (true);
    }
}
