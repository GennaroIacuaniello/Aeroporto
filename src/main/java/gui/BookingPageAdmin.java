package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * Administrative booking management interface for flight operations and passenger oversight.
 * <p>
 * This class extends {@link BookingPage} to provide specialized administrative functionality
 * for managing flight bookings within the airport management system. It offers comprehensive
 * administrative controls including flight status management, check-in operations, delay
 * configuration, and complete passenger oversight capabilities for confirmed bookings.
 * </p>
 * <p>
 * The BookingPageAdmin class supports comprehensive administrative operations including:
 * </p>
 * <ul>
 *   <li><strong>Flight Status Management:</strong> Real-time flight status modification through dedicated status chooser interface</li>
 *   <li><strong>Check-in Operations:</strong> Passenger check-in workflow initiation with flight status validation</li>
 *   <li><strong>Delay Management:</strong> Flight delay configuration with input validation and system integration</li>
 *   <li><strong>Passenger Oversight:</strong> Complete passenger information display for all confirmed bookings on the flight</li>
 *   <li><strong>Luggage Management:</strong> Comprehensive luggage information display and status tracking</li>
 *   <li><strong>Administrative Controls:</strong> Specialized control panels for flight operations management</li>
 *   <li><strong>Resource Management:</strong> Advanced resource cleanup and disposal for administrative components</li>
 * </ul>
 * <p>
 * The interface is designed with administrative workflow optimization allowing operators to:
 * </p>
 * <ul>
 *   <li><strong>Monitor Flight Operations:</strong> Comprehensive view of all confirmed passenger bookings</li>
 *   <li><strong>Manage Flight Status:</strong> Dynamic flight status updates through specialized chooser interfaces</li>
 *   <li><strong>Initiate Check-in Procedures:</strong> Passenger check-in workflow with status-aware validation</li>
 *   <li><strong>Configure Flight Delays:</strong> Real-time delay configuration with immediate system updates</li>
 *   <li><strong>Oversee Passenger Information:</strong> Complete passenger data display including personal details and luggage</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see BookingPage
 * @see Controller
 * @see StatusChooser
 * @see CheckinPassengers
 * @see PassengerPanel
 * @see FloatingMessage
 * @see DisposableObject
 */
public class BookingPageAdmin extends BookingPage {

    /**
     * Confirmation panel container for administrative control buttons and operations.
     * <p>
     * This panel houses all administrative control buttons including flight status
     * management, check-in operations, and delay configuration controls. The panel
     * uses GridBagLayout for precise control positioning and optimal administrative
     * workflow organization.
     * </p>
     */
    protected JPanel confirmPanel;

        /**
         * Button for initiating flight status modification operations.
         * <p>
         * This button triggers the status chooser interface, allowing administrators
         * to modify flight operational status through a dedicated selection dialog.
         * The button integrates with the StatusChooser component for comprehensive
         * status management capabilities.
         * </p>
         */
        protected JButton statusButton;
        
        /**
         * Status chooser dialog for flight status modification.
         * <p>
         * This component provides administrators with an intuitive interface for
         * selecting and applying flight status changes. The chooser integrates
         * with the flight management system to ensure proper status updates
         * and operational continuity.
         * </p>
         */
        protected StatusChooser statusChooser;

        /**
         * Button for initiating passenger check-in procedures.
         * <p>
         * This button triggers the check-in workflow validation and interface
         * transition. The button includes comprehensive flight status validation
         * to ensure check-in procedures are only initiated when operationally
         * appropriate.
         * </p>
         */
        protected JButton checkinButton;

        /**
         * Button for configuring flight delay parameters.
         * <p>
         * This button triggers delay configuration processing based on administrator
         * input in the associated delay text field. The button integrates with
         * validation and feedback mechanisms to ensure proper delay configuration.
         * </p>
         */
        protected JButton setDelayButton;
        
        /**
         * Text field for delay value input by administrators.
         * <p>
         * This input field accepts delay values in minutes for flight delay
         * configuration. The field integrates with validation processing to
         * ensure only valid delay values are accepted and processed by the system.
         * </p>
         */
        protected JTextField delayTextField;

