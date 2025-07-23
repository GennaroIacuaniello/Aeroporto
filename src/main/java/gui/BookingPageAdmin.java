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
 * <p>
 * Passenger management includes comprehensive display of all confirmed bookings associated
 * with the flight, providing administrators with complete oversight of passenger information,
 * seat assignments, luggage details, and booking status. The interface displays passengers
 * in read-only mode to prevent accidental modifications while maintaining full visibility.
 * </p>
 * <p>
 * Administrative controls are organized in a dedicated confirmation panel that houses
 * specialized buttons for flight status management, check-in operations, and delay
 * configuration. Each control integrates with appropriate validation and feedback
 * mechanisms to ensure operational integrity.
 * </p>
 * <p>
 * Flight status management leverages the {@link StatusChooser} component to provide
 * administrators with intuitive status selection capabilities. The status chooser
 * integrates seamlessly with the flight management system to ensure proper status
 * updates and operational continuity.
 * </p>
 * <p>
 * Check-in operations include sophisticated flight status validation to ensure that
 * check-in procedures are only initiated when appropriate. The system validates flight
 * status and operational readiness before transitioning to the dedicated check-in interface.
 * </p>
 * <p>
 * Delay management provides administrators with the ability to configure flight delays
 * through direct input with comprehensive validation. The system includes proper error
 * handling for invalid delay values and provides immediate feedback on delay configuration
 * success or failure.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through the
 * {@link Controller} interface, ensuring proper data persistence, operational coordination,
 * and system state management throughout administrative operations.
 * </p>
 * <p>
 * Visual design maintains consistency with the overall application styling while providing
 * specialized administrative interface elements. Error messaging and operational feedback
 * are provided through {@link FloatingMessage} components for clear administrative
 * communication and operational status updates.
 * </p>
 * <p>
 * Resource management includes enhanced cleanup procedures for administrative components
 * including status chooser dialogs and passenger-related resources. The disposal process
 * ensures proper cleanup of all administrative interface elements and associated resources.
 * </p>
 * <p>
 * The administrative interface supports navigation integration while maintaining
 * administrative context and operational state. Integration with the calling objects
 * hierarchy ensures proper resource management and seamless transitions between
 * administrative operations.
 * </p>
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
     *   <li>Setting up immediate visibility for administrative interaction readiness</li>
     * </ul>
     * <p>
     * The constructor leverages the parent class initialization to establish the core
     * booking interface structure while adding specialized administrative functionality
     * including status management controls, check-in operations, and delay configuration
     * capabilities.
     * </p>
     * <p>
     * Administrative control integration includes the creation and configuration of
     * specialized control panels that house flight status management, check-in
     * initiation, and delay configuration controls. These controls are positioned
     * and configured for optimal administrative workflow efficiency.
     * </p>
     * <p>
     * Window management ensures proper integration with the calling objects hierarchy
     * for seamless navigation and resource management throughout administrative
     * operations. The interface becomes immediately visible and ready for
     * administrative interaction upon construction completion.
     * </p>
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
     * <p>
     * Booking confirmation filtering ensures that only confirmed reservations are displayed,
     * providing administrators with operationally relevant passenger information while
     * excluding pending or cancelled bookings that do not require immediate operational
     * attention.
     * </p>
     * <p>
     * Passenger information extraction includes comprehensive data retrieval covering
     * personal identification details (name, surname, tax code), operational information
     * (seat assignments, ticket numbers), temporal data (birth dates), and luggage
     * management details (types, tickets, status) for complete administrative oversight.
     * </p>
     * <p>
     * Luggage information integration provides detailed luggage management data including
     * luggage types, associated tickets, and current status for each passenger. This
     * comprehensive luggage data supports operational procedures including check-in
     * and boarding operations.
     * </p>
     * <p>
     * Panel configuration includes setting passenger panels to disabled state to prevent
     * accidental modifications while maintaining full visibility of passenger information.
     * This read-only configuration ensures data integrity while providing complete
     * administrative oversight capabilities.
     * </p>
     * <p>
     * The method integrates seamlessly with the inherited pagination system to manage
     * large numbers of passengers efficiently, ensuring optimal administrative interface
     * performance and usability across different passenger volumes.
     * </p>
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
     *   <li><strong>Structured Layout:</strong> Grid-based organization for optimal control placement and workflow efficiency</li>
     *   <li><strong>Visual Consistency:</strong> Transparent background maintaining application design coherence</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for operational readiness</li>
     * </ul>
     * <p>
     * The panel serves as the primary administrative control center for flight operations,
     * providing operators with centralized access to essential flight management capabilities
     * including status modification, check-in workflow initiation, and operational delay
     * configuration within a unified interface.
     * </p>
     * <p>
     * Control organization follows operational workflow patterns with logical grouping
     * of related functions. Status management controls are positioned for immediate
     * access, check-in operations are centrally located for operational efficiency,
     * and delay configuration controls are positioned for administrative convenience.
     * </p>
     * <p>
     * Layout management uses {@link GridBagLayout} to provide precise control over
     * button positioning and sizing, ensuring optimal administrative experience across
     * different screen sizes and operational configurations. The layout accommodates
     * various control types including buttons, input fields, and specialized components.
     * </p>
     * <p>
     * The method coordinates the creation of specialized administrative controls through
     * dedicated methods, ensuring proper separation of concerns and maintainable
     * administrative interface configuration management.
     * </p>
     * <p>
     * Panel integration with the main frame ensures proper positioning within the
     * overall interface hierarchy while maintaining administrative control accessibility
     * and operational workflow efficiency.
     * </p>
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
     * The status change button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Status Chooser Integration:</strong> Direct integration with StatusChooser component for intuitive status selection</li>
     *   <li><strong>Administrative Access:</strong> Provides administrators with comprehensive flight status modification capabilities</li>
     *   <li><strong>System Coordination:</strong> Ensures proper integration with flight management systems for status updates</li>
     *   <li><strong>Interface Management:</strong> Proper component lifecycle management for status chooser dialogs</li>
     *   <li><strong>Visual Consistency:</strong> Maintains application design consistency with proper button styling and positioning</li>
     * </ul>
     * <p>
     * Status chooser integration creates a dedicated {@link StatusChooser} component when
     * the button is activated, providing administrators with an intuitive interface for
     * selecting appropriate flight status values. The chooser component integrates
     * seamlessly with the flight management system for immediate status updates.
     * </p>
     * <p>
     * Component lifecycle management ensures that status chooser dialogs are properly
     * created, displayed, and managed throughout the administrative workflow. The
     * button maintains references to active chooser components for proper resource
     * management and cleanup operations.
     * </p>
     * <p>
     * System coordination includes integration with the controller's flight management
     * capabilities to ensure that status changes are properly validated, applied, and
     * coordinated across the airport management system. This ensures operational
     * consistency and proper status propagation.
     * </p>
     * <p>
     * Visual integration includes proper button styling consistent with administrative
     * interface design patterns. The button is configured as non-focusable to maintain
     * clean interface behavior and positioned appropriately within the confirmation
     * panel layout structure.
     * </p>
     * <p>
     * Navigation integration includes passing the calling objects hierarchy to the
     * status chooser component, ensuring proper resource management and navigation
     * state maintenance throughout status modification operations.
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
     * <p>
     * Flight status validation ensures that check-in procedures are only initiated when
     * the flight status permits such operations. The validation integrates with the
     * flight management system to verify operational readiness and prevent invalid
     * check-in attempts that could disrupt operational workflows.
     * </p>
     * <p>
     * Workflow integration includes automatic transition to the dedicated {@link CheckinPassengers}
     * interface when validation succeeds. The transition maintains proper application
     * state including window positioning, sizing, and navigation hierarchy preservation
     * for seamless operational continuity.
     * </p>
     * <p>
     * Resource management includes proper cleanup of passenger panel components during
     * check-in transition, ensuring that associated resources such as luggage views
     * are properly hidden or disposed to prevent interface conflicts and resource leaks.
     * </p>
     * <p>
     * The button is configured with appropriate styling and positioning within the
     * administrative control panel for optimal operational access and workflow efficiency.
     * Visual consistency is maintained with other administrative controls while ensuring
     * prominent visibility for this critical operational function.
     * </p>
     * <p>
     * Error feedback integration provides administrators with clear information about
     * why check-in operations may not be available, helping them understand operational
     * constraints and take appropriate actions to resolve any issues.
     * </p>
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
     * <p>
     * Flight status validation includes checking for "PROGRAMMED" status with additional
     * check-in system activation validation, and "ABOUT_TO_DEPART" status for immediate
     * check-in capability. Each status condition includes appropriate validation logic
     * to ensure operational integrity and prevent invalid check-in attempts.
     * </p>
     * <p>
     * Check-in system activation for "PROGRAMMED" flights includes validation of
     * operational readiness through the flight controller's check-in start functionality.
     * This ensures that all system preconditions are met before check-in procedures
     * are initiated, including date validation and operational status verification.
     * </p>
     * <p>
     * Interface transition includes creating a new {@link CheckinPassengers} instance
     * with proper window configuration, position inheritance, and navigation hierarchy
     * preservation. The transition maintains application state consistency while
     * providing administrators with specialized check-in management capabilities.
     * </p>
     * <p>
     * Resource cleanup includes proper management of existing passenger panel components,
     * particularly luggage view windows that must be hidden or disposed during check-in
     * transitions to prevent interface conflicts and ensure optimal check-in interface
     * performance.
     * </p>
     * <p>
     * Error handling provides specific feedback for different failure scenarios including
     * check-in system activation failures and inappropriate flight status conditions.
     * Error messages are displayed through {@link FloatingMessage} components with
     * specific information about why check-in cannot be initiated.
     * </p>
     * <p>
     * Window state management includes hiding the current administrative interface
     * during check-in operations to prevent interface conflicts while maintaining
     * proper application state for potential return navigation.
     * </p>
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
     *   <li><strong>Layout Organization:</strong> Proper grouping of delay controls in a dedicated panel for workflow efficiency</li>
     *   <li><strong>Visual Consistency:</strong> Maintains application design patterns with proper control styling</li>
     *   <li><strong>Operational Feedback:</strong> Integration with feedback mechanisms for delay configuration results</li>
     * </ul>
     * <p>
     * Input field configuration includes appropriate sizing for typical delay values
     * while maintaining visual consistency with other administrative controls. The
     * field accepts delay values in minutes, providing administrators with precise
     * control over delay configuration.
     * </p>
     * <p>
     * Validation integration connects the delay configuration button to comprehensive
     * delay validation and application logic through the {@link #addDelay(Controller)}
     * method. This ensures that delay values are properly validated, processed, and
     * applied to the flight management system.
     * </p>
     * <p>
     * Layout organization groups the delay button and input field in a dedicated
     * panel with centered flow layout for optimal visual organization and operational
     * efficiency. The grouping provides clear association between the input field
     * and action button while maintaining interface clarity.
     * </p>
     * <p>
     * Control positioning within the administrative confirmation panel ensures
     * appropriate placement relative to other administrative controls while
     * maintaining workflow efficiency and visual balance throughout the
     * administrative interface.
     * </p>
     * <p>
     * Button configuration includes proper event handling, styling consistency,
     * and focus management to ensure optimal administrative interaction patterns
     * and interface behavior consistency with other administrative controls.
     * </p>
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
     * <p>
     * Input validation includes trimming whitespace from the delay input field and
     * attempting to parse the value as an integer representing delay minutes. The
     * validation process ensures that only valid numeric values are processed while
     * providing clear feedback for invalid input formats.
     * </p>
     * <p>
     * Value processing includes proper integer parsing with exception handling for
     * invalid numeric formats. The processing ensures that delay values are properly
     * formatted and within acceptable ranges for flight delay configuration.
     * </p>
     * <p>
     * System integration leverages the flight controller's delay addition functionality
     * to apply validated delay values to flight scheduling. The integration includes
     * result checking to verify successful delay application and provide appropriate
     * feedback based on system response.
     * </p>
     * <p>
     * Success feedback provides administrators with clear confirmation when delay
     * values are successfully applied to the flight schedule. The feedback uses
     * {@link FloatingMessage} components with success messaging to provide immediate
     * visual confirmation of successful delay configuration.
     * </p>
     * <p>
     * Error handling includes comprehensive exception management for invalid input
     * formats and system integration failures. Different error scenarios receive
     * specific error messages to help administrators understand and resolve any
     * issues with delay configuration.
     * </p>
     * <p>
     * Logging integration ensures that error conditions are properly recorded in
     * the system log through the {@link Controller#getLogger()} mechanism, providing
     * system administrators with detailed error information for troubleshooting
     * and system monitoring purposes.
     * </p>
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
     *   <li><strong>Control State Reset:</strong> Proper reset of administrative control states for potential reuse</li>
     * </ul>
     * <p>
     * Controller resource management includes conditional cleanup of flight and booking
     * data based on the controller disposal flag. When enabled, this ensures that
     * controller state is properly reset to prevent memory leaks and state conflicts
     * in scenarios where controllers may be shared across multiple interface components.
     * </p>
     * <p>
     * Passenger panel cleanup includes comprehensive disposal of associated components
     * such as seat chooser dialogs and luggage view windows for all passenger panels
     * managed by the administrative interface. This prevents resource leaks from
     * sub-components that may have independent lifecycles and resource requirements.
     * </p>
     * <p>
     * Administrative component cleanup includes specialized handling for status chooser
     * dialogs and other administrative-specific components. The cleanup process ensures
     * that status chooser dialogs are properly disposed and that administrative controls
     * are reset to appropriate states for potential interface reuse.
     * </p>
     * <p>
     * Control state reset includes re-enabling administrative controls such as the
     * status button and ensuring that associated dialog components are properly
     * disposed. This cleanup ensures that administrative interface components are
     * left in a clean state that supports proper resource reclamation.
     * </p>
     * <p>
     * The disposal process is designed to be safe for multiple invocations and
     * handles null references gracefully to prevent exceptions during cleanup
     * operations. This ensures robust cleanup behavior regardless of the current
     * state of administrative interface components.
     * </p>
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