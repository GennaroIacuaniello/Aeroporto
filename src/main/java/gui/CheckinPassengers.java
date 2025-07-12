package gui;

import controller.BookingStatusController;
import controller.Controller;
import controller.FlightController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CheckinPassengers extends BookingPageAdmin{

    protected JButton confirmButton;

    public CheckinPassengers (ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        setCheckinCheckBoxes(controller);

        mainFrame.setVisible(true);
    }

    public CheckinPassengers (ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        setConfirmButton(callingObjects, controller);

        constraints.setConstraints(0, 3, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getConstraints());

        confirmPanel.setVisible(true);
    }

    protected void setConfirmButton (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmButton = new JButton("CONFERMA");

        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                controller.getFlightController().setCheckins(passengerPanels, confirmButton);
            }
        });

        confirmButton.setFocusable(false);
        confirmButton.setVisible(true);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(confirmButton, constraints.getConstraints());
    }

    protected void setCheckinCheckBoxes (Controller controller) {

        /*int index = 0;

        for (int i = 0; i < controller.getFlightController().getBookingsSize(); i++) {

            for (int j = 0; j < controller.getFlightController().getBookingSize(i); j++, index++) {

                passengerPanels.get(index).addCheckinCheckBox(controller.getFlightController().getPassengerCheckedin(index));
                passengerPanels.get(index).getCheckinCheckBox().setEnabled(controller.getFlightController().checkBookingConfirm(i));
            }
        }*/

        for (int i = 0; i < passengerPanels.size(); i++)
            passengerPanels.get(i).addCheckinCheckBox(controller.getFlightController().getPassengerCheckedin(i));
    }
}
