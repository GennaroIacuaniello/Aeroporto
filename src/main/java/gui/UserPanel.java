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

        this.setLayout (new BorderLayout ());

        label = new JLabel ("Ciao, " + user.get_username());
        //label.setIcon (new ImageIcon (this.getClass ().getResource ("images/img.png")));
        this.add (label, BorderLayout.WEST);

        label.setVisible (true);

        this.setSize(300, 200);
        this.setVisible (true);
    }
}
