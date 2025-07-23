package gui;

import controller.Controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

/**
 * Customer-focused booking management interface for existing reservation display and modification operations.
 * <p>
 * This class extends {@link BookingPage} to provide specialized functionality for customer booking
 * management within the airport management system. It offers comprehensive customer-specific controls
 * including booking modification initiation, booking cancellation capabilities, and complete passenger
 * information display for existing confirmed and pending reservations.
 * </p>
 * <p>
 * The BookingPageCustomer class supports comprehensive customer booking operations including:
 * </p>
 * <ul>
 *   <li><strong>Booking Display:</strong> Complete passenger information display from existing booking data</li>
 *   <li><strong>Booking Modification:</strong> Initiation of booking modification workflows with status validation</li>
 *   <li><strong>Booking Cancellation:</strong> Secure booking deletion with comprehensive status checking</li>
 *   <li><strong>Status Validation:</strong> Flight and booking status verification for operation authorization</li>
 *   <li><strong>Passenger Management:</strong> Complete passenger data loading and display from booking controller</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with application navigation and workflow management</li>
 *   <li><strong>Resource Management:</strong> Proper cleanup and state management for customer operations</li>
 * </ul>
 * <p>
 * The interface is designed with customer workflow optimization allowing customers to:
 * </p>
 * <ul>
 *   <li><strong>View Booking Details:</strong> Comprehensive display of all passenger information and booking status</li>
 *   <li><strong>Modify Reservations:</strong> Initiate booking modification workflows when operationally appropriate</li>
 *   <li><strong>Cancel Bookings:</strong> Delete reservations with proper validation and confirmation</li>
 *   <li><strong>Navigate Seamlessly:</strong> Return to previous application states after operations completion</li>
 * </ul>
 * <p>
 * Passenger information display includes comprehensive loading of all booking-associated passenger
 * data including personal information, seat assignments, luggage details, and booking metadata.
 * The interface presents passengers in paginated format for optimal usability with large bookings.
 * </p>
 * <p>
 * Booking modification functionality includes sophisticated status validation to ensure modifications
 * are only initiated when flight and booking status permit such operations. The system validates
 * both flight operational status and booking confirmation status before allowing modification workflows.
 * </p>
 * <p>
 * Booking cancellation provides secure deletion capabilities with comprehensive validation to prevent
 * inappropriate cancellations. The system ensures that only programmed flights with confirmed or
 * pending bookings can be cancelled by customers.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader booking management system through the {@link Controller}
 * interface, ensuring proper data access, operation validation, and state management throughout
 * customer booking operations.
 * </p>
 * <p>
 * Visual design maintains consistency with the overall application styling while providing
 * customer-specific interface elements. Error messaging and operational feedback are provided
 * through {@link FloatingMessage} components for clear customer communication.
 * </p>
 * <p>
 * Resource management includes proper cleanup of passenger-related components during navigation
 * transitions, ensuring optimal interface performance and preventing resource leaks during
 * customer workflow operations.
 * </p>
 * <p>
 * The customer booking interface supports navigation integration while maintaining booking
 * context and operational state. Integration with the calling objects hierarchy ensures
 * proper resource management and seamless transitions between customer operations.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see BookingPage
 * @see Controller
 * @see BookingModifyPage
 * @see PassengerPanel
 * @see FloatingMessage
 * @see DisposableObject
 */
public class BookingPageCustomer extends BookingPage {

    /**
     * Confirmation panel container for customer booking operation controls.
     * <p>
     * This panel houses customer-specific action buttons including booking
     * modification and cancellation controls. The panel uses FlowLayout
     * with right alignment for optimal customer interface organization.
     * </p>
     */
    protected JPanel confirmPanel;

        /**
         * Button for initiating booking modification operations.
         * <p>
         * This button triggers the booking modification workflow validation
         * and interface transition. The button includes comprehensive flight
         * and booking status validation to ensure modifications are only
         * initiated when operationally appropriate.
         * </p>
         */
        protected JButton modifyButton;
        
