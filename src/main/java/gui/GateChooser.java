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
 * The dialog is typically instantiated during flight check-in operations when flights require
 * gate assignments for passenger boarding. It integrates seamlessly with the {@link CheckinPassengers}
 * interface and provides immediate feedback through button state management and automatic dialog
 * disposal upon successful gate assignment.
 * </p>
 * <p>
 * Integration with the {@link controller.GateController} ensures that gate assignments are immediately
 * persisted to the database and available across all system components for operational
 * coordination and passenger information systems.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see controller.GateController
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
     * </ul>
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
     * Selection validation prevents assignment of the default "GATE" option (index 0) which
     * serves as a placeholder and does not represent a valid gate selection. Only selections
     * with index greater than 0 (representing gates 1-20) are processed for assignment.
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
     *
     * @return the main JFrame instance containing the gate selection interface
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}