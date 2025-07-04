package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SeatChooser extends JFrame {

    private JScrollPane scrollPane;
    private JPanel seatPanel;
    private Constraints constraints;
    private ArrayList<JButton> seatButtons;
    private JButton confirmButton;
    private JButton deleteButton;
    private int seat;

    public SeatChooser(Controller controller, PassengerPanel callingPanel, ArrayList<PassengerPanel> passengerPanels) {

        super("Seat Chooser");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        constraints = new Constraints();
        this.setLayout(new GridBagLayout());
        setSize(450, 600);
        setLocation(callingPanel.getSeatButton());
        setAlwaysOnTop(true);

        seatPanel = new JPanel();
        seatPanel.setLayout(new GridBagLayout());
        scrollPane = new JScrollPane();

        seat = -1;
        seatButtons = new ArrayList<JButton>();
        confirmButton = new JButton("CONFERMA");
        deleteButton = new JButton("ELIMINA");

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

        //aggiungo bottoni
        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 5, 5, GridBagConstraints.CENTER, 0.01f, 0.01f);
        this.add(confirmButton, constraints.getConstraints());

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 5, 5, GridBagConstraints.CENTER, 0.01f, 0.01f);
        this.add(deleteButton, constraints.getConstraints());

        //aggiungo scrollPane
        constraints.setConstraints(0, 1, 2, 1,
                GridBagConstraints.BOTH, 5, 5, GridBagConstraints.CENTER, 0.5f, 0.5f);
        this.add(scrollPane, constraints.getConstraints());

        //aggiungo posti
        for (int i = 0; i < controller.getFlightController().getMaxSeats(); i++) {
            int finalI = i;
            seatButtons.add(new JButton(printSeat(finalI)));
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

            int leftInset = 0;
            int rightInset = 0;

            if (i%6 == 3) leftInset = 30;
            if (i%6 == 2) rightInset = 30;

            seatButtons.get(i).setPreferredSize(new Dimension(54, 54));

            constraints.setConstraints(i%6, i/6, 1, 1,
                    GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER, new Insets(8, leftInset, 8, rightInset));
            seatPanel.add(seatButtons.get(i), constraints.getConstraints());
        }
        
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

        seatPanel.setVisible(true);
        scrollPane.setViewportView(seatPanel);
        scrollPane.setVisible(true);

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

        switch(seat%6){
            case 0: literal = "A"; break;
            case 1: literal = "B"; break;
            case 2: literal = "C"; break;
            case 3: literal = "D"; break;
            case 4: literal = "E"; break;
            case 5: literal = "F"; break;
            default: literal = "";
        }

        return Integer.toString((seat/6)+1) + literal;
    }


}
