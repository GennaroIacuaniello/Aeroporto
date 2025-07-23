package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The type Gate chooser.
 */
public class GateChooser {

    private final JFrame mainFrame;
    private final JComboBox comboBox;

    /**
     * Instantiates a new Gate chooser.
     *
     * @param controller    the controller
     * @param callingButton the calling button
     */
    public GateChooser(Controller controller, JButton callingButton) {

        mainFrame = new JFrame("Gate Chooser");
        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainFrame.setAlwaysOnTop(true);

        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                callingButton.setEnabled(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

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

    private void setGate (int id, Controller controller, JButton callingButton) {

        if (id > 0) {

            controller.getGateController().setGate(id, controller);

            callingButton.setEnabled(true);

            mainFrame.dispose();
        }
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
