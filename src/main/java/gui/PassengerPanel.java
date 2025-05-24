package gui;

import model.Passenger;

import javax.swing.*;
import java.awt.*;

public class PassengerPanel extends JPanel
{
    public PassengerPanel ()
    {
        super ();

        this.setLayout (new GridBagLayout());
        Constraints constraints = new Constraints ();
        this.setBackground (Color.BLUE);

        JLabel label = new JLabel ("Passenger");
        JTextField passengerName = new JTextField ("Nome", 20);
        JTextField passengerSurname = new JTextField ("Cognome", 20);
        JTextField passengerCF = new JTextField ("CF", 20);
        JButton seatButton = new JButton("Scegli Posto");

        constraints.setConstraints(0, 0, 3, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add (label, constraints.getConstraints());
        label.setVisible (true);

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add (passengerName, constraints.getConstraints());
        passengerName.setVisible (true);

        constraints.setConstraints (1, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add (passengerSurname, constraints.getConstraints());
        passengerSurname.setVisible (true);

        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add (passengerCF, constraints.getConstraints());
        passengerCF.setVisible (true);

        constraints.setConstraints (1, 2, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add (seatButton, constraints.getConstraints());
        seatButton.setVisible (true);

        this.setVisible (true);
    }
}
