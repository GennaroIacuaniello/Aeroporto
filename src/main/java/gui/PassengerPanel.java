package gui;

import com.github.lgooddatepicker.components.DatePicker;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;

public class PassengerPanel extends JPanel {

    //label con eventuale ticketNumber
    protected JLabel passengerLabel;
    protected String ticketNumber;

    protected JTextField passengerNameField;
    protected JTextField passengerSurnameField;
    protected JTextField passengerCField;
    protected DatePicker passengerDatePicker;

    //posto
    protected JButton seatButton;
    protected SeatChooser seatChooser;
    protected int seat = -1;

    //bagagli
    protected JButton luggagesViewButton;
    protected LuggagesView luggagesView;

    //checkin
    private JCheckBox checkinCheckBox;

    protected Constraints constraints;

    //cose utili
    private String displayedNameText = "~Nome~";
    private String displayedSurnameText = "~Cognome~";
    private String displayedCFText = "~Codice Fiscale~";
    private String displayedDateText = "00/00/00";
    private Color displayedTextColor = new Color(128, 128, 128);
    private Color userTextColor = new Color(32, 32, 32);

    public PassengerPanel (Controller controller, ArrayList<PassengerPanel> passengerPanels)
    {
        super ();

        this.setLayout (new GridBagLayout());
        constraints = new Constraints ();

        this.setOpaque(false);

        passengerLabel = new JLabel ("Passenger");

        //info
        passengerNameField = new JTextField(displayedNameText, 20);
        passengerNameField.setForeground(displayedTextColor);
        passengerSurnameField = new JTextField (displayedSurnameText, 20);
        passengerSurnameField.setForeground(displayedTextColor);
        passengerCField = new JTextField (displayedCFText, 20);
        passengerCField.setForeground(displayedTextColor);

        //posto
        seatButton = new JButton("Scegli Posto");

        seatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PassengerPanel passengerPanel : passengerPanels) {
                    passengerPanel.getSeatButton().setEnabled(false);
                }
                seatChooser = new SeatChooser (controller, thisPanel(), passengerPanels);
            }
        });

        if (!passengerPanels.isEmpty())
            this.seatButton.setEnabled(passengerPanels.getFirst().getSeatButton().isEnabled());

        //bagagli
        luggagesViewButton = new JButton("Luggages");

        luggagesView = new LuggagesView (controller);

        luggagesViewButton.addActionListener (new ActionListener () {
           @Override
           public void actionPerformed (ActionEvent e) {
               luggagesView.setLocation(luggagesViewButton);
               luggagesView.setVisible(true);
           }
        });

        //date
        passengerDatePicker = new DatePicker();
        passengerDatePicker.getComponentDateTextField().setText(displayedDateText);
        passengerDatePicker.getComponentDateTextField().setForeground(displayedTextColor);

        //posizionamento
        //label
        constraints.setConstraints(0, 0, 3, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerLabel, constraints.getConstraints());
        passengerLabel.setVisible (true);

        //name
        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerNameField, constraints.getConstraints());
        passengerNameField.setVisible (true);

        //surname
        constraints.setConstraints (1, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerSurnameField, constraints.getConstraints());
        passengerSurnameField.setVisible (true);

        //luggages
        constraints.setConstraints (2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (luggagesViewButton, constraints.getConstraints());
        luggagesViewButton.setVisible (true);

        //CF
        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerCField, constraints.getConstraints());
        passengerCField.setVisible (true);

        //date
        constraints.setConstraints (1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerDatePicker, constraints.getConstraints());
        passengerDatePicker.setVisible (true);

        //seat
        constraints.setConstraints (2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (seatButton, constraints.getConstraints());
        seatButton.setVisible (true);


        //valori di default
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
                if (passengerNameField.getText().isEmpty()) {
                    passengerNameField.setText(displayedNameText);
                    passengerNameField.setForeground(displayedTextColor);
                }
            }
        });

        passengerSurnameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (passengerSurnameField.getText().equals(displayedSurnameText)) {
                    passengerSurnameField.setText("");
                    passengerSurnameField.setForeground(userTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (passengerSurnameField.getText().isEmpty()) {
                    passengerSurnameField.setText(displayedSurnameText);
                    passengerSurnameField.setForeground(displayedTextColor);
                }
            }
        });

        passengerCField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (passengerCField.getText().equals(displayedCFText)) {
                    passengerCField.setText("");
                    passengerCField.setForeground(userTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (passengerCField.getText().isEmpty()) {
                    passengerCField.setText(displayedCFText);
                    passengerCField.setForeground(displayedTextColor);
                }
            }
        });

        passengerDatePicker.getComponentDateTextField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (passengerDatePicker.getComponentDateTextField().getText().equals(displayedDateText)) {
                    passengerDatePicker.getComponentDateTextField().setText("");
                    passengerDatePicker.getComponentDateTextField().setForeground(userTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (passengerDatePicker.getComponentDateTextField().getText().isEmpty()) {
                    System.out.println("ciao");
                    passengerDatePicker.getComponentDateTextField().setText(displayedDateText);
                    passengerDatePicker.getComponentDateTextField().setForeground(displayedTextColor);
                }
            }
        });

        this.setVisible (true);
    }

    public String printSeat(){

        if (seat == -1) return "SCEGLI POSTO";

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

        return (seat / 6) + 1 + literal;
    }

    public int getSeat(){
        return seat;
    }

    public void setSeat(int par_seat){
        seat = par_seat;
        seatButton.setText(printSeat());
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
        return passengerCField.getText ();
    }

    public void setPassengerName(String passengerName){
        passengerNameField.setText (passengerName);
    }

    public void setPassengerSurname(String passengerSurname){
        passengerSurnameField.setText (passengerSurname);
    }

    public void setPassengerCF(String passengerCF){
        passengerCField.setText (passengerCF);
    }

    public boolean checkPassengerName (){
        return passengerNameField.getText().equals(displayedNameText) || passengerNameField.getText().equals("");
    }

    public boolean checkPassengerSurname (){
        return passengerSurnameField.getText().equals(displayedSurnameText) || passengerSurnameField.getText().equals("");
    }

    public boolean checkPassengerCF (){
        return passengerCField.getText().equals(displayedCFText) || passengerCField.getText().equals("");
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

    public JButton getSeatButton() {
        return seatButton;
    }

    public void setTicketNumber (String ticketNumber) {

        this.ticketNumber = ticketNumber;
        this.passengerLabel.setText("Passenger: " + ticketNumber);
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setPanelEnabled (boolean flag) {

        passengerNameField.setEnabled(flag);
        passengerSurnameField.setEnabled(flag);
        passengerCField.setEnabled(flag);
        seatButton.setEnabled(flag);

        luggagesView.getAddLuggageButton().setVisible(flag);
        luggagesView.getConfirmButton().setVisible(flag);
        for (int i = 0; i < luggagesView.getLuggagesPanels().size(); i++) {

            luggagesView.getLuggagesPanels().get(i).getComboBox().setEnabled(flag);
            luggagesView.getRemoveLuggageButtons().get(i).setVisible(flag);
        }
    }

    public void addCheckinCheckBox (boolean flag) {
        checkinCheckBox = new JCheckBox("Checkin", flag);

        constraints.setConstraints(0, 3, 3, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(checkinCheckBox, constraints.getConstraints());
        checkinCheckBox.setVisible(true);
    }

    public JCheckBox getCheckinCheckBox () {
        return checkinCheckBox;
    }

    public Date getPassengerDate () {
        return new java.sql.Date(passengerDatePicker.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    public boolean checkPassengerDate () {
        return passengerDatePicker.getComponentDateTextField().getText().equals(displayedDateText) || !passengerDatePicker.isTextFieldValid();
    }
}
