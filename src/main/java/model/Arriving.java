package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Represents an arriving flight in the airport management system.
 * <p>
 * This class extends the {@link Flight} class to provide specialized functionality
 * for flights that are arriving at the airport. It includes additional attributes
 * specific to arriving flights such as origin location and arrival delay information.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see FlightStatus
 * @see Gate
 * @see Booking
 * @see Ticket
 */
public class Arriving extends Flight{

    /**
     * The origin location of this arriving flight.
     * <p>
     * This field stores the departure location or city from which the flight
     * is arriving.
     * </p>
     */
    private String origin;
    
    /**
     * The arrival delay in minutes for this flight.
     * <p>
     * This field represents any delay affecting the scheduled arrival time,
     * measured in minutes. A value of 0 indicates no delay, while positive
     * values indicate the number of minutes the flight is delayed.
     * Defaults to 0 when no delay information is provided.
     * </p>
     */
    private int arrivalDelay = 0;

    /**
     * Constructs a new arriving flight with basic information.
     * <p>
     * Creates an arriving flight with essential details including flight identification,
     * company information, scheduling, and seating capacity. The flight status defaults
     * to PROGRAMMED and free seats equal the maximum seats.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from origin
     * @param parArrivalTime the scheduled arrival time at this airport
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parOrigin the origin location of the flight
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.origin = parOrigin;

    }

    /**
     * Constructs a new arriving flight with status and seating information.
     * <p>
     * Creates an arriving flight with specified flight status and detailed seating
     * information, allowing for flights that may already have bookings or specific
     * operational status.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from origin
     * @param parArrivalTime the scheduled arrival time at this airport
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parOrigin the origin location of the flight
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;

    }

    /**
     * Constructs a new arriving flight with delay information.
     * <p>
     * Creates an arriving flight that includes arrival delay information.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from origin
     * @param parArrivalTime the scheduled arrival time at this airport
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parOrigin the origin location of the flight
     * @param parArrivalDelay the arrival delay in minutes
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Constructs a new arriving flight with gate assignment.
     * <p>
     * Creates an arriving flight with a specific gate assignment.
     * Includes delay information for flight tracking.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from origin
     * @param parArrivalTime the scheduled arrival time at this airport
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parOrigin the origin location of the flight
     * @param parArrivalDelay the arrival delay in minutes
     * @param parGate the assigned gate for this flight
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parOrigin, int parArrivalDelay, Gate parGate){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parGate);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Constructs a new arriving flight with existing bookings and tickets.
     * <p>
     * Creates an arriving flight that includes existing booking and ticket
     * information, useful when reconstructing flight objects from database.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from origin
     * @param parArrivalTime the scheduled arrival time at this airport
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parBookings the list of existing bookings for this flight
     * @param parTickets the list of existing tickets for this flight
     * @param parOrigin the origin location of the flight
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parOrigin){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.origin = parOrigin;

    }

    /**
     * Constructs a new arriving flight with bookings, tickets, and delay information.
     * <p>
     * Creates a comprehensive arriving flight object that includes existing bookings,
     * tickets, and arrival delay information. This constructor is typically used
     * when creating flight objects from complete database records.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from origin
     * @param parArrivalTime the scheduled arrival time at this airport
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parBookings the list of existing bookings for this flight
     * @param parTickets the list of existing tickets for this flight
     * @param parOrigin the origin location of the flight
     * @param parArrivalDelay the arrival delay in minutes
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Constructs a new arriving flight with complete information including gate assignment.
     * <p>
     * Creates the most comprehensive arriving flight object with all available information
     * including existing bookings, tickets, gate assignment, and delay information.
     * This constructor provides the complete flight representation.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from origin
     * @param parArrivalTime the scheduled arrival time at this airport
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parBookings the list of existing bookings for this flight
     * @param parTickets the list of existing tickets for this flight
     * @param parGate the assigned gate for this flight
     * @param parOrigin the origin location of the flight
     * @param parArrivalDelay the arrival delay in minutes
     */
    public Arriving(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, Gate parGate, String parOrigin, int parArrivalDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets, parGate);
        this.origin = parOrigin;
        this.arrivalDelay = parArrivalDelay;

    }

    /**
     * Gets the origin location of this arriving flight.
     * <p>
     * Returns the departure location or city from which this flight is arriving.
     * This information is used for passenger information displays and airport
     * operational systems.
     * </p>
     *
     * @return the origin location of the flight
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin location of this arriving flight.
     * <p>
     * Updates the departure location or city from which this flight is arriving.
     * This method allows for corrections or updates to flight origin information.
     * </p>
     *
     * @param origin the new origin location of the flight
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets the arrival delay in minutes for this flight.
     * <p>
     * Returns the number of minutes this flight is delayed from its scheduled
     * arrival time. A value of 0 indicates no delay, while positive values
     * represent the delay duration in minutes.
     * </p>
     *
     * @return the arrival delay in minutes
     */
    public int getArrivalDelay() {
        return arrivalDelay;
    }

    /**
     * Sets the arrival delay in minutes for this flight.
     * <p>
     * Updates the arrival delay information for this flight. This method is
     * typically used when flight delays are reported or updated by air traffic
     * control or airline operations.
     * </p>
     *
     * @param arrivalDelay the new arrival delay in minutes
     */
    public void setArrivalDelay(int arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }


}