    /**
     * Constructs a new BookingPageAdmin interface for administrative flight management.
     * <p>
     * This constructor initializes the complete administrative booking interface by extending
     * the {@link BookingPage} functionality and configuring it specifically for administrative
     * flight management workflows. The constructor establishes the main application window
     * with proper sizing, positioning, and integration with administrative control systems.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li>Inheriting comprehensive booking interface capabilities from the parent class</li>
     *   <li>Establishing window properties optimized for administrative operations</li>
     *   <li>Integrating with the application's disposable object management system</li>
     *   <li>Configuring specialized administrative control panels and interfaces</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight management, administrative operations, and data persistence
     * @param dimension the preferred window size for the administrative booking interface
     * @param point the screen position where the administrative booking window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     */
    public BookingPageAdmin(List<DisposableObject> callingObjects, Controller controller,
                            Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        addConfirmPanel(callingObjects, controller);

        mainFrame.setVisible(true);
    }

    /**
     * Populates the administrative interface with comprehensive passenger information from all confirmed bookings.
     * <p>
     * This method implements the abstract passenger insertion functionality by loading and displaying
     * passenger information from all confirmed bookings associated with the current flight. The method
     * provides administrators with complete oversight of passenger data, including personal information,
     * seat assignments, luggage details, and booking status for operational management purposes.
     * </p>
     * <p>
     * The passenger population process includes:
     * </p>
     * <ul>
     *   <li><strong>Booking Iteration:</strong> Systematic processing of all flight bookings</li>
     *   <li><strong>Confirmation Filtering:</strong> Display only confirmed bookings for operational relevance</li>
     *   <li><strong>Comprehensive Data Loading:</strong> Complete passenger information extraction and display</li>
     *   <li><strong>Luggage Integration:</strong> Detailed luggage information including types, tickets, and status</li>
     *   <li><strong>Panel Configuration:</strong> Proper passenger panel setup and read-only configuration</li>
     * </ul>
     *
     * @param controller the system controller providing access to flight booking data, passenger information, and luggage management
     */
    @Override
    protected void insertPassengers(Controller controller) {

        for (int j = 0; j < controller.getFlightController().getBookingsSize(); j++) {

            if (controller.getFlightController().checkBookingConfirm(j)) {

                for (int i = 0; i < controller.getFlightController().getBookingSize(j); i++) {
                    PassengerPanel passengerPanel = new PassengerPanel(controller, passengerPanels, bookedSeats);

                    String string = controller.getFlightController().getPassengerNameFromBooking(j, i);
                    if (string != null) passengerPanel.setPassengerName(string);

                    string = controller.getFlightController().getPassengerTicketNumberFromBooking(j, i);
                    if (string != null) passengerPanel.setPassengerSurname(string);

                    string = controller.getFlightController().getPassengerCFFromBooking(j, i);
                    if (string != null) passengerPanel.setPassengerCF(string);

                    int seat = controller.getFlightController().getPassengerSeatFromBooking(j, i);
                    if (seat != -1) passengerPanel.setSeat(seat);

                    Date date = controller.getFlightController().getPassengerDateFromBooking(j, i);
                    if (date != null) passengerPanel.setPassengerDate(date);

                    string = controller.getFlightController().getPassengerTicketNumberFromBooking(j, i);
                    if (string != null) passengerPanel.setTicketNumber(string);

                    passengerPanel.setLuggages(controller.getFlightController().getPassengerLuggagesTypesFromBooking(j, i),
                            controller.getFlightController().getPassengerLuggagesTicketsFromBooking(j, i), controller.getFlightController().getPassengerLuggagesStatusFromBooking(j, i), controller);

                    insertPassengerPanel(passengerPanel);

                    passengerPanel.setPanelEnabled(false);
                }
            }
        }
    }

    /**
     * Creates and configures the administrative confirmation panel with specialized control buttons.
     * <p>
     * This method establishes the comprehensive administrative control interface that provides
     * flight operators with specialized controls for flight status management, check-in operations,
     * and delay configuration. The panel uses a structured grid layout to organize administrative
     * controls in an intuitive and efficient manner for operational workflow optimization.
     * </p>
     * <p>
     * The administrative confirmation panel includes:
     * </p>
     * <ul>
     *   <li><strong>Flight Status Controls:</strong> Buttons and interfaces for flight status modification</li>
     *   <li><strong>Check-in Operations:</strong> Controls for initiating passenger check-in procedures</li>
     *   <li><strong>Delay Management:</strong> Input fields and controls for flight delay configuration</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation and resource management
     * @param controller the system controller providing access to flight operations, status management, and administrative capabilities
     */
    @Override
    protected void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        setChangeStatusButton(controller, callingObjects);
        setCheckinButton(callingObjects, controller);
        setSetDelayButton(controller);

        constraints.setConstraints(0, 3, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getGridBagConstraints());