        /**
         * Button for initiating booking cancellation operations.
         * <p>
         * This button triggers secure booking deletion with comprehensive
         * status validation. The button ensures that only appropriate
         * bookings can be cancelled by customers while maintaining
         * operational integrity.
         * </p>
         */
        protected JButton cancelButton;

    /**
     * Constructs a new BookingPageCustomer interface for customer booking management.
     * <p>
     * This constructor initializes the complete customer booking interface by extending
     * the {@link BookingPage} functionality and configuring it specifically for customer
     * booking management workflows. The constructor establishes the main application window
     * with proper sizing, positioning, and integration with customer-specific controls.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li>Inheriting comprehensive booking interface capabilities from the parent class</li>
     *   <li>Establishing window properties optimized for customer operations</li>
     *   <li>Integrating with the application's disposable object management system</li>
     *   <li>Configuring customer-specific confirmation panel with modification and cancellation controls</li>
     *   <li>Setting up immediate visibility for customer interaction readiness</li>
     * </ul>
     * <p>
     * The constructor leverages the parent class initialization to establish the core
     * booking interface structure while adding customer-specific functionality including
     * booking modification initiation and secure cancellation capabilities.
     * </p>
     * <p>
     * Customer control integration includes the creation and configuration of the
     * confirmation panel that houses booking modification and cancellation buttons.
     * These controls are positioned and configured for optimal customer workflow
     * efficiency and operational safety.
     * </p>
     * <p>
     * Window management ensures proper integration with the calling objects hierarchy
     * for seamless navigation and resource management throughout customer operations.
     * The interface becomes immediately visible and ready for customer interaction
     * upon construction completion.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to booking management, customer operations, and data persistence
     * @param dimension the preferred window size for the customer booking interface
     * @param point the screen position where the customer booking window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     */
    public BookingPageCustomer(List<DisposableObject> callingObjects, Controller controller,
                               Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        addConfirmPanel(callingObjects, controller);

        mainFrame.setVisible(true);
    }

    /**
     * Populates the customer interface with comprehensive passenger information from existing booking data.
     * <p>
     * This method implements the abstract passenger insertion functionality by loading and displaying
     * complete passenger information from the current booking managed by the booking controller.
     * The method provides customers with comprehensive oversight of their reservation data including
     * personal information, seat assignments, luggage details, and booking metadata.
     * </p>
     * <p>
     * The passenger population process includes:
     * </p>
     * <ul>
     *   <li><strong>Ticket Iteration:</strong> Systematic processing of all tickets associated with the current booking</li>
     *   <li><strong>Comprehensive Data Loading:</strong> Complete passenger information extraction and display</li>
     *   <li><strong>Personal Information Integration:</strong> Loading of passenger names, identification, and demographic data</li>
     *   <li><strong>Operational Data Loading:</strong> Integration of seat assignments, ticket numbers, and booking metadata</li>
     *   <li><strong>Luggage Information Display:</strong> Detailed luggage information including types, tickets, and status</li>
     *   <li><strong>Panel Configuration:</strong> Proper passenger panel setup for customer viewing</li>
     * </ul>
     * <p>
     * Personal information extraction includes comprehensive data retrieval covering passenger
     * names (first and last), identification codes (SSN/CF), and birth date information for
     * complete customer visibility into their booking details and passenger information.
     * </p>
     * <p>
     * Operational data loading includes seat assignment information, ticket number display,
     * and booking metadata that enables customers to understand their reservation status
     * and operational details for travel planning and preparation.
     * </p>
     * <p>
     * Luggage information integration provides detailed luggage management data including
     * luggage types, associated tickets, and current status for each passenger. This
     * comprehensive luggage data supports customer travel planning and operational
     * preparation for their flight experience.
     * </p>
     * <p>
     * Panel configuration includes proper passenger panel setup with all loaded information
     * displayed in a user-friendly format that enables customers to review their booking
     * details and make informed decisions about potential modifications or cancellations.
     * </p>
     * <p>
     * The method integrates seamlessly with the inherited pagination system to manage
     * large numbers of passengers efficiently, ensuring optimal customer interface
     * performance and usability across different booking sizes and passenger counts.
     * </p>
     * <p>
     * Data validation ensures that null values are handled gracefully, displaying only
     * available information while maintaining interface stability and preventing display
     * errors that could impact customer experience.
     * </p>
     *
     * @param controller the system controller providing access to booking data, passenger information, and luggage management
     */
    @Override
    protected void insertPassengers (Controller controller) {

        for (int i = 0; i < controller.getBookingController().getTicketsSize(); i++) {

            PassengerPanel passengerPanel = new PassengerPanel(controller, passengerPanels, bookedSeats);

            String string = controller.getBookingController().getPassengerName(i);
            if (string != null) passengerPanel.setPassengerName(string);

            string = controller.getBookingController().getPassengerLastName(i);
            if (string != null) passengerPanel.setPassengerSurname(string);

            string = controller.getBookingController().getPassengerSSN(i);
            if (string != null) passengerPanel.setPassengerCF(string);

            int seat = controller.getBookingController().getPassengerSeat(i);
            if (seat != -1) passengerPanel.setSeat(seat);

            Date date = controller.getBookingController().getPassengerDate(i);
            if(date != null) passengerPanel.setPassengerDate(date);

            string = controller.getBookingController().getPassengerTicketNumber(i);
            if (string != null) passengerPanel.setTicketNumber(string);

            passengerPanel.setLuggages(controller.getBookingController().getPassengerLuggagesTypes(i),
                    controller.getBookingController().getPassengerLuggagesTickets(i), controller.getBookingController().getPassengerLuggagesStatus(i), controller);

            insertPassengerPanel(passengerPanel);
        }
    }

