package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Represents a departing flight in the airport management system.
 * <p>
 * This class extends the {@link Flight} class to provide specialized functionality
 * for flights that are departing from the airport. It includes additional attributes
 * specific to departing flights such as destination location and departure delay information.
 * </p>
 * <p>
 * The class maintains information about the flight's destination and any delays that may
 * affect the departure time. The departure delay is measured in minutes and defaults to 0
 * when no delay is present. This information is crucial for airport operations,
 * passenger notifications, and gate management.
 * </p>
 * <p>
 * The class provides multiple constructors to accommodate different scenarios of
 * flight creation, from basic flight information to comprehensive details including
 * bookings, tickets, and gate assignments.
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
public class Departing extends Flight{

    /**
     * The destination location of this departing flight.
     * <p>
     * This field stores the arrival location or city to which the flight
     * is departing. This information is essential for passenger information
     * systems and airport displays.
     * </p>
     */
    private String destination;
    
    /**
     * The departure delay in minutes for this flight.
     * <p>
     * This field represents any delay affecting the scheduled departure time,
     * measured in minutes. A value of 0 indicates no delay, while positive
     * values indicate the number of minutes the flight is delayed.
     * Defaults to 0 when no delay information is provided.
     * </p>
     */
    private int departureDelay = 0;

    /**
     * Constructs a new departing flight with basic information.
     * <p>
     * Creates a departing flight with essential details including flight identification,
     * company information, scheduling, and seating capacity. The flight status defaults
     * to PROGRAMMED and free seats equal the maximum seats.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from this airport
     * @param parArrivalTime the scheduled arrival time at destination
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parDestination the destination location of the flight
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, int parMaxSeats, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parMaxSeats);
        this.destination = parDestination;

    }

    /**
     * Constructs a new departing flight with status information.
     * <p>
     * Creates a departing flight with specified flight status, allowing for flights
     * that may already have a specific operational status different from the default.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from this airport
     * @param parArrivalTime the scheduled arrival time at destination
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parDestination the destination location of the flight
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                     Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats);
        this.destination = parDestination;

    }

    /**
     * Constructs a new departing flight with status and seating information.
     * <p>
     * Creates a departing flight with specified flight status and detailed seating
     * information, allowing for flights that may already have bookings or specific
     * operational status.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from this airport
     * @param parArrivalTime the scheduled arrival time at destination
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parDestination the destination location of the flight
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.destination = parDestination;

    }

    /**
     * Constructs a new departing flight with delay information.
     * <p>
     * Creates a departing flight that includes departure delay information,
     * which is essential for real-time flight tracking and passenger notifications.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from this airport
     * @param parArrivalTime the scheduled arrival time at destination
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parDestination the destination location of the flight
     * @param parDepartureDelay the departure delay in minutes
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, String parDestination, int parDepartureDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;

    }

    /**
     * Constructs a new departing flight with existing bookings and tickets.
     * <p>
     * Creates a departing flight that includes existing booking and ticket
     * information, useful when reconstructing flight objects from database
     * records or transferring flight data between systems.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from this airport
     * @param parArrivalTime the scheduled arrival time at destination
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parBookings the list of existing bookings for this flight
     * @param parTickets the list of existing tickets for this flight
     * @param parDestination the destination location of the flight
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parDestination){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.destination = parDestination;

    }

    /**
     * Constructs a new departing flight with bookings, tickets, and delay information.
     * <p>
     * Creates a comprehensive departing flight object that includes existing bookings,
     * tickets, and departure delay information. This constructor is typically used
     * when creating flight objects from complete database records.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from this airport
     * @param parArrivalTime the scheduled arrival time at destination
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parBookings the list of existing bookings for this flight
     * @param parTickets the list of existing tickets for this flight
     * @param parDestination the destination location of the flight
     * @param parDepartureDelay the departure delay in minutes
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, String parDestination, int parDepartureDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;

    }

    /**
     * Constructs a new departing flight with complete information including gate assignment.
     * <p>
     * Creates the most comprehensive departing flight object with all available information
     * including existing bookings, tickets, gate assignment, and delay information.
     * This constructor provides the complete flight representation for full system integration.
     * </p>
     *
     * @param parId the unique identifier for the flight
     * @param parCompanyName the name of the airline company
     * @param parDate the date when the flight is scheduled
     * @param parDepartureTime the scheduled departure time from this airport
     * @param parArrivalTime the scheduled arrival time at destination
     * @param parStatus the current status of the flight
     * @param parMaxSeats the maximum number of seats on the flight
     * @param parFreeSeats the number of available seats
     * @param parBookings the list of existing bookings for this flight
     * @param parTickets the list of existing tickets for this flight
     * @param parGate the assigned gate for this flight
     * @param parDestination the destination location of the flight
     * @param parDepartureDelay the departure delay in minutes
     */
    public Departing(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                    Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                    List<Booking> parBookings, List<Ticket> parTickets, Gate parGate, String parDestination, int parDepartureDelay){

        super(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parStatus, parMaxSeats, parFreeSeats, parBookings, parTickets, parGate);
        this.destination = parDestination;
        this.departureDelay = parDepartureDelay;

    }

    /**
     * Gets the destination location of this departing flight.
     * <p>
     * Returns the arrival location or city to which this flight is departing.
     * This information is used for passenger information displays and airport
     * operational systems.
     * </p>
     *
     * @return the destination location of the flight
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination location of this departing flight.
     * <p>
     * Updates the arrival location or city to which this flight is departing.
     * This method allows for corrections or updates to flight destination information.
     * </p>
     *
     * @param destination the new destination location of the flight
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the departure delay in minutes for this flight.
     * <p>
     * Returns the number of minutes this flight is delayed from its scheduled
     * departure time. A value of 0 indicates no delay, while positive values
     * represent the delay duration in minutes.
     * </p>
     *
     * @return the departure delay in minutes
     */
    public int getDepartureDelay() {
        return departureDelay;
    }

    /**
     * Sets the departure delay in minutes for this flight.
     * <p>
     * Updates the departure delay information for this flight. This method is
     * typically used when flight delays are reported or updated by air traffic
     * control or airline operations.
     * </p>
     *
     * @param departureDelay the new departure delay in minutes
     */
    public void setDepartureDelay(int departureDelay) {
        this.departureDelay = departureDelay;
    }


}