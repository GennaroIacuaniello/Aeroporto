package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class BookingPageAdmin extends BookingPage {

    protected JPanel confirmPanel;

        protected JButton statusButton;
        protected StatusChooser statusChooser;

        protected JButton checkinButton;

    public BookingPageAdmin(ArrayList<DisposableObject> callingObjects, Controller controller,
                            Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        addConfirmPanel(callingObjects, controller);

        mainFrame.setVisible(true);
    }

    public BookingPageAdmin(ArrayList<DisposableObject> callingObjects, Controller controller,
                            Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    @Override
    protected void insertPassengers(Controller controller) {

        for (int j = 0; j < controller.getFlightController().getBookingsSize(); j++) {

            if (controller.getFlightController().checkBookingConfirm(j)) {

                for (int i = 0; i < controller.getFlightController().getBookingSize(j); i++) {
                    PassengerPanel passengerPanel = new PassengerPanel(controller, passengerPanels, bookedSeats);

                    String string = controller.getFlightController().getPassengerNameFromBooking(j, i);
                    if (string != null) passengerPanel.setPassengerName(string);

                    string = controller.getFlightController().getPassengerTicketNumberFromBooking(j, i);
                    if (string != null) passengerPanel.setPassengerSurname(string);

                    string = controller.getFlightController().getPassengerCFFromBooking(j, i);
                    if (string != null) passengerPanel.setPassengerCF(string);

                    int seat = controller.getFlightController().getPassengerSeatFromBooking(j, i);
                    if (seat != -1) passengerPanel.setSeat(seat);

                    Date date = controller.getFlightController().getPassengerDateFromBooking(j, i);
                    if (date != null) passengerPanel.setPassengerDate(date);

                    string = controller.getFlightController().getPassengerTicketNumberFromBooking(j, i);
                    if (string != null) passengerPanel.setTicketNumber(string);

                    passengerPanel.setLuggages(controller.getFlightController().getPassengerLuggagesTypesFromBooking(j, i),
                            controller.getFlightController().getPassengerLuggagesTicketsFromBooking(j, i), controller.getFlightController().getPassengerLuggagesStatusFromBooking(j, i), controller);

                    insertPassengerPanel(controller, passengerPanel);

                    passengerPanel.setPanelEnabled(false);
                }
            }
        }
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new GridBagLayout());

        confirmPanel.setOpaque(false);

        setChangeStatusButton(controller, callingObjects);

        setCheckinButton(callingObjects, controller);

        constraints.setConstraints(0, 3, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getConstraints());

        confirmPanel.setVisible(true);
    }

    protected void setChangeStatusButton (Controller controller, ArrayList<DisposableObject> callingObjects) {

        statusButton = new JButton("CAMBIA STATO VOLO");

        statusButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                statusChooser = new StatusChooser(controller, statusButton, callingObjects);
                statusButton.setEnabled(false);
            }
        });

        statusButton.setFocusable(false);
        statusButton.setVisible(true);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(statusButton, constraints.getConstraints());
    }

    private void setCheckinButton (ArrayList<DisposableObject> callingObjects, Controller controller) {

        checkinButton = new JButton("CHECKIN");

        checkinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                checkCheckinButton(callingObjects, controller);
            }
        });

        checkinButton.setFocusable(false);
        checkinButton.setVisible(true);

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(checkinButton, constraints.getConstraints());
    }

    protected void checkCheckinButton (ArrayList<DisposableObject> callingObjects, Controller controller) {

        if (controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("PROGRAMMED")) {

            if (controller.getFlightController().startCheckin() == 1) {

                new CheckinPassengers(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

                for (PassengerPanel passengerPanel : passengerPanels) {

                    if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
                }

                mainFrame.setVisible(false);

            } else new FloatingMessage("Lo stato del volo è rimasto invariato (controlla la data di partenza)", checkinButton, FloatingMessage.ERROR_MESSAGE);

        } else if (controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("ABOUT_TO_DEPART")) {

            new CheckinPassengers(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

            for (PassengerPanel passengerPanel : passengerPanels) {

                if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
            }

            mainFrame.setVisible(false);
        } else new FloatingMessage("Non è possibile effettuare check-in per un volo in sato: " + controller.getFlightController().getFlightStatus(), checkinButton, FloatingMessage.ERROR_MESSAGE);
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

        statusButton.setEnabled(true);
        if (statusChooser != null) statusChooser.getMainFrame().dispose();
    }
}
