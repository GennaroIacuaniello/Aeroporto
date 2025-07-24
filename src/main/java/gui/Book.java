package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Flight booking interface for new reservation creation with comprehensive passenger management.
 * <p>
 * This class provides a complete user interface for customers to create new flight bookings
 * within the airport management system. It extends {@link BookingModifyPage} to inherit
 * passenger management capabilities while providing specialized functionality for new
 * booking creation workflows.
 * </p>
 * <p>
 * The Book class supports comprehensive booking operations including:
 * </p>
 * <ul>
 *   <li><strong>Dynamic Passenger Addition:</strong> Real-time addition of passengers with seat availability validation</li>
 *   <li><strong>Flexible Booking Status:</strong> Support for both confirmed and pending reservation states</li>
 *   <li><strong>Comprehensive Validation:</strong> Complete passenger data validation before booking confirmation</li>
 *   <li><strong>Seat Management:</strong> Integration with flight capacity management and seat availability checking</li>
 * </ul>
 * <p>
 * The interface is designed with a dual-action approach allowing customers to either:
 * </p>
 * <ul>
 *   <li><strong>Confirm Booking:</strong> Immediately confirm the reservation with complete passenger information</li>
 *   <li><strong>Save Pending:</strong> Save incomplete reservations for later completion and confirmation</li>
 * </ul>
 * <p>
 * Passenger management includes dynamic addition capabilities with real-time seat availability
 * checking to prevent overbooking. The interface validates flight capacity against current
 * passenger count and provides immediate feedback when seat limits are reached.
 * </p>
 * <p>
 * Data validation ensures that all required passenger information is complete before allowing
 * booking confirmation, while providing flexible pending save options for incomplete bookings.
 * This approach accommodates different customer workflows and booking completion patterns.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader booking management system through the
 * {@link Controller} interface, ensuring proper data persistence and application state
 * management throughout the booking creation process.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see BookingModifyPage
 * @see Controller
 * @see PassengerPanel
 * @see FloatingMessage
 * @see DisposableObject
 */
public class Book extends BookingModifyPage {

    /**
     * Constructs a new Book interface for flight reservation creation.
     * <p>
     * This constructor initializes the complete booking creation interface by extending
     * the {@link BookingModifyPage} functionality and configuring it specifically for
     * new booking workflows. The constructor establishes the main application window
     * with proper sizing, positioning, and integration with the calling object hierarchy.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li>Inheriting passenger management capabilities from the parent class</li>
     *   <li>Establishing window properties including size, position, and display state</li>
     *   <li>Integrating with the application's disposable object management system</li>
     *   <li>Configuring the interface for new booking creation workflows</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight management, booking operations, and data persistence
     * @param dimension the preferred window size for the booking interface
     * @param point the screen position where the booking window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     */
    public Book(List<DisposableObject> callingObjects, Controller controller,
                Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        mainFrame.setVisible(true);
    }

    /**
     * Creates and configures the passenger addition button with capacity validation.
     * <p>
     * This method creates a dynamic passenger addition button that allows customers to
     * add additional passengers to their booking while enforcing flight capacity constraints.
     * The button integrates real-time seat availability checking to prevent overbooking
     * and provides immediate user feedback when capacity limits are reached.
     * </p>
     * <p>
     * The button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Capacity Validation:</strong> Real-time checking of available seats against current passenger count</li>
     *   <li><strong>Dynamic Panel Creation:</strong> Automatic creation of new {@link PassengerPanel} instances for additional passengers</li>
     *   <li><strong>Interface Integration:</strong> Seamless integration with the existing passenger management interface</li>
     * </ul>
     * <p>
     * Seat availability calculation compares the flight's total free seats against the
     * current number of passenger panels to determine if additional passengers can be
     * accommodated. This prevents customers from attempting to book more seats than
     * are available on the flight.
     * </p>
     * <p>
     * When capacity allows, the method creates a new {@link PassengerPanel} with proper
     * integration into the existing passenger management system, including seat selection
     * and booking coordination. The new panel inherits all standard passenger input
     * capabilities and validation features.
     * </p>
     * <p>
     * The button is styled consistently with the application design and positioned
     * appropriately within the interface flow panel for intuitive user access during
     * the booking creation process.
     * </p>
     *
     * @param controller the system controller providing access to flight capacity information and passenger management operations
     */
    @Override
    protected void addAddPassengerButton(Controller controller) {

        addPassengerButton = new JButton("AGGIUNGI PASSEGGERO");

        addPassengerButton.setFocusable(false);

        addPassengerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (controller.getFlightController().getFreeSeats() - passengerPanels.size() > 0)
                    insertPassengerPanel(new PassengerPanel(controller, passengerPanels, bookedSeats));
                else
                    new FloatingMessage("Non ci sono altri posti disponibili per questo volo", addPassengerButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        flowPanel.add(addPassengerButton);
    }

    /**
     * Creates and configures the confirmation panel with booking action buttons.
     * <p>
     * This method establishes the main confirmation interface that provides customers
     * with options to either confirm their booking immediately or save it in a pending
     * state for later completion. The panel uses a structured grid layout to organize
     * the action buttons in an intuitive and accessible manner.
     * </p>
     * <p>
     * The panel serves as the primary decision point in the booking creation workflow,
     * allowing customers to choose between immediate booking confirmation with complete
     * passenger information or saving incomplete reservations for future completion.
     * </p>
     * <p>
     * The method coordinates the creation of both confirmation and pending save buttons
     * through dedicated methods, ensuring proper separation of concerns and maintainable
     * button configuration management.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation and resource management
     * @param controller the system controller providing access to booking operations and data persistence capabilities
     */
    @Override
    protected void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        addConfirmButton(controller, callingObjects);
        addSavePendingButton(controller, callingObjects);

        confirmPanel.setVisible (true);
    }

