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
import java.util.List;

/**
 * The type Passenger panel.
 */
public class PassengerPanel extends JPanel {

    /**
     * The Passenger label.
     */
//label con eventuale ticketNumber
    protected JLabel passengerLabel;
    /**
     * The Ticket number.
     */
    protected String ticketNumber;

    /**
     * The Passenger name field.
     */
//info
    protected JTextField passengerNameField;
    /**
     * The Passenger surname field.
     */
    protected JTextField passengerSurnameField;
    /**
     * The Passenger c field.
     */
    protected JTextField passengerCField;
    /**
     * The Passenger date picker.
     */
    protected DatePicker passengerDatePicker;

    /**
     * The Seat button.
     */
//posto
    protected JButton seatButton;
    /**
     * The Seat chooser.
     */
    protected SeatChooser seatChooser;
    /**
     * The Seat.
     */
    protected int seat = -1;

    /**
     * The Luggages view button.
     */
//bagagli
    protected JButton luggagesViewButton;
    /**
     * The Luggages view.
     */
    protected LuggagesView luggagesView;

    //checkin
    private JCheckBox checkinCheckBox;

    /**
     * The Constraints.
     */
    protected Constraints constraints;

    //cose utili
    private static final String DISPLAYED_NAME_TEXT = "~Nome~";
    private static final String DISPLAYED_SURNAME_TEXT = "~Cognome~";
    private static final String DISPLAYED_CF_TEXT = "~Codice Fiscale~";
    private static final String DISPLAYED_DATE_TEXT = "00/00/00";
    private static final Color displayedTextColor = new Color(128, 128, 128);
    private static final Color userTextColor = new Color(32, 32, 32);