        confirmPanel.setVisible(true);
    }

    /**
     * Creates and configures the flight status change button with status chooser integration.
     * <p>
     * This method establishes the flight status modification control that enables administrators
     * to change flight operational status through a dedicated status chooser interface. The button
     * provides immediate access to comprehensive status management capabilities with integrated
     * validation and system coordination for operational continuity.
     * </p>
     * <p>
     * Status chooser integration creates a dedicated {@link StatusChooser} component when
     * the button is activated, providing administrators with an intuitive interface for
     * selecting appropriate flight status values. The chooser component integrates
     * seamlessly with the flight management system for immediate status updates.
     * </p>
     *
     * @param controller the system controller providing access to flight status management and system coordination capabilities
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     */
    protected void setChangeStatusButton (Controller controller, List<DisposableObject> callingObjects) {

        statusButton = new JButton("CAMBIA STATO VOLO");

        statusButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                statusChooser = new StatusChooser(controller, statusButton, callingObjects);
            }
        });

        statusButton.setFocusable(false);
        statusButton.setVisible(true);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(statusButton, constraints.getGridBagConstraints());
    }

    /**
     * Creates and configures the passenger check-in button with workflow validation integration.
     * <p>
     * This method establishes the check-in initiation control that enables administrators
     * to begin passenger check-in procedures with comprehensive flight status validation.
     * The button integrates sophisticated validation logic to ensure check-in operations
     * are only initiated when flight status and operational conditions are appropriate.
     * </p>
     * <p>
     * The check-in button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Status Validation:</strong> Comprehensive flight status checking before check-in initiation</li>
     *   <li><strong>Workflow Integration:</strong> Seamless transition to dedicated check-in interface upon validation success</li>
     *   <li><strong>Error Prevention:</strong> Prevents inappropriate check-in initiation through status validation</li>
     *   <li><strong>Resource Management:</strong> Proper cleanup and state management during check-in transitions</li>
     *   <li><strong>User Feedback:</strong> Clear feedback for both successful and unsuccessful check-in attempts</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     * @param controller the system controller providing access to flight status validation and check-in operations
     */
    private void setCheckinButton (List<DisposableObject> callingObjects, Controller controller) {

        checkinButton = new JButton("CHECKIN");

        checkinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                checkCheckinButton(callingObjects, controller);
            }
        });

        checkinButton.setFocusable(false);
        checkinButton.setVisible(true);

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(checkinButton, constraints.getGridBagConstraints());
    }

    /**
     * Validates flight status and initiates passenger check-in procedures with comprehensive workflow management.
     * <p>
     * This method performs sophisticated flight status validation before initiating passenger
     * check-in procedures, ensuring that check-in operations are only conducted when flight
     * status and operational conditions are appropriate. The method integrates comprehensive
     * validation logic with seamless transition to dedicated check-in interfaces.
     * </p>
     * <p>
     * The check-in validation and initiation process includes:
     * </p>
     * <ul>
     *   <li><strong>Flight Status Validation:</strong> Comprehensive checking of flight operational status</li>
     *   <li><strong>Check-in Workflow Initiation:</strong> Proper check-in system activation with validation</li>
     *   <li><strong>Interface Transition:</strong> Seamless transition to dedicated check-in management interface</li>
     *   <li><strong>Resource Management:</strong> Proper cleanup and state management during transitions</li>
     *   <li><strong>Error Handling:</strong> Clear feedback for invalid check-in attempts with specific error messaging</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow and resource management
     * @param controller the system controller providing access to flight status validation, check-in system activation, and operational coordination
     */
    protected void checkCheckinButton (List<DisposableObject> callingObjects, Controller controller) {

        if (controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("PROGRAMMED")) {

            if (controller.getFlightController().startCheckin() == 1) {

                new CheckinPassengers(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

                for (PassengerPanel passengerPanel : passengerPanels) {

                    if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
                }

                mainFrame.setVisible(false);

            } else new FloatingMessage("Lo stato del volo è rimasto invariato (controlla la data di partenza)", checkinButton, FloatingMessage.ERROR_MESSAGE);

        } else if (controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("ABOUT_TO_DEPART")) {

            new CheckinPassengers(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

            for (PassengerPanel passengerPanel : passengerPanels) {

                if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
            }

            mainFrame.setVisible(false);
        } else new FloatingMessage("Non è possibile effettuare check-in per un volo in sato: " + controller.getFlightController().getFlightStatus(), checkinButton, FloatingMessage.ERROR_MESSAGE);
    }

    /**
     * Creates and configures the flight delay configuration button with input field integration.
     * <p>
     * This method establishes the delay management control that enables administrators to
     * configure flight delays through direct input with comprehensive validation and system
     * integration. The control includes both a button for delay application and an input
     * field for delay value specification, organized in an intuitive layout for efficient
     * administrative operation.
     * </p>
     * <p>
     * The delay configuration control functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Input Field Integration:</strong> Dedicated text field for delay value entry in minutes</li>
     *   <li><strong>Validation Integration:</strong> Connection to comprehensive delay validation and application logic</li>
     *   <li><strong>Operational Feedback:</strong> Integration with feedback mechanisms for delay configuration results</li>
     * </ul>
     *
     * @param controller the system controller providing access to delay management and flight operations coordination
     */
    protected void setSetDelayButton (Controller controller) {

        setDelayButton = new JButton("SET DELAY");
        delayTextField = new JTextField(15);

        setDelayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                addDelay(controller);
            }
        });

        setDelayButton.setFocusable(false);
        setDelayButton.setEnabled(true);

        JPanel delayPanel = new JPanel();
        delayPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        delayPanel.add(setDelayButton);
        delayPanel.add(delayTextField);

        constraints.setConstraints(2, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(delayPanel, constraints.getGridBagConstraints());
    }

    /**
     * Processes flight delay configuration with comprehensive validation and system integration.
     * <p>
     * This method handles the complete delay configuration process including input validation,
     * delay value processing, system integration, and feedback provision. The method ensures
     * that delay values are properly validated, formatted, and applied to the flight management
     * system with appropriate error handling and user feedback throughout the process.
     * </p>
     * <p>
     * The delay configuration process includes:
     * </p>
     * <ul>
     *   <li><strong>Input Validation:</strong> Comprehensive validation of delay input including format and value checking</li>
     *   <li><strong>Value Processing:</strong> Proper parsing and formatting of delay values for system integration</li>
     *   <li><strong>System Integration:</strong> Application of validated delay values through flight controller</li>
     *   <li><strong>Success Feedback:</strong> Clear confirmation messaging for successful delay configuration</li>
     *   <li><strong>Error Handling:</strong> Comprehensive error handling with specific error messaging for different failure scenarios</li>
     * </ul>
     *
     * @param controller the system controller providing access to flight delay management and logging capabilities
     */
    protected void addDelay(Controller controller) {

        try {

            /*//delayTextField.setText("20");

            System.out.println("empty" + delayTextField.getText().isEmpty());

            System.out.println("lenght: " + delayTextField.getText().length());

            System.out.println("delay: " + delayTextField.getText());

            System.out.println("delay: " + delayTextField.getText().trim());*/

            int delay = Integer.parseInt(delayTextField.getText().trim());

            //System.out.println("delay: " + delay);

            if (controller.getFlightController().addDelay(delay) == 1)
                new FloatingMessage("Ritardo settato correttamente", setDelayButton, FloatingMessage.SUCCESS_MESSAGE);
            else new FloatingMessage("Il ritardo non è stato settato correttamente", setDelayButton, FloatingMessage.ERROR_MESSAGE);

        } catch (NumberFormatException e) {
            Controller.getLogger().log(Level.SEVERE, e.getMessage());
            new FloatingMessage("Ritardo non valido", setDelayButton, FloatingMessage.ERROR_MESSAGE);
        }
    }

    /**
     * Performs comprehensive resource cleanup and disposal operations for administrative components.
     * <p>
     * This method implements enhanced disposal functionality by extending the parent class
     * disposal operations with specialized cleanup for administrative interface components.
     * The disposal process ensures that all administrative resources including status chooser
     * dialogs, passenger panel components, and specialized administrative controls are
     * properly cleaned up to prevent resource leaks and ensure optimal system performance.
     * </p>
     * <p>
     * The enhanced disposal operations include:
     * </p>
     * <ul>
     *   <li><strong>Controller Resource Management:</strong> Conditional cleanup of flight and booking controller data based on disposal flag</li>
     *   <li><strong>Passenger Panel Cleanup:</strong> Comprehensive disposal of seat choosers and luggage views for all passenger panels</li>
     *   <li><strong>Administrative Component Cleanup:</strong> Specialized cleanup for status chooser dialogs and administrative controls</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper cleanup coordination
     * @param controller the system controller for resource management and state cleanup coordination
     */
    @Override
    public void doOnDispose (List<DisposableObject> callingObjects, Controller controller) {

        if (controllerDisposeFlag) {

            controller.getFlightController().setFlight(null);
            controller.getBookingController().setBooking(null);
        }

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.getSeatChooser() != null) passengerPanel.getSeatChooser().dispose();

            if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().dispose();
        }

        statusButton.setEnabled(true);
        if (statusChooser != null) statusChooser.getMainFrame().dispose();
    }
}