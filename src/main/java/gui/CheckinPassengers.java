package gui;


import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Specialized administrative interface for passenger check-in operations and gate management.
 * <p>
 * This class extends {@link BookingPageAdmin} to provide comprehensive check-in functionality
 * for flight operations within the airport management system. It offers administrative controls
 * for passenger check-in processing, gate assignment, luggage management integration, and
 * operational workflow coordination for departing flights.
 * </p>
 * <p>
 * The CheckinPassengers class supports comprehensive check-in operations including:
 * </p>
 * <ul>
 *   <li><strong>Interactive Check-in Processing:</strong> Individual passenger check-in control through interactive checkboxes</li>
 *   <li><strong>Gate Assignment Management:</strong> Dynamic gate selection and assignment for flight operations</li>
 *   <li><strong>Batch Check-in Confirmation:</strong> Simultaneous processing of multiple passenger check-ins</li>
 *   <li><strong>Luggage Integration:</strong> Automatic luggage check-in coordination with passenger processing</li>
 *   <li><strong>Flight Status Management:</strong> Inherited flight status modification capabilities for operational coordination</li>
 *   <li><strong>Delay Configuration:</strong> Real-time delay management during check-in operations</li>
 *   <li><strong>Resource Management:</strong> Advanced cleanup and disposal for check-in specific components</li>
 * </ul>
 * <p>
 * The interface is designed with operational workflow optimization allowing check-in staff to:
 * </p>
 * <ul>
 *   <li><strong>Process Individual Check-ins:</strong> Select/deselect passengers for check-in processing</li>
 *   <li><strong>Manage Gate Assignments:</strong> Assign or modify flight gate assignments</li>
 *   <li><strong>Coordinate Luggage Processing:</strong> Integrate luggage check-in with passenger processing</li>
 *   <li><strong>Confirm Batch Operations:</strong> Process multiple check-ins simultaneously</li>
 *   <li><strong>Monitor Flight Operations:</strong> Access inherited flight status and delay management</li>
 * </ul>
 * <p>
 * Passenger check-in management includes interactive checkbox controls for each passenger,
 * enabling check-in staff to individually select passengers for processing. The system
 * maintains separate collections for passengers to be checked in and those to remain
 * unchecked, providing precise control over check-in operations.
 * </p>
 * <p>
 * Gate management functionality provides dynamic gate assignment capabilities through
 * integrated gate selection interfaces. The system supports both new gate assignments
 * and modifications to existing gate allocations based on operational requirements.
 * </p>
 * <p>
 * Batch processing capabilities enable efficient check-in operations by allowing staff
 * to process multiple passengers simultaneously. The confirmation process integrates
 * with luggage management systems to ensure complete passenger and baggage processing.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through the
 * {@link Controller} interface, ensuring proper data persistence, operational coordination,
 * and system state management throughout check-in operations.
 * </p>
 * <p>
 * Visual design maintains consistency with administrative interface styling while providing
 * specialized check-in controls. Operational feedback is provided through {@link FloatingMessage}
 * components for clear staff communication and operation status updates.
 * </p>
 * <p>
 * Resource management includes enhanced cleanup procedures for check-in specific components
 * including gate chooser dialogs and passenger-related resources. The disposal process
 * ensures proper cleanup of all check-in interface elements and associated resources.
 * </p>
 * <p>
 * The check-in interface supports navigation integration while maintaining operational
 * context and check-in state. Integration with the calling objects hierarchy ensures
 * proper resource management and seamless transitions between operational interfaces.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see BookingPageAdmin
 * @see Controller
 * @see GateChooser
 * @see PassengerPanel
 * @see FloatingMessage
 * @see DisposableObject
 */
public class CheckinPassengers extends BookingPageAdmin{

    /**
     * Button for gate assignment and management operations.
     * <p>
     * This button provides gate assignment functionality, enabling check-in
     * staff to assign or modify flight gate allocations. The button integrates
     * with gate management systems for comprehensive gate coordination.
     * </p>
     */
    protected JButton gateButton;
    
    /**
     * Button for confirming batch check-in operations.
     * <p>
     * This button triggers the processing of selected passenger check-ins,
     * coordinating with luggage systems and updating operational status
     * for all passengers selected for check-in processing.
     * </p>
     */
    protected JButton confirmButton;
    
