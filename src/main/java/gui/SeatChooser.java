package gui;

import model.Flight;
import controller.Controller;
import model.Passenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SeatChooser extends JFrame {

    private Constraints constraints;
    private ArrayList<JButton> seatButtons;
    private JButton confirmButton;
    private int offset;
    private int seat;

    public SeatChooser(Controller controller, PassengerPanel callingPanel, Flight flight, ArrayList<PassengerPanel> passengerPanels) {

        super("Seat Chooser");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        constraints = new Constraints();
        setLayout(new GridBagLayout());
        setSize(450, 800);

        seat = -1;
        seatButtons = new ArrayList<>();
        confirmButton = new JButton("CONFERMA");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callingPanel.setSeat(seat);
                dispose();
            }
        });

        constraints.setConstraints(3, flight.get_max_seats() / 6 + 1, 1, flight.get_max_seats() / 6 + 2, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.PAGE_END);
        this.add(confirmButton, constraints.getConstraints());

        for (int i = 0; i < flight.get_max_seats(); i++) {

            int finalI = i;
            seatButtons.add(new JButton(print_seat(finalI)));
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

            if (i%6 > 2) offset = 1; else offset = 0;
            constraints.setConstraints(i%6 + offset, i/6, 1, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.CENTER);
            this.add(seatButtons.get(i), constraints.getConstraints());
        }

        for (Passenger passenger : flight.getPassengers()) {

            seatButtons.get(passenger.get_Seat()).setEnabled(false);
        }

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.getSeat() != -1) seatButtons.get(passengerPanel.getSeat()).setEnabled(false);
        }

        //aggiungi action listener ai singoli bottoni
        
        //aggiungi action listener su conferma

        this.setVisible(true);
    }

    public String print_seat(int seat) {

        if (seat == -1) return "/";

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
