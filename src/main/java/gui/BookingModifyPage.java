package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive booking modification interface for existing reservation management with enhanced passenger controls.
 * <p>
 * This class extends {@link BookingPageCustomer} to provide specialized functionality for modifying existing
 * flight bookings within the airport management system. It offers advanced passenger management capabilities
 * including dynamic passenger addition and removal, comprehensive validation, and flexible booking status
 * management for both confirmed and pending reservations.
 * </p>
 * <p>
 * The BookingModifyPage class supports comprehensive booking modification operations including:
 * </p>
 * <ul>
 *   <li><strong>Dynamic Passenger Management:</strong> Real-time addition and removal of passengers with capacity validation</li>
 *   <li><strong>Enhanced Interface Controls:</strong> Specialized buttons for passenger removal and booking management</li>
 *   <li><strong>Flexible Booking Status:</strong> Support for both confirmed and pending reservation modifications</li>
 *   <li><strong>Comprehensive Validation:</strong> Multi-level validation for passenger data and booking constraints</li>
 *   <li><strong>Seat Management:</strong> Advanced seat availability checking considering existing bookings</li>
 *   <li><strong>User Feedback:</strong> Real-time validation feedback and error messaging through floating messages</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with application navigation and workflow management</li>
 * </ul>
 * <p>
 * The interface is designed with enhanced modification capabilities allowing customers to:
 * </p>
 * <ul>
 *   <li><strong>Modify Existing Bookings:</strong> Update passenger information, seats, and luggage details</li>
 *   <li><strong>Add New Passengers:</strong> Dynamically add passengers to existing bookings with capacity validation</li>
 *   <li><strong>Remove Passengers:</strong> Remove passengers from bookings with proper validation and cleanup</li>
 *   <li><strong>Confirm Changes:</strong> Immediately confirm modifications with complete passenger information</li>
 *   <li><strong>Save Pending:</strong> Save incomplete modifications for later completion and confirmation</li>
 * </ul>
 * <p>
 * Passenger management includes advanced addition and removal capabilities with real-time seat availability
 * checking that considers existing booking allocations. The interface validates flight capacity against current
 * passenger count plus existing booking tickets and provides immediate feedback when seat limits are reached.
 * </p>
 * <p>
 * Data validation ensures that all required passenger information is complete before allowing booking
 * confirmation, while providing flexible pending save options for incomplete modifications. This approach
 * accommodates different customer workflows and booking completion patterns during modification processes.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader booking management system through the {@link Controller}
 * interface, ensuring proper data persistence and application state management throughout the booking
 * modification process. Integration includes coordination with existing booking data and proper cleanup
 * of removed passengers and associated resources.
 * </p>
 * <p>
 * Visual design follows consistent application styling with responsive layout management and intuitive
 * user interaction patterns. Enhanced controls include remove passenger buttons positioned alongside each
 * passenger panel for efficient passenger management. Error messaging and validation feedback are
 * provided through {@link FloatingMessage} components for clear user communication.
 * </p>
 * <p>
 * The booking modification workflow supports navigation integration allowing users to seamlessly return
 * to previous application states after successful modification completion or cancellation. The interface
 * maintains proper resource cleanup and state management throughout the modification lifecycle.
 * </p>
 * <p>
 * Page navigation is enhanced to handle both passenger panels and their corresponding remove buttons,
 * ensuring that visibility states are properly synchronized during page changes. This provides consistent
 * user experience across different passenger counts and page configurations.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see BookingPageCustomer
 * @see Controller
 * @see PassengerPanel
 * @see RemovePassengerButton
 * @see FloatingMessage
 * @see DisposableObject
 */
public class BookingModifyPage extends BookingPageCustomer {

    /**
     * Collection of remove passenger buttons corresponding to each passenger panel.
     * <p>
     * This list maintains remove buttons that provide passengers removal functionality,
     * with each button corresponding to a specific passenger panel at the same index.
     * The buttons are dynamically created and managed alongside passenger panels
     * to provide intuitive passenger removal capabilities.
     * </p>
     */
    protected ArrayList<RemovePassengerButton> removePassengerButtons;
    
