package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

/**
 * Flight status selection dialog providing comprehensive administrative flight status management capabilities for operational control.
 * <p>
 * This class provides a specialized modal dialog interface for flight status modification operations within
 * the airport management system's administrative workflows. The StatusChooser serves as the primary interface
 * for flight status updates, offering comprehensive status selection capabilities, validation processing,
 * and operational workflow coordination through an intuitive dropdown-based selection system optimized
 * for administrative flight management and operational oversight operations.
 * </p>
 * <p>
 * The StatusChooser class provides comprehensive flight status management capabilities including:
 * </p>
 * <ul>
 *   <li><strong>Status Selection Interface:</strong> Comprehensive dropdown menu with all available flight operational status options</li>
 *   <li><strong>Administrative Validation:</strong> Input validation with default selection prevention for operational accuracy</li>
 *   <li><strong>System Integration:</strong> Direct integration with flight management controllers for immediate status updates</li>
 *   <li><strong>Workflow Coordination:</strong> Automatic navigation management for specific status transitions</li>
 *   <li><strong>Error Handling:</strong> Comprehensive error feedback through floating message notifications</li>
 *   <li><strong>Resource Management:</strong> Proper dialog lifecycle management with button state coordination</li>
 *   <li><strong>Modal Behavior:</strong> Always-on-top dialog design ensuring administrative focus and operational context</li>
 * </ul>
 * <p>
 * The interface is designed with administrative efficiency optimization, providing flight managers with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Status Selection:</strong> Clear dropdown interface with Italian labels for administrative understanding</li>
 *   <li><strong>Operational Safety:</strong> Default selection prevention ensuring valid status transitions</li>
 *   <li><strong>Immediate Feedback:</strong> Real-time validation and confirmation through integrated messaging system</li>
 *   <li><strong>Workflow Integration:</strong> Seamless coordination with administrative navigation and resource management</li>
 *   <li><strong>Professional Presentation:</strong> Clean modal dialog design optimized for administrative operations</li>
 *   <li><strong>System Coordination:</strong> Direct integration with flight management systems for operational continuity</li>
 * </ul>
 * <p>
 * Dialog architecture utilizes {@link FlowLayout} with center alignment for optimal component organization
 * and professional presentation. The layout ensures proper component positioning and maintains visual
 * consistency throughout flight status modification operations within administrative workflows.
 * </p>
 * <p>
 * Status selection includes comprehensive flight operational status options:
 * </p>
 * <ul>
 *   <li><strong>PROGRAMMED:</strong> Scheduled flight status for planned operations</li>
 *   <li><strong>CANCELLED:</strong> Cancelled flight status with automatic navigation coordination</li>
 *   <li><strong>DEPARTED:</strong> Departed flight status indicating successful departure</li>
 *   <li><strong>ABOUT_TO_DEPART:</strong> Pre-departure status for imminent flight operations</li>
 *   <li><strong>LANDED:</strong> Landed flight status for completed arrival operations</li>
 * </ul>
 * <p>
 * Administrative validation ensures that only valid status selections are processed through
 * comprehensive default selection prevention and input validation. The system prevents
 * accidental invalid status changes while providing clear feedback about selection requirements
 * throughout administrative flight status management operations.
 * </p>
 * <p>
 * System integration provides direct communication with {@link Controller} and flight management
 * systems for immediate status updates and operational coordination. The integration ensures
 * real-time status persistence and maintains operational consistency throughout administrative
 * flight management workflows.
 * </p>
 * <p>
 * Workflow coordination includes intelligent navigation management that automatically redirects
 * administrators to appropriate interfaces based on status selection outcomes. The coordination
 * supports operational continuity and ensures proper administrative workflow transitions.
 * </p>
 * <p>
 * Error handling utilizes comprehensive {@link FloatingMessage} integration for clear operational
 * feedback during status update failures. The error handling provides immediate administrative
 * awareness of system issues while maintaining dialog context for continued operations.
 * </p>
 * <p>
 * Resource management includes sophisticated button state coordination and dialog lifecycle
 * management that ensures proper cleanup and interface state restoration throughout status
 * selection operations and administrative workflow transitions.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through comprehensive
 * controller integration, enabling access to flight status management services, administrative
 * coordination workflows, and operational status persistence. The integration supports administrative
 * flight management while maintaining proper separation of concerns and interface consistency.
 * </p>
 * <p>
 * Modal behavior includes always-on-top window configuration that ensures administrative focus
 * and prevents operational disruption during critical status modification operations. The modal
 * design maintains operational context while providing clear administrative guidance throughout
 * flight status management workflows.
 * </p>
 * <p>
 * The StatusChooser serves as a critical component of the administrative flight management ecosystem,
 * providing essential status modification capabilities while maintaining interface consistency,
 * operational safety, and administrative effectiveness throughout flight oversight and management
 * operations within the airport management system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Controller
 * @see BookingPageAdmin
 * @see FloatingMessage
 * @see JFrame
 * @see JComboBox
 * @see WindowListener
 * @see ActionListener
 */
