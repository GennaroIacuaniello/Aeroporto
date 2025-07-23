package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Specialized dialog for gate selection and assignment during flight check-in operations.
 * <p>
 * This class provides a user-friendly interface for administrative staff to select and assign
 * gates to flights during check-in procedures. It presents a dropdown selection of available
 * gates (1-20) and handles the assignment process through integration with the gate management
 * system and database persistence layer.
 * </p>
 * <p>
 * The GateChooser class supports comprehensive gate selection functionality including:
 * </p>
 * <ul>
 *   <li><strong>Gate Selection Interface:</strong> Dropdown menu presenting all available gate options</li>
 *   <li><strong>Assignment Processing:</strong> Direct integration with gate controller for database updates</li>
 *   <li><strong>Button State Management:</strong> Automatic re-enabling of calling buttons after operations</li>
 *   <li><strong>Window Lifecycle Management:</strong> Proper cleanup and disposal after gate assignment</li>
 *   <li><strong>Always-on-Top Behavior:</strong> Ensures dialog remains visible during gate selection</li>
 *   <li><strong>Input Validation:</strong> Prevents invalid gate selections and ensures proper assignment</li>
 * </ul>
 * <p>
 * The dialog is typically instantiated during flight check-in operations when flights require
 * gate assignments for passenger boarding. It integrates seamlessly with the {@link CheckinPassengers}
 * interface and provides immediate feedback through button state management and automatic dialog
 * disposal upon successful gate assignment.
 * </p>
 * <p>
 * Gate selection functionality includes:
 * </p>
 * <ul>
 *   <li><strong>Available Gates Display:</strong> Shows all gates numbered 1-20 for selection</li>
 *   <li><strong>Default Selection Prevention:</strong> Ensures valid gate selection before assignment</li>
 *   <li><strong>Immediate Assignment:</strong> Processes gate assignment immediately upon confirmation</li>
 *   <li><strong>Database Integration:</strong> Persists gate assignments through controller layer</li>
 * </ul>
 * <p>
 * The interface design follows airport operational workflows with clear labeling, intuitive
 * controls, and immediate feedback mechanisms. The dialog uses {@link FlowLayout} with center
 * alignment for optimal component organization and user experience.
 * </p>
 * <p>
 * Window management includes comprehensive event listening for proper resource cleanup when
 * the dialog is closed through window controls rather than confirmation button. This ensures
 * that calling buttons are properly re-enabled regardless of dismissal method.
 * </p>
 * <p>
 * Integration with the {@link GateController} ensures that gate assignments are immediately
 * persisted to the database and available across all system components for operational
 * coordination and passenger information systems.
 * </p>
 * <p>
 * The class is designed for single-use scenarios where each instance handles one gate
 * assignment operation. Multiple instances can be created simultaneously for different
 * flights requiring gate assignments during concurrent check-in operations.
 * </p>
 * <p>
 * Visual consistency is maintained with the overall application design through proper
 * button styling, layout management, and font usage that matches administrative interface
 * standards throughout the airport management system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see GateController
 * @see CheckinPassengers
 * @see Controller
 * @see JFrame
 * @see JComboBox
 */
public class GateChooser {

    /**
     * Main dialog window for gate selection interface.
     * <p>
     * This JFrame serves as the container for the gate selection interface,
     * configured with always-on-top behavior to ensure visibility during
     * check-in operations. The frame uses FlowLayout with center alignment
     * for optimal component organization.
     * </p>
     */
    private final JFrame mainFrame;
    
    /**
     * Dropdown selection component for gate choice.
     * <p>
     * This JComboBox presents available gates (1-20) for selection by
     * administrative staff. The component includes a default "GATE" option
     * that prevents invalid selections and ensures proper gate assignment.
     * </p>
     */
    private final JComboBox comboBox;

