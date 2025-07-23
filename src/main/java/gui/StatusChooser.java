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
 * The StatusChooser class provides a dialog for selecting and updating a flight's status.
 * This dialog displays a dropdown menu with available flight statuses (such as PROGRAMMED,
 * CANCELLED, DEPARTED, etc.) and allows administrators to change the status of a flight.
 * The dialog is modal and appears on top of other windows, requiring user interaction
 * before returning to the main application flow.
 */
public class StatusChooser {

    /**
     * The main frame of the status chooser dialog.
     * This frame contains the status selection dropdown and confirmation button.
     */
    private final JFrame mainFrame;

    /**
     * The button that confirms the selected status change.
     * When clicked, it applies the selected status to the flight if valid.
     */
    private final JButton confirmButton;

    /**
     * The dropdown menu containing available flight statuses.
     * Allows the user to select from predefined status options.
     */
    private final JComboBox comboBox;

    /**
     * Instantiates a new Status chooser dialog.
     * This constructor creates and configures the dialog with a dropdown menu of flight statuses
     * and a confirmation button. It also sets up event listeners for window events and button clicks.
     * The dialog disables the calling button until the status selection is confirmed or the dialog is closed.
     *
     * @param controller        the main controller that manages application state and handles flight status updates
     * @param callingButton     the button that triggered this dialog, which is disabled while the dialog is open
     * @param disposableObjects the list of disposable objects in the application hierarchy,
     *                          used for navigation when returning to the home screen after certain status changes
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
     * Gets the main frame of this status chooser dialog.
     * This method provides access to the dialog's JFrame, allowing external components
     * to interact with the dialog, such as positioning it relative to other windows
     * or checking its state.
     *
     * @return the JFrame instance representing this dialog
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}
