package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * The Arriving class represents an inbound flight in the airport management system.
 * 
 * <p>This class extends the base {@link Flight} class and adds arrival-specific
 * functionality including origin tracking and arrival delay management.
 * Arriving flights represent aircraft coming into the airport from various origins.</p>
 * 
 * <p>Key features specific to arriving flights:</p>
 * <ul>
 *   <li>Origin city/airport tracking</li>
 *   <li>Arrival delay management</li>
 *   <li>Specialized arrival-related operations</li>
 *   <li>Integration with gate assignment for arrival procedures</li>
 * </ul>
 * 
 * <p>The class supports various construction scenarios from basic flight information
 * to complete arrival details including existing bookings and delay information.</p>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see Departing
 * @see Gate
 */
public class Arriving extends Flight {

    /**
     * The origin city or airport code for this arriving flight.
     * Represents where the flight is coming from.
     */
    private String origin;
    
    /**
     * Arrival delay in minutes.
     * Positive values indicate delay, zero indicates on-time arrival.
     * Defaults to 0 (no delay) when flight is created.
     */
    private int arrivalDelay = 0;

    /**
     * Constructs a basic Arriving flight with essential information.
     * 
     * <p>Creates an arriving flight with minimum required information.
     * The arrival delay is initialized to 0 (on-time), and the flight
     * status defaults to {@link FlightStatus#PROGRAMMED}.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to arrive
     * @param parDepartureTime scheduled departure time from origin
     * @param parArrivalTime scheduled arrival time at this airport
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parOrigin origin city or airport code
     * @throws IllegalArgumentException if any required parameter is null or invalid
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats, String parOrigin) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.origin = parOrigin;
    }

    /**
     * Constructs an Arriving flight with capacity information.
     * 
     * <p>This constructor allows specifying both maximum seats and currently
     * available seats, along with flight status information.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to arrive
     * @param parDepartureTime scheduled departure time from origin
     * @param parArrivalTime scheduled arrival time at this airport
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parOrigin origin city or airport code
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;
    }

    /**
     * Constructs an Arriving flight with delay information.
     * 
     * <p>This constructor includes arrival delay information, allowing
     * creation of flights with known delays from the start.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to arrive
     * @param parDepartureTime scheduled departure time from origin
     * @param parArrivalTime scheduled arrival time at this airport
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parOrigin origin city or airport code
     * @param parArrivalDelay arrival delay in minutes (0 for on-time)
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, 
                     String parOrigin, int parArrivalDelay) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;
    }

    /**
     * Constructs an Arriving flight with gate assignment and delay information.
     * 
     * <p>This constructor includes gate assignment along with delay information,
     * useful for flights that have pre-assigned arrival gates.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to arrive
     * @param parDepartureTime scheduled departure time from origin
     * @param parArrivalTime scheduled arrival time at this airport
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parOrigin origin city or airport code
     * @param parArrivalDelay arrival delay in minutes
     * @param parGate gate assigned for arrival
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, 
                    String parOrigin, int parArrivalDelay, Gate parGate) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parGate);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;
    }

    /**
     * Constructs an Arriving flight with existing bookings and tickets.
     * 
     * <p>This constructor is typically used when loading flight data from
     * the database where bookings and tickets already exist.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled to arrive
     * @param parDepartureTime scheduled departure time from origin
     * @param parArrivalTime scheduled arrival time at this airport
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parBookings existing bookings for this flight
     * @param parTickets existing tickets for this flight
     * @param parOrigin origin city or airport code
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parOrigin) {
        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.origin = parOrigin;
    }

    /**
     * Gets the flight origin.
     * 
     * <p>Returns the origin city or airport code where this flight
     * is coming from. This information is used for passenger information,
     * arrival announcements, and route tracking.</p>
     *
     * @return the origin city or airport code
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the flight origin.
     * 
     * <p>Updates the origin for this arriving flight. This might be
     * necessary in case of route changes or corrections to flight information.</p>
     *
     * @param origin the new origin city or airport code
     * @throws IllegalArgumentException if origin is null or empty
     */
    public void setOrigin(String origin) {
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("L'origine non può essere la stringa vuota o essere null!");
        }
        this.origin = origin;
    }

    /**
     * Gets the arrival delay in minutes.
     * 
     * <p>Returns the current arrival delay. A value of 0 indicates
     * the flight is on time, positive values indicate delay in minutes.</p>
     *
     * @return the arrival delay in minutes (0 for on-time)
     */
    public int getArrivalDelay() {
        return arrivalDelay;
    }

    /**
     * Sets the arrival delay.
     * 
     * <p>Updates the arrival delay for this flight. This method is typically
     * called when flight delays are announced or when delay information is
     * updated from air traffic control or airline operations.</p>
     * 
     * <p>Setting a delay may trigger passenger notifications and updates
     * to arrival boards and other display systems.</p>
     *
     * @param arrivalDelay the new arrival delay in minutes (0 for on-time, negative values not allowed)
     * @throws IllegalArgumentException if arrivalDelay is negative
     */
    public void setArrivalDelay(int arrivalDelay) {
        if (arrivalDelay < 0) {
            throw new IllegalArgumentException("Il ritardo non può essere negativo!");
        }
        this.arrivalDelay = arrivalDelay;
    }
}