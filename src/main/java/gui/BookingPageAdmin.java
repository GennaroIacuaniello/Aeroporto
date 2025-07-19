package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookingPageAdmin extends BookingPage {

    protected JPanel confirmPanel;

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
                    PassengerPanel passengerPanel = new PassengerPanel(controller, passengerPanels);

                    passengerPanel.setPassengerName(controller.getFlightController().getPassengerNameFromBooking(j, i));
                    passengerPanel.setPassengerSurname(controller.getFlightController().getPassengerSurnameFromBooking(j, i));
                    passengerPanel.setPassengerCF(controller.getFlightController().getPassengerCFFromBooking(j, i));
                    passengerPanel.setSeat(controller.getFlightController().getPassengerSeatFromBooking(j, i));
                    passengerPanel.setPassengerDate(controller.getFlightController().getPassengerDateFromBooking(i, j));
                    passengerPanel.setTicketNumber(controller.getFlightController().getPassengerTicketNumberFromBooking(j, i));
                    passengerPanel.setLuggagesTypes(controller.getFlightController().getPassengerLuggagesTypesFromBooking(j, i), controller);

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

        setCheckinButton(callingObjects, controller);

        constraints.setConstraints(0, 3, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getConstraints());

        confirmPanel.setVisible(true);
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

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        confirmPanel.add(checkinButton, constraints.getConstraints());
    }

    protected void checkCheckinButton (ArrayList<DisposableObject> callingObjects, Controller controller) {

        if (controller.getFlightController().getFlightStatus() == controller.getFlightStatusController().programmed) {

            controller.getFlightController().startCheckin();

            new CheckinPassengers(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

            for (PassengerPanel passengerPanel : passengerPanels) {

                if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
            }

            mainFrame.setVisible(false);
        } else if (controller.getFlightController().getFlightStatus() == controller.getFlightStatusController().aboutToDepart) {

            new CheckinPassengers(callingObjects, controller, mainFrame.getSize(), mainFrame.getLocation(), mainFrame.getExtendedState(), false);

            for (PassengerPanel passengerPanel : passengerPanels) {

                if (passengerPanel.getLuggagesView() != null) passengerPanel.getLuggagesView().setVisible(false);
            }

            mainFrame.setVisible(false);
        } else new FloatingMessage("Non è possibile effettuare check-in per un volo in sato: " + controller.getFlightController().getFlightStatus(), checkinButton, FloatingMessage.ERROR_MESSAGE);
    }
}