    /**
     * Creates and configures the customer confirmation panel with booking management controls.
     * <p>
     * This method establishes the customer-specific control interface that provides booking
     * modification and cancellation capabilities with comprehensive validation and workflow
     * integration. The panel uses a structured flow layout to organize customer controls
     * in an intuitive and accessible manner for optimal customer experience.
     * </p>
     * <p>
     * The customer confirmation panel includes:
     * </p>
     * <ul>
     *   <li><strong>Booking Modification Control:</strong> Button for initiating booking modification workflows</li>
     *   <li><strong>Booking Cancellation Control:</strong> Button for secure booking deletion operations</li>
     *   <li><strong>Status Validation Integration:</strong> Comprehensive flight and booking status checking</li>
     *   <li><strong>Navigation Management:</strong> Seamless workflow transitions and state management</li>
     *   <li><strong>Error Handling:</strong> Clear feedback for invalid operations and status conflicts</li>
     *   <li><strong>Resource Management:</strong> Proper cleanup during workflow transitions</li>
     * </ul>
     * <p>
     * The panel serves as the primary customer control center for booking operations,
     * providing customers with intuitive access to modification and cancellation capabilities
     * while ensuring that all operations are properly validated and executed within
     * operational constraints and business rules.
     * </p>
     * <p>
     * Booking modification control includes sophisticated validation that checks both flight
     * operational status and booking confirmation status before initiating modification
     * workflows. Successful validation transitions customers to the {@link BookingModifyPage}
     * interface with proper state preservation and resource management.
     * </p>
     * <p>
     * Booking cancellation control provides secure deletion capabilities with comprehensive
     * validation to ensure that only appropriate bookings can be cancelled. Successful
     * validation triggers booking deletion through the controller and returns customers
     * to the previous application state.
     * </p>
     * <p>
     * Layout management uses {@link FlowLayout} with right alignment to provide intuitive
     * control organization that follows conventional interface design patterns for action
     * buttons. The layout ensures proper button spacing and visual hierarchy for optimal
     * customer interaction.
     * </p>
     * <p>
     * Error handling provides specific feedback for validation failures, helping customers
     * understand why certain operations may not be available and providing clear guidance
     * about booking status requirements for modification and cancellation operations.
     * </p>
     * <p>
     * Resource management includes proper cleanup of passenger-related components during
     * workflow transitions, ensuring that luggage view windows are properly hidden and
     * interface resources are managed efficiently during navigation operations.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow and navigation management
     * @param controller the system controller providing access to booking operations, status validation, and workflow coordination
     */
    @Override
    protected void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        confirmPanel.setOpaque(false);

