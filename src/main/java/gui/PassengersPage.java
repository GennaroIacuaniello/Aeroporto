package gui;

import model.Passenger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class PassengersPage extends JPanel
{
    public int pageNumber = 0;
    Constraints constraints;

    PassengersPage ()
    {
        super ();

        constraints = new Constraints ();
        this.setLayout (new GridBagLayout ());
        this.setBackground (Color.WHITE);
    }

    public void addPassenger ()
    {
        /*PassengerPanel passenger = new PassengerPanel ();

        constraints.setConstraints (0, passengers.size (), 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add (passenger, constraints.getConstraints ());
        passengers.add (passenger);*/
    }
}
