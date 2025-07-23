package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * The Departing class represents an outbound flight in the airport management system.
 * 
 * <p>This class extends the base {@link Flight} class and adds departure-specific
 * functionality including destination management and departure delay tracking.
 * Departing flights represent aircraft leaving the airport to various destinations.</p>
 * 
 * <p>Key features specific to departing flights:</p>
 * <ul>
 *   <li>Destination city/airport tracking</li>
 *   <li>Departure delay management</li>
 *   <li>Specialized departure-related operations</li>
 *   <li>Integration with gate assignment for departure procedures</li>
 * </ul>
 * 
 * <p>The class supports various construction scenarios from basic flight information
 * to complete departure details including existing bookings and tickets.</p>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see Arriving
 * @see Gate
 */
public class Departing extends Flight {

    /**
     * The destination city or airport code for this departing flight.
     * Represents where the flight is heading to.
     */
    private String destination;
    
    /**
     * Departure delay in minutes.
     * Positive values indicate delay, zero indicates on-time departure.
     * Defaults to 0 (no delay) when flight is created.
     */
    private int departureDelay = 0;

    /**
     * Constructs a basic Departing flight with essential information.
     * 
     * <p>Creates a departing flight with minimum required information.
     * The departure delay is initialized to 0 (on-time), and the flight
     * status defaults to {@link FlightStatus#PROGRAMMED}.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to depart
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time at destination
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parDestination destination city or airport code
     * @throws IllegalArgumentException if any required parameter is null or invalid
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, int parMaxSeats, String parDestination) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.destination = parDestination;
    }

    /**
     * Constructs a Departing flight with status information.
     * 
     * <p>This constructor allows setting the initial flight status during creation,
     * which is useful when creating flights that are not in the default
     * {@link FlightStatus#PROGRAMMED} state.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to depart
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time at destination
     * @param parStatus initial status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parDestination destination city or airport code
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, String parDestination) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats);
        this.destination = parDestination;
    }

    /**
     * Constructs a Departing flight with capacity and availability information.
     * 
     * <p>This constructor allows specifying both maximum seats and currently
     * available seats, which may differ if some seats are blocked or reserved.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to depart
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time at destination
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parDestination destination city or airport code
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.destination = parDestination;
    }

    /**
     * Constructs a Departing flight with delay information.
     * 
     * <p>This constructor includes departure delay information, allowing
     * creation of flights with known delays from the start.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to depart
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time at destination
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parDestination destination city or airport code
     * @param parDepartureDelay departure delay in minutes (0 for on-time)
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, 
                    String parDestination, int parDepartureDelay) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;
    }

    /**
     * Constructs a Departing flight with existing bookings and tickets.
     * 
     * <p>This constructor is typically used when loading flight data from
     * the database where bookings and tickets already exist.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to depart
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time at destination
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parBookings existing bookings for this flight
     * @param parTickets existing tickets for this flight
     * @param parDestination destination city or airport code
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parDestination) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.destination = parDestination;
    }

    /**
     * Constructs a Departing flight with bookings, tickets, and delay information.
     * 
     * <p>Comprehensive constructor including existing bookings, tickets, and
     * departure delay information.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to depart
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time at destination
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parBookings existing bookings for this flight
     * @param parTickets existing tickets for this flight
     * @param parDestination destination city or airport code
     * @param parDepartureDelay departure delay in minutes
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parDestination, int parDepartureDelay) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;
    }

    /**
     * Constructs a complete Departing flight with gate assignment.
     * 
     * <p>Most comprehensive constructor including gate assignment along with
     * all other flight information. Used for complete flight reconstruction
     * or when creating flights with pre-assigned gates.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to depart
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time at destination
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parBookings existing bookings for this flight
     * @param parTickets existing tickets for this flight
     * @param parGate gate assigned for departure
     * @param parDestination destination city or airport code
     * @param parDepartureDelay departure delay in minutes
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, Gate parGate, 
                    String parDestination, int parDepartureDelay) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets, parGate);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;
    }

    /**
     * Gets the flight destination.
     * 
     * <p>Returns the destination city or airport code where this flight
     * is heading. This information is used for passenger information,
     * boarding announcements, and route planning.</p>
     *
     * @return the destination city or airport code
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the flight destination.
     * 
     * <p>Updates the destination for this departing flight. This might be
     * necessary in case of route changes or corrections to flight information.</p>
     *
     * @param destination the new destination city or airport code
     * @throws IllegalArgumentException if destination is null or empty
     */
    public void setDestination(String destination) {
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("La destinazione non può essere la stringa vuota o essere null!");
        }
        this.destination = destination;
    }

    /**
     * Gets the departure delay in minutes.
     * 
     * <p>Returns the current departure delay. A value of 0 indicates
     * the flight is on time, positive values indicate delay in minutes.</p>
     *
     * @return the departure delay in minutes (0 for on-time)
     */
    public int getDepartureDelay() {
        return departureDelay;
    }

    /**
     * Sets the departure delay.
     * 
     * <p>Updates the departure delay for this flight. This method is typically
     * called when flight delays are announced or when delay information is
     * updated from air traffic control or airline operations.</p>
     * 
     * <p>Setting a delay may trigger passenger notifications and updates
     * to departure boards and other display systems.</p>
     *
     * @param departureDelay the new departure delay in minutes (0 for on-time, negative values not allowed)
     * @throws IllegalArgumentException if departureDelay is negative
     */
    public void setDepartureDelay(int departureDelay) {
        if (departureDelay < 0) {
            throw new IllegalArgumentException("Il ritardo non può essere negativo!");
        }
        this.departureDelay = departureDelay;
    }
}