    /**
     * Button for adding new passengers to the existing booking.
     * <p>
     * This button enables dynamic passenger addition with real-time capacity validation,
     * considering both flight capacity and existing booking allocations. The button
     * integrates with seat availability checking to prevent overbooking scenarios.
     * </p>
     */
    protected JButton addPassengerButton;

    /**
     * Button for confirming booking modifications with complete validation.
     * <p>
     * This button triggers comprehensive validation of all passenger data and
     * confirms the booking modifications, updating the reservation status to
     * "CONFIRMED" upon successful validation and processing.
     * </p>
     */
    protected JButton confirmButton;
    
    /**
     * Button for saving incomplete booking modifications in pending status.
     * <p>
     * This button allows customers to save partial booking modifications for
     * later completion, updating the reservation status to "PENDING" while
     * preserving the current modification state for future continuation.
     * </p>
     */
    protected JButton savePendingButton;

    /**
     * Constructs a new BookingModifyPage interface for existing reservation modification.
     * <p>
     * This constructor initializes the complete booking modification interface by extending
     * the {@link BookingPageCustomer} functionality and configuring it specifically for
     * existing booking modification workflows. The constructor establishes the main
     * application window with proper sizing, positioning, and integration with the
     * calling object hierarchy.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li>Inheriting passenger management capabilities from the parent class</li>
     *   <li>Establishing window properties including size, position, and display state</li>
     *   <li>Integrating with the application's disposable object management system</li>
     *   <li>Configuring the interface for existing booking modification workflows</li>
     *   <li>Setting up the main frame visibility and user interaction readiness</li>
     * </ul>
     * <p>
     * The constructor leverages the parent class initialization to establish the core
     * booking interface structure while preparing the interface specifically for existing
     * booking modification rather than new booking creation.
     * </p>
     * <p>
     * Window management includes proper integration with the calling objects hierarchy
     * to ensure seamless navigation and resource management throughout the application
     * lifecycle. The interface becomes immediately visible and ready for user interaction
     * upon construction completion.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight management, booking operations, and data persistence
     * @param dimension the preferred window size for the booking modification interface
     * @param point the screen position where the booking modification window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     */
    public BookingModifyPage(List<DisposableObject> callingObjects, Controller controller,
                             Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        mainFrame.setVisible(true);
    }

