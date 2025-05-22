package gui;

import controller.Controller;
import model.User;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Book extends Home
{
    private JFrame mainframe;

    private TitlePanel titlepanel;
    private HamburgerPanel hamburgerpanel;
    private UserPanel userpanel;

    private JPanel mainpanel;
    private JPanel flightInfoPanel;
    private JPanel passengersPanel;

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
        //this.addMainPanel (flight);
        this.addFlightInfoPanel (flight);
        this.addPassengersPanel ();

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

    private void addFlightInfoPanel (Flight flight)
    {
        flightInfoPanel = new JPanel ();
        flightInfoPanel.setLayout (new GridBagLayout ());
        flightInfoPanel.setBackground(Color.ORANGE);

        setLabels (flight);

        constraints.setConstraints (0, 2, 3, 1, GridBagConstraints.BOTH, 0, 60, GridBagConstraints.PAGE_START);

        flightInfoPanel.setVisible (true);
        mainframe.add (flightInfoPanel, constraints.getConstraints ());
    }

    private void setLabels (Flight flight)
    {
        setTitleLabels (flight);
        setInfoLabels (flight);
    }

    private void setTitleLabels (Flight flight)
    {
        ArrayList<JLabel> titlelabels = new ArrayList<JLabel> ();

        titlelabels.add (new JLabel ("Company"));
        titlelabels.add (new JLabel ("City"));
        titlelabels.add (new JLabel ("Day"));
        titlelabels.add (new JLabel ("Departure Time"));
        titlelabels.add (new JLabel ("Arrival Time"));
        titlelabels.add (new JLabel ("Duration"));
        titlelabels.add (new JLabel ("Status"));
        titlelabels.add (new JLabel ("Disponibility"));

        for (int i = 0; i < titlelabels.size (); i++)
        {
            constraints.setConstraints(i, 0, 1, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add (titlelabels.get (i), constraints.getConstraints ());
        }
    }

    private void setInfoLabels (Flight flight)
    {
        ArrayList<JLabel> infolabels = new ArrayList<JLabel> ();

        infolabels.add (new JLabel (flight.get_company_name()));
        infolabels.add (new JLabel ("/"));
        infolabels.add (new JLabel (flight.get_date ().toString()));
        infolabels.add (new JLabel (flight.get_departure_time()));
        infolabels.add (new JLabel (flight.get_arrival_time()));
        infolabels.add (new JLabel ("/"));
        infolabels.add (new JLabel (flight.get_status().toString()));
        infolabels.add (new JLabel ("/"));

        for (int i = 0; i < infolabels.size (); i++)
        {
            constraints.setConstraints(i, 1, 1, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add (infolabels.get (i), constraints.getConstraints ());
        }
    }

    private void addPassengersPanel ()
    {
        passengersPanel = new JPanel ();
        passengersPanel.setLayout (new GridBagLayout ());
        passengersPanel.setBackground(Color.BLUE);

        constraints.setConstraints (0, 4, 3, 1, GridBagConstraints.BOTH, 0, 340, GridBagConstraints.PAGE_START);

        passengersPanel.setVisible (true);
        mainframe.add (passengersPanel, constraints.getConstraints ());
    }

    private void addFooterPanel ()
    {
        footerpanel = new FooterPanel ();

        constraints.setConstraints(0, 5, 3, 1, GridBagConstraints.BOTH, 0, 75, GridBagConstraints.PAGE_END);

        mainframe.add (footerpanel, constraints.getConstraints ());
    }
}
