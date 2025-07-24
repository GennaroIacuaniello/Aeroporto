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
     * </ul>
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
     * </ul>
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