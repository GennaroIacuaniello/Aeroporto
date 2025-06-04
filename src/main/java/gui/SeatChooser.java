package gui;

import model.Flight;
import controller.Controller;
import model.Passenger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SeatChooser extends JFrame {

    private Constraints constraints;
    private ArrayList<JButton> seatButtons;
    private JButton confirmButton;
    private int offset;

    public SeatChooser(Controller controller, Flight flight, ArrayList<PassengerPanel> passengerPanels) {

        super("Seat Chooser");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        constraints = new Constraints();
        setLayout(new GridBagLayout());
        setSize(400, 800);

        seatButtons = new ArrayList<>();
        confirmButton = new JButton("CONFERMA");

        constraints.setConstraints(3, flight.get_max_seats() / 6 + 1, 1, flight.get_max_seats() / 6 + 2, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.PAGE_END);
        this.add(confirmButton, constraints.getConstraints());

        for (int i = 0; i < flight.get_max_seats(); i++) {

            seatButtons.add(new JButton());
            seatButtons.get(i).setEnabled(true);
            if (i%6 > 2) offset = 1; else offset = 0;
            constraints.setConstraints(i%6 + offset, i/6, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
            this.add(seatButtons.get(i), constraints.getConstraints());
        }

        for (Passenger passenger : flight.getPassengers()) {

            seatButtons.get(passenger.get_Seat()).setEnabled(false);
        }

        for (PassengerPanel passengerPanel : passengerPanels) {
            //setEnabled a false per  i posti gia selezionati per altri passeggeri della stessa prenotazione
        }

        //aggiungi action listener ai singoli bottoni
        
        //aggiungi action listener su conferma

        this.setVisible(true);
    }
}
