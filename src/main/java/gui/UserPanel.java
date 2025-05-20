package gui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel
{
    private JButton userButton;
    private JButton logoutButton;
    private Constraints constraints;
    private JPanel invisiblePanel;

    public UserPanel (User user)
    {
        super ();

        constraints = new Constraints ();

        this.setLayout (new GridBagLayout ());

        this.setLogoutButton ();
        this.setUserButton (user);

        this.setVisible (true);
    }

    private void setLogoutButton ()
    {
        logoutButton = new JButton ("Logout");
        logoutButton.setLayout (new GridBagLayout ());
        logoutButton.setEnabled (false);
        logoutButton.setVisible (false);

        logoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //logout
            }
        });

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.FIRST_LINE_END);

        this.add (logoutButton, constraints.getConstraints());
    }

    private void setUserButton (User user)
    {
        userButton = new JButton ("Ciao,\n" + user.get_username ());
        userButton.setLayout (new GridBagLayout ());

        userButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                logoutButton.setEnabled (true);
                logoutButton.setVisible (true);
            }
        });

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.FIRST_LINE_END);

        this.add(userButton, constraints.getConstraints ());

        invisiblePanel = new JPanel ();
        invisiblePanel.setBackground (Color.black);
        invisiblePanel.setLayout (new GridBagLayout ());
        invisiblePanel.setSize(10, 10);
        invisiblePanel.setVisible(true);

        constraints.setConstraints (0, 1, 2, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.FIRST_LINE_END);

        this.add(invisiblePanel, constraints.getConstraints ());
    }
}
