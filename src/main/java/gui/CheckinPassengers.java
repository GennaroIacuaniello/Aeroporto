package gui;


import controller.Controller;
import controller.GateController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckinPassengers extends BookingPageAdmin{

    protected JButton gateButton;
    protected JButton confirmButton;
    protected GateChooser gateChooser;

    protected ArrayList<PassengerPanel> truePassengerPanels;
    protected ArrayList<PassengerPanel> falsePassengerPanels;

    public CheckinPassengers (ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        truePassengerPanels = new ArrayList<PassengerPanel>();
        falsePassengerPanels = new ArrayList<PassengerPanel>();

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

        setChangeStatusButton(controller, callingObjects);
        setGateButton(controller);
        setConfirmButton(controller);
        setSetDelayButton(controller);

        constraints.setConstraints(0, 3, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getConstraints());

        confirmPanel.setVisible(true);
    }

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
        confirmPanel.add(gateButton, constraints.getConstraints());
    }

    protected void setGate (Controller controller) {

        if (gateButton.getText().equals("GATE")) controller.getGateController().newGate(gateButton, controller, this);
        else gateChooser = new GateChooser(controller, gateButton);
    }

    protected void setConfirmButton (Controller controller) {

        confirmButton = new JButton("CONFERMA");

        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                ArrayList<ArrayList<String>> megaArrayList = controller.getFlightController().setCheckins(truePassengerPanels, falsePassengerPanels);

                for (int i = 0; i < truePassengerPanels.size(); i++)
                    truePassengerPanels.get(i).getLuggagesView().setLuggagesIds(megaArrayList.get(i));


                new FloatingMessage("Checkins effettuati con successo", confirmButton, FloatingMessage.SUCCESS_MESSAGE);

                truePassengerPanels = new ArrayList<PassengerPanel>();
                falsePassengerPanels = new ArrayList<PassengerPanel>();
            }
        });

        confirmButton.setFocusable(false);
        confirmButton.setVisible(true);

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(confirmButton, constraints.getConstraints());
    }

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

    @Override
    public void doOnDispose (ArrayList<DisposableObject> callingObjects, Controller controller) {

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

    public void setGateChooser(GateChooser gateChooser) {

        this.gateChooser = gateChooser;
    }
}
