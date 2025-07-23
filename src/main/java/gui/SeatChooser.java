package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The SeatChooser class provides a graphical interface for selecting airplane seats.
 * This dialog displays a grid of seat buttons arranged in a layout similar to an airplane cabin,
 * with rows numbered and columns labeled A-F. It allows passengers to select an available seat,
 * showing which seats are already booked or selected by other passengers in the same booking.
 * The dialog includes confirmation and deletion buttons to apply or remove the seat selection.
 */
public class SeatChooser extends JFrame {

    /**
     * The collection of all seat buttons in the grid.
     * Each button represents a seat in the airplane and can be selected by the user.
     * Buttons are enabled or disabled based on seat availability.
     */
    private final ArrayList<JButton> seatButtons;

    /**
     * The currently selected seat number.
     * This value is -1 if no seat is selected, otherwise it represents
     * the index of the selected seat in the seatButtons array.
     */
    private int seat;

    /**
     * Instantiates a new Seat chooser dialog.
     * This constructor creates and configures the seat selection grid, showing available
     * and unavailable seats. It sets up the confirmation and deletion buttons, and
     * handles the interaction between selected seats and the passenger panel.
     *
     * @param controller      the main controller that provides access to flight information
     *                        including the maximum number of seats
     * @param callingPanel    the passenger panel that initiated this seat selection,
     *                        which will receive the selected seat information
     * @param passengerPanels the list of all passenger panels in the current booking,
     *                        used to identify seats already selected by other passengers
     * @param bookedSeats     the list of seat numbers that are already booked on this flight
     *                        and cannot be selected
     */
    public SeatChooser(Controller controller, PassengerPanel callingPanel, List<PassengerPanel> passengerPanels, List<Integer> bookedSeats) {

        super("Seat Chooser");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Constraints constraints = new Constraints();
        setLayout(new GridBagLayout());
        setSize(450, 800);
        setLocation(callingPanel.getSeatButton());
        setAlwaysOnTop(true);

        seat = -1;
        seatButtons = new ArrayList<>();
        JButton confirmButton = new JButton("CONFERMA");
        JButton deleteButton = new JButton("ELIMINA");

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
        this.add(confirmButton, constraints.getGridBagConstraints());

        constraints.setConstraints(4, controller.getFlightController().getMaxSeats() / 6 + 1, 1,
                controller.getFlightController().getMaxSeats() / 6 + 2, GridBagConstraints.NONE, 0, 0,
                GridBagConstraints.LAST_LINE_START);
        this.add(deleteButton, constraints.getGridBagConstraints());

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

            int offset;
            if (i % 6 > 2) offset = 2;
            else offset = 0;
            constraints.setConstraints(i % 6 + offset, i / 6, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
            this.add(seatButtons.get(i), constraints.getGridBagConstraints());
        }

        for (Integer bookedSeat : bookedSeats) {
            seatButtons.get(bookedSeat).setEnabled(false);
        }

        for (PassengerPanel passengerPanel : passengerPanels) {
            if (passengerPanel.getSeat() != -1) seatButtons.get(passengerPanel.getSeat()).setEnabled(false);
        }

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                //
            }

            @Override
            public void windowClosing(WindowEvent e) {
                for (PassengerPanel passengerPanel : passengerPanels) {
                    passengerPanel.getSeatButton().setEnabled(true);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //
            }
        });

        this.setVisible(true);
    }

    /**
     * Centers this dialog over the specified button.
     * This method calculates the position to place the dialog so that it appears
     * centered over the button that triggered it, creating a more intuitive
     * user experience by connecting the visual elements spatially.
     *
     * @param callingButton the button that triggered this dialog and over which
     *                      the dialog should be centered
     */
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

    /**
     * Converts a seat index to a human-readable seat code.
     * This method transforms a numeric seat index (0-based) into a standard airline
     * seat code format combining row number (1-based) and column letter (A-F).
     * For example, seat index 7 would be converted to "2B" (second row, column B).
     * If the seat index is -1, it returns "***" to indicate no seat selection.
     *
     * @param seat the numeric index of the seat (0-based), or -1 for no selection
     * @return the formatted seat code (e.g., "1A", "2B") or "***" for no selection
     */
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

        return (seat / 6) + 1 + literal;
    }
}