    /**
     * Gate chooser dialog for gate selection operations.
     * <p>
     * This component provides staff with an interface for selecting
     * appropriate gates for flight assignments. The chooser integrates
     * with gate management systems for real-time gate availability.
     * </p>
     */
    protected GateChooser gateChooser;

    /**
     * Collection of passenger panels selected for check-in processing.
     * <p>
     * This list maintains passengers that have been selected for check-in
     * through the interactive checkbox controls. These passengers will be
     * processed during batch check-in confirmation operations.
     * </p>
     */
    protected ArrayList<PassengerPanel> truePassengerPanels;
    
    /**
     * Collection of passenger panels not selected for check-in processing.
     * <p>
     * This list maintains passengers that are not selected for check-in
     * processing. These passengers will remain in their current check-in
     * status during batch processing operations.
     * </p>
     */
    protected ArrayList<PassengerPanel> falsePassengerPanels;

    /**
     * Constructs a new CheckinPassengers interface for passenger check-in operations.
     * <p>
     * This constructor initializes the complete check-in interface by extending the
     * {@link BookingPageAdmin} functionality and configuring it specifically for
     * passenger check-in workflows. The constructor establishes the main application
     * window with proper sizing, positioning, and integration with check-in control systems.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li>Inheriting comprehensive administrative booking interface capabilities from the parent class</li>
     *   <li>Establishing window properties optimized for check-in operations</li>
     *   <li>Initializing passenger collection management for check-in state tracking</li>
     *   <li>Configuring interactive checkbox controls for individual passenger check-in selection</li>
     *   <li>Setting up immediate visibility for operational readiness</li>
     * </ul>
     * <p>
     * The constructor leverages the parent class initialization to establish the core
     * administrative interface structure while adding specialized check-in functionality
     * including interactive passenger selection, gate management, and batch processing
     * capabilities.
     * </p>
     * <p>
     * Passenger collection initialization creates separate tracking for passengers
     * selected for check-in and those remaining unchecked, enabling precise control
     * over check-in operations and maintaining clear operational state throughout
     * the check-in process.
     * </p>
     * <p>
     * Interactive checkbox setup configures individual passenger panels with check-in
     * selection controls, integrating current passenger check-in status from the
     * flight controller to provide accurate operational state representation.
     * </p>
     * <p>
     * Window management ensures proper integration with the calling objects hierarchy
     * for seamless navigation and resource management throughout check-in operations.
     * The interface becomes immediately visible and ready for check-in processing
     * upon construction completion.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight management, check-in operations, and data persistence
     * @param dimension the preferred window size for the check-in interface
     * @param point the screen position where the check-in window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     */
    public CheckinPassengers (List<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        truePassengerPanels = new ArrayList<>();
        falsePassengerPanels = new ArrayList<>();

        setCheckinCheckBoxes(controller);

        mainFrame.setVisible(true);
    }

