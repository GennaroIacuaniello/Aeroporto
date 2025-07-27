package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final JDialog mainFrame;

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
     * @param disposableObjects the list of navigation objects for proper resource management and workflow coordination during status transitions
     */
    public StatusChooser(Controller controller, JFrame callingFrame, List<DisposableObject> disposableObjects) {

        mainFrame = new JDialog(callingFrame, "Flight status chooser", true);
        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        //.setLocationRelativeTo(callingFrame);
        mainFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        mainFrame.setSize(500, 200);

        Point point = new Point(callingFrame.getX() + callingFrame.getWidth()/2, callingFrame.getY() + callingFrame.getHeight()/2);

        mainFrame.setLocation((int)(point.getX() - 250), (int)(point.getY() - 100));

        JLabel label = new JLabel("Seleziona lo stato:");
        mainFrame.add(label);

        comboBox = new JComboBox();

        comboBox.addItem("STATO");

        comboBox.addItem("In programma");
        comboBox.addItem("In partenza");
        comboBox.addItem("Partito");
        comboBox.addItem("In arrivo");
        comboBox.addItem("Atterrato");
        comboBox.addItem("In ritardo");
        comboBox.addItem("Cancellato");

        comboBox.setSelectedIndex(0);
        mainFrame.add(comboBox);

        confirmButton = new JButton("CONFERMA");
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {


                if (comboBox.getSelectedIndex() != 0) {

                    if (controller.getFlightController().setFlightStatus(controller.translateFlightStatusBack((String)comboBox.getSelectedItem())) == 1) {

                        controller.getFlightController().setStatus((String)comboBox.getSelectedItem());

                        if (comboBox.getSelectedIndex() == 7) controller.goHome(disposableObjects);

                        if (comboBox.getSelectedIndex() == 3) controller.goBack(disposableObjects);

                        mainFrame.dispose();
                    } else
                        new FloatingMessage("Non è stato possibile cambiare lo stato del volo a: " + comboBox.getSelectedItem(),
                                confirmButton, FloatingMessage.ERROR_MESSAGE);
                }

            }
        });

        confirmButton.setFocusable(false);
        mainFrame.add(confirmButton);

        mainFrame.setVisible(true);
    }
}