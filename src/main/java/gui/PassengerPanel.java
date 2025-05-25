package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PassengerPanel extends JPanel
{
    String displayedNameText = "Nome";
    String displayedSurnameText = "Cognome";
    String displayedCFText = "Codice Fiscale";
    Color displayedTextColor = new Color(128, 128, 128);
    Color userTextColor = new Color(32, 32, 32);

    public PassengerPanel ()
    {
        super ();

        this.setLayout (new GridBagLayout());
        Constraints constraints = new Constraints ();
        this.setBackground (Color.BLUE);

        JLabel label = new JLabel ("Passenger");
        JTextField passengerNameField = new JTextField(displayedNameText, 20);
        passengerNameField.setForeground(displayedTextColor);
        JTextField passengerSurnameField = new JTextField (displayedSurnameText, 20);
        passengerSurnameField.setForeground(displayedTextColor);
        JTextField passengerCFField = new JTextField (displayedCFText, 20);
        passengerCFField.setForeground(displayedTextColor);
        JButton seatButton = new JButton("Scegli Posto");

        constraints.setConstraints(0, 0, 3, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (label, constraints.getConstraints());
        label.setVisible (true);

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (passengerNameField, constraints.getConstraints());
        passengerNameField.setVisible (true);

        constraints.setConstraints (1, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (passengerSurnameField, constraints.getConstraints());
        passengerSurnameField.setVisible (true);

        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (passengerCFField, constraints.getConstraints());
        passengerCFField.setVisible (true);

        constraints.setConstraints (1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (seatButton, constraints.getConstraints());
        seatButton.setVisible (true);

        /* Removing the displayed text and changing the font color if user focuses
         * Putting it back if user unfocuses without writing anything
         * Using focus to avoid problem with:
         * - mouse clicking followed by quick typing
         * - changing pages and being (focusing) already on a field without clicking
         */
        passengerNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(passengerNameField.getText().equals(displayedNameText)){
                    passengerNameField.setText("");
                    passengerNameField.setForeground(userTextColor);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(passengerNameField.getText().isEmpty()){
                    passengerNameField.setText(displayedNameText);
                    passengerNameField.setForeground(displayedTextColor);
                }
            }
        });

        passengerSurnameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(passengerSurnameField.getText().equals(displayedSurnameText)){
                    passengerSurnameField.setText("");
                    passengerSurnameField.setForeground(userTextColor);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(passengerSurnameField.getText().isEmpty()){
                    passengerSurnameField.setText(displayedSurnameText);
                    passengerSurnameField.setForeground(displayedTextColor);
                }
            }
        });

        passengerCFField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(passengerCFField.getText().equals(displayedCFText)){
                    passengerCFField.setText("");
                    passengerCFField.setForeground(userTextColor);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(passengerCFField.getText().isEmpty()){
                    passengerCFField.setText(displayedCFText);
                    passengerCFField.setForeground(displayedTextColor);
                }
            }
        });

        this.setVisible (true);
    }
}