    /**
     * Constructs a new CheckinPassengers interface with configurable controller disposal settings.
     * <p>
     * This constructor provides an alternative initialization path that allows configuration
     * of controller resource disposal behavior during interface cleanup. This constructor
     * delegates to the primary constructor while providing additional control over resource
     * management for specialized operational scenarios.
     * </p>
     * <p>
     * The constructor provides identical functionality to the primary constructor while
     * enabling customization of controller disposal behavior. This is particularly useful
     * for operational scenarios where controller resources may be shared across multiple
     * interface components or require specialized cleanup procedures.
     * </p>
     * <p>
     * Controller disposal flag configuration determines whether controller resources
     * should be cleaned up when the check-in interface is disposed. Setting the flag
     * to false preserves controller resources for continued use by other interface
     * components, while true enables standard cleanup procedures.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and navigation
     * @param controller the system controller providing access to flight management, check-in operations, and data persistence
     * @param dimension the preferred window size for the check-in interface
     * @param point the screen position where the check-in window should be displayed
     * @param fullScreen the window state indicating whether the interface should be maximized or in normal window mode
     * @param flag true to enable controller disposal on interface cleanup, false to preserve controller resources
     */
    public CheckinPassengers (List<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    /**
     * Creates and configures the check-in specific confirmation panel with operational controls.
     * <p>
     * This method overrides the parent class confirmation panel to provide specialized
     * check-in controls including gate management, check-in confirmation, and inherited
     * flight operational controls. The panel uses a structured grid layout to organize
     * check-in controls in an intuitive and efficient manner for operational workflow
     * optimization.
     * </p>
     * <p>
     * The check-in confirmation panel includes:
     * </p>
     * <ul>
     *   <li><strong>Flight Status Controls:</strong> Inherited flight status modification capabilities</li>
     *   <li><strong>Gate Management Controls:</strong> Gate assignment and modification functionality</li>
     *   <li><strong>Check-in Confirmation Controls:</strong> Batch passenger check-in processing</li>
     *   <li><strong>Delay Management Controls:</strong> Inherited flight delay configuration capabilities</li>
     *   <li><strong>Structured Layout:</strong> Grid-based organization for optimal control placement and workflow efficiency</li>
     *   <li><strong>Visual Consistency:</strong> Transparent background maintaining application design coherence</li>
     *   <li><strong>Immediate Visibility:</strong> Panel becomes visible immediately upon creation for operational readiness</li>
     * </ul>
     * <p>
     * The panel serves as the primary operational control center for check-in operations,
     * providing check-in staff with centralized access to essential flight management
     * capabilities and specialized check-in functionality within a unified interface.
     * </p>
     * <p>
     * Control organization follows operational workflow patterns with logical grouping
     * of related functions. Flight status controls are positioned for immediate access,
     * gate management is centrally located for operational efficiency, check-in confirmation
     * is prominently positioned, and delay configuration is positioned for administrative
     * convenience.
     * </p>
     * <p>
     * Layout management uses {@link GridBagLayout} to provide precise control over
     * button positioning and sizing, ensuring optimal check-in staff experience across
     * different screen sizes and operational configurations. The layout accommodates
     * various control types including buttons, input fields, and specialized components.
     * </p>
     * <p>
     * The method coordinates the creation of specialized check-in controls through
     * dedicated methods, ensuring proper separation of concerns and maintainable
     * check-in interface configuration management.
     * </p>
     * <p>
     * Panel integration with the main frame ensures proper positioning within the
     * overall interface hierarchy while maintaining check-in control accessibility
     * and operational workflow efficiency.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation and resource management
     * @param controller the system controller providing access to flight operations, check-in management, and administrative capabilities
     */
    @Override
    protected void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        setChangeStatusButton(controller, callingObjects);
        setGateButton(controller);
        setConfirmButton(controller);
        setSetDelayButton(controller);

        constraints.setConstraints(0, 3, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getGridBagConstraints());

        confirmPanel.setVisible(true);
    }

