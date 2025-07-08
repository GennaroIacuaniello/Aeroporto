package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class BookingPageAdmin extends BookingPage {

    protected ArrayList<JButton> checkinButtons;

    public BookingPageAdmin(ArrayList<DisposableObject> callingObjects, Controller controller,
                            Dimension dimension, Point point, int fullScreen) {

        super(callingObjects, controller, dimension, point, fullScreen);

        insertPassengers(controller);

        addConfirmPanel(callingObjects, controller);
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

        setCheckinButtons(callingObjects, controller);

        constraints.setConstraints(0, 4, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainPanel.add(confirmPanel, constraints.getConstraints());

        confirmPanel.setVisible(true);
    }

    private void setCheckinButtons (ArrayList<DisposableObject> callingObjects, Controller controller) {

        checkinButtons = new ArrayList<JButton>();

        for (int i = 0; i < 3; i++) {

            int finalI = i;

            checkinButtons.addLast(new JButton("CHECKIN"));

            checkinButtons.getLast().addActionListener(new ActionListener() {

                @Override
                public void actionPerformed (ActionEvent e) {

                    if (controller.getFlightController().getFlightStatus() == controller.getFlightStatusController().aboutToDepart) {

                    } else {

                        //programmed
                    }
                }
            });

            checkinButtons.getLast().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered (MouseEvent e) {
                    if (controller.getFlightController().getFlightStatus() != controller.getFlightStatusController().aboutToDepart
                        && controller.getFlightController().getFlightStatus() != controller.getFlightStatusController().programmed) {

                        moveCheekinButton(finalI);
                    }
                }
            });

            constraints.setConstraints (i*2, 0, 1, 1, GridBagConstraints.NONE,
                    0, 0, GridBagConstraints.CENTER, 0.015f, 0.015f);
            confirmPanel.add(checkinButtons.getLast(), constraints.getConstraints());

            if (i != 2) {
                constraints.setConstraints(i * 2 + 1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                        0, 0, GridBagConstraints.CENTER, 0.015f, 0.015f);
                confirmPanel.add(new JPanel(), constraints.getConstraints());
            }

            checkinButtons.getLast().setFocusable(false);
            checkinButtons.getLast().setVisible(false);
        }

        checkinButtons.get(1).setVisible(true);
    }

    private void moveCheekinButton (int index) {

        System.out.println(index);
        checkinButtons.get(index).setVisible(false);

        int random = new Random().nextInt(2);

        if (index == 0) {
            random++;
        } else if (index == 1) {
            random *= 2;
        }

        checkinButtons.get(random).setVisible (true);

        new ErrorMessage ("I dati dei passeggeri sono incompleti", checkinButtons.get(random));
    }
}
