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
 * Comprehensive passenger information panel for managing passenger data within the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide a complete passenger information management interface
 * that handles personal details, seat assignments, luggage management, and check-in operations. The
 * panel serves as the primary component for passenger data entry and display across customer booking
 * workflows and administrative oversight operations throughout the airport management system.
 * </p>
 * <p>
 * The PassengerPanel class supports comprehensive passenger management functionality including:
 * </p>
 * <ul>
 *   <li><strong>Personal Information Management:</strong> Complete passenger identification including name, surname, tax code, and birth date</li>
 *   <li><strong>Seat Assignment Integration:</strong> Interactive seat selection with real-time availability checking and conflict prevention</li>
 *   <li><strong>Luggage Management:</strong> Comprehensive luggage association with type selection and status tracking</li>
 *   <li><strong>Check-in Operations:</strong> Administrative check-in control integration for operational workflow support</li>
 *   <li><strong>Ticket Association:</strong> Ticket number display and management for passenger identification and tracking</li>
 *   <li><strong>Validation System:</strong> Complete input validation with placeholder text management and data integrity checking</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see Controller
 * @see SeatChooser
 * @see LuggagesView
 * @see DatePicker
 * @see BookingPage
 * @see CheckinPassengers
 * @see GridBagLayout
 */
public class PassengerPanel extends JPanel {

    /**
     * Passenger identification label displaying ticket number when available.
     * <p>
     * This label provides passenger identification display with automatic ticket
     * number integration when tickets are assigned. The label updates dynamically
     * to show "Passenger: [ticket_number]" format for clear passenger tracking
     * and identification throughout booking and operational workflows.
     * </p>
     */
    protected JLabel passengerLabel;
    
    /**
     * Ticket number identifier for passenger tracking and system coordination.
     * <p>
     * This field maintains the passenger's assigned ticket number for system
     * integration and passenger identification. The ticket number enables
     * cross-system tracking and coordination between booking, check-in, and
     * operational systems throughout the passenger journey.
     * </p>
     */
    protected String ticketNumber;

    /**
     * Text field for passenger first name entry and display.
     * <p>
     * This field handles passenger first name data with intelligent placeholder
     * text management. The field displays "~Nome~" as placeholder text and
     * automatically clears on focus while restoring the placeholder when empty,
     * providing clear user guidance throughout data entry operations.
     * </p>
     */
    protected JTextField passengerNameField;
    
    /**
     * Text field for passenger surname entry and display.
     * <p>
     * This field manages passenger surname information with smart placeholder
     * behavior. The field shows "~Cognome~" as placeholder text with automatic
     * focus-based clearing and restoration, ensuring consistent user experience
     * and clear data entry guidance across passenger management workflows.
     * </p>
     */
    protected JTextField passengerSurnameField;
    
    /**
     * Text field for passenger tax code (Codice Fiscale) entry and display.
     * <p>
     * This field handles Italian tax code information with placeholder text
     * management showing "~Codice Fiscale~". The field provides essential
     * passenger identification capability with focus-based text management
     * for optimal user experience during passenger data entry operations.
     * </p>
     */
    protected JTextField passengerCField;
    
    /**
     * Date picker component for passenger birth date selection and display.
     * <p>
     * This specialized date component provides comprehensive birth date management
     * with custom placeholder text ("00/00/00") and validation capabilities. The
     * picker integrates with SQL date systems and provides localized date handling
     * for passenger demographic information management throughout the system.
     * </p>
     */
    protected DatePicker passengerDatePicker;

    /**
     * Interactive button for seat selection and seat assignment display.
     * <p>
     * This button provides dual functionality: initiating seat selection through
     * the SeatChooser interface and displaying currently assigned seats in
     * human-readable format (e.g., "12A", "15F"). The button integrates with
     * seat availability systems to prevent booking conflicts and provides
     * clear visual feedback about passenger seat assignments.
     * </p>
     */
    protected JButton seatButton;
    
    /**
     * Seat selection dialog component for interactive seat assignment.
     * <p>
     * This component provides comprehensive seat selection capabilities with
     * real-time availability checking and visual seat map interaction. The
     * seat chooser integrates with booking systems to prevent conflicts and
     * maintains seat assignment state throughout passenger management workflows.
     * </p>
     */
    protected SeatChooser seatChooser;
    
