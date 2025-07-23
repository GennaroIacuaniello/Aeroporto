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
 *   <li><strong>Professional Layout:</strong> Standard aircraft configuration with aisle separation and row organization</li>
 *   <li><strong>Assignment Controls:</strong> Confirmation and deletion buttons for seat assignment management</li>
 *   <li><strong>Multi-passenger Coordination:</strong> Synchronized seat button management across all passenger panels</li>
 *   <li><strong>Positioning System:</strong> Intelligent dialog positioning relative to calling buttons for optimal user experience</li>
 * </ul>
 * <p>
 * The interface is designed with passenger experience optimization, providing travelers with:
 * </p>
 * <ul>
 *   <li><strong>Visual Seat Map:</strong> Complete aircraft seating visualization with clear seat identification and availability status</li>
 *   <li><strong>Intuitive Selection:</strong> Click-based seat selection with immediate visual feedback and selection confirmation</li>
 *   <li><strong>Clear Assignment Controls:</strong> Dedicated confirmation and deletion buttons for precise seat management</li>
 *   <li><strong>Availability Awareness:</strong> Real-time seat availability display with disabled states for unavailable seats</li>
 *   <li><strong>Standard Formatting:</strong> Airline-standard seat designations with row numbers and letter columns (A-F)</li>
 *   <li><strong>Responsive Interface:</strong> Modal dialog design that maintains focus and provides clear operational feedback</li>
 * </ul>
 * <p>
 * Dialog architecture utilizes {@link GridBagLayout} for precise seat positioning and standard
 * aircraft configuration simulation. The layout includes proper aisle separation between seat
 * columns C-D, standard row organization, and control button positioning for optimal user
 * interaction throughout seat selection and assignment workflows.
 * </p>
 * <p>
 * Seat visualization includes comprehensive aircraft seating representation with:
 * </p>
 * <ul>
 *   <li><strong>Standard Configuration:</strong> 6-seat rows with A-B-C aisle D-E-F pattern for realistic aircraft simulation</li>
 *   <li><strong>Seat Designation:</strong> Standard airline formatting with row numbers and letter positions (e.g., "12A", "15F")</li>
 *   <li><strong>Visual Status:</strong> Enabled/disabled button states for immediate availability indication</li>
 *   <li><strong>Selection Feedback:</strong> Dynamic button state management with visual selection confirmation</li>
 *   <li><strong>Aisle Separation:</strong> Proper visual spacing between seat groups for realistic layout representation</li>
 * </ul>
 * <p>
 * Availability management provides sophisticated seat conflict detection through integration
 * with booked seats tracking and multi-passenger coordination. The system prevents double-booking
 * conflicts, manages passenger panel synchronization, and maintains seat assignment consistency
 * throughout booking and modification workflows within the airport management system.
 * </p>
 * <p>
 * Assignment controls include dedicated confirmation and deletion buttons positioned at the
 * bottom of the seat map for clear operational access. The controls provide immediate seat
 * assignment confirmation, seat deletion capabilities, and proper passenger panel synchronization
 * throughout seat selection and modification operations.
 * </p>
 * <p>
 * Multi-passenger coordination ensures comprehensive seat button management across all passenger
 * panels within the booking context. The coordination includes button state synchronization,
 * conflict prevention across multiple passengers, and proper resource management throughout
 * seat selection operations and passenger interaction workflows.
 * </p>
 * <p>
 * Positioning system provides intelligent dialog placement relative to calling buttons for
 * optimal user experience. The system calculates precise screen coordinates to center the
 * dialog relative to the seat selection button, ensuring consistent visual presentation
 * and optimal accessibility throughout passenger seat selection operations.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through comprehensive
 * {@link Controller} integration, enabling access to flight capacity information, seat management
 * services, and passenger coordination workflows. The integration supports passenger travel
 * planning while maintaining proper separation of concerns and interface consistency.
 * </p>
 * <p>
 * Resource management includes comprehensive window event handling for proper cleanup and
 * passenger panel synchronization during dialog closure. The management ensures that passenger
 * panel seat buttons are properly re-enabled and interface state is maintained throughout
 * seat selection operations and dialog lifecycle management.
 * </p>
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
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Dialog Configuration:</strong> Window properties setup with modal behavior and positioning system</li>
     *   <li><strong>Layout Management:</strong> GridBagLayout establishment with constraints utility for precise seat positioning</li>
     *   <li><strong>Control Components:</strong> Confirmation and deletion button creation with comprehensive event handling</li>
     *   <li><strong>Seat Map Generation:</strong> Dynamic seat button creation based on flight capacity with standard formatting</li>
     *   <li><strong>Availability Integration:</strong> Booked seat checking and passenger conflict prevention throughout selection</li>
     *   <li><strong>Event Handling:</strong> Window listener setup for proper cleanup and passenger panel synchronization</li>
     *   <li><strong>Visual Activation:</strong> Dialog visibility setup for immediate user interaction and seat selection</li>
     * </ul>
     * <p>
     * Dialog configuration establishes comprehensive window properties including "Seat Chooser"
     * title, DISPOSE_ON_CLOSE behavior for proper resource management, fixed sizing (450x800)
     * for optimal seat map display, and always-on-top behavior for modal interaction throughout
     * seat selection operations and passenger workflow coordination.
     * </p>
     * <p>
     * Layout management utilizes {@link GridBagLayout} for precise seat positioning with
     * {@link Constraints} utility integration for consistent component placement. The layout
     * supports standard aircraft configuration with proper aisle separation and row organization
     * for realistic seat map representation throughout passenger selection workflows.
     * </p>
     * <p>
     * Control components include dedicated confirmation ("CONFERMA") and deletion ("ELIMINA")
     * buttons positioned at the bottom of the seat map. Both buttons include comprehensive
     * {@link ActionListener} implementation for seat assignment management, passenger panel
     * synchronization, and dialog disposal coordination throughout seat selection operations.
     * </p>
     * <p>
     * Confirmation button functionality includes seat assignment through {@link PassengerPanel#setSeat(int)},
     * passenger panel button re-enablement across all passengers, and proper dialog disposal
     * for workflow completion. The confirmation ensures immediate seat assignment with
     * comprehensive passenger panel coordination throughout booking management operations.
     * </p>
     * <p>
     * Deletion button functionality includes seat removal through -1 assignment, passenger
     * panel button re-enablement for continued selection, and proper dialog disposal for
     * workflow reset. The deletion enables seat assignment cancellation with proper interface
     * state restoration throughout passenger seat management workflows.
     * </p>
     * <p>
     * Seat map generation creates dynamic seat buttons based on flight maximum capacity
     * retrieved through {@link Controller#getFlightController()#getMaxSeats()}. Each button
     * includes standard airline designation formatting, interactive selection capabilities,
     * and visual state management for optimal passenger seat selection experience.
     * </p>
     * <p>
     * Seat button positioning utilizes sophisticated grid calculations with aisle offset
     * logic to separate seat columns C-D for realistic aircraft layout simulation. The
     * positioning ensures proper visual organization with standard 6-seat row configuration
     * and appropriate spacing throughout the complete seat map display.
     * </p>
     * <p>
     * Interactive seat selection includes comprehensive {@link ActionListener} implementation
     * for each seat button that manages current selection updates, previous selection cleanup,
     * visual state synchronization, and conflict prevention throughout multi-passenger
     * seat selection operations and booking workflow coordination.
     * </p>
     * <p>
     * Availability integration processes booked seats collection to disable unavailable
     * seats and passenger seats collection to prevent conflicts with existing assignments.
     * The integration ensures comprehensive conflict prevention and real-time availability
     * checking throughout seat selection and passenger coordination workflows.
     * </p>
     * <p>
     * Window event handling includes comprehensive {@link WindowListener} implementation
     * for proper cleanup during dialog closure. The handling ensures passenger panel seat
     * button re-enablement and interface state restoration when seat selection is cancelled
     * or interrupted throughout passenger interaction workflows.
     * </p>
     * <p>
     * Visual activation includes immediate dialog visibility setup for user interaction
     * and positioning coordination through {@link #setLocation(JButton)} method integration.
     * The activation ensures optimal dialog placement and immediate availability for
     * passenger seat selection throughout booking and modification workflows.
     * </p>
     * <p>
     * The constructor completes by establishing a fully functional seat selection dialog
     * ready for immediate passenger interaction, providing comprehensive seat visualization,
     * conflict prevention, and passenger coordination capabilities throughout seat assignment
     * and booking management operations within the airport management system.
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
     * <p>
     * The positioning calculation process includes:
     * </p>
     * <ul>
     *   <li><strong>Button Location Detection:</strong> Screen coordinate retrieval for calling button position</li>
     *   <li><strong>Center Point Calculation:</strong> Button center coordinate determination for optimal reference</li>
     *   <li><strong>Dialog Offset Calculation:</strong> Dialog positioning offset for centered placement relative to button</li>
     *   <li><strong>Coordinate Application:</strong> Final screen position setting for dialog display optimization</li>
     * </ul>
     * <p>
     * Button location detection utilizes {@link JButton#getLocationOnScreen()} to determine
     * the absolute screen coordinates of the calling seat button, providing accurate reference
     * positioning for dialog placement calculations throughout seat selection operations.
     * </p>
     * <p>
     * Center point calculation includes button dimension analysis through {@link JButton#getSize()}
     * to determine the visual center of the calling button. The calculation ensures dialog
     * positioning relative to the button's center rather than its corner for optimal
     * visual alignment and user experience throughout seat selection workflows.
     * </p>
     * <p>
     * Dialog offset calculation determines the appropriate screen coordinates for dialog
     * placement by subtracting half of the dialog's width and height from the button's
     * center coordinates. The calculation ensures the dialog appears centered relative
     * to the calling button for consistent visual presentation.
     * </p>
     * <p>
     * Coordinate application includes final position setting through {@link JFrame#setLocation(int, int)}
     * with integer coordinate conversion for precise dialog placement. The application ensures
     * the seat chooser dialog appears in the optimal position for passenger interaction
     * and accessibility throughout seat selection operations.
     * </p>
     * <p>
     * The positioning system enhances user experience by providing predictable dialog
     * placement that maintains visual context with the calling seat button while ensuring
     * the dialog remains accessible and properly positioned throughout seat selection
     * workflows within the airport management system passenger interface.
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
     * <p>
     * Row calculation utilizes integer division by 6 to determine the row number, accounting
     * for the standard aircraft configuration of 6 seats per row (A-B-C aisle D-E-F). The
     * calculation adds 1 to provide human-readable row numbering starting from 1 rather
     * than 0 for passenger familiarity and airline convention consistency.
     * </p>
     * <p>
     * Column assignment employs modulo 6 operation to determine seat position within the
     * row, mapping remainders 0-5 to letters A-F respectively. The mapping follows standard
     * airline seating conventions with A and F as window seats, B and E as middle seats,
     * and C and D as aisle seats for passenger seat type identification.
     * </p>
     * <p>
     * Standard format combination creates complete seat designations by concatenating the
     * calculated row number with the assigned letter column. The format produces familiar
     * airline seat identifiers (e.g., "1A", "12C", "25F") that passengers can easily
     * understand and reference throughout booking and travel operations.
     * </p>
     * <p>
     * Error state handling provides appropriate feedback for invalid seat positions by
     * returning "***" when the seat parameter equals -1. This handling supports interface
     * states where no seat is selected or error conditions exist throughout seat
     * selection and assignment workflows.
     * </p>
     * <p>
     * The method ensures consistent seat designation formatting throughout the airport
     * management system, supporting passenger comprehension and operational clarity
     * during seat selection, assignment, and travel coordination workflows while
     * maintaining compatibility with standard airline industry conventions.
     * </p>
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