    /**
     * Constructs a new GateChooser dialog for gate selection and assignment operations.
     * <p>
     * This constructor creates and displays a comprehensive gate selection interface that
     * enables administrative staff to assign gates to flights during check-in procedures.
     * The constructor initializes the dialog window, configures the gate selection components,
     * and establishes event handling for proper gate assignment workflow.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Window Configuration:</strong> Creating and configuring the main dialog frame with appropriate properties</li>
     *   <li><strong>Event Listener Setup:</strong> Establishing window event handling for proper resource management</li>
     *   <li><strong>Interface Components:</strong> Creating and configuring gate selection dropdown and confirmation controls</li>
     *   <li><strong>Gate Options Population:</strong> Loading all available gates (1-20) into the selection dropdown</li>
     *   <li><strong>Action Handler Configuration:</strong> Setting up confirmation button event handling for gate assignment</li>
     *   <li><strong>Dialog Display:</strong> Making the dialog immediately visible for user interaction</li>
     * </ul>
     * <p>
     * Window configuration establishes a JFrame with "Gate Chooser" title, FlowLayout with
     * center alignment, and always-on-top behavior to ensure visibility during check-in
     * operations. The window is sized appropriately (500x200) for comfortable gate selection.
     * </p>
     * <p>
     * Event listener setup includes comprehensive WindowListener implementation that handles
     * window closing events to ensure the calling button is properly re-enabled when the
     * dialog is dismissed through window controls rather than confirmation button.
     * </p>
     * <p>
     * Interface components include a descriptive label explaining gate selection requirements,
     * a JComboBox populated with gate options (1-20), and a confirmation button for
     * processing the selected gate assignment.
     * </p>
     * <p>
     * Gate options population creates a dropdown menu with a default "GATE" option followed
     * by numbered gate options from 1 to 20. The default option prevents accidental invalid
     * selections and ensures proper gate assignment validation.
     * </p>
     * <p>
     * Action handler configuration establishes event handling for the confirmation button
     * that processes the selected gate through the setGate method, ensuring proper validation
     * and database persistence of gate assignments.
     * </p>
     * <p>
     * The constructor completes by making the dialog immediately visible, enabling staff
     * to begin gate selection without additional interface interactions. The dialog becomes
     * the focus of user interaction until gate assignment is completed or cancelled.
     * </p>
     *
     * @param controller the system controller providing access to gate management and database operations
     * @param callingButton the button that triggered gate selection, used for state management and re-enabling after operations
     */
    public GateChooser(Controller controller, JButton callingButton) {

        mainFrame = new JFrame("Gate Chooser");
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

        JLabel label = new JLabel("Attualmente non ci sono gate liberi, selezionare un gate tra 1 e 20:");
        mainFrame.add(label);

        comboBox = new JComboBox();

        comboBox.addItem("GATE");

        for (int i = 1; i <= 20; i++) {

            comboBox.addItem(i);
        }
        comboBox.setSelectedIndex(0);
        mainFrame.add(comboBox);

        JButton confirmButton = new JButton("CONFERMA");
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                setGate(comboBox.getSelectedIndex(), controller, callingButton);
            }
        });

        confirmButton.setFocusable(false);
        mainFrame.add(confirmButton);

        mainFrame.setSize(500, 200);
        mainFrame.setVisible(true);
    }

    /**
     * Processes gate assignment with validation and database persistence.
     * <p>
     * This method handles the gate assignment process by validating the selected gate,
     * coordinating with the gate controller for database persistence, and managing
     * the dialog lifecycle including button state restoration and window disposal.
     * The method ensures that only valid gate selections result in database updates.
     * </p>
     * <p>
     * The gate assignment process includes:
     * </p>
     * <ul>
     *   <li><strong>Selection Validation:</strong> Ensures that a valid gate (non-default) has been selected</li>
     *   <li><strong>Database Persistence:</strong> Delegates to gate controller for immediate database update</li>
     *   <li><strong>Button State Management:</strong> Re-enables the calling button for continued operations</li>
     *   <li><strong>Dialog Disposal:</strong> Closes and disposes the dialog window after successful assignment</li>
     * </ul>
     * <p>
     * Selection validation prevents assignment of the default "GATE" option (index 0) which
     * serves as a placeholder and does not represent a valid gate selection. Only selections
     * with index greater than 0 (representing gates 1-20) are processed for assignment.
     * </p>
     * <p>
     * Database persistence is handled through the {@link GateController#setGate(int, Controller)}
     * method, which ensures immediate database update and availability of gate assignments
     * across all system components for operational coordination.
     * </p>
     * <p>
     * Button state management includes re-enabling the calling button to restore normal
     * operational state after gate assignment completion. This ensures that check-in
     * workflows can continue without interface disruption.
     * </p>
     * <p>
     * Dialog disposal includes proper cleanup of the dialog window through the dispose()
     * method, ensuring that system resources are freed and the interface returns to
     * normal operational state after gate assignment completion.
     * </p>
     * <p>
     * The method design ensures that invalid selections (default option) do not trigger
     * any processing, providing feedback through interface behavior while preventing
     * erroneous database updates or system state changes.
     * </p>
     *
     * @param id the selected index from the dropdown menu, where values greater than 0 represent valid gates (1-20)
     * @param controller the system controller for coordinating gate assignment with database persistence
     * @param callingButton the button that triggered gate selection, restored to enabled state after assignment
     */
    private void setGate (int id, Controller controller, JButton callingButton) {

        if (id > 0) {

            controller.getGateController().setGate(id, controller);

            callingButton.setEnabled(true);

            mainFrame.dispose();
        }
    }

    /**
     * Provides access to the dialog's main window frame for resource management.
     * <p>
     * This method returns the main JFrame instance that contains the gate selection
     * interface, enabling external components to perform window management operations
     * such as disposal, positioning, or state management. The method is typically
     * used by calling components for proper resource cleanup during interface disposal.
     * </p>
     * <p>
     * Frame access enables external resource management for scenarios where the
     * gate selection dialog needs to be programmatically closed or managed as part
     * of larger interface cleanup operations. This is particularly important in
     * the {@link CheckinPassengers} interface disposal process.
     * </p>
     * <p>
     * The returned frame reference provides access to standard JFrame operations
     * including visibility control, disposal, positioning, and property management
     * that may be required for integration with the broader interface lifecycle.
     * </p>
     *
     * @return the main JFrame instance containing the gate selection interface
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}