    /**
     * Constructs a new BookingModifyPage interface with controller disposal flag configuration.
     * <p>
     * This overloaded constructor provides additional control over controller resource
     * management by accepting a disposal flag that determines whether the controller
     * should be disposed of when the interface is closed. This is particularly useful
     * for integration scenarios where the controller may be shared across multiple
     * interface components.
     * </p>
     * <p>
     * The constructor delegates to the main constructor for interface initialization
     * and then configures the controller disposal behavior based on the provided flag.
     * This ensures proper resource management while providing flexibility for different
     * usage scenarios.
     * </p>
     * <p>
     * Controller disposal management affects cleanup operations when the interface
     * is closed, determining whether controller resources should be released or
     * preserved for continued use by other application components.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight management, booking operations, and data persistence
     * @param dimension the preferred window size for the booking modification interface
     * @param point the screen position where the booking modification window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     * @param flag the controller disposal flag indicating whether controller resources should be disposed when the interface closes
     */
    public BookingModifyPage(List<DisposableObject> callingObjects, Controller controller,
                             Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    /**
     * Creates and configures the enhanced modification panel with passenger management controls.
     * <p>
     * This method establishes the comprehensive modification interface that provides enhanced
     * controls for passenger management including dynamic addition capabilities and navigation
     * controls. The panel uses a structured grid layout to organize the controls in an
     * intuitive and accessible manner for efficient booking modification operations.
     * </p>
     * <p>
     * The modification panel includes:
     * </p>
     * <ul>
     *   <li><strong>Enhanced Control Layout:</strong> Grid-based organization for optimal control placement and user interaction</li>
     *   <li><strong>Dynamic Passenger Addition:</strong> Integration of passenger addition capabilities with capacity validation</li>
     *   <li><strong>Navigation Controls:</strong> Page change buttons for managing large numbers of passengers</li>
     *   <li><strong>Visual Consistency:</strong> Transparent background to maintain application design coherence</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for user interaction</li>
     * </ul>
     * <p>
     * The panel serves as the primary control center for booking modification operations,
     * providing customers with intuitive access to passenger management and navigation
     * capabilities. The flow panel within the modification panel ensures proper alignment
     * and spacing of control elements.
     * </p>
     * <p>
     * Layout management uses {@link GridBagLayout} to provide precise control over
     * component positioning and sizing, ensuring optimal user experience across different
     * screen sizes and interface configurations. The nested flow panel provides additional
     * layout flexibility for control button organization.
     * </p>
     * <p>
     * The method coordinates the creation of passenger addition and navigation controls
     * through dedicated methods, ensuring proper separation of concerns and maintainable
     * component configuration management.
     * </p>
     *
     * @param controller the system controller providing access to flight capacity information and passenger management operations
     */
    protected void addModifyPanel (Controller controller) {

        modifyPanel = new JPanel();

        modifyPanel.setLayout(new GridBagLayout());

        modifyPanel.setOpaque(false);

        flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        flowPanel.setOpaque(false);

        constraints.setConstraints (1, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_END);
        modifyPanel.add(flowPanel, constraints.getGridBagConstraints());

        flowPanel.setVisible(true);

        addAddPassengerButton(controller);
        addPageChangeButtons();

        constraints.setConstraints (0, 3, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainPanel.add (modifyPanel, constraints.getGridBagConstraints());
        modifyPanel.setVisible (true);
    }

    /**
     * Creates and configures the passenger addition button with enhanced capacity validation.
     * <p>
     * This method creates an advanced passenger addition button that allows customers to
     * add additional passengers to their existing booking while enforcing comprehensive
     * flight capacity constraints. The button integrates real-time seat availability
     * checking that considers both total flight capacity and existing booking allocations.
     * </p>
     * <p>
     * The button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Enhanced Capacity Validation:</strong> Real-time checking considering existing booking tickets and current passenger count</li>
     *   <li><strong>Dynamic Panel Creation:</strong> Automatic creation of new {@link PassengerPanel} instances for additional passengers</li>
     *   <li><strong>User Feedback:</strong> Immediate error messaging when capacity constraints are exceeded</li>
     *   <li><strong>Interface Integration:</strong> Seamless integration with the existing passenger management interface</li>
     *   <li><strong>Booking Context Awareness:</strong> Considers existing booking ticket allocations in capacity calculations</li>
     * </ul>
     * <p>
     * Enhanced seat availability calculation compares the flight's total free seats against
     * the current number of passenger panels while adding back the existing booking's ticket
     * count to determine accurate availability for additional passengers. This prevents
     * customers from exceeding capacity while allowing modification within existing allocations.
     * </p>
     * <p>
     * When capacity allows, the method creates a new {@link PassengerPanel} with proper
     * integration into the existing passenger management system, including seat selection
     * and booking coordination. The new panel inherits all standard passenger input
     * capabilities and validation features.
     * </p>
     * <p>
     * Error handling provides clear user feedback through {@link FloatingMessage}
     * components when capacity constraints are exceeded, informing customers about
     * availability limitations and preventing booking errors during modification.
     * </p>
     * <p>
     * The button is styled consistently with the application design and positioned
     * appropriately within the interface flow panel for intuitive user access during
     * the booking modification process.
     * </p>
     *
     * @param controller the system controller providing access to enhanced flight capacity information and passenger management operations
     */
    protected void addAddPassengerButton(Controller controller) {

        addPassengerButton = new JButton("AGGIUNGI PASSEGGERO");

        addPassengerButton.setFocusable(false);

        addPassengerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (controller.getFlightController().getFreeSeats() - passengerPanels.size() + controller.getBookingController().getTicketsSize() > 0)
                    insertPassengerPanel(new PassengerPanel(controller, passengerPanels, bookedSeats));
                else
                    new FloatingMessage("Non ci sono altri posti disponibili per questo volo", addPassengerButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        flowPanel.add(addPassengerButton);
    }

    /**
     * Enhanced page navigation method with synchronized passenger and remove button visibility management.
     * <p>
     * This method provides comprehensive page navigation functionality that manages both
     * passenger panels and their corresponding remove buttons, ensuring that visibility
     * states are properly synchronized during page changes. The method handles the complex
     * interaction between multiple UI components during navigation operations.
     * </p>
     * <p>
     * The enhanced navigation process includes:
     * </p>
     * <ul>
     *   <li><strong>Synchronized Visibility Management:</strong> Coordinated hiding and showing of both passenger panels and remove buttons</li>
     *   <li><strong>Current Page State Management:</strong> Proper cleanup of current page elements before transition</li>
     *   <li><strong>Target Page Preparation:</strong> Appropriate setup of target page elements for display</li>
     *   <li><strong>Navigation Control Updates:</strong> Dynamic enabling/disabling of navigation buttons based on page position</li>
     *   <li><strong>Page Label Updates:</strong> Real-time updating of current page indicators</li>
     * </ul>
     * <p>
     * Visibility management ensures that both passenger panels and their corresponding
     * remove buttons at the same index are handled together, maintaining the integrity
     * of the passenger management interface. This prevents UI inconsistencies where
     * passenger panels and remove buttons might have mismatched visibility states.
     * </p>
     * <p>
     * The method processes up to 3 passenger/button pairs per page, which is the
     * standard page size for passenger display. This provides optimal screen utilization
     * while maintaining usability across different screen sizes and resolutions.
     * </p>
     * <p>
     * Navigation button state management ensures that users can only navigate to valid
     * pages, preventing navigation errors and providing clear visual feedback about
     * available navigation options. The previous page button is disabled on the first
     * page, and the next page button is disabled when reaching the last page.
     * </p>
     * <p>
     * Page calculation logic accounts for the enhanced passenger management structure,
     * ensuring that page boundaries are correctly calculated based on the total number
     * of passenger panels and the three-per-page display layout.
     * </p>
     *
     * @param page the zero-based target page index to navigate to
     */
    @Override
    protected void goToPage (int page) {

        //sistemo visibilità
        for (int i = 0; i < 3; i++) {

            if (i + currPage * 3 < passengerPanels.size()) {

                passengerPanels.get(i + currPage * 3).setVisible(false);
                removePassengerButtons.get(i + currPage * 3).setVisible(false);
            }

            if (i + page * 3 < passengerPanels.size()) {

                passengerPanels.get(i + page * 3).setVisible(true);
                removePassengerButtons.get(i + page * 3).setVisible(true);
            }
        }

        //sistemo currPage
        currPage = page;
        currentPageLabel.setText(Integer.toString(currPage + 1));

        //sistemo attivabilità bottoni
        prevPageButton.setEnabled(currPage > 0);
        nextPageButton.setEnabled(currPage < ((passengerPanels.size() - 1) / 3));
    }

    /**
     * Enhanced passenger panel insertion with integrated remove button management.
     * <p>
     * This method provides comprehensive passenger panel insertion functionality that
     * creates and manages both the passenger panel and its corresponding remove button
     * as a coordinated unit. The method ensures proper layout, visibility management,
     * and integration with the enhanced passenger management system.
     * </p>
     * <p>
     * The enhanced insertion process includes:
     * </p>
     * <ul>
     *   <li><strong>Remove Button Collection Initialization:</strong> Ensures remove button collection is properly initialized</li>
     *   <li><strong>Coordinated Layout Management:</strong> Positions passenger panels and remove buttons in adjacent layout positions</li>
     *   <li><strong>Synchronized Visibility Control:</strong> Manages visibility states for both passenger panels and remove buttons</li>
     *   <li><strong>Enhanced Page Management:</strong> Integrates with advanced page navigation and visibility systems</li>
     *   <li><strong>Remove Button State Management:</strong> Ensures proper enabling/disabling of remove functionality</li>
     * </ul>
     * <p>
     * Layout positioning uses a two-column approach where passenger panels are placed
     * in the left column (column 0) and their corresponding remove buttons are placed
     * in the right column (column 1). This provides intuitive association between
     * passengers and their removal controls.
     * </p>
     * <p>
     * Remove button creation includes proper initialization with references to the
     * modification page, passenger panel collections, and the button's index position.
     * This enables the remove button to perform proper cleanup and state management
     * when activated.
     * </p>
     * <p>
     * Visibility management includes sophisticated logic that either uses the enhanced
     * page visibility system for existing passenger scenarios or provides immediate
     * visibility for the first passenger addition. This ensures consistent user
     * experience regardless of the current passenger count.
     * </p>
     * <p>
     * Collection management adds both the passenger panel and remove button to their
     * respective collections, ensuring that all subsequent operations can access both
     * components in a coordinated manner.
     * </p>
     * <p>
     * Page refresh functionality ensures that the passenger page display is properly
     * updated to reflect the new passenger addition, maintaining visual consistency
     * and proper component rendering.
     * </p>
     * <p>
     * Remove button state management ensures that the first remove button is only
     * enabled when there are multiple passengers, preventing users from removing
     * the last passenger from a booking modification.
     * </p>
     *
     * @param passengerPanel the new passenger panel to be inserted into the interface
     */
    @Override
    protected void insertPassengerPanel (PassengerPanel passengerPanel) {

        if (removePassengerButtons == null) removePassengerButtons = new ArrayList<>();

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        passengerPage.add(passengerPanel, constraints.getGridBagConstraints());

        RemovePassengerButton removePassengerButton = new RemovePassengerButton(this, passengerPanels, removePassengerButtons, removePassengerButtons.size());

        constraints.setConstraints(1, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        passengerPage.add(removePassengerButton, constraints.getGridBagConstraints());

        if (!passengerPanels.isEmpty()) setPassengersVisibility();
        else {
            passengerPanel.setVisible(true);
            removePassengerButton.setVisible(true);
        }

        passengerPanels.addLast(passengerPanel);
        removePassengerButtons.addLast(removePassengerButton);

        passengerPage.setVisible(false);
        passengerPage.setVisible(true);

        removePassengerButtons.getFirst().setEnabled(passengerPanels.size() > 1);
    }

    /**
     * Sets appropriate passenger and remove button visibility based on current passenger count.
     * <p>
     * This utility method manages the visibility of passenger panels and their corresponding
     * remove buttons by navigating to the appropriate page based on the current passenger
     * count. The method ensures that newly added passengers are immediately visible to
     * users by calculating and navigating to the page containing the most recently added passenger.
     * </p>
     * <p>
     * The visibility management process includes:
     * </p>
     * <ul>
     *   <li><strong>Page Calculation:</strong> Determines the appropriate page based on total passenger count</li>
     *   <li><strong>Automatic Navigation:</strong> Navigates to the calculated page to show recent additions</li>
     *   <li><strong>Synchronized Display:</strong> Ensures both passenger panels and remove buttons are properly visible</li>
     *   <li><strong>User Experience Optimization:</strong> Provides immediate feedback for passenger addition operations</li>
     * </ul>
     * <p>
     * Page calculation uses integer division to determine which page should display the
     * most recently added passenger, ensuring that users can immediately see the results
     * of their passenger addition actions. This calculation accounts for the three-passenger
     * per page display layout used throughout the interface.
     * </p>
     * <p>
     * The method leverages the enhanced {@link #goToPage(int)} functionality to ensure
     * that both passenger panels and remove buttons are properly displayed with correct
     * visibility states and navigation control updates.
     * </p>
     */
    protected void setPassengersVisibility () {

        goToPage(passengerPanels.size() / 3);
    }

    /**
     * Creates and configures the enhanced confirmation panel with modification action buttons.
     * <p>
     * This method establishes the comprehensive confirmation interface that provides customers
     * with options to either confirm their booking modifications immediately or save them in
     * a pending state for later completion. The panel uses a structured grid layout to organize
     * the action buttons and provides conditional functionality based on current booking status.
     * </p>
     * <p>
     * The enhanced confirmation panel includes:
     * </p>
     * <ul>
     *   <li><strong>Dual Action Options:</strong> Both immediate confirmation and conditional pending save capabilities</li>
     *   <li><strong>Status-Aware Interface:</strong> Conditional display of pending save button based on current booking status</li>
     *   <li><strong>Structured Layout:</strong> Grid-based organization for optimal button placement and user interaction</li>
     *   <li><strong>Visual Consistency:</strong> Transparent background to maintain application design coherence</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for user interaction</li>
     * </ul>
     * <p>
     * The panel serves as the primary decision point in the booking modification workflow,
     * allowing customers to choose between immediate booking confirmation with complete
     * passenger information or saving incomplete modifications for future completion.
     * The interface intelligently adapts based on the current booking status.
     * </p>
     * <p>
     * Status-aware functionality ensures that the pending save button is only displayed
     * when the current booking status is "PENDING", providing appropriate interface
     * options based on the booking's current state. This prevents confusion and provides
     * contextually relevant actions.
     * </p>
     * <p>
     * Layout management uses {@link GridBagLayout} to provide precise control over
     * button positioning and sizing, ensuring optimal user experience across different
     * screen sizes and interface configurations.
     * </p>
     * <p>
     * The method coordinates the creation of confirmation and conditional pending save
     * buttons through dedicated methods, ensuring proper separation of concerns and
     * maintainable button configuration management.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation and resource management
     * @param controller the system controller providing access to booking operations, status information, and data persistence capabilities
     */
    @Override
    protected void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        addConfirmButton(controller, callingObjects);
        if (controller.getBookingController().getBookingStatus().toString().equalsIgnoreCase("PENDING"))
            addSavePendingButton(controller, callingObjects);

        confirmPanel.setVisible (true);
    }

    /**
     * Creates and configures the booking modification confirmation button with comprehensive validation.
     * <p>
     * This method creates the primary booking modification confirmation button that allows
     * customers to finalize their flight reservation changes with full passenger information
     * validation. The button integrates comprehensive data checking to ensure all required
     * passenger information is complete before processing the booking modification confirmation.
     * </p>
     * <p>
     * The confirmation button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Data Validation:</strong> Comprehensive checking of all passenger information completeness and accuracy</li>
     *   <li><strong>Booking Modification:</strong> Integration with the controller to update existing reservations</li>
     *   <li><strong>Navigation Management:</strong> Automatic return to previous application state after successful confirmation</li>
     *   <li><strong>Error Feedback:</strong> Clear messaging when passenger data is incomplete, invalid, or contains errors</li>
     *   <li><strong>Status Management:</strong> Automatic assignment of "CONFIRMED" status to successfully modified bookings</li>
     * </ul>
     * <p>
     * Validation processing uses the inherited {@link #checkConfirmButton()} method to
     * verify that all required passenger information fields are completed, accurate, and
     * properly formatted. This includes personal information, seat assignments, luggage
     * details, and any required documentation for all passengers in the modified booking.
     * </p>
     * <p>
     * Successful validation triggers booking modification through the {@link Controller#modifyBooking}
     * method with "CONFIRMED" status, ensuring immediate reservation update confirmation and
     * availability of the modified booking in the system for operational purposes.
     * </p>
     * <p>
     * Navigation integration automatically returns customers to the previous application
     * state after successful booking modification confirmation, providing seamless workflow
     * completion and preventing interface confusion or navigation errors.
     * </p>
     * <p>
     * Error handling provides specific feedback about incomplete, invalid, or erroneous
     * passenger data through {@link FloatingMessage} components, helping customers understand
     * what information needs to be corrected for successful booking modification confirmation.
     * </p>
     * <p>
     * Button styling and positioning maintain application design consistency while ensuring
     * prominent visibility for this critical booking modification action. The button is
     * properly integrated into the confirmation panel layout structure.
     * </p>
     *
     * @param controller the system controller providing access to booking modification operations and validation services
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     */
    protected void addConfirmButton (Controller controller, List<DisposableObject> callingObjects) {

        confirmButton = new JButton("CONFERMA PRENOTAZIONE");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkConfirmButton()) {
                    controller.modifyBooking(passengerPanels, "CONFIRMED");
                    controller.goBack(callingObjects);
                } else
                    new FloatingMessage("I dati dei passeggeri sono incompleti o errati", confirmButton, FloatingMessage.ERROR_MESSAGE);
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
     * Creates and configures the pending save button for incomplete booking modification preservation.
     * <p>
     * This method creates a specialized save button that allows customers to preserve
     * incomplete booking modification information in a pending state for later completion
     * and confirmation. This functionality accommodates customers who may need to gather
     * additional information or complete the modification process across multiple sessions.
     * </p>
     * <p>
     * The pending save button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Flexible Validation:</strong> Less restrictive validation allowing incomplete but valid partial modifications</li>
     *   <li><strong>Pending Status Assignment:</strong> Automatic assignment of "PENDING" status to preserved modifications</li>
     *   <li><strong>Error Prevention:</strong> Validation to prevent invalid or erroneous modifications from being saved</li>
     *   <li><strong>Navigation Management:</strong> Automatic return to previous application state after successful save</li>
     *   <li><strong>User Feedback:</strong> Clear messaging for both successful saves and invalid incomplete modifications</li>
     * </ul>
     * <p>
     * Validation processing uses the inherited {@link #checkSavePendingButton()} method
     * to verify that the booking modifications contain valid passenger information while
     * allowing incomplete data sets. This prevents completely invalid modifications while
     * accommodating partial completion scenarios during modification workflows.
     * </p>
     * <p>
     * Successful validation triggers booking modification through the {@link Controller#modifyBooking}
     * method with "PENDING" status, ensuring the modified reservation is preserved in the
     * system but marked as incomplete for future completion requirements.
     * </p>
     * <p>
     * Error handling provides specific feedback when attempting to save modifications with
     * incomplete or erroneous data, guiding customers to correct information or add
     * additional required details before the booking modifications can be preserved.
     * </p>
     * <p>
     * Navigation integration ensures customers are returned to the appropriate application
     * state after successful pending save operations, maintaining workflow continuity
     * and preventing interface confusion during multi-session modification processes.
     * </p>
     * <p>
     * Button positioning and styling maintain visual balance with the confirmation button
     * while providing clear distinction between immediate confirmation and pending save
     * actions for intuitive user understanding. The button is positioned within the
     * modification panel for logical grouping with other modification controls.
     * </p>
     *
     * @param controller the system controller providing access to booking modification operations and validation services
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     */
    protected void addSavePendingButton (Controller controller, List<DisposableObject> callingObjects) {

        savePendingButton = new JButton("SALVA IN ATTESA");

        savePendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkSavePendingButton()) {
                    controller.modifyBooking(passengerPanels, "PENDING");
                    controller.goBack(callingObjects);
                } else
                    new FloatingMessage("I dati dei passeggeri sono incompleti o errati", savePendingButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_START);
        modifyPanel.add (savePendingButton, constraints.getGridBagConstraints());

        savePendingButton.setFocusable(false);
        savePendingButton.setVisible (true);
    }

    /**
     * Validates passenger data for pending save operations with relaxed validation criteria.
     * <p>
     * This method performs specialized validation designed for pending save operations,
     * applying less restrictive criteria that allow incomplete but valid partial booking
     * modifications to be preserved. The validation focuses on preventing invalid data
     * while accommodating incomplete information that can be completed in future sessions.
     * </p>
     * <p>
     * The pending save validation process includes:
     * </p>
     * <ul>
     *   <li><strong>Essential Data Validation:</strong> Ensures critical passenger identification data is valid when present</li>
     *   <li><strong>Luggage Constraint Checking:</strong> Validates luggage selections to prevent booking conflicts</li>
     *   <li><strong>Incomplete Data Tolerance:</strong> Allows missing non-critical information for future completion</li>
     *   <li><strong>Business Rule Enforcement:</strong> Maintains essential business rules even with incomplete data</li>
     * </ul>
     * <p>
     * Passenger validation focuses on the most critical identification field (SSN/CF)
     * using the {@link PassengerPanel#checkPassengerCF()} method. This ensures that
     * if passenger identification is provided, it is valid, while allowing the field
     * to be empty for later completion.
     * </p>
     * <p>
     * Luggage validation enforces the business rule that each passenger can have at most
     * one hand luggage item, preventing booking conflicts even when other passenger
     * information is incomplete. This validation examines luggage combo box selections
     * to identify hand luggage (index 1) and ensures no passenger has multiple hand
     * luggage items selected.
     * </p>
     * <p>
     * The validation is designed to support iterative booking modification workflows
     * where customers may need to complete their modifications across multiple sessions,
     * gathering information or making decisions over time while maintaining booking
     * integrity and preventing system conflicts.
     * </p>
     * <p>
     * Error conditions result in immediate validation failure, preventing the save
     * operation and prompting users to correct the identified issues before attempting
     * to save their incomplete modifications.
     * </p>
     *
     * @return true if the passenger data is valid for pending save operations, false if critical validation errors are present
     */
    protected boolean checkSavePendingButton() {

        for (PassengerPanel passengerPanel : passengerPanels) {

            if (passengerPanel.checkPassengerCF()) return false;

            boolean flag = false;

            for (LuggagePanel  luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.getComboBox().getSelectedIndex() == 1) {

                    if (flag) return false;
                    else flag = true;
                }
            }
        }

        return true;
    }

    /**
     * Validates complete passenger data for booking modification confirmation operations.
     * <p>
     * This method performs comprehensive validation designed for booking modification
     * confirmation operations, applying strict criteria to ensure all required passenger
     * information is complete, accurate, and properly formatted before allowing booking
     * confirmation. The validation encompasses all critical passenger data and business rules.
     * </p>
     * <p>
     * The comprehensive confirmation validation process includes:
     * </p>
     * <ul>
     *   <li><strong>Complete Personal Information Validation:</strong> Ensures all passenger personal details are complete and valid</li>
     *   <li><strong>Identification Data Verification:</strong> Validates passenger identification information and formatting</li>
     *   <li><strong>Date Information Checking:</strong> Confirms passenger birth date information is complete and valid</li>
     *   <li><strong>Luggage Data Validation:</strong> Validates all luggage information and selections</li>
     *   <li><strong>Business Rule Enforcement:</strong> Ensures all business rules are satisfied across all passengers</li>
     * </ul>
     * <p>
     * Personal information validation uses multiple {@link PassengerPanel} validation
     * methods to ensure completeness and accuracy of passenger names (first and last),
     * identification codes (SSN/CF), and birth date information. All fields must pass
     * validation for the confirmation to proceed.
     * </p>
     * <p>
     * Luggage validation performs two levels of checking: first ensuring that all
     * luggage information is complete and valid using {@link LuggagePanel#checkLuggage()},
     * and second enforcing the business rule that each passenger can have at most one
     * hand luggage item to prevent booking conflicts and operational issues.
     * </p>
     * <p>
     * The validation process uses early termination logic, immediately returning false
     * when any validation error is encountered. This provides efficient validation
     * processing while ensuring that all critical requirements are satisfied before
     * allowing booking modification confirmation.
     * </p>
     * <p>
     * Business rule enforcement includes the hand luggage constraint where combo box
     * selection index 1 indicates hand luggage, and the validation ensures no passenger
     * has multiple hand luggage items selected across their luggage panels.
     * </p>
     * <p>
     * The method is designed to support the highest standard of data integrity for
     * confirmed booking modifications, ensuring that all confirmed bookings meet
     * operational requirements and contain complete, accurate passenger information.
     * </p>
     *
     * @return true if all passenger data is complete and valid for booking confirmation, false if any validation errors are present
     */
    protected boolean checkConfirmButton() {
        for (PassengerPanel passengerPanel : passengerPanels) {

            if (passengerPanel.checkPassengerName() || passengerPanel.checkPassengerSurname() || passengerPanel.checkPassengerCF() || passengerPanel.checkPassengerDate())
                return false;

            boolean flag = false;

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.checkLuggage())
                    return false;

                if (luggagePanel.getComboBox().getSelectedIndex() == 1) {

                    if (flag) return false;
                    else flag = true;
                }
            }

        }

        return true;
    }
}