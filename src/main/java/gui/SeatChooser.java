package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Interactive seat selection dialog providing comprehensive aircraft seat visualization and assignment capabilities for passenger booking management.
 * <p>
 * This class extends {@link JFrame} to provide a specialized seat selection interface that displays
 * an interactive aircraft seat map with real-time availability checking and visual seat assignment
 * capabilities. The SeatChooser serves as the primary seat selection component for passenger workflows,
 * offering comprehensive seat visualization, availability management, and direct seat assignment
 * through an intuitive grid-based interface optimized for passenger seat selection operations.
 * </p>
 * <p>
 * The SeatChooser class provides comprehensive seat selection capabilities including:
 * </p>
 * <ul>
 *   <li><strong>Interactive Seat Map:</strong> Visual aircraft seating layout with standard airline seat designation formatting</li>
 *   <li><strong>Real-time Availability:</strong> Dynamic seat availability checking with immediate visual feedback</li>
 *   <li><strong>Conflict Prevention:</strong> Comprehensive seat conflict detection and prevention across multiple passengers</li>
 *   <li><strong>Assignment Controls:</strong> Confirmation and deletion buttons for seat assignment management</li>
 *   <li><strong>Multi-passenger Coordination:</strong> Synchronized seat button management across all passenger panels</li>
 * </ul>
 * <p>
 * The SeatChooser serves as a critical component of the passenger booking ecosystem,
 * providing essential seat selection capabilities while maintaining interface consistency,
 * user experience quality, and operational effectiveness throughout passenger seat assignment
 * and booking management workflows within the airport management system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JFrame
 * @see Controller
 * @see PassengerPanel
 * @see GridBagLayout
 * @see WindowListener
 * @see ActionListener
 */
public class SeatChooser extends JFrame {

    /**
     * Collection of interactive seat buttons representing the complete aircraft seating layout.
     * <p>
     * This final ArrayList contains all seat buttons for the aircraft, organized sequentially
     * by seat position with standard 6-seat row configuration. Each button represents a specific
     * seat position and provides interactive selection capabilities with visual feedback for
     * availability status and selection state throughout seat assignment operations.
     * </p>
     */
    private final ArrayList<JButton> seatButtons;

    /**
     * Currently selected seat identifier for assignment tracking and visual feedback management.
     * <p>
     * This integer field maintains the numerical identifier of the currently selected seat
     * within the seat chooser interface. The value -1 indicates no seat selection, while
     * positive values correspond to specific seat positions within the aircraft configuration
     * for assignment confirmation and visual state management.
     * </p>
     */
    private int seat;

    /**
     * Constructs a new SeatChooser dialog with comprehensive seat selection capabilities and multi-passenger coordination.
     * <p>
     * This constructor initializes the complete seat selection interface by creating the aircraft
     * seat map, configuring availability checking, and establishing passenger panel coordination
     * for comprehensive seat assignment management. The constructor creates a fully functional
     * seat selection dialog ready for immediate passenger interaction with integrated conflict
     * prevention and multi-passenger synchronization throughout seat selection workflows.
     * </p>
     * <p>
     * Control components include dedicated confirmation ("CONFERMA") and deletion ("ELIMINA")
     * buttons positioned at the bottom of the seat map. Both buttons include comprehensive
     * {@link ActionListener} implementation for seat assignment management, passenger panel
     * synchronization, and dialog disposal coordination throughout seat selection operations.
     * </p>
     * <p>
     * Deletion button functionality includes seat removal through -1 assignment, passenger
     * panel button re-enablement for continued selection, and proper dialog disposal for
     * workflow reset. The deletion enables seat assignment cancellation with proper interface
     * state restoration throughout passenger seat management workflows.
     * </p>
     * <p>
     * Seat map generation creates dynamic seat buttons based on flight maximum capacity
     * retrieved through <code>controller.{@link Controller#getFlightController()}.{@link controller.FlightController#getMaxSeats()}</code>.
     * Each button includes standard airline designation formatting, interactive selection
     * capabilities, and visual state management for optimal passenger seat selection experience.
     * </p>
     * <p>
     * Seat button positioning utilizes sophisticated grid calculations with aisle offset
     * logic to separate seat columns C-D for realistic aircraft layout simulation. The
     * positioning ensures proper visual organization with standard 6-seat row configuration
     * and appropriate spacing throughout the complete seat map display.
     * </p>
     * <p>
     * Availability integration processes booked seats collection to disable unavailable
     * seats and passenger seats collection to prevent conflicts with existing assignments.
     * The integration ensures comprehensive conflict prevention and real-time availability
     * checking throughout seat selection and passenger coordination workflows.
     * </p>
     *
     * @param controller the system controller providing access to flight capacity information and seat management functionality
     * @param callingPanel the passenger panel initiating seat selection for assignment coordination and result communication
     * @param passengerPanels the list of all passenger panels for multi-passenger coordination and conflict prevention
     * @param bookedSeats the list of already booked seat positions for availability checking and conflict prevention
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
     * Positions the seat chooser dialog optimally relative to the calling seat selection button for enhanced user experience.
     * <p>
     * This method calculates precise screen coordinates to center the seat selection dialog
     * relative to the passenger panel's seat button, ensuring optimal visual presentation
     * and accessibility during seat selection operations. The positioning algorithm considers
     * button screen location, dialog dimensions, and screen geometry to provide consistent
     * dialog placement throughout passenger seat selection workflows.
     * </p>
     *
     * @param callingButton the seat selection button from the passenger panel for dialog positioning reference and visual alignment
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
     * Converts numerical seat position to standard airline seat designation with row and letter formatting for passenger display.
     * <p>
     * This method transforms internal numerical seat identifiers into human-readable airline
     * seat designations using standard row numbers and letter columns (A-F). The conversion
     * provides clear seat identification for passenger understanding and maintains consistency
     * with standard airline seating conventions throughout seat selection and assignment
     * operations within the airport management system.
     * </p>
     * <p>
     * The seat designation formatting includes:
     * </p>
     * <ul>
     *   <li><strong>Row Calculation:</strong> Determines row number using integer division by 6 (seats per row) plus 1 for human-readable numbering</li>
     *   <li><strong>Column Assignment:</strong> Maps seat remainder to letters A-F for position within row using standard airline configuration</li>
     *   <li><strong>Standard Format:</strong> Combines row number and letter for complete seat designation (e.g., "12A", "15F")</li>
     *   <li><strong>Error State Handling:</strong> Returns "***" for invalid seat positions (-1) indicating no selection or error states</li>
     * </ul>
     *
     * @param seat the numerical seat identifier for conversion to airline seat designation format
     * @return formatted seat designation string (e.g., "12A", "15F") or "***" for invalid positions
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