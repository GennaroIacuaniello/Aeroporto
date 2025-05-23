package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigatorBarPanel extends JPanel
{
    JButton homeButton;
    JButton backButton;
    JLabel pathLabel;
    Constraints constraints;

    public NavigatorBarPanel ()
    {
        super ();

        constraints = new Constraints ();
        this.setLayout (new GridBagLayout ());
        this.setBackground (Color.white);

        setHomeButton ();
        setBackButton ();
        setPath ();

    }

    private void setHomeButton ()
    {
        homeButton = new JButton ("Home");
        homeButton.setLayout (new GridBagLayout ());

        homeButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                //home
            }
        });

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.LINE_START);
        this.add (homeButton, constraints.getConstraints ());
        homeButton.setVisible (true);
    }

    private void setBackButton ()
    {
        backButton = new JButton ("Back");
        backButton.setLayout (new GridBagLayout ());

        backButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                //back
            }
        });

        constraints.setConstraints (1, 0, 1, 1, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.LINE_START);
        this.add (backButton, constraints.getConstraints ());
        backButton.setVisible (true);
    }

    private void setPath ()
    {
        pathLabel = new JLabel ("Posizione: ");
        pathLabel.setLayout (new GridBagLayout ());

        constraints.setConstraints (2, 0, 1, 1, GridBagConstraints.BOTH, 1800, 0, GridBagConstraints.LINE_START);
        this.add (pathLabel, constraints.getConstraints ());
        pathLabel.setVisible (true);
    }
}
