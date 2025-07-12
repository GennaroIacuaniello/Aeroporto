package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Book extends BookingModifyPage {

    private ArrayList<JButton> confirmButtons;

    public Book(ArrayList<DisposableObject> callingObjects, Controller controller,
                Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        addSavePendingButton(controller, callingObjects);

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
                    insertPassengerPanel(controller, new PassengerPanel(controller, passengerPanels));
                else
                    new FloatingMessage("Non ci sono altri posti disponibili per questo volo", addPassengerButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        flowPanel.add(addPassengerButton);
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmButtons = new ArrayList<JButton>();
        confirmPanel = new JPanel();
        confirmPanel.setLayout(new GridLayout());

        confirmPanel.setOpaque(false);

        for (int i = 0; i < 3; i++) {

            confirmButtons.add(new JButton("CONFERMA PRENOTAZIONE"));

            int finalI = i;

            confirmButtons.getLast().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e) {

                    controller.addBooking(passengerPanels, controller.getBookingStatusController().confirmed);

                    controller.goBack(callingObjects);

                    //new GoodMessage("La tua richiesta di prenotazione è stata presa in carico", confirmButtons.get(finalI));
                }
            });

            confirmButtons.getLast().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered (MouseEvent e) {
                    checkConfirmButton(finalI);
                }
            });

            constraints.setConstraints (i*2, 0, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER, 0.015f, 0.015f);
            confirmPanel.add (confirmButtons.getLast(), constraints.getConstraints());

            JPanel invisiblePanel = new JPanel();
            invisiblePanel.setOpaque(false);

            if (i != 2) {
                constraints.setConstraints(i * 2 + 1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                        0, 0, GridBagConstraints.CENTER, 0.015f, 0.015f);
                confirmPanel.add(invisiblePanel, constraints.getConstraints());
            }

            confirmButtons.getLast().setFocusable(false);
            confirmButtons.getLast().setVisible (false);
        }
        confirmButtons.get(1).setVisible (true);

        constraints.setConstraints (0, 4, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainPanel.add (confirmPanel, constraints.getConstraints());
        confirmPanel.setVisible (true);
    }

    @Override
    protected void addSavePendingButton (Controller controller, ArrayList<DisposableObject> callingObjects) {

        savePendingButton = new JButton("SALVA IN ATTESA");

        savePendingButton.setFocusable(false);

        savePendingButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                    if (checkSavePendingButton(controller)) {

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

    private void checkConfirmButton(int index) {

        boolean flag = true;

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.checkPassengerName() || passengerPanel.checkPassengerSurname() || passengerPanel.checkPassengerCF()){
                flag = false;
            }

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {
                if (luggagePanel.checkLuggage()) {
                    flag = false;
                }
            }
        }

        if (!flag) {
            confirmButtons.get(index).setVisible (false);

            int random = new Random().nextInt(2);

            if (index == 0) {
                random++;
            } else if (index == 1) {
                random *= 2;
            }

            confirmButtons.get(random).setVisible (true);

            new FloatingMessage ("I dati dei passeggeri sono incompleti", confirmButtons.get(random), FloatingMessage.ERROR_MESSAGE);
        }
    }

    @Override
    protected void insertPassengers (Controller controller) {

        insertPassengerPanel(controller, new PassengerPanel(controller, passengerPanels));
    }

    private boolean checkSavePendingButton (Controller controller) {

        for (PassengerPanel passengerPanel : passengerPanels) {

            if (!passengerPanel.checkPassengerName()) return true;
            if (!passengerPanel.checkPassengerSurname()) return true;
            if (!passengerPanel.checkPassengerCF()) return true;
            if (!passengerPanel.checkPassengerSeat()) return true;

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {
                if (!luggagePanel.checkLuggage()) return true;
            }
        }

        return false;
    }
}
