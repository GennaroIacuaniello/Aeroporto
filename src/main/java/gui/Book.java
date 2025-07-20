package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Book extends BookingModifyPage {

    public Book(ArrayList<DisposableObject> callingObjects, Controller controller,
                Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        mainFrame.setVisible(true);
    }

    protected Book(ArrayList<DisposableObject> callingObjects, Controller controller,
                   Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    @Override
    protected void addAddPassengerButton(Controller controller) {

        addPassengerButton = new JButton("AGGIUNGI PASSEGGERO");

        addPassengerButton.setFocusable(false);

        addPassengerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (controller.getFlightController().getFreeSeats() - passengerPanels.size() > 0)
                    insertPassengerPanel(controller, new PassengerPanel(controller, passengerPanels, bookedSeats));
                else
                    new FloatingMessage("Non ci sono altri posti disponibili per questo volo", addPassengerButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        flowPanel.add(addPassengerButton);
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        addConfirmButton(controller, callingObjects);
        addSavePendingButton(controller, callingObjects);

        confirmPanel.setVisible (true);
    }

    @Override
    protected void addConfirmButton (Controller controller, ArrayList<DisposableObject> callingObjects) {

        confirmButton = new JButton("CONFERMA PRENOTAZIONE");

        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkConfirmButton()) {
                    controller.addBooking(passengerPanels, controller.getBookingStatusController().confirmed);
                    controller.goBack(callingObjects);
                } else
                    new FloatingMessage("I dati dei passeggeri sono incompleti", confirmButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER);
        confirmPanel.add (confirmButton, constraints.getConstraints());

        confirmButton.setFocusable(false);
        confirmButton.setVisible (true);

        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER);
        mainFrame.add (confirmPanel, constraints.getConstraints());
    }

    @Override
    protected void addSavePendingButton (Controller controller, ArrayList<DisposableObject> callingObjects) {

        savePendingButton = new JButton("SALVA IN ATTESA");

        savePendingButton.setFocusable(false);

        savePendingButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                    if (checkSavePendingButton()) {

                        controller.addBooking(passengerPanels, controller.getBookingStatusController().pending);

                        controller.goBack(callingObjects);

                        //new GoodMessage("La tua prenotazione è in attesa di conferma", savePendingButton);
                    } else {
                        new FloatingMessage("Impossibile aggiungere una prenotazione vuota", savePendingButton, FloatingMessage.ERROR_MESSAGE);
                    }
            }
        });

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START, 0.5f, 0.5f);
        modifyPanel.add(savePendingButton, constraints.getConstraints());

        savePendingButton.setVisible(true);
    }

    @Override
    protected void insertPassengers (Controller controller) {

        insertPassengerPanel(controller, new PassengerPanel(controller, passengerPanels, bookedSeats));
    }
}