public class StatusChooser {

    /**
     * Main dialog frame providing the flight status selection interface container.
     * <p>
     * This final JFrame serves as the primary dialog window for flight status selection operations,
     * configured with modal behavior, always-on-top positioning, and center-aligned layout for
     * optimal administrative interaction. The frame provides the foundational container for all
     * status selection interface components and maintains proper window management throughout
     * administrative flight status modification workflows.
     * </p>
     */
    private final JFrame mainFrame;

    /**
     * Confirmation button for processing selected flight status changes.
     * <p>
     * This final JButton provides the primary action trigger for flight status modification
     * operations, integrating comprehensive validation, controller communication, and error
     * handling capabilities. The button coordinates status update processing, administrative
     * feedback, and workflow navigation throughout flight status management operations.
     * </p>
     */
    private final JButton confirmButton;

    /**
     * Status selection dropdown containing all available flight operational status options.
     * <p>
     * This final JComboBox provides the primary status selection interface, populated with
     * comprehensive flight operational status options including PROGRAMMED, CANCELLED, DEPARTED,
     * ABOUT_TO_DEPART, and LANDED. The dropdown includes default selection prevention and
     * validation integration for operational accuracy throughout administrative status management.
     * </p>
     */
    private final JComboBox comboBox;

    /**
     * Constructs a new StatusChooser dialog with comprehensive flight status selection capabilities and administrative workflow integration.
     * <p>
     * This constructor initializes the complete flight status selection interface by creating the modal
     * dialog, configuring status selection options, and establishing comprehensive event handling
     * for administrative flight status management operations. The constructor creates a fully functional
     * status selection dialog ready for immediate administrative interaction with integrated validation,
     * error handling, and workflow coordination throughout flight management operations.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Dialog Configuration:</strong> Main frame creation with modal behavior and always-on-top positioning</li>
     *   <li><strong>Window Event Management:</strong> Comprehensive WindowListener setup for proper resource management</li>
     *   <li><strong>Interface Components:</strong> Status selection label, dropdown, and confirmation button creation</li>
     *   <li><strong>Status Options Population:</strong> Comprehensive flight status options loading with default selection prevention</li>
     *   <li><strong>Event Handler Integration:</strong> Confirmation button action listener with validation and controller integration</li>
     *   <li><strong>Dialog Presentation:</strong> Window sizing, button state management, and immediate visibility activation</li>
     * </ul>
     * <p>
     * Dialog configuration establishes a JFrame with "Flight status chooser" title, center-aligned
     * FlowLayout, and always-on-top behavior to ensure administrative focus during critical status
     * modification operations. The configuration maintains modal dialog behavior while providing
     * optimal accessibility throughout administrative flight management workflows.
     * </p>
     * <p>
     * Window event management includes comprehensive WindowListener implementation that handles
     * dialog closure events through window controls, ensuring proper calling button re-enablement
     * and resource cleanup when status selection is cancelled or interrupted through administrative
     * interface interactions rather than confirmation processing.
     * </p>
     * <p>
     * Interface components creation includes Italian-localized instructional label ("Seleziona lo stato:")
     * for clear administrative guidance, comprehensive status selection dropdown with all operational
     * status options, and confirmation button ("CONFERMA") with integrated validation and processing
     * capabilities for complete administrative status management functionality.
     * </p>
     * <p>
     * Status options population creates a dropdown menu with comprehensive flight operational
     * status options including default "STATO" selection for validation purposes, followed by
     * PROGRAMMED, CANCELLED, DEPARTED, ABOUT_TO_DEPART, and LANDED options. The population
     * ensures complete administrative access to all flight status management capabilities.
     * </p>
     * <p>
     * Event handler integration establishes sophisticated ActionListener functionality for the
     * confirmation button that includes:
     * </p>
     * <ul>
     *   <li><strong>Default Selection Validation:</strong> Prevents processing of invalid default selections</li>
     *   <li><strong>Controller Integration:</strong> Direct communication with flight management systems</li>
     *   <li><strong>Success Processing:</strong> Calling button re-enablement and dialog disposal on successful updates</li>
     *   <li><strong>Navigation Coordination:</strong> Automatic home navigation for cancelled flight status selections</li>
     *   <li><strong>Error Handling:</strong> FloatingMessage error notifications for unsuccessful status update operations</li>
     * </ul>
     * <p>
     * Default selection validation ensures that administrators cannot accidentally process
     * invalid status changes by checking combo box selection index and preventing action
     * processing when the default "STATO" option remains selected, maintaining operational
     * safety throughout administrative flight status management workflows.
     * </p>
     * <p>
     * Controller integration utilizes <code>controller.{@link Controller#getFlightController()}.{@link controller.FlightController#setFlightStatus(Object)}</code>
     * for direct flight status updates with immediate database persistence and system-wide
     * status coordination. The integration ensures real-time operational status updates and
     * maintains consistency across all airport management system components.
     * </p>
     * <p>
     * Success processing includes automatic calling button re-enablement through setEnabled(true)
     * and proper dialog disposal through dispose() method, ensuring clean interface state
     * restoration and resource cleanup following successful flight status modification operations.
     * </p>
     * <p>
     * Navigation coordination provides intelligent workflow management that automatically
     * redirects administrators to the home interface when cancelled flight status is selected
     * (combo box index 2), utilizing {@link Controller#goHome(List)} for proper navigation
     * coordination and administrative workflow continuity.
     * </p>
     * <p>
     * Error handling creates comprehensive {@link FloatingMessage} notifications with Italian
     * error messages when flight status updates fail, providing immediate administrative
     * feedback while maintaining dialog context for continued operational attempts and
     * administrative guidance throughout error resolution processes.
     * </p>
     * <p>
     * Dialog presentation includes optimal window sizing (500x200 pixels) for comfortable
     * administrative interaction, calling button disablement to prevent concurrent operations,
     * and immediate visibility activation for responsive administrative access to flight
     * status management capabilities.
     * </p>
     * <p>
     * The constructor completes by establishing a fully functional flight status selection dialog
     * ready for immediate administrative interaction, providing comprehensive status management
     * capabilities, validation processing, and workflow coordination throughout flight oversight
     * and administrative management operations within the airport management system.
     * </p>
     *
     * @param controller the system controller providing access to flight management services and status update functionality
     * @param callingButton the administrative button that triggered status selection for proper state management and interface coordination
     * @param disposableObjects the list of navigation objects for proper resource management and workflow coordination during status transitions
     */
    public StatusChooser(Controller controller, JButton callingButton, List<DisposableObject> disposableObjects) {

        mainFrame = new JFrame("Flight status chooser");
        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainFrame.setAlwaysOnTop(true);

        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                //
            }

