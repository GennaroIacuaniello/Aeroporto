package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SeatChooser extends JFrame {

    private Constraints constraints;
    private ArrayList<RoundedButton> seatButtons;
    private RoundedButton confirmButton;
    private RoundedButton deleteButton;
    private int offset;
    private int seat;

    public SeatChooser(Controller controller, PassengerPanel callingPanel, ArrayList<PassengerPanel> passengerPanels) {

        super("Seat Chooser");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        constraints = new Constraints();
        setLayout(new GridBagLayout());
        setSize(450, 800);
        setLocation(callingPanel.getSeatButton());
        setAlwaysOnTop(true);

        seat = -1;
        seatButtons = new ArrayList<RoundedButton>();
        confirmButton = new RoundedButton("CONFERMA");
        deleteButton = new RoundedButton("ELIMINA");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callingPanel.setSeat(seat);
                for (PassengerPanel passengerPanel : passengerPanels) {
                    passengerPanel.getSeatButton().setEnabled(true);
                }
                dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callingPanel.setSeat(-1);
                for (PassengerPanel passengerPanel : passengerPanels) {
                    passengerPanel.getSeatButton().setEnabled(true);
                }
                dispose();
            }
        });

        confirmButton.setFocusable(false);
        deleteButton.setFocusable(false);

        constraints.setConstraints(3, controller.getFlightController().getMaxSeats() / 6 + 1, 1,
                controller.getFlightController().getMaxSeats() / 6 + 2, GridBagConstraints.NONE, 0, 0,
                GridBagConstraints.LAST_LINE_END);
        this.add(confirmButton, constraints.getConstraints());

        constraints.setConstraints(4, controller.getFlightController().getMaxSeats() / 6 + 1, 1,
                controller.getFlightController().getMaxSeats() / 6 + 2, GridBagConstraints.NONE, 0, 0,
                GridBagConstraints.LAST_LINE_START);
        this.add(deleteButton, constraints.getConstraints());

        for (int i = 0; i < controller.getFlightController().getMaxSeats(); i++) {
            int finalI = i;
            seatButtons.add(new RoundedButton(printSeat(finalI)));
            seatButtons.get(finalI).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (callingPanel.getSeat() != -1) {
                        seatButtons.get(callingPanel.getSeat()).setEnabled(true);
                    }

                    if (seat != -1) {
                        seatButtons.get(seat).setEnabled(true);
                    }

                    seat = finalI;
                    seatButtons.get(finalI).setEnabled(false);
                }
            });
            seatButtons.get(i).setEnabled(true);
            seatButtons.get(i).setFocusable(false);

            if (i % 6 > 2) offset = 2;
            else offset = 0;
            constraints.setConstraints(i % 6 + offset, i / 6, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
            this.add(seatButtons.get(i), constraints.getConstraints());
        }
        /*
        for (Passenger passenger : controller.getFlightController().getPassengers()) {

            seatButtons.get(passenger.get_Seat()).setEnabled(false);
        }
        */

        for (int i = 0; i < controller.getFlightController().getBookingsSize(); i++) {

            if (controller.checkBooking(i))
                for (int j = 0; j < controller.getFlightController().getBookingSize(i); j++) {
                    seatButtons.get(controller.getFlightController().getPassengerSeatFromBooking(i, j)).setEnabled(false);
                }
        }

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.getSeat() != -1) seatButtons.get(passengerPanel.getSeat()).setEnabled(false);
        }

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                for (PassengerPanel passengerPanel : passengerPanels) {
                    passengerPanel.getSeatButton().setEnabled(true);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        this.setVisible(true);
    }

    public void setLocation(JButton callingButton) {
        //coordinate punto in alto a sx del bottone
        double x = callingButton.getLocationOnScreen().getX();
        double y = callingButton.getLocationOnScreen().getY();

        //coordinate centro
        x += callingButton.getSize().getWidth() / 2;
        y += callingButton.getSize().getHeight() / 2;

        //coordinate punto in alto a sx frame
        x -= (double) this.getWidth() / 2;
        y -= (double) this.getHeight() / 2;

        this.setLocation((int) x, (int) y);
    }

    public String printSeat(int seat) {

        if (seat == -1) return "***";

        String literal;

        switch (seat % 6) {
            case 0:
                literal = "A";
                break;
            case 1:
                literal = "B";
                break;
            case 2:
                literal = "C";
                break;
            case 3:
                literal = "D";
                break;
            case 4:
                literal = "E";
                break;
            case 5:
                literal = "F";
                break;
            default:
                literal = "";
        }

        return Integer.toString((seat / 6) + 1) + literal;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
    }
}