    /**
     * Creates and configures the gate assignment button with gate management integration.
     * <p>
     * This method establishes the gate management control that enables check-in staff
     * to assign or modify flight gate allocations through integrated gate selection
     * interfaces. The button provides immediate access to comprehensive gate management
     * capabilities with integrated validation and operational coordination.
     * </p>
     * <p>
     * The gate assignment button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Gate Selection Integration:</strong> Direct integration with gate management systems for real-time gate availability</li>
     *   <li><strong>Dynamic Button State:</strong> Button behavior adapts based on current gate assignment status</li>
     *   <li><strong>Operational Coordination:</strong> Ensures proper integration with flight management systems for gate updates</li>
     *   <li><strong>Interface Management:</strong> Proper component lifecycle management for gate selection dialogs</li>
     *   <li><strong>Visual Consistency:</strong> Maintains application design consistency with proper button styling and positioning</li>
     * </ul>
     * <p>
     * Gate management integration creates dynamic gate assignment capabilities based on
     * current flight gate status. For flights without assigned gates, the button triggers
     * new gate assignment processes, while flights with existing gates provide gate
     * modification capabilities through dedicated chooser interfaces.
     * </p>
     * <p>
     * Component lifecycle management ensures that gate selection dialogs are properly
     * created, displayed, and managed throughout the check-in workflow. The button
     * maintains references to active gate selection components for proper resource
     * management and cleanup operations.
     * </p>
     * <p>
     * Operational coordination includes integration with the controller's gate management
     * capabilities to ensure that gate assignments are properly validated, applied, and
     * coordinated across the airport management system. This ensures operational
     * consistency and proper gate allocation management.
     * </p>
     * <p>
     * Visual integration includes proper button styling consistent with check-in
     * interface design patterns. The button is configured as non-focusable to maintain
     * clean interface behavior and positioned appropriately within the confirmation
     * panel layout structure.
     * </p>
     * <p>
     * Button state management includes automatic disabling after gate assignment
     * operations to prevent operational conflicts and provide clear visual feedback
     * about gate assignment completion.
     * </p>
     *
     * @param controller the system controller providing access to gate management and operational coordination capabilities
     */
    protected void setGateButton (Controller controller) {

        gateButton = new JButton("GATE");

        gateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                setGate(controller);

                gateButton.setEnabled(false);
            }
        });

        gateButton.setFocusable(false);
        gateButton.setVisible(true);

        constraints.setConstraints(3, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(gateButton, constraints.getGridBagConstraints());
    }

    /**
     * Processes gate assignment operations with dynamic behavior based on current gate status.
     * <p>
     * This method handles gate assignment operations by determining the appropriate action
     * based on the current gate assignment status of the flight. The method provides
     * different functionality for new gate assignments versus gate modifications,
     * ensuring appropriate operational behavior for various gate management scenarios.
     * </p>
     * <p>
     * The gate assignment process includes:
     * </p>
     * <ul>
     *   <li><strong>Status Evaluation:</strong> Determines current gate assignment status based on button text</li>
     *   <li><strong>New Gate Assignment:</strong> Initiates new gate assignment process for flights without assigned gates</li>
     *   <li><strong>Gate Modification:</strong> Provides gate change functionality for flights with existing gate assignments</li>
     *   <li><strong>Component Integration:</strong> Coordinates with appropriate gate management components based on operation type</li>
     * </ul>
     * <p>
     * New gate assignment processing delegates to the gate controller's new gate
     * functionality, providing comprehensive gate assignment workflows that include
     * gate availability checking, assignment validation, and operational coordination
     * with other airport systems.
     * </p>
     * <p>
     * Gate modification operations create dedicated {@link GateChooser} components
     * that provide staff with interfaces for selecting alternative gates. The chooser
     * integrates with gate availability systems to ensure only appropriate gates
     * are presented for selection.
     * </p>
     * <p>
     * Component integration ensures that gate assignment operations are properly
     * coordinated with the check-in interface lifecycle. The method maintains
     * references to active gate selection components for proper resource management
     * and cleanup operations.
     * </p>
     * <p>
     * The method provides seamless integration between different gate management
     * workflows while maintaining consistent user experience and operational
     * behavior across various gate assignment scenarios.
     * </p>
     *
     * @param controller the system controller providing access to gate management capabilities and operational coordination
     */
    protected void setGate (Controller controller) {

        if (gateButton.getText().equals("GATE")) controller.getGateController().newGate(gateButton, controller, this);
        else gateChooser = new GateChooser(controller, gateButton);
    }

    /**
     * Creates and configures the check-in confirmation button with batch processing integration.
     * <p>
     * This method establishes the check-in confirmation control that enables check-in staff
     * to process selected passenger check-ins in batch operations. The button integrates
     * comprehensive check-in processing logic including luggage coordination, operational
     * status updates, and user feedback for successful check-in completion.
     * </p>
     * <p>
     * The check-in confirmation button functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Batch Processing:</strong> Simultaneous processing of multiple passenger check-ins</li>
     *   <li><strong>Luggage Integration:</strong> Automatic coordination with luggage check-in systems</li>
     *   <li><strong>Status Updates:</strong> Comprehensive passenger and luggage status updates</li>
     *   <li><strong>Collection Management:</strong> Proper management of passenger selection collections</li>
     *   <li><strong>User Feedback:</strong> Clear confirmation messaging for successful operations</li>
     * </ul>
     * <p>
     * Batch processing functionality leverages the flight controller's check-in processing
     * capabilities to handle multiple passengers simultaneously. The processing includes
     * validation of selected passengers, status updates, and coordination with related
     * operational systems.
     * </p>
     * <p>
     * Luggage integration ensures that passenger check-in operations are properly
     * coordinated with luggage processing systems. The method processes luggage check-in
     * information for all successfully checked-in passengers, maintaining operational
     * integrity across passenger and baggage handling systems.
     * </p>
     * <p>
     * Collection management includes proper handling of passenger selection lists,
     * ensuring that check-in operations are applied to the correct passengers while
     * maintaining clear operational state throughout the processing workflow.
     * </p>
     * <p>
     * User feedback provides immediate confirmation of successful check-in operations
     * through {@link FloatingMessage} components, enabling check-in staff to understand
     * operation completion and proceed with subsequent operational tasks.
     * </p>
     * <p>
     * The button is configured with appropriate styling and positioning within the
     * check-in control panel for optimal operational access and workflow efficiency.
     * Visual consistency is maintained with other operational controls while ensuring
     * prominent visibility for this critical operational function.
     * </p>
     *
     * @param controller the system controller providing access to check-in processing, luggage coordination, and operational status management
     */
    protected void setConfirmButton (Controller controller) {

        confirmButton = new JButton("CONFERMA");

        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                ArrayList<ArrayList<String>> megaArrayList = controller.getFlightController().setCheckins(truePassengerPanels, falsePassengerPanels);

                for (int i = 0; i < truePassengerPanels.size(); i++)
                    truePassengerPanels.get(i).getLuggagesView().setLuggagesIds(megaArrayList.get(i));


                new FloatingMessage("Checkins effettuati con successo", confirmButton, FloatingMessage.SUCCESS_MESSAGE);

                truePassengerPanels = new ArrayList<>();
                falsePassengerPanels = new ArrayList<>();
            }
        });

        confirmButton.setFocusable(false);
        confirmButton.setVisible(true);

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(confirmButton, constraints.getGridBagConstraints());
    }

    /**
     * Configures interactive check-in checkboxes for individual passenger selection.
     * <p>
     * This method establishes the interactive check-in control system by adding checkbox
     * controls to each passenger panel, enabling check-in staff to individually select
     * passengers for batch processing operations. The method integrates current passenger
     * check-in status and provides real-time collection management for operational efficiency.
     * </p>
     * <p>
     * The check-in checkbox configuration process includes:
     * </p>
     * <ul>
     *   <li><strong>Status Integration:</strong> Loads current passenger check-in status from flight controller</li>
     *   <li><strong>Interactive Controls:</strong> Adds checkbox controls to each passenger panel for individual selection</li>
     *   <li><strong>Event Handling:</strong> Configures checkbox event handlers for real-time collection management</li>
     *   <li><strong>Collection Synchronization:</strong> Maintains synchronized passenger collections based on checkbox states</li>
     *   <li><strong>Operational State Management:</strong> Provides clear operational state tracking throughout check-in process</li>
     * </ul>
     * <p>
     * Status integration retrieves current passenger check-in status from the flight
     * controller to ensure that checkbox controls accurately reflect operational reality.
     * This prevents operational conflicts and provides check-in staff with accurate
     * information about passenger check-in states.
     * </p>
     * <p>
     * Interactive controls are added to each passenger panel through the addCheckinCheckBox
     * method, providing consistent checkbox functionality across all passengers. The
     * checkboxes are initialized with current check-in status to provide accurate
     * operational representation.
     * </p>
     * <p>
     * Event handling configuration includes action listeners for each checkbox that
     * automatically manage passenger collections based on checkbox state changes.
     * Selected passengers are added to the check-in collection while deselected
     * passengers are moved to the non-check-in collection.
     * </p>
     * <p>
     * Collection synchronization ensures that passenger selection state is properly
     * maintained throughout the check-in process. The method handles addition and
     * removal operations to maintain accurate operational state and prevent
     * processing conflicts.
     * </p>
     * <p>
     * The method provides real-time operational state management, enabling check-in
     * staff to dynamically adjust passenger selections and immediately see the impact
     * on operational collections and processing readiness.
     * </p>
     *
     * @param controller the system controller providing access to current passenger check-in status and flight management data
     */
    protected void setCheckinCheckBoxes (Controller controller) {

        for (int i = 0; i < passengerPanels.size(); i++) {
            passengerPanels.get(i).addCheckinCheckBox(controller.getFlightController().getPassengerCheckedin(i));

            int finalI = i;

            passengerPanels.get(i).getCheckinCheckBox().addActionListener(new ActionListener() {

                @Override
                public void actionPerformed (ActionEvent e) {

                    if (passengerPanels.get(finalI).getCheckinCheckBox().isSelected()) {
                        truePassengerPanels.add(passengerPanels.get(finalI));
                        falsePassengerPanels.remove(passengerPanels.get(finalI));
                    } else {
                        falsePassengerPanels.add(passengerPanels.get(finalI));
                        truePassengerPanels.remove(passengerPanels.get(finalI));
                    }
                }
            });
        }
    }

    /**
     * Performs comprehensive resource cleanup and disposal operations for check-in specific components.
     * <p>
     * This method implements enhanced disposal functionality by extending the parent class
     * disposal operations with specialized cleanup for check-in interface components.
     * The disposal process ensures that all check-in resources including gate chooser
     * dialogs, passenger panel components, and check-in specific controls are properly
     * cleaned up to prevent resource leaks and ensure optimal system performance.
     * </p>
     * <p>
     * The enhanced disposal operations include:
     * </p>
     * <ul>
     *   <li><strong>Controller Resource Management:</strong> Conditional cleanup of flight and booking controller data based on disposal flag</li>
     *   <li><strong>Passenger Panel Cleanup:</strong> Comprehensive disposal of seat choosers and luggage views for all passenger panels</li>
     *   <li><strong>Gate Management Cleanup:</strong> Specialized cleanup for gate chooser dialogs and gate button state reset</li>
     *   <li><strong>Administrative Component Cleanup:</strong> Inherited cleanup for status chooser dialogs and administrative controls</li>
     *   <li><strong>Control State Reset:</strong> Proper reset of check-in control states for potential reuse</li>
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
     * managed by the check-in interface. This prevents resource leaks from sub-components
     * that may have independent lifecycles and resource requirements.
     * </p>
     * <p>
     * Gate management cleanup includes specialized handling for gate chooser dialogs
     * and gate button state management. The cleanup process ensures that gate chooser
     * dialogs are properly disposed and that the gate button is reset to enabled state
     * for potential interface reuse.
     * </p>
     * <p>
     * Administrative component cleanup includes inherited cleanup for status chooser
     * dialogs and other administrative controls from the parent class. This ensures
     * complete cleanup of all inherited administrative interface components.
     * </p>
     * <p>
     * Control state reset includes re-enabling operational controls such as the gate
     * button and status button while ensuring that associated dialog components are
     * properly disposed. This cleanup ensures that check-in interface components are
     * left in a clean state that supports proper resource reclamation.
     * </p>
     * <p>
     * The disposal process is designed to be safe for multiple invocations and
     * handles null references gracefully to prevent exceptions during cleanup
     * operations. This ensures robust cleanup behavior regardless of the current
     * state of check-in interface components.
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

        gateButton.setEnabled(true);
        if (gateChooser != null) gateChooser.getMainFrame().dispose();

        statusButton.setEnabled(true);
        if (statusChooser != null) statusChooser.getMainFrame().dispose();
    }

    /**
     * Sets the gate chooser component for proper resource management integration.
     * <p>
     * This method provides a mechanism for external components to register gate chooser
     * instances with the check-in interface, enabling proper resource management and
     * cleanup coordination. The method is typically called by gate management components
     * to ensure that gate selection dialogs are properly tracked and disposed during
     * interface cleanup operations.
     * </p>
     * <p>
     * Gate chooser registration enables the check-in interface to maintain references
     * to active gate selection components, ensuring that these dialogs are properly
     * cleaned up during interface disposal operations. This prevents resource leaks
     * and ensures complete cleanup of all check-in related components.
     * </p>
     * <p>
     * The method supports the gate management workflow by providing a clean integration
     * point between gate assignment operations and interface resource management.
     * External gate management components can register their dialog instances to
     * participate in the interface cleanup lifecycle.
     * </p>
     *
     * @param gateChooser the gate chooser dialog instance to be registered for resource management
     */
    public void setGateChooser(GateChooser gateChooser) {

        this.gateChooser = gateChooser;
    }
}