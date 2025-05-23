package gui;

import controller.Controller;
import model.User;
import model.Customer;
import model.Admin;
import org.example.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NavigatorBarPanel extends JPanel
{
    JButton homeButton;
    JButton backButton;
    JLabel pathLabel;
    Constraints constraints;

    public NavigatorBarPanel (ArrayList<JFrame> callingFrames)
    {
        super ();

        constraints = new Constraints ();
        this.setLayout (new FlowLayout (FlowLayout.LEFT));
        this.setBackground (Color.white);

        setHomeButton (callingFrames);
        setBackButton (callingFrames);
        setPath (callingFrames);
    }

    private void setHomeButton (ArrayList<JFrame> callingFrames)
    {
        homeButton = new JButton ("Home");
        //homeButton.setLayout (new FlowLayout ());

        homeButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                int size = callingFrames.size ();

                for (int i = size - 2; i > 1; i--)
                {
                    callingFrames.get (i).dispose ();
                    callingFrames.remove (i);
                }

                callingFrames.get (1).setVisible (true);
                JFrame tmp = callingFrames.getLast ();
                callingFrames.removeLast();
                tmp.dispose ();
            }
        });

        //constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.LINE_START);
        this.add (homeButton);
        homeButton.setVisible (true);
    }

    private void setBackButton (ArrayList<JFrame> callingFrames)
    {
        backButton = new JButton ("Back");
        //backButton.setLayout (new GridBagLayout ());

        backButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                int size = callingFrames.size ();

                callingFrames.get (size - 2).setVisible (true);
                JFrame tmp = callingFrames.getLast ();
                callingFrames.removeLast();
                tmp.dispose ();
            }
        });

        //constraints.setConstraints (1, 0, 1, 1, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.LINE_START);
        this.add (backButton);
        backButton.setVisible (true);
    }

    private void setPath (ArrayList<JFrame> callingFrames)
    {
        String path = "Posizione:\"";
        for (int i = 1; i < callingFrames.size (); i++){
            path += callingFrames.get(i).getTitle()+"/";
        }
        path += "\"";
        pathLabel = new JLabel (path);

        //pathLabel.setLayout (new GridBagLayout ());

        //constraints.setConstraints (2, 0, 17, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.LINE_START);
        this.add (pathLabel);
        pathLabel.setVisible (true);
    }
}