    /**
     * Instantiates a new Passenger panel.
     *
     * @param controller      the controller
     * @param passengerPanels the passenger panels
     * @param bookedSeats     the booked seats
     */
    public PassengerPanel (Controller controller, List<PassengerPanel> passengerPanels, List<Integer> bookedSeats)
    {
        super ();

        this.setLayout (new GridBagLayout());
        constraints = new Constraints ();

        this.setOpaque(false);

        passengerLabel = new JLabel ("Passenger");

        //info
        passengerNameField = new JTextField(DISPLAYED_NAME_TEXT, 20);
        passengerNameField.setForeground(displayedTextColor);
        passengerSurnameField = new JTextField (DISPLAYED_SURNAME_TEXT, 20);
        passengerSurnameField.setForeground(displayedTextColor);
        passengerCField = new JTextField (DISPLAYED_CF_TEXT, 20);
        passengerCField.setForeground(displayedTextColor);

        //posto
        seatButton = new JButton("Scegli Posto");

        seatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PassengerPanel passengerPanel : passengerPanels) {
                    passengerPanel.getSeatButton().setEnabled(false);
                }
                seatChooser = new SeatChooser (controller, thisPanel(), passengerPanels, bookedSeats);
            }
        });

        if (!passengerPanels.isEmpty())
            this.seatButton.setEnabled(passengerPanels.getFirst().getSeatButton().isEnabled());

        //bagagli
        luggagesViewButton = new JButton("Luggages");

        luggagesView = new LuggagesView ();

        luggagesViewButton.addActionListener (new ActionListener () {
           @Override
           public void actionPerformed (ActionEvent e) {
               luggagesView.setLocation(luggagesViewButton);
               luggagesView.setVisible(true);
           }
        });

        //date
        passengerDatePicker = new DatePicker();
        passengerDatePicker.getComponentDateTextField().setText(DISPLAYED_DATE_TEXT);
        passengerDatePicker.getComponentDateTextField().setForeground(displayedTextColor);

        //posizionamento
        //label
        constraints.setConstraints(0, 0, 3, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerLabel, constraints.getGridBagConstraints());
        passengerLabel.setVisible (true);

        //name
        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerNameField, constraints.getGridBagConstraints());
        passengerNameField.setVisible (true);

        //surname
        constraints.setConstraints (1, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerSurnameField, constraints.getGridBagConstraints());
        passengerSurnameField.setVisible (true);

        //luggages
        constraints.setConstraints (2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (luggagesViewButton, constraints.getGridBagConstraints());
        luggagesViewButton.setVisible (true);

        //CF
        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerCField, constraints.getGridBagConstraints());
        passengerCField.setVisible (true);

        //date
        constraints.setConstraints (1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (passengerDatePicker, constraints.getGridBagConstraints());
        passengerDatePicker.setVisible (true);

        //seat
        constraints.setConstraints (2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, new Insets (5, 5, 5, 5));
        this.add (seatButton, constraints.getGridBagConstraints());
        seatButton.setVisible (true);


        //valori di default
        passengerNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(passengerNameField.getText().equals(DISPLAYED_NAME_TEXT)){
                    passengerNameField.setText("");
                    passengerNameField.setForeground(userTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (passengerNameField.getText().isEmpty()) {
                    passengerNameField.setText(DISPLAYED_NAME_TEXT);
                    passengerNameField.setForeground(displayedTextColor);
                }
            }
        });

        passengerSurnameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (passengerSurnameField.getText().equals(DISPLAYED_SURNAME_TEXT)) {
                    passengerSurnameField.setText("");
                    passengerSurnameField.setForeground(userTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (passengerSurnameField.getText().isEmpty()) {
                    passengerSurnameField.setText(DISPLAYED_SURNAME_TEXT);
                    passengerSurnameField.setForeground(displayedTextColor);
                }
            }
        });

        passengerCField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (passengerCField.getText().equals(DISPLAYED_CF_TEXT)) {
                    passengerCField.setText("");
                    passengerCField.setForeground(userTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (passengerCField.getText().isEmpty()) {
                    passengerCField.setText(DISPLAYED_CF_TEXT);
                    passengerCField.setForeground(displayedTextColor);
                }
            }
        });

        passengerDatePicker.getComponentDateTextField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (passengerDatePicker.getComponentDateTextField().getText().equals(DISPLAYED_DATE_TEXT)) {
                    passengerDatePicker.getComponentDateTextField().setText("");
                    passengerDatePicker.getComponentDateTextField().setForeground(userTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (passengerDatePicker.getComponentDateTextField().getText().isEmpty()) {
                    passengerDatePicker.getComponentDateTextField().setText(DISPLAYED_DATE_TEXT);
                    passengerDatePicker.getComponentDateTextField().setForeground(displayedTextColor);
                }
            }
        });

        this.setVisible (true);
    }

    /**
     * Print seat string.
     *
     * @return the string
     */
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

    /**
     * Get seat int.
     *
     * @return the int
     */
    public int getSeat(){
        return seat;
    }

    /**
     * Set seat.
     *
     * @param parSeat the par seat
     */
    public void setSeat(int parSeat){
        seat = parSeat;
        seatButton.setText(printSeat());
    }

    private PassengerPanel thisPanel (){
        return this;
    }

    /**
     * Get passenger name string.
     *
     * @return the string
     */
    public String getPassengerName(){
        return passengerNameField.getText().equals(DISPLAYED_NAME_TEXT) ? null : passengerNameField.getText();
    }

    /**
     * Get passenger surname string.
     *
     * @return the string
     */
    public String getPassengerSurname(){
        return passengerSurnameField.getText().equals(DISPLAYED_SURNAME_TEXT) ? null : passengerSurnameField.getText() ;
    }

    /**
     * Get passenger cf string.
     *
     * @return the string
     */
    public String getPassengerCF(){
        return passengerCField.getText ();
    }

    /**
     * Set passenger name.
     *
     * @param passengerName the passenger name
     */
    public void setPassengerName(String passengerName){
        passengerNameField.setText (passengerName);
    }

    /**
     * Set passenger surname.
     *
     * @param passengerSurname the passenger surname
     */
    public void setPassengerSurname(String passengerSurname){
        passengerSurnameField.setText (passengerSurname);
    }

    /**
     * Set passenger cf.
     *
     * @param passengerCF the passenger cf
     */
    public void setPassengerCF(String passengerCF){
        passengerCField.setText (passengerCF);
    }

    /**
     * Check passenger name boolean.
     *
     * @return the boolean
     */
    public boolean checkPassengerName (){
        return passengerNameField.getText().equals(DISPLAYED_NAME_TEXT) || passengerNameField.getText().isEmpty();
    }

    /**
     * Check passenger surname boolean.
     *
     * @return the boolean
     */
    public boolean checkPassengerSurname (){
        return passengerSurnameField.getText().equals(DISPLAYED_SURNAME_TEXT) || passengerSurnameField.getText().isEmpty();
    }

    /**
     * Check passenger cf boolean.
     *
     * @return the boolean
     */
    public boolean checkPassengerCF (){
        return passengerCField.getText().equals(DISPLAYED_CF_TEXT) || passengerCField.getText().isEmpty();
    }

    /**
     * Gets luggages panels.
     *
     * @return the luggages panels
     */
    public List<LuggagePanel> getLuggagesPanels() {
        return luggagesView.getLuggagesPanels();
    }

    /**
     * Set luggages.
     *
     * @param luggageTypes    the luggage types
     * @param luggagesTickets the luggages tickets
     * @param luggagesStatus  the luggages status
     * @param controller      the controller
     */
    public void setLuggages (List<Integer> luggageTypes, List<String> luggagesTickets, List<String> luggagesStatus, Controller controller){
        luggagesView.setLuggages (luggageTypes, luggagesTickets, luggagesStatus,controller);
    }

    /**
     * Gets seat chooser.
     *
     * @return the seat chooser
     */
    public SeatChooser getSeatChooser() {
        return seatChooser;
    }

    /**
     * Gets luggages view.
     *
     * @return the luggages view
     */
    public LuggagesView getLuggagesView() {
        return luggagesView;
    }

    /**
     * Gets seat button.
     *
     * @return the seat button
     */
    public JButton getSeatButton() {
        return seatButton;
    }

    /**
     * Sets ticket number.
     *
     * @param ticketNumber the ticket number
     */
    public void setTicketNumber (String ticketNumber) {

        this.ticketNumber = ticketNumber;
        this.passengerLabel.setText("Passenger: " + ticketNumber);
    }

    /**
     * Gets ticket number.
     *
     * @return the ticket number
     */
    public String getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Sets panel enabled.
     *
     * @param flag the flag
     */
    public void setPanelEnabled (boolean flag) {

        passengerNameField.setEnabled(flag);
        passengerSurnameField.setEnabled(flag);
        passengerCField.setEnabled(flag);
        seatButton.setEnabled(flag);
        passengerDatePicker.setEnabled(flag);

        luggagesView.getAddLuggageButton().setVisible(flag);
        luggagesView.getConfirmButton().setVisible(flag);
        for (int i = 0; i < luggagesView.getLuggagesPanels().size(); i++) {

            luggagesView.getLuggagesPanels().get(i).getComboBox().setEnabled(flag);

        }
    }

    /**
     * Add checkin check box.
     *
     * @param flag the flag
     */
    public void addCheckinCheckBox (boolean flag) {
        checkinCheckBox = new JCheckBox("Checkin", flag);

        constraints.setConstraints(0, 3, 3, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(checkinCheckBox, constraints.getGridBagConstraints());
        checkinCheckBox.setVisible(true);
    }

    /**
     * Gets checkin check box.
     *
     * @return the checkin check box
     */
    public JCheckBox getCheckinCheckBox () {
        return checkinCheckBox;
    }

    /**
     * Gets passenger date.
     *
     * @return the passenger date
     */
    public Date getPassengerDate () {
        return passengerDatePicker.getComponentDateTextField().getText().equals(DISPLAYED_DATE_TEXT) ? null
                : new java.sql.Date(passengerDatePicker.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    /**
     * Check passenger date boolean.
     *
     * @return the boolean
     */
    public boolean checkPassengerDate () {
        return passengerDatePicker.getComponentDateTextField().getText().equals(DISPLAYED_DATE_TEXT) || !passengerDatePicker.isTextFieldValid();
    }

    /**
     * Sets passenger date.
     *
     * @param passengerDate the passenger date
     */
    public void setPassengerDate (Date passengerDate) {
        passengerDatePicker.setDate(passengerDate.toLocalDate());
    }
}
