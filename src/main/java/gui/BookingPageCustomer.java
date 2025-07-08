package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookingPageCustomer extends BookingPage {

    protected JButton modifyButton;
    protected JButton cancelButton;

    public BookingPageCustomer(ArrayList<DisposableObject> callingObjects, Controller controller,
                               Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);



        addConfirmPanel(callingObjects, controller);
    }

    @Override
    protected void insertPassengers (Controller controller) {

        for (int i = 0; i < controller.getBookingController().getPassengersSize(); i++) {

            PassengerPanel passengerPanel = new PassengerPanel(controller, passengerPanels);

            passengerPanel.setPassengerName(controller.getBookingController().getPassengerName(i));
            passengerPanel.setPassengerSurname(controller.getBookingController().getPassengerLastName(i));
            passengerPanel.setPassengerCF(controller.getBookingController().getPassengerSSN(i));
            passengerPanel.setSeat(controller.getBookingController().getPassengerSeat(i));
            passengerPanel.setTicketNumber(controller.getBookingController().getPassengerTicketNumber(i));
            passengerPanel.setLuggagesTypes(controller.getBookingController().getPassengerLuggagesTypes(i), controller);

            insertPassengerPanel(controller, passengerPanel);

            passengerPanel.setPanelEnabled(false);
        }
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        modifyButton = new JButton("MODIFICA PRENOTAZIONE");
        cancelButton = new JButton("CANCELLA PRENOTAZIONE");

        modifyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkFlightNBookingStatus(controller)) {

                    new Book(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState());

                    mainFrame.setVisible(false);

                    for (PassengerPanel passengerPanel : passengerPanels) {

                        if  (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                controller.getBookingController().deleteBooking();

                controller.goBack(callingObjects);
            }
        });

        modifyButton.setFocusable(false);
        cancelButton.setFocusable(false);

        confirmPanel.add(modifyButton);
        confirmPanel.add(cancelButton);

        constraints.setConstraints(0, 4, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainPanel.add(confirmPanel, constraints.getConstraints());
    }

    protected Boolean checkFlightNBookingStatus (Controller controller) {

        return controller.getFlightController().getFlightStatus() == controller.getFlightStatusController().programmed
                && (controller.getBookingController().getBookingStatus() == controller.getBookingStatusController().confirmed
                || controller.getBookingController().getBookingStatus() == controller.getBookingStatusController().pending);
    }
}