    /**
     * Creates and configures the booking confirmation button with comprehensive validation.
     * <p>
     * This method creates the primary booking confirmation button that allows customers
     * to finalize their flight reservation with full passenger information validation.
     * The button integrates comprehensive data checking to ensure all required passenger
     * information is complete before processing the booking confirmation.
     * </p>
     * <p>
     * The confirmation button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Data Validation:</strong> Comprehensive checking of all passenger information completeness</li>
     *   <li><strong>Booking Creation:</strong> Integration with the controller to create confirmed reservations</li>
     *   <li><strong>Navigation Management:</strong> Automatic return to previous application state after successful confirmation</li>
     *   <li><strong>Error Feedback:</strong> Clear messaging when passenger data is incomplete or invalid</li>
     *   <li><strong>Status Management:</strong> Automatic assignment of "CONFIRMED" status to successful bookings</li>
     * </ul>
     *
     * @param controller the system controller providing access to booking creation operations and validation services
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     */
    @Override
    protected void addConfirmButton (Controller controller, List<DisposableObject> callingObjects) {

        confirmButton = new JButton("CONFERMA PRENOTAZIONE");

        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkConfirmButton()) {
                    controller.addBooking(passengerPanels, "CONFIRMED");
                    controller.goBack(callingObjects);
                } else
                    new FloatingMessage("I dati dei passeggeri sono incompleti", confirmButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        confirmPanel.add (confirmButton, constraints.getGridBagConstraints());

        confirmButton.setFocusable(false);
        confirmButton.setVisible (true);

        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER);
        mainFrame.add (confirmPanel, constraints.getGridBagConstraints());
    }

    /**
     * Creates and configures the pending save button for incomplete booking preservation.
     * <p>
     * This method creates a specialized save button that allows customers to preserve
     * incomplete booking information in a pending state for later completion and
     * confirmation. This functionality accommodates customers who may need to gather
     * additional information or complete the booking process across multiple sessions.
     * </p>
     * <p>
     * The pending save button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Flexible Validation:</strong> Less restrictive validation allowing incomplete but valid partial bookings</li>
     *   <li><strong>Pending Status Assignment:</strong> Automatic assignment of "PENDING" status to preserved reservations</li>
     *   <li><strong>Empty Booking Prevention:</strong> Validation to prevent completely empty bookings from being saved</li>
     *   <li><strong>Navigation Management:</strong> Automatic return to previous application state after successful save</li>
     *   <li><strong>User Feedback:</strong> Clear messaging for both successful saves and invalid empty bookings</li>
     * </ul>
     *
     * @param controller the system controller providing access to booking creation operations and validation services
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     */
    @Override
    protected void addSavePendingButton (Controller controller, List<DisposableObject> callingObjects) {

        savePendingButton = new JButton("SALVA IN ATTESA");

        savePendingButton.setFocusable(false);

        savePendingButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                    if (checkSavePendingButton()) {

                        controller.addBooking(passengerPanels, "PENDING");

                        controller.goBack(callingObjects);

                    } else {
                        new FloatingMessage("Impossibile aggiungere una prenotazione vuota", savePendingButton, FloatingMessage.ERROR_MESSAGE);
                    }
            }
        });

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START, 0.5f, 0.5f);
        modifyPanel.add(savePendingButton, constraints.getGridBagConstraints());

        savePendingButton.setVisible(true);
    }

    /**
     * Initializes the passenger management interface with a single default passenger panel.
     * <p>
     * This method establishes the initial passenger management state for new bookings
     * by creating a single {@link PassengerPanel} that serves as the starting point
     * for customer information entry. The method provides the foundation for the
     * booking creation workflow by ensuring at least one passenger entry interface
     * is immediately available.
     * </p>
     * <p>
     * The created passenger panel includes all standard passenger information entry
     * capabilities including personal details, seat selection, and any required
     * documentation fields. The panel is immediately ready for customer interaction
     * and data entry.
     * </p>
     *
     * @param controller the system controller providing access to passenger management, seat tracking, and booking coordination services
     */
    @Override
    protected void insertPassengers (Controller controller) {

        insertPassengerPanel(new PassengerPanel(controller, passengerPanels, bookedSeats));
    }
}