        modifyButton = new JButton("MODIFICA PRENOTAZIONE");
        cancelButton = new JButton("CANCELLA PRENOTAZIONE");

        modifyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                if (Boolean.TRUE.equals(checkFlightNBookingStatus(controller))) {

                    new BookingModifyPage(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

                    mainFrame.setVisible(false);

                    for (PassengerPanel passengerPanel : passengerPanels) {

                        if  (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
                    }
                } else new FloatingMessage("Lo stato del volo o della prenotazione non permettono di modificare questa prenotazione",
                        modifyButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                if (Boolean.TRUE.equals(checkFlightNBookingStatus(controller))) {

                    controller.getBookingController().deleteBooking();

                    controller.goBack(callingObjects);
                } else new FloatingMessage("Lo stato del volo o della prenotazione non permettono di cancellare questa prenotazione",
                        modifyButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        modifyButton.setFocusable(false);
        cancelButton.setFocusable(false);

        confirmPanel.add(modifyButton);
        confirmPanel.add(cancelButton);

        constraints.setConstraints(0, 4, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainPanel.add(confirmPanel, constraints.getGridBagConstraints());
    }

    /**
     * Validates flight and booking status for customer operation authorization.
     * <p>
     * This method performs comprehensive status validation to determine whether customer
     * booking operations (modification or cancellation) are operationally appropriate
     * based on current flight status and booking confirmation state. The validation
     * ensures that customers can only perform operations when business rules and
     * operational constraints permit such actions.
     * </p>
     * <p>
     * The status validation process includes:
     * </p>
     * <ul>
     *   <li><strong>Flight Status Checking:</strong> Validates that the flight is in "PROGRAMMED" operational status</li>
     *   <li><strong>Booking Status Validation:</strong> Ensures booking is either "CONFIRMED" or "PENDING" status</li>
     *   <li><strong>Combined Logic Evaluation:</strong> Applies logical AND operation for comprehensive authorization</li>
     *   <li><strong>Business Rule Enforcement:</strong> Maintains operational integrity and customer service standards</li>
     * </ul>
     * <p>
     * Flight status validation ensures that booking operations are only permitted when
     * the flight is in "PROGRAMMED" status, preventing customer operations on flights
     * that have departed, been cancelled, or are in other non-modifiable operational
     * states that would compromise operational integrity.
     * </p>
     * <p>
     * Booking status validation verifies that the booking is in either "CONFIRMED" or
     * "PENDING" status, ensuring that customers can modify or cancel active reservations
     * while preventing operations on bookings that may have been previously cancelled
     * or are in other non-operational states.
     * </p>
     * <p>
     * Combined logic evaluation uses Boolean AND operation to ensure that both flight
     * and booking status requirements are satisfied before authorizing customer operations.
     * This comprehensive approach prevents operational conflicts and maintains system
     * integrity throughout customer workflow operations.
     * </p>
     * <p>
     * Business rule enforcement ensures that customer operations align with airport
     * operational procedures and industry standards for booking management, preventing
     * customers from performing operations that could impact flight operations or
     * compromise service delivery.
     * </p>
     * <p>
     * The method returns a Boolean value that determines whether customer operations
     * should be permitted, enabling calling methods to provide appropriate feedback
     * and workflow management based on current operational conditions.
     * </p>
     *
     * @param controller the system controller providing access to flight status and booking status information
     * @return true if both flight and booking status permit customer operations, false if operations should be restricted
     */
    protected Boolean checkFlightNBookingStatus (Controller controller) {

        return controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("PROGRAMMED")
                && (controller.getBookingController().getBookingStatus().toString().equalsIgnoreCase("CONFIRMED")
                || controller.getBookingController().getBookingStatus().toString().equalsIgnoreCase("PENDING"));
    }
}