            @Override
            public void windowClosing(WindowEvent e) {
                callingButton.setEnabled(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //
            }
        });

        JLabel label = new JLabel("Seleziona lo stato:");
        mainFrame.add(label);

        comboBox = new JComboBox();

        comboBox.addItem("STATO");

        comboBox.addItem("PROGRAMMED");
        comboBox.addItem("CANCELLED");
        comboBox.addItem("DEPARTED");
        comboBox.addItem("ABOUT_TO_DEPART");
        comboBox.addItem("LANDED");

        comboBox.setSelectedIndex(0);
        mainFrame.add(comboBox);

        confirmButton = new JButton("CONFERMA");
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {


                if (comboBox.getSelectedIndex() != 0) {

                    if (controller.getFlightController().setFlightStatus(comboBox.getSelectedItem()) == 1) {

                        callingButton.setEnabled(true);

                        if (comboBox.getSelectedIndex() == 2) controller.goHome(disposableObjects);

                        mainFrame.dispose();
                    } else
                        new FloatingMessage("Non è stato possibile cambiare lo stato del volo a: " + comboBox.getSelectedItem(),
                                confirmButton, FloatingMessage.ERROR_MESSAGE);
                }

            }
        });

        confirmButton.setFocusable(false);
        mainFrame.add(confirmButton);

        callingButton.setEnabled(false);

        mainFrame.setSize(500, 200);
        mainFrame.setVisible(true);
    }

    /**
     * Returns the main dialog frame for external component integration and window management operations.
     * <p>
     * This method provides access to the underlying JFrame component for administrative interface
     * coordination, window management operations, and external component integration requirements.
     * The method enables proper dialog lifecycle management and supports advanced administrative
     * interface coordination throughout flight status management and operational oversight workflows.
     * </p>
     * <p>
     * The main frame access includes:
     * </p>
     * <ul>
     *   <li><strong>Window Management:</strong> Direct access to dialog window properties and configuration</li>
     *   <li><strong>Component Integration:</strong> Support for external component coordination and interface management</li>
     *   <li><strong>Lifecycle Coordination:</strong> Enhanced dialog lifecycle management and resource coordination</li>
     *   <li><strong>Administrative Integration:</strong> Support for advanced administrative workflow coordination and interface management</li>
     * </ul>
     * <p>
     * Window management capabilities provide direct access to dialog window properties including
     * size, position, visibility, and modal behavior configuration for advanced administrative
     * interface coordination and specialized workflow requirements throughout flight status
     * management and operational oversight operations.
     * </p>
     * <p>
     * Component integration support enables external components to coordinate with the status
     * chooser dialog through direct frame access, supporting advanced administrative interface
     * architectures and specialized workflow coordination requirements throughout airport
     * management system operations.
     * </p>
     * <p>
     * Lifecycle coordination includes enhanced dialog lifecycle management that supports
     * external cleanup, resource management, and advanced disposal coordination for specialized
     * administrative workflows and complex interface interaction scenarios throughout flight
     * management and operational oversight operations.
     * </p>
     * <p>
     * The method serves administrative interface coordination requirements while maintaining
     * proper encapsulation and supporting advanced flight status management workflows throughout
     * the airport management system's administrative and operational oversight capabilities.
     * </p>
     *
     * @return the main dialog frame for external component integration and administrative window management operations
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}