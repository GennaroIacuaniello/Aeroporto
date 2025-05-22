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
    private HamburgerPanel hamburgerpanel;
    private UserPanel userpanel;
    private JPanel mainpanel;
    private FooterPanel footerpanel;

    private Constraints constraints;

    public Book (JFrame calling_f, Controller controller, User user, Flight flight)
    {
        super ();

        constraints = new Constraints ();

        //imposto mainframe
        this.setMainframe ();
        calling_f.setVisible (false);

        //aggiungo il titolo
        this.addTitlePanel ("AEROPORTO DI NAPOLI");

        //aggiungo hamburger panel
        this.addHamburgerPanel ();

        //aggiungo riferimento a utente
        this.addUserPanel (user);

        //mainpanel
        this.addMainPanel ();

        //aggiungo footer
        this.addFooterPanel ();
    }

    private void setMainframe ()
    {
        mainframe = new JFrame ("Book");
        mainframe.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        mainframe.setLayout(new GridBagLayout ());
        mainframe.setSize (1920, 1080);
        mainframe.setBackground(Color.BLACK);
        mainframe.setVisible (true);
    }

    private void addTitlePanel (String title)
    {
        titlepanel = new TitlePanel (title);

        constraints.setConstraints (0, 0, 3, 1, GridBagConstraints.BOTH, 0, 125, GridBagConstraints.PAGE_START);

        mainframe.add (titlepanel, constraints.getConstraints ());
    }

    private void addHamburgerPanel ()
    {
        hamburgerpanel = new HamburgerPanel ();

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.FIRST_LINE_START);

        mainframe.add (hamburgerpanel, constraints.getConstraints ());
    }

    private void addUserPanel (User user)
    {
        userpanel = new UserPanel (user);

        constraints.setConstraints (2, 1, 1, 1, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.LINE_END);

        mainframe.add (userpanel, constraints.getConstraints ());
    }

    private void addMainPanel ()
    {
        mainpanel = new JPanel ();
        mainpanel.setLayout (new GridBagLayout ());
        mainpanel.setBackground(Color.WHITE);

        constraints.setConstraints (0, 2, 3, 1, GridBagConstraints.BOTH, 0, 600, GridBagConstraints.CENTER);

        mainpanel.setVisible (true);
        mainframe.add (mainpanel, constraints.getConstraints ());
    }

    private void addFooterPanel ()
    {
        footerpanel = new FooterPanel ();

        constraints.setConstraints(0, 3, 3, 1, GridBagConstraints.BOTH, 0, 75, GridBagConstraints.PAGE_END);

        mainframe.add (footerpanel, constraints.getConstraints ());
    }
}
