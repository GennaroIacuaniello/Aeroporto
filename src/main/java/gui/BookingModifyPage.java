package gui;

import controller.Controller;
import model.Passenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class BookingModifyPage extends BookingPageCustomer {

    protected ArrayList<RemovePassengerButton> removePassengerButtons;
    protected JButton addPassengerButton;

    protected JButton confirmButton;
    protected JButton savePendingButton;

    public BookingModifyPage(ArrayList<DisposableObject> callingObjects, Controller controller,
                             Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        //addAddPassengerButton(controller);

        mainFrame.setVisible(true);
    }

    public BookingModifyPage(ArrayList<DisposableObject> callingObjects, Controller controller,
                             Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    @Override
    protected void addModifyPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

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

                System.out.println(controller.getFlightController().getFreeSeats() + " " + passengerPanels.size() + " " + controller.getBookingController().getTicketsSize());
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
        currentPageLabel.setText(Integer.valueOf(currPage + 1).toString());

        //sistemo attivabilità bottoni
        prevPageButton.setEnabled(currPage > 0);
        nextPageButton.setEnabled(currPage < (passengerPanels.size() / 3));
    }

    @Override
    protected void insertPassengerPanel (Controller controller, PassengerPanel passengerPanel) {

        if (removePassengerButtons == null) removePassengerButtons = new ArrayList<RemovePassengerButton>();

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

        for (int i = 0; i < 3; i++) {//metto a false la pagina corrente

            passengerPanels.get((currPage * 3) + i).setVisible(false);
            removePassengerButtons.get((currPage * 3) + i).setVisible(false);
        }

        if (currPage + 1 == (passengerPanels.size() - 1) / 3) { //sto andando all'ultima pagina quindi non so quanti sono

            for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++) {

                passengerPanels.get(passengerPanels.size() - i - 1).setVisible(true);
                removePassengerButtons.get(removePassengerButtons.size() - i - 1).setVisible(true);
            }

            nextPageButton.setEnabled (false);
        } else { //la prossima pagina ne ha 3

            for (int i = 0; i < 3; i++) {

                passengerPanels.get(((currPage + 1) * 3) + i).setVisible(true);
                removePassengerButtons.get(((currPage + 1) * 3) + i).setVisible(true);
            }
        }

        currPage++;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());
        prevPageButton.setEnabled (true);
    }

    @Override
    protected void goPreviousPage () {

        if (currPage != (passengerPanels.size() - 1) / 3) { //non sto all'ultima pagina quindi sono 3

            for (int i = 0; i < 3; i++) {

                passengerPanels.get((currPage * 3) + i).setVisible(false);
                removePassengerButtons.get((currPage * 3) + i).setVisible(false);
            }

        } else { //sto all'ultima pagina quindi non so quanti sono

            for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++) {

                passengerPanels.get((passengerPanels.size() - i - 1)).setVisible(false);
                removePassengerButtons.get(passengerPanels.size() - i - 1).setVisible(false);
            }
        }

        currPage--;
        currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());

        for (int i = 0; i < 3; i++) {//vado indietro quindi sono 3

            passengerPanels.get((currPage * 3) + i).setVisible(true);
            removePassengerButtons.get((currPage * 3) + i).setVisible(true);
        }

        nextPageButton.setEnabled (true);

        if (currPage == 0) prevPageButton.setEnabled (false);
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        addConfirmButton(controller, callingObjects);
        if (controller.getBookingController().getBookingStatus() == controller.getBookingStatusController().pending)
            addSavePendingButton(controller, callingObjects);

        confirmPanel.setVisible (true);
    }

    protected void addConfirmButton (Controller controller, ArrayList<DisposableObject> callingObjects) {

        confirmButton = new JButton("CONFERMA PRENOTAZIONE");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkConfirmButton()) {
                    controller.modifyBooking(passengerPanels, controller.getBookingStatusController().confirmed);
                    controller.goBack(callingObjects);
                }

                else
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

    protected void addSavePendingButton (Controller controller, ArrayList<DisposableObject> callingObjects) {

        savePendingButton = new JButton("SALVA IN ATTESA");

        savePendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkSavePendingButton()) {
                    controller.modifyBooking(passengerPanels, controller.getBookingStatusController().pending);
                    controller.goBack(callingObjects);
                } else
                    new FloatingMessage("I codici fiscali dei passeggeri sono incompleti", savePendingButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_START);
        modifyPanel.add (savePendingButton, constraints.getConstraints());

        savePendingButton.setFocusable(false);
        savePendingButton.setVisible (true);
    }

    protected boolean checkSavePendingButton() {

        for (PassengerPanel passengerPanel : passengerPanels) if (passengerPanel.checkPassengerCF()) return false;

        return true;
    }

    protected boolean checkConfirmButton() {
        System.out.println("check");
        for (PassengerPanel passengerPanel : passengerPanels) {

            if (passengerPanel.checkPassengerName() || passengerPanel.checkPassengerSurname() || passengerPanel.checkPassengerCF() || passengerPanel.checkPassengerDate())
                return false;
            System.out.println("we");
            for (LuggagePanel luggagePanel : passengerPanel.getLuggagesPanels())
                if (luggagePanel.checkLuggage())
                    return false;
        }

        return true;
    }
}
