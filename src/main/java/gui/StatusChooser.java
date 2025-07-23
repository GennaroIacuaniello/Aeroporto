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
 * The type Status chooser.
 */
public class StatusChooser {

    private final JFrame mainFrame;
    private final JButton confirmButton;
    private final JComboBox comboBox;

    /**
     * Instantiates a new Status chooser.
     *
     * @param controller        the controller
     * @param callingButton     the calling button
     * @param disposableObjects the disposable objects
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
     * Gets main frame.
     *
     * @return the main frame
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}
