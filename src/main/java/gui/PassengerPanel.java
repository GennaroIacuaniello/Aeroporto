package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class PassengerPanel extends JPanel
{
    private String displayedNameText = "~Nome~";
    private String displayedSurnameText = "~Cognome~";
    private String displayedCFText = "~Codice Fiscale~";
    private Color displayedTextColor = new Color(128, 128, 128);
    private Color userTextColor = new Color(32, 32, 32);
    private SeatChooser seatChooser;
    private int seat = -1;
    private LuggagesView luggagesView;
    private RoundedButton luggagesViewButton;
    private RoundedButton seatButton;

    private JTextField passengerNameField;
    private JTextField passengerSurnameField;
    private JTextField passengerCFField;
    private JLabel seatLabel;

    public PassengerPanel (Controller controller, ArrayList<PassengerPanel> passengerPanels)
    {
        super ();

        this.setLayout (new GridBagLayout());
        Constraints constraints = new Constraints ();
        if(controller.developerMode) this.setBackground (Color.BLUE);

        JLabel label = new JLabel ("Passenger");
        passengerNameField = new JTextField(displayedNameText, 20);
        passengerNameField.setForeground(displayedTextColor);
        passengerSurnameField = new JTextField (displayedSurnameText, 20);
        passengerSurnameField.setForeground(displayedTextColor);
        passengerCFField = new JTextField (displayedCFText, 20);
        passengerCFField.setForeground(displayedTextColor);
        seatButton = new RoundedButton("Scegli Posto");
        seatLabel = new JLabel (print_seat());
        luggagesViewButton = new RoundedButton("Luggages");
        luggagesView = new LuggagesView (controller);
        luggagesViewButton.addActionListener (new ActionListener () {
           @Override
           public void actionPerformed (ActionEvent e) {
               luggagesView.setLocation(luggagesViewButton);
               luggagesView.setVisible(true);
           }
        });

        constraints.setConstraints(0, 0, 3, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (label, constraints.getConstraints());
        label.setVisible (true);

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (passengerNameField, constraints.getConstraints());
        passengerNameField.setVisible (true);

        constraints.setConstraints (1, 1, 3, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (passengerSurnameField, constraints.getConstraints());
        passengerSurnameField.setVisible (true);

        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        this.add (passengerCFField, constraints.getConstraints());
        passengerCFField.setVisible (true);

        constraints.setConstraints (1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END);
        this.add (seatButton, constraints.getConstraints());
        seatButton.setVisible (true);

        constraints.setConstraints (2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_START);
        this.add (seatLabel, constraints.getConstraints());
        seatLabel.setVisible (true);

        constraints.setConstraints (3, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END);
        this.add (luggagesViewButton, constraints.getConstraints());
        luggagesViewButton.setVisible (true);

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

        seatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PassengerPanel passengerPanel : passengerPanels) {
                    passengerPanel.getSeatButton().setEnabled(false);
                }
                seatChooser = new SeatChooser (controller, thisPanel(), passengerPanels);
            }
        });

        this.setVisible (true);
    }

    protected PassengerPanel () {}

    public String print_seat(){

        if (seat == -1) return "POSTO";

        String literal;

        switch(seat%6){
            case 0: literal = "A"; break;
            case 1: literal = "B"; break;
            case 2: literal = "C"; break;
            case 3: literal = "D"; break;
            case 4: literal = "E"; break;
            case 5: literal = "F"; break;
            default: literal = "";
        }

        return Integer.toString((seat/6)+1) + literal;
    }

    public int getSeat(){
        return seat;
    }

    public void setSeat(int par_seat){
        seat = par_seat;
        seatLabel.setText(print_seat());
    }

    private PassengerPanel thisPanel (){
        return this;
    }

    public String getPassengerName(){
        return passengerNameField.getText ();
    }

    public String getPassengerSurname(){
        return passengerSurnameField.getText ();
    }

    public String getPassengerCF(){
        return passengerCFField.getText ();
    }

    public void setPassengerName(String passengerName){
        passengerNameField.setText (passengerName);
    }

    public void setPassengerSurname(String passengerSurname){
        passengerSurnameField.setText (passengerSurname);
    }

    public void setPassengerCF(String passengerCF){
        passengerCFField.setText (passengerCF);
    }

    public boolean checkPassengerName (){
        return passengerNameField.getText().equals(displayedNameText) || passengerNameField.getText().equals("");
    }

    public boolean checkPassengerSurname (){
        return passengerSurnameField.getText().equals(displayedSurnameText) || passengerSurnameField.getText().equals("");
    }

    public boolean checkPassengerCF (){
        return passengerCFField.getText().equals(displayedCFText) || passengerCFField.getText().equals("");
    }

    public boolean checkPassengerSeat (){
        return seat == -1;
    }

    public ArrayList<LuggagePanel> getLuggagesPanels() {
        return luggagesView.getLuggagesPanels();
    }

    public void setLuggagesTypes (ArrayList<Integer> luggageTypes, Controller controller){
        luggagesView.setLuggagesTypes (luggageTypes, controller);
    }

    public SeatChooser getSeatChooser() {
        return seatChooser;
    }

    public LuggagesView getLuggagesView() {
        return luggagesView;
    }

    public RoundedButton getSeatButton() {
        return seatButton;
    }
}
