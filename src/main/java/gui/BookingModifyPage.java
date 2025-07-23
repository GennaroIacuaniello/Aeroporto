package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BookingModifyPage extends BookingPageCustomer {

    protected ArrayList<RemovePassengerButton> removePassengerButtons;
    protected JButton addPassengerButton;

    protected JButton confirmButton;
    protected JButton savePendingButton;

    public BookingModifyPage(List<DisposableObject> callingObjects, Controller controller,
                             Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        mainFrame.setVisible(true);
    }

    public BookingModifyPage(List<DisposableObject> callingObjects, Controller controller,
                             Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    @Override
    protected void addModifyPanel (List<DisposableObject> callingObjects, Controller controller) {

        modifyPanel = new JPanel();

        modifyPanel.setLayout(new GridBagLayout());

        modifyPanel.setOpaque(false);

        flowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        flowPanel.setOpaque(false);

        constraints.setConstraints (1, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_END);
        modifyPanel.add(flowPanel, constraints.getConstraints());

        flowPanel.setVisible(true);

        addAddPassengerButton(controller);
        addPageChangeButtons();

        constraints.setConstraints (0, 3, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainPanel.add (modifyPanel, constraints.getConstraints());
        modifyPanel.setVisible (true);
    }

    protected void addAddPassengerButton(Controller controller) {

        addPassengerButton = new JButton("AGGIUNGI PASSEGGERO");

        addPassengerButton.setFocusable(false);

        addPassengerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (controller.getFlightController().getFreeSeats() - passengerPanels.size() + controller.getBookingController().getTicketsSize() > 0)
                    insertPassengerPanel(controller, new PassengerPanel(controller, passengerPanels, bookedSeats));
                else
                    new FloatingMessage("Non ci sono altri posti disponibili per questo volo", addPassengerButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        flowPanel.add(addPassengerButton);
    }

    @Override
    protected void goToPage (int page) {

        //sistemo visibilità
        for (int i = 0; i < 3; i++) {

            if (i + currPage * 3 < passengerPanels.size()) {

                passengerPanels.get(i + currPage * 3).setVisible(false);
                removePassengerButtons.get(i + currPage * 3).setVisible(false);
            }

            if (i + page * 3 < passengerPanels.size()) {

                passengerPanels.get(i + page * 3).setVisible(true);
                removePassengerButtons.get(i + page * 3).setVisible(true);
            }
        }

        //sistemo currPage
        currPage = page;
        currentPageLabel.setText(Integer.toString(currPage + 1));

        //sistemo attivabilità bottoni
        prevPageButton.setEnabled(currPage > 0);
        nextPageButton.setEnabled(currPage < ((passengerPanels.size() - 1) / 3));
    }

    @Override
    protected void insertPassengerPanel (Controller controller, PassengerPanel passengerPanel) {

        if (removePassengerButtons == null) removePassengerButtons = new ArrayList<>();

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        passengerPage.add(passengerPanel, constraints.getConstraints());

        RemovePassengerButton removePassengerButton = new RemovePassengerButton(this, controller, passengerPanels, removePassengerButtons, removePassengerButtons.size());

        constraints.setConstraints(1, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        passengerPage.add(removePassengerButton, constraints.getConstraints());

        if (!passengerPanels.isEmpty()) setPassengersVisibility();
        else {
            passengerPanel.setVisible(true);
            removePassengerButton.setVisible(true);
        }

        passengerPanels.addLast(passengerPanel);
        removePassengerButtons.addLast(removePassengerButton);

        passengerPage.setVisible(false);
        passengerPage.setVisible(true);

        removePassengerButtons.getFirst().setEnabled(passengerPanels.size() > 1);
    }

    protected void setPassengersVisibility () {

        goToPage(passengerPanels.size() / 3);
    }

    @Override
    protected void goNextPage () {

        goToPage(currPage + 1);

    }

    @Override
    protected void addConfirmPanel (List<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        addConfirmButton(controller, callingObjects);
        if (controller.getBookingController().getBookingStatus().toString().equalsIgnoreCase("PENDING"))
            addSavePendingButton(controller, callingObjects);

        confirmPanel.setVisible (true);
    }

    protected void addConfirmButton (Controller controller, List<DisposableObject> callingObjects) {

        confirmButton = new JButton("CONFERMA PRENOTAZIONE");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkConfirmButton()) {
                    controller.modifyBooking(passengerPanels, "CONFIRMED");
                    controller.goBack(callingObjects);
                } else
                    new FloatingMessage("I dati dei passeggeri sono incompleti o errati", confirmButton, FloatingMessage.ERROR_MESSAGE);
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

    protected void addSavePendingButton (Controller controller, List<DisposableObject> callingObjects) {

        savePendingButton = new JButton("SALVA IN ATTESA");

        savePendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkSavePendingButton()) {
                    controller.modifyBooking(passengerPanels, "PENDING");
                    controller.goBack(callingObjects);
                } else
                    new FloatingMessage("I dati dei passeggeri sono incompleti o errati", savePendingButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_START);
        modifyPanel.add (savePendingButton, constraints.getConstraints());

        savePendingButton.setFocusable(false);
        savePendingButton.setVisible (true);
    }

    protected boolean checkSavePendingButton() {

        for (PassengerPanel passengerPanel : passengerPanels) {

            if (passengerPanel.checkPassengerCF()) return false;

            boolean flag = false;

            for (LuggagePanel  luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.getComboBox().getSelectedIndex() == 1) {

                    if (flag) return false;
                    else flag = true;
                }
            }
        }

        return true;
    }

    protected boolean checkConfirmButton() {
        for (PassengerPanel passengerPanel : passengerPanels) {

            if (passengerPanel.checkPassengerName() || passengerPanel.checkPassengerSurname() || passengerPanel.checkPassengerCF() || passengerPanel.checkPassengerDate())
                return false;

            boolean flag = false;

            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels()) {

                if (luggagePanel.checkLuggage())
                    return false;

                if (luggagePanel.getComboBox().getSelectedIndex() == 1) {

                    if (flag) return false;
                    else flag = true;
                }
            }

        }

        return true;
    }
}
