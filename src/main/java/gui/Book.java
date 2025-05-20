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

    public Book (JFrame calling_f, Controller controller, User user, Flight flight)
    {
        super ();

        //imposto mainframe
        this.setMainframe ();

        //aggiungo il titolo
        this.addTitle ("AEROPORTO DI NAPOLI");

        this.addUserPanel (user);
    }

    void setMainframe ()
    {
        mainframe = new JFrame ("Book");
        mainframe.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        mainframe.setLayout(new GridBagLayout ());
        mainframe.setSize (1920, 1080);
        mainframe.setBackground(Color.LIGHT_GRAY);
        mainframe.setVisible (true);
    }

    void addTitle (String title)
    {
        titlepanel = new TitlePanel (title);
        mainframe.add  (titlepanel);
    }

    void addUserPanel (User user)
    {
        userpanel = new UserPanel (user);
        mainframe.add (userpanel);
    }
}