    /**
     * Numerical seat identifier for internal seat tracking and system coordination.
     * <p>
     * This field maintains the passenger's assigned seat as a numerical identifier
     * for system integration and database operations. The value -1 indicates no
     * seat assignment, while positive values correspond to specific seat positions
     * within the aircraft seating configuration.
     * </p>
     */
    protected int seat = -1;

    /**
     * Interactive button for accessing comprehensive luggage management interface.
     * <p>
     * This button provides access to the luggage management system through the
     * LuggagesView component. The button enables passengers and staff to manage
     * luggage associations, types, and status information with full integration
     * to luggage tracking and operational systems throughout the airport.
     * </p>
     */
    protected JButton luggagesViewButton;
    
    /**
     * Comprehensive luggage management interface for passenger luggage operations.
     * <p>
     * This component provides complete luggage management capabilities including
     * luggage type selection, status tracking, and ticket association. The
     * luggage view integrates with operational systems to support check-in,
     * boarding, and luggage handling workflows throughout passenger operations.
     * </p>
     */
    protected LuggagesView luggagesView;

    /**
     * Optional check-in checkbox for administrative passenger selection operations.
     * <p>
     * This checkbox provides administrative staff with interactive passenger
     * selection capabilities for batch check-in operations and individual
     * passenger management. The checkbox integrates with operational workflows
     * to support efficient passenger processing during flight preparation.
     * </p>
     */
    private JCheckBox checkinCheckBox;

    /**
     * Layout constraints utility for precise component positioning throughout the passenger panel.
     * <p>
     * This Constraints helper object provides standardized GridBagConstraints
     * configuration for optimal component layout and positioning. The constraints
     * ensure consistent spacing (5-pixel margins), proper alignment, and visual
     * organization across all passenger panel components and form elements.
     * </p>
     */
    protected Constraints constraints;

    /**
     * Placeholder text constant for the passenger name field providing user guidance.
     * <p>
     * This constant defines the Italian placeholder text "~Nome~" displayed
     * in the name field when empty, providing clear user guidance for data
     * entry while maintaining consistent localization throughout the passenger
     * management interface.
     * </p>
     */
    private static final String DISPLAYED_NAME_TEXT = "~Nome~";
    
    /**
     * Placeholder text constant for the passenger surname field providing user guidance.
     * <p>
     * This constant defines the Italian placeholder text "~Cognome~" displayed
     * in the surname field when empty, ensuring consistent user experience and
     * clear data entry guidance throughout passenger information management
     * workflows.
     * </p>
     */
    private static final String DISPLAYED_SURNAME_TEXT = "~Cognome~";
    
    /**
     * Placeholder text constant for the passenger tax code field providing user guidance.
     * <p>
     * This constant defines the Italian placeholder text "~Codice Fiscale~"
     * displayed in the tax code field when empty, providing essential guidance
     * for Italian tax code entry and maintaining consistent localization
     * throughout passenger identification workflows.
     * </p>
     */
    private static final String DISPLAYED_CF_TEXT = "~Codice Fiscale~";
    
    /**
     * Placeholder text constant for the passenger birth date field providing format guidance.
     * <p>
     * This constant defines the date format placeholder "00/00/00" displayed
     * in the date picker when empty, providing clear format guidance for
     * date entry and maintaining consistent user experience throughout
     * passenger demographic information management.
     * </p>
     */
    private static final String DISPLAYED_DATE_TEXT = "00/00/00";
    
    /**
     * Color constant for placeholder text providing subtle visual guidance.
     * <p>
     * This color (128, 128, 128) provides appropriate visual weight for
     * placeholder text that guides users without overwhelming the interface.
     * The gray tone ensures clear distinction from user-entered content while
     * maintaining readability and professional appearance.
     * </p>
     */
    private static final Color displayedTextColor = new Color(128, 128, 128);
    
    /**
     * Color constant for user-entered text ensuring optimal readability.
     * <p>
     * This color (32, 32, 32) provides optimal contrast and readability for
     * actual user-entered content. The dark gray tone ensures clear distinction
     * from placeholder text while maintaining professional appearance and
     * accessibility standards throughout passenger data entry operations.
     * </p>
     */
    private static final Color userTextColor = new Color(32, 32, 32);

