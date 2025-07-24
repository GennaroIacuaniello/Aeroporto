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
 * The interface is designed with administrative efficiency optimization, providing flight managers with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Status Selection:</strong> Clear dropdown interface with Italian labels for administrative understanding</li>
 *   <li><strong>Operational Safety:</strong> Default selection prevention ensuring valid status transitions</li>
 *   <li><strong>Immediate Feedback:</strong> Real-time validation and confirmation through integrated messaging system</li>
 *   <li><strong>Workflow Integration:</strong> Seamless coordination with administrative navigation and resource management</li>
 * </ul>
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
     * Window event management includes comprehensive WindowListener implementation that handles
     * dialog closure events through window controls, ensuring proper calling button re-enablement
     * and resource cleanup when status selection is cancelled or interrupted through administrative
     * interface interactions rather than confirmation processing.
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
     *
     * @return the main dialog frame for external component integration and administrative window management operations
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}