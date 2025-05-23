package gui;

import controller.Controller;
import model.User;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Book {

    private JFrame mainFrame;

    private TitlePanel titlePanel;
    private HamburgerPanel hamburgerPanel;
    private UserPanel userPanel;

    private JPanel mainPanel;
    private JPanel flightInfoPanel;
    private JPanel passengersPanel;

    private FooterPanel footerPanel;

    private Constraints constraints;

    public Book (JFrame calling_f, Controller controller, User user, Flight flight)
    {
        super ();

        constraints = new Constraints ();

        //imposto mainFrame
        this.setMainframe ();
        calling_f.setVisible (false);

        //setting surrounding panels
        this.addTitlePanel ("AEROPORTO DI NAPOLI");

        //aggiungo hamburger panel
        this.addHamburgerPanel(mainFrame, controller);

        //aggiungo riferimento a utente
        this.addUserPanel (mainFrame, controller, user);

        //mainPanel
        //this.addMainPanel (flight);
        this.addFlightInfoPanel (flight);
        this.addPassengersPanel ();

        //aggiungo footer
        this.addFooterPanel ();
    }

    private void setMainframe ()
    {
        mainFrame = new JFrame ("Book");
        mainFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout ());
        mainFrame.setSize (1920, 1080);
        mainFrame.setBackground(Color.BLACK);
        mainFrame.setVisible (true);
    }

    private void addTitlePanel (String title)
    {
        titlePanel = new TitlePanel (title);

        constraints.setConstraints (0, 0, 3, 1, GridBagConstraints.BOTH, 0, 125, GridBagConstraints.PAGE_START);

        mainFrame.add (titlePanel, constraints.getConstraints ());
    }

    private void addHamburgerPanel(JFrame calling_f, Controller controller)
    {
        hamburgerPanel = new HamburgerPanel(calling_f, controller);

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.FIRST_LINE_START);

        mainFrame.add (hamburgerPanel, constraints.getConstraints ());
    }

    private void addUserPanel (JFrame calling_f, Controller controller, User user)
    {
        userPanel = new UserPanel(calling_f, controller, user);

        constraints.setConstraints (2, 1, 1, 1, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.LINE_END);

        mainFrame.add (userPanel, constraints.getConstraints ());
    }

    private void addFlightInfoPanel (Flight flight)
    {
        flightInfoPanel = new JPanel ();
        flightInfoPanel.setLayout (new GridBagLayout ());
        flightInfoPanel.setBackground(Color.ORANGE);

        setLabels (flight);

        constraints.setConstraints (0, 2, 3, 1, GridBagConstraints.BOTH, 0, 60, GridBagConstraints.PAGE_START);

        flightInfoPanel.setVisible (true);
        mainFrame.add (flightInfoPanel, constraints.getConstraints ());
    }

    private void setLabels (Flight flight)
    {
        setTitleLabels (flight);
        setInfoLabels (flight);
    }

    private void setTitleLabels (Flight flight)
    {
        ArrayList<JLabel> titlelabels = new ArrayList<JLabel> ();

        titlelabels.add (new JLabel ("   "));
        titlelabels.add (new JLabel ("Company"));
        titlelabels.add (new JLabel ("City"));
        titlelabels.add (new JLabel ("Day"));
        titlelabels.add (new JLabel ("Departure Time"));
        titlelabels.add (new JLabel ("Arrival Time"));
        titlelabels.add (new JLabel ("Duration"));
        titlelabels.add (new JLabel ("Status"));
        titlelabels.add (new JLabel ("Disponibility"));
        titlelabels.add (new JLabel ("   "));

        for (int i = 0; i < titlelabels.size (); i++)
        {
            constraints.setConstraints(i, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
            flightInfoPanel.add (titlelabels.get (i), constraints.getConstraints ());
        }
    }

    private void setInfoLabels (Flight flight)
    {
        ArrayList<JLabel> infolabels = new ArrayList<JLabel> ();

        infolabels.add (new JLabel ("   "));
        infolabels.add (new JLabel (flight.get_company_name()));
        infolabels.add (new JLabel ("/"));
        infolabels.add (new JLabel (flight.get_date ().toString()));
        infolabels.add (new JLabel (flight.get_departure_time()));
        infolabels.add (new JLabel (flight.get_arrival_time()));
        infolabels.add (new JLabel ("/"));
        infolabels.add (new JLabel (flight.get_status().toString()));
        infolabels.add (new JLabel ("/"));
        infolabels.add (new JLabel ("   "));

        for (int i = 0; i < infolabels.size (); i++)
        {
            constraints.setConstraints(i, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
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
        mainFrame.add (passengersPanel, constraints.getConstraints ());
    }

    private void addFooterPanel ()
    {
        footerPanel = new FooterPanel ();

        constraints.setConstraints(0, 5, 3, 1, GridBagConstraints.BOTH, 0, 75, GridBagConstraints.PAGE_END);

        mainFrame.add (footerPanel, constraints.getConstraints ());
    }
}