    /**
     * Constructs a new PassengerPanel with comprehensive passenger management functionality.
     * <p>
     * This constructor initializes the complete passenger management interface by creating
     * all passenger information components, configuring layout management, and establishing
     * comprehensive event handling for passenger data operations. The constructor creates
     * a fully functional passenger panel ready for immediate user interaction with integrated
     * seat management, luggage coordination, and validation capabilities.
     * </p>
     * <p>
     * Information fields creation includes all essential passenger data fields with intelligent
     * placeholder text in Italian localization. Each field is configured with appropriate
     * placeholder text color and focus behavior for optimal user guidance and data entry
     * experience throughout passenger management operations.
     * </p>
     * <p>
     * Seat selection integration includes sophisticated multi-panel coordination that disables
     * all passenger panel seat buttons during selection to prevent conflicts, creates the
     * {@link SeatChooser} interface with current passenger context, and maintains seat
     * availability coordination across all passenger panels in the booking system.
     * </p>
     * <p>
     * Luggage management integration creates the {@link LuggagesView} component with proper
     * positioning relative to the luggage button and comprehensive luggage management
     * capabilities including type selection, status tracking, and administrative oversight.
     * </p>
     * <p>
     * Date picker integration includes the {@link DatePicker} component with custom placeholder
     * text management and focus behavior that matches other form fields. The date picker
     * supports SQL date conversion and Italian date formatting for consistent system integration.
     * </p>
     *
     * @param controller the system controller providing access to passenger management, seat coordination, and system integration functionality
     * @param passengerPanels the list of existing passenger panels for multi-passenger coordination and seat availability management
     * @param bookedSeats the list of already booked seats for conflict prevention and availability checking during seat selection operations
     */
    public PassengerPanel (Controller controller, List<PassengerPanel> passengerPanels, List<Integer> bookedSeats)
    {
        super ();

        this.setLayout (new GridBagLayout());
        constraints = new Constraints ();

        this.setOpaque(false);

        passengerLabel = new JLabel ("Passeggero");

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
        luggagesViewButton = new JButton("Bagagli");

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
     * Converts numerical seat assignment to human-readable seat designation with row and letter format.
     * <p>
     * This method transforms internal numerical seat identifiers into standard airline seat
     * designations using row numbers and letter columns (A-F). The conversion provides clear
     * visual representation of seat assignments for passenger understanding and operational
     * coordination throughout booking and check-in workflows.
     * </p>
     *
     * @return formatted seat designation (e.g., "12A", "15F") or "SCEGLI POSTO" if no seat assigned
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
     * Retrieves the current numerical seat assignment for system integration and database operations.
     * <p>
     * This method returns the internal numerical seat identifier used for system coordination
     * and database storage. The numerical format enables efficient seat management and
     * availability checking throughout booking and operational workflows.
     * </p>
     *
     * @return the numerical seat identifier or -1 if no seat is assigned
     */
    public int getSeat(){
        return seat;
    }

    /**
     * Assigns a seat to the passenger with immediate button text update and visual feedback.
     * <p>
     * This method sets the passenger's seat assignment and immediately updates the seat
     * button display to show the human-readable seat designation. The assignment integrates
     * with seat availability systems and provides immediate visual feedback to users about
     * their seat selection throughout booking and modification workflows.
     * </p>
     *
     * @param parSeat the numerical seat identifier to assign to this passenger
     */
    public void setSeat(int parSeat){
        seat = parSeat;
        seatButton.setText(printSeat());
    }

    /**
     * Provides self-reference for inner class access and callback operations.
     * <p>
     * This utility method returns a reference to the current PassengerPanel instance,
     * enabling inner classes and callback operations to access the panel context.
     * The method supports integration with seat selection and other interactive
     * components that require passenger panel context for operation coordination.
     * </p>
     *
     * @return reference to this PassengerPanel instance
     */
    private PassengerPanel thisPanel (){
        return this;
    }

    /**
     * Retrieves passenger first name with placeholder text filtering for data processing.
     * <p>
     * This method returns the passenger's first name while filtering out placeholder
     * text to ensure clean data retrieval. The method supports both complete and
     * partial data scenarios by returning null when only placeholder text is present,
     * enabling flexible data processing throughout booking and operational workflows.
     * </p>
     *
     * @return passenger first name or null if placeholder text or empty
     */
    public String getPassengerName(){
        return passengerNameField.getText().equals(DISPLAYED_NAME_TEXT) ? null : passengerNameField.getText();
    }

    /**
     * Retrieves passenger surname with placeholder text filtering for data processing.
     * <p>
     * This method returns the passenger's surname while filtering out placeholder
     * text to ensure clean data retrieval. The method supports flexible data
     * processing by returning null when only placeholder text is present, enabling
     * both complete and partial passenger information scenarios throughout system workflows.
     * </p>
     *
     * @return passenger surname or null if placeholder text or empty
     */
    public String getPassengerSurname(){
        return passengerSurnameField.getText().equals(DISPLAYED_SURNAME_TEXT) ? null : passengerSurnameField.getText() ;
    }

    /**
     * Retrieves passenger tax code (Codice Fiscale) for identification and system operations.
     * <p>
     * This method returns the passenger's Italian tax code as entered in the field.
     * Unlike other fields, this method returns the raw field content to support
     * various validation scenarios and administrative requirements throughout
     * passenger identification and booking operations.
     * </p>
     *
     * @return passenger tax code as entered in the field
     */
    public String getPassengerCF(){
        return passengerCField.getText ();
    }

    /**
     * Sets passenger first name with immediate field update and display.
     * <p>
     * This method updates the passenger first name field with the provided value,
     * immediately displaying the name in the interface. The method supports both
     * initial data loading and dynamic updates throughout passenger management
     * workflows including booking creation and modification operations.
     * </p>
     *
     * @param passengerName the first name to set for the passenger
     */
    public void setPassengerName(String passengerName){
        passengerNameField.setText (passengerName);
    }

    /**
     * Sets passenger surname with immediate field update and display.
     * <p>
     * This method updates the passenger surname field with the provided value,
     * providing immediate visual feedback in the interface. The method enables
     * both initial data population and dynamic updates during booking modification
     * and administrative operations throughout the passenger management system.
     * </p>
     *
     * @param passengerSurname the surname to set for the passenger
     */
    public void setPassengerSurname(String passengerSurname){
        passengerSurnameField.setText (passengerSurname);
    }

    /**
     * Sets passenger tax code (Codice Fiscale) with immediate field update and display.
     * <p>
     * This method updates the passenger tax code field with the provided value,
     * enabling immediate display of identification information. The method supports
     * administrative operations and passenger identification workflows requiring
     * tax code display and management throughout the airport management system.
     * </p>
     *
     * @param passengerCF the tax code to set for the passenger
     */
    public void setPassengerCF(String passengerCF){
        passengerCField.setText (passengerCF);
    }

    /**
     * Validates passenger first name field for completeness checking with placeholder awareness.
     * <p>
     * This validation method checks whether the passenger first name field contains
     * meaningful data by detecting placeholder text and empty fields. The method
     * supports flexible validation scenarios that accommodate both complete and
     * partial data entry requirements throughout booking and modification workflows.
     * </p>
     *
     * @return true if field contains placeholder text or is empty, false if contains user data
     */
    public boolean checkPassengerName (){
        return passengerNameField.getText().equals(DISPLAYED_NAME_TEXT) || passengerNameField.getText().isEmpty();
    }

    /**
     * Validates passenger surname field for completeness checking with placeholder awareness.
     * <p>
     * This validation method determines whether the passenger surname field requires
     * completion by detecting placeholder text and empty content. The method enables
     * flexible validation that supports both complete passenger information requirements
     * and partial data entry scenarios throughout booking workflows.
     * </p>
     *
     * @return true if field contains placeholder text or is empty, false if contains user data
     */
    public boolean checkPassengerSurname (){
        return passengerSurnameField.getText().equals(DISPLAYED_SURNAME_TEXT) || passengerSurnameField.getText().isEmpty();
    }

    /**
     * Validates passenger tax code field for completeness checking with placeholder awareness.
     * <p>
     * This validation method assesses whether the passenger tax code field requires
     * completion by detecting placeholder text and empty content. The method supports
     * administrative validation requirements while accommodating flexible data entry
     * scenarios throughout passenger identification and booking workflows.
     * </p>
     *
     * @return true if field contains placeholder text or is empty, false if contains user data
     */
    public boolean checkPassengerCF (){
        return passengerCField.getText().equals(DISPLAYED_CF_TEXT) || passengerCField.getText().isEmpty();
    }

    /**
     * Provides direct access to passenger luggage panels for external luggage management operations.
     * <p>
     * This method returns the list of luggage panels associated with the passenger,
     * enabling external components to access and manage passenger luggage information.
     * The method supports administrative oversight and booking modification workflows
     * that require direct luggage panel access for data processing and validation.
     * </p>
     *
     * @return list of LuggagePanel objects associated with this passenger
     */
    public List<LuggagePanel> getLuggagesPanels() {
        return luggagesView.getLuggagesPanels();
    }

    /**
     * Configures passenger luggage information with comprehensive luggage data integration.
     * <p>
     * This method populates the passenger's luggage management system with complete
     * luggage information including types, ticket associations, and status tracking.
     * The method enables both initial luggage data loading and dynamic updates
     * throughout booking modification and administrative operations.
     * </p>
     *
     * @param luggageTypes list of luggage type identifiers for classification
     * @param luggagesTickets list of ticket numbers associated with luggage items
     * @param luggagesStatus list of current status values for luggage tracking
     * @param controller the system controller for luggage management coordination
     */
    public void setLuggages (List<Integer> luggageTypes, List<String> luggagesTickets, List<String> luggagesStatus, Controller controller){
        luggagesView.setLuggages (luggageTypes, luggagesTickets, luggagesStatus,controller);
    }

    /**
     * Provides access to the seat chooser dialog for external seat management operations.
     * <p>
     * This method returns the currently active seat chooser dialog associated with
     * this passenger panel, enabling external components to manage seat selection
     * workflows and resource cleanup operations throughout booking and administrative
     * processes.
     * </p>
     *
     * @return the SeatChooser dialog instance or null if no seat selection is active
     */
    public SeatChooser getSeatChooser() {
        return seatChooser;
    }

    /**
     * Provides access to the luggage management interface for external luggage operations.
     * <p>
     * This method returns the luggage view component associated with this passenger,
     * enabling external systems to access luggage management functionality for
     * administrative oversight, booking modification, and operational coordination
     * throughout passenger management workflows.
     * </p>
     *
     * @return the LuggagesView component for this passenger's luggage management
     */
    public LuggagesView getLuggagesView() {
        return luggagesView;
    }

    /**
     * Provides access to the seat selection button for external interface coordination.
     * <p>
     * This method returns the seat selection button component, enabling external
     * systems to coordinate seat selection workflows, manage button states, and
     * integrate seat assignment operations with broader booking and administrative
     * processes throughout the passenger management system.
     * </p>
     *
     * @return the JButton component for seat selection operations
     */
    public JButton getSeatButton() {
        return seatButton;
    }

    /**
     * Associates a ticket number with the passenger and updates the identification display.
     * <p>
     * This method assigns a ticket number to the passenger and immediately updates
     * the passenger label to display the ticket association. The ticket assignment
     * enables passenger tracking and identification throughout booking, check-in,
     * and operational workflows across the airport management system.
     * </p>
     *
     * @param ticketNumber the ticket number to associate with this passenger
     */
    public void setTicketNumber (String ticketNumber) {

        this.ticketNumber = ticketNumber;
        this.passengerLabel.setText("Passeggero: " + ticketNumber);
    }

    /**
     * Retrieves the passenger's assigned ticket number for system coordination and tracking.
     * <p>
     * This method returns the ticket number associated with the passenger, enabling
     * cross-system integration and passenger identification throughout booking,
     * operational, and administrative workflows across the airport management system.
     * </p>
     *
     * @return the ticket number assigned to this passenger or null if no ticket assigned
     */
    public String getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Controls the interactive state of all passenger panel components with comprehensive enable/disable functionality.
     * <p>
     * This method provides centralized control over the interactive state of all passenger
     * panel components, enabling both data entry and read-only display modes. The method
     * supports administrative oversight scenarios where passenger information should be
     * visible but not editable, as well as full interactive modes for booking creation
     * and modification workflows.
     * </p>
     * <p>
     * Component state management includes:
     * </p>
     * <ul>
     *   <li><strong>Personal Information Fields:</strong> Controls all text field editability</li>
     *   <li><strong>Interactive Buttons:</strong> Manages seat selection button availability</li>
     *   <li><strong>Date Picker:</strong> Controls date selection functionality</li>
     *   <li><strong>Luggage Controls:</strong> Manages luggage addition and modification capabilities</li>
     *   <li><strong>Luggage Components:</strong> Controls individual luggage item editability</li>
     * </ul>
     *
     * @param flag true to enable all interactive components, false to set read-only mode
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
     * Adds administrative check-in checkbox control for operational passenger selection workflows.
     * <p>
     * This method adds a check-in checkbox to the passenger panel for administrative
     * use during passenger check-in operations. The checkbox enables operational staff
     * to select passengers for batch processing and individual check-in management
     * throughout flight preparation and boarding workflows.
     * </p>
     * <p>
     * Check-in checkbox configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Administrative Control:</strong> Provides staff interface for passenger selection</li>
     *   <li><strong>Status Integration:</strong> Reflects current passenger check-in status</li>
     * </ul>
     *
     * @param flag the initial check-in status to display in the checkbox
     */
    public void addCheckinCheckBox (boolean flag) {
        checkinCheckBox = new JCheckBox("Checkin", flag);

        constraints.setConstraints(0, 3, 3, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(checkinCheckBox, constraints.getGridBagConstraints());
        checkinCheckBox.setVisible(true);
    }

    /**
     * Provides access to the administrative check-in checkbox for operational workflow coordination.
     * <p>
     * This method returns the check-in checkbox component, enabling external systems
     * to access check-in selection state and coordinate batch processing operations
     * throughout administrative check-in workflows and passenger management operations
     * across the airport management system.
     * </p>
     *
     * @return the JCheckBox component for check-in operations or null if not configured
     */
    public JCheckBox getCheckinCheckBox () {
        return checkinCheckBox;
    }

    /**
     * Retrieves passenger birthdate with comprehensive placeholder handling and SQL date conversion.
     * <p>
     * This method returns the passenger's birthdate with intelligent placeholder text
     * filtering and automatic conversion to SQL date format for database operations.
     * The method supports both data retrieval and system integration requirements
     * throughout passenger management and administrative workflows.
     * </p>
     *
     * @return passenger birthdate as SQL Date or null if placeholder text or invalid date
     */
    public Date getPassengerDate () {
        return passengerDatePicker.getComponentDateTextField().getText().equals(DISPLAYED_DATE_TEXT) ? null
                : new java.sql.Date(passengerDatePicker.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    /**
     * Validates passenger birthdate field for completeness and validity with comprehensive checking.
     * <p>
     * This validation method assesses whether the passenger birthdate field requires
     * completion or contains invalid data by checking for placeholder text and date
     * picker validation state. The method supports flexible validation scenarios that
     * accommodate both complete and partial passenger information requirements.
     * </p>
     * <p>
     * Date validation includes:
     * </p>
     * <ul>
     *   <li><strong>Placeholder Detection:</strong> Identifies when field contains only placeholder text</li>
     *   <li><strong>Date Validity:</strong> Checks DatePicker internal validation state</li>
     *   <li><strong>Flexible Validation:</strong> Returns true when field needs completion or correction</li>
     * </ul>
     *
     * @return true if field contains placeholder text or invalid date, false if contains valid user data
     */
    public boolean checkPassengerDate () {
        return passengerDatePicker.getComponentDateTextField().getText().equals(DISPLAYED_DATE_TEXT) || !passengerDatePicker.isTextFieldValid();
    }

    /**
     * Sets passenger birthdate with automatic LocalDate conversion and immediate display update.
     * <p>
     * This method updates the passenger birthdate field by converting the provided
     * SQL Date to LocalDate format and immediately updating the date picker display.
     * The method supports both initial data loading and dynamic updates throughout
     * passenger management workflows including booking modification and administrative operations.
     * </p>
     *
     * @param passengerDate the birthdate to set for the passenger in SQL Date format
     */
    public void setPassengerDate (Date passengerDate) {
        passengerDatePicker.setDate(passengerDate.toLocalDate());
    }
}