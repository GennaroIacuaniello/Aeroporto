package gui;

import controller.Controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class BookingPageCustomer extends BookingPage {

    protected JPanel confirmPanel;

        protected JButton modifyButton;
        protected JButton cancelButton;

    public BookingPageCustomer(ArrayList<DisposableObject> callingObjects, Controller controller,
                               Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        addConfirmPanel(callingObjects, controller);

        mainFrame.setVisible(true);
    }

    public BookingPageCustomer(ArrayList<DisposableObject> callingObjects, Controller controller,
                               Dimension dimension, Point point, int fullScreen, boolean flag) {

        this(callingObjects, controller, dimension, point, fullScreen);

        setControllerDisposeFlag(flag);
    }

    @Override
    protected void insertPassengers (Controller controller) {

        for (int i = 0; i < controller.getBookingController().getTicketsSize(); i++) {

            PassengerPanel passengerPanel = new PassengerPanel(controller, passengerPanels, bookedSeats);

            String string = controller.getBookingController().getPassengerName(i);
            if (string != null) passengerPanel.setPassengerName(string);

            string = controller.getBookingController().getPassengerLastName(i);
            if (string != null) passengerPanel.setPassengerSurname(string);

            string = controller.getBookingController().getPassengerSSN(i);
            if (string != null) passengerPanel.setPassengerCF(string);

            int seat = controller.getBookingController().getPassengerSeat(i);
            if (seat != -1) passengerPanel.setSeat(seat);

            Date date = controller.getBookingController().getPassengerDate(i);
            if(date != null) passengerPanel.setPassengerDate(date);

            string = controller.getBookingController().getPassengerTicketNumber(i);
            if (string != null) passengerPanel.setTicketNumber(string);

            passengerPanel.setLuggages((ArrayList<Integer>) controller.getBookingController().getPassengerLuggagesTypes(i),
                    (ArrayList<String>) controller.getBookingController().getPassengerLuggagesTickets(i), (ArrayList<String>) controller.getBookingController().getPassengerLuggagesStatus(i), controller);

            insertPassengerPanel(controller, passengerPanel);
        }
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        confirmPanel = new JPanel();

        confirmPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        confirmPanel.setOpaque(false);

        modifyButton = new JButton("MODIFICA PRENOTAZIONE");
        cancelButton = new JButton("CANCELLA PRENOTAZIONE");

        modifyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkFlightNBookingStatus(controller)) {

                    new BookingModifyPage(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

                    mainFrame.setVisible(false);

                    for (PassengerPanel passengerPanel : passengerPanels) {

                        if  (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
                    }
                } else new FloatingMessage("Lo stato del volo o della prenotazione non permettono di modificare questa prenotazione",
                        modifyButton, FloatingMessage.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                if (checkFlightNBookingStatus(controller)) {

                    controller.getBookingController().deleteBooking();

                    controller.goBack(callingObjects);
                } else new FloatingMessage("Lo stato del volo o della prenotazione non permettono di cancellare questa prenotazione",
                        modifyButton, FloatingMessage.ERROR_MESSAGE);
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

        return controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("PROGRAMMED")
                && (controller.getBookingController().getBookingStatus().toString().equalsIgnoreCase("CONFIRMED")
                || controller.getBookingController().getBookingStatus().toString().equalsIgnoreCase("PENDING"));
    }
}
