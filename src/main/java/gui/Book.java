package gui;

import controller.Controller;
import model.User;
import model.Flight;

import javax.swing.*;
import java.awt.*;

public class Book extends Home
{
    private JFrame mainframe;
    private TitlePanel titlepanel;
    private UserPanel userpanel;
    private GridBagConstraints constraints;
    private JPanel mainpanel;

    public Book (JFrame calling_f, Controller controller, User user, Flight flight)
    {
        super ();

        constraints = new GridBagConstraints ();
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;

        //imposto mainframe
        this.setMainframe ();
        calling_f.setVisible (false);

        //aggiungo il titolo
        this.addTitle ("AEROPORTO DI NAPOLI");

        //aggiungo riferimento a utente
        this.addUserPanel (user);

        //mainpanel
        this.addMainPanel ();
    }

    private void setMainframe ()
    {
        mainframe = new JFrame ("Book");
        mainframe.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        mainframe.setLayout(new GridBagLayout ());
        mainframe.setSize (1920, 1080);
        mainframe.setBackground(Color.WHITE);
        mainframe.setVisible (true);
    }

    private void addTitle (String title)
    {
        titlepanel = new TitlePanel (title);

        setConstraints (constraints, 0, 0, 3, 1, GridBagConstraints.HORIZONTAL, 0, 250, GridBagConstraints.PAGE_START);

        mainframe.add (titlepanel, constraints);
    }

    private void addUserPanel (User user)
    {
        userpanel = new UserPanel (user);

        setConstraints (constraints, 2, GridBagConstraints.RELATIVE, 1, 1, GridBagConstraints.NONE, 0, 30, GridBagConstraints.FIRST_LINE_END);

        mainframe.add (userpanel, constraints);
    }

    private void addMainPanel ()
    {
        mainpanel = new JPanel ();
        mainpanel.setLayout (new GridBagLayout ());
        mainpanel.setBackground(Color.LIGHT_GRAY);

        setConstraints (constraints, 1, 2, 3, 1, GridBagConstraints.HORIZONTAL, 0, 400, GridBagConstraints.CENTER);

        mainpanel.setVisible (true);
        mainframe.add (mainpanel, constraints);
    }

    private void setConstraints (GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int gridheight, int fill, int ipadx, int ipady, int anchor, float weightx, float weighty, Insets insets)
    {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        constraints.anchor = anchor;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.insets = insets;
    }

    private void setConstraints (GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int gridheight, int fill, int ipadx, int ipady, int anchor)
    {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        constraints.anchor = anchor;
    }
}
