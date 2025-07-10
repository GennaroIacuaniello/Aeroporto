package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookingPageAdmin extends BookingPage {

    protected JPanel confirmPanel;

        //private ArrayList<JButton> checkinButtons;
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

        constraints.setConstraints(0, 3, 1, 1,
                GridBagConstraints.HORIZONTAL,  0, 0, GridBagConstraints.CENTER);
        mainFrame.add(confirmPanel, constraints.getConstraints());

        confirmPanel.setVisible(true);
    }

    private void setCheckinButtons (ArrayList<DisposableObject> callingObjects, Controller controller) {

        /*checkinButtons = new ArrayList<JButton>();

        for (int i = 0; i < 3; i++) {

            int finalI = i;

            checkinButtons.add(new JButton("CHECKIN " + finalI));

            checkinButtons.getLast().addActionListener(new ActionListener() {

                @Override
                public void actionPerformed (ActionEvent e) {

                    if (controller.getFlightController().getFlightStatus() == controller.getFlightStatusController().aboutToDepart) {

                        //riprendi checkin
                    } else {

                        //inizia checkin
                    }
                }
            });

            checkinButtons.getLast().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered (MouseEvent e) {
                    if (controller.getFlightController().getFlightStatus() != controller.getFlightStatusController().aboutToDepart
                        && controller.getFlightController().getFlightStatus() != controller.getFlightStatusController().programmed) {

                        System.out.println("f: " + finalI);
                        System.out.println(checkinButtons.size());
                        System.out.println(checkinButtons.getFirst().isVisible() + " " + checkinButtons.get(1).isVisible() + " " + checkinButtons.getLast().isVisible());

                        moveCheckinButton(finalI);
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
            checkinButtons.getLast().setVisible(i == 1);
        }

        //checkinButtons.get(1).setVisible(true);*/

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

    /*private void moveCheckinButton (int index) {

        checkinButtons.get(index).setVisible(false);

        int random = new Random().nextInt(2);

        if (index == 0) {
            random++;
        } else if (index == 1) {
            random *= 2;
        }

        checkinButtons.get(random).setVisible (true);

        System.out.println(random);
        System.out.println(checkinButtons.size());
        System.out.println(checkinButtons.getFirst().isVisible() + " " + checkinButtons.get(1).isVisible() + " " + checkinButtons.getLast().isVisible());

        checkinButtons.get(index).revalidate();
        checkinButtons.get(index).repaint();

        confirmPanel.revalidate();
        confirmPanel.repaint();

        new ErrorMessage ("I dati dei passeggeri sono incompleti", checkinButtons.get(random));
    }*/
}
