package model;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Represents a flight in the airport management system.
 * <p>
 * This class serves as the base class for different types of flights in the system,
 * including {@link Arriving} and {@link Departing} flights. It encapsulates all the
 * essential flight information such as flight identification, company details, scheduling,
 * seating capacity, and associated bookings and tickets.
 * </p>
 * <p>
 * The flight ID is stored as a final field to ensure immutability after object creation,
 * while other flight details can be modified through their respective setter methods.
 * The class maintains collections of bookings and tickets associated with the flight,
 * providing comprehensive flight management capabilities.
 * </p>
 * <p>
 * Flight status is managed through the {@link FlightStatus} enumeration, with the default
 * status being {@code PROGRAMMED}. The class also supports gate assignment for better
 * airport operations management.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see FlightStatus
 * @see Arriving
 * @see Departing
 * @see Booking
 * @see Ticket
 * @see Gate
 */
public class Flight {

    /**
     * The unique identifier for this flight.
     * <p>
     * This field represents the flight number or code that uniquely identifies
     * the flight within the airport management system. It is declared as final
     * to ensure immutability after object creation.
     * </p>
     */
    private final String id;
    
    /**
     * The name of the airline company operating this flight.
     * <p>
     * This field stores the airline company name and can be modified after
     * flight creation through the {@link #setCompanyName(String)} method.
     * </p>
     */
    private String companyName;
    
    /**
     * The date when this flight is scheduled to operate.
     * <p>
     * This field represents the flight date and can be modified through
     * the {@link #setDate(Date)} method for rescheduling purposes.
     * </p>
     */
    private Date date;
    
    /**
     * The scheduled departure time for this flight.
     * <p>
     * This field stores the time when the flight is scheduled to depart
     * and can be updated through the {@link #setDepartureTime(Time)} method.
     * </p>
     */
    private Time departureTime;
    
    /**
     * The scheduled arrival time for this flight.
     * <p>
     * This field stores the time when the flight is scheduled to arrive
     * at its destination and can be updated through the {@link #setArrivalTime(Time)} method.
     * </p>
     */
    private Time arrivalTime;
    
    /**
     * The current status of this flight.
     * <p>
     * This field represents the operational status of the flight using the
     * {@link FlightStatus} enumeration. The default status is {@code PROGRAMMED}.
     * The status can be updated through the {@link #setStatus(FlightStatus)} method.
     * </p>
     *
     * @see FlightStatus
     */
    private FlightStatus status = FlightStatus.PROGRAMMED;
    
    /**
     * The maximum number of seats available on this flight.
     * <p>
     * This field represents the total seating capacity of the aircraft
     * assigned to this flight. It can be modified through the
     * {@link #setMaxSeats(int)} method.
     * </p>
     */
    private int maxSeats;
    
    /**
     * The number of available (unbooked) seats on this flight.
     * <p>
     * This field tracks the current number of seats that are still available
     * for booking. It decreases as bookings are made and can be updated
     * through the {@link #setFreeSeats(int)} method.
     * </p>
     */
    private int freeSeats;
    
    /**
     * The list of bookings associated with this flight.
     * <p>
     * This field stores all booking records for passengers who have reserved
     * seats on this flight. The collection is implemented as an {@link ArrayList}
     * for efficient access and modification operations.
     * </p>
     *
     * @see Booking
     */
    private ArrayList<Booking> bookings = new ArrayList<>(0);
    
    /**
     * The list of tickets issued for this flight.
     * <p>
     * This field stores all ticket records for passengers traveling on this flight.
     * Each ticket corresponds to a specific passenger and seat assignment.
     * The collection is implemented as an {@link ArrayList} for efficient operations.
     * </p>
     *
     * @see Ticket
     */
    private ArrayList<Ticket> tickets = new ArrayList<>(0);
    
    /**
     * The gate assigned to this flight.
     * <p>
     * This field represents the airport gate where passengers board or disembark
     * from this flight. It can be null if no gate has been assigned yet.
     * The gate can be set through the {@link #setGate(Gate)} method.
     * </p>
     *
     * @see Gate
     */
    private Gate gate = null;

    /**
     * Constructs a new Flight with basic flight information.
     * <p>
     * Creates a flight with essential details including ID, company name, date,
     * departure and arrival times, and maximum seats. The free seats are
     * automatically set to equal the maximum seats, and the status defaults
     * to {@code PROGRAMMED}.
     * </p>
     *
     * @param parId the unique identifier for the flight. Must not be null or empty.
     * @param parCompanyName the name of the airline company. Must not be null or empty.
     * @param parDate the date when the flight is scheduled. Must not be null.
     * @param parDepartureTime the scheduled departure time. Must not be null.
     * @param parArrivalTime the scheduled arrival time. Must not be null.
     * @param parMaxSeats the maximum number of seats on the flight. Must be positive.
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.maxSeats = parMaxSeats;
        this.freeSeats = this.maxSeats;

    }

    /**
     * Constructs a new Flight with basic information and specified status.
     * <p>
     * Creates a flight with essential details and a specific flight status.
     * The free seats are automatically set to equal the maximum seats.
     * </p>
     *
     * @param parId the unique identifier for the flight. Must not be null or empty.
     * @param parCompanyName the name of the airline company. Must not be null or empty.
     * @param parDate the date when the flight is scheduled. Must not be null.
     * @param parDepartureTime the scheduled departure time. Must not be null.
     * @param parArrivalTime the scheduled arrival time. Must not be null.
     * @param parStatus the initial status of the flight. Must not be null.
     * @param parMaxSeats the maximum number of seats on the flight. Must be positive.
     * @see FlightStatus
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = this.maxSeats;

    }

    /**
     * Constructs a new Flight with detailed seating information.
     * <p>
     * Creates a flight with specific maximum and free seat counts, allowing
     * for flights that may already have some bookings or reservations.
     * </p>
     *
     * @param parId the unique identifier for the flight. Must not be null or empty.
     * @param parCompanyName the name of the airline company. Must not be null or empty.
     * @param parDate the date when the flight is scheduled. Must not be null.
     * @param parDepartureTime the scheduled departure time. Must not be null.
     * @param parArrivalTime the scheduled arrival time. Must not be null.
     * @param parStatus the initial status of the flight. Must not be null.
     * @param parMaxSeats the maximum number of seats on the flight. Must be positive.
     * @param parFreeSeats the number of available seats. Must not be negative or exceed maxSeats.
     * @see FlightStatus
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;

    }

    /**
     * Constructs a new Flight with gate assignment.
     * <p>
     * Creates a flight with detailed information including gate assignment
     * for better airport operations management.
     * </p>
     *
     * @param parId the unique identifier for the flight. Must not be null or empty.
     * @param parCompanyName the name of the airline company. Must not be null or empty.
     * @param parDate the date when the flight is scheduled. Must not be null.
     * @param parDepartureTime the scheduled departure time. Must not be null.
     * @param parArrivalTime the scheduled arrival time. Must not be null.
     * @param parStatus the initial status of the flight. Must not be null.
     * @param parMaxSeats the maximum number of seats on the flight. Must be positive.
     * @param parFreeSeats the number of available seats. Must not be negative or exceed maxSeats.
     * @param parGate the gate assigned to this flight. Can be null if not assigned.
     * @see FlightStatus
     * @see Gate
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, Gate parGate ){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;
        this.gate = parGate;

    }

    /**
     * Constructs a new Flight with existing bookings and tickets.
     * <p>
     * Creates a flight with complete information including existing bookings
     * and tickets. This constructor is typically used when loading flight
     * data from persistent storage.
     * </p>
     *
     * @param parId the unique identifier for the flight. Must not be null or empty.
     * @param parCompanyName the name of the airline company. Must not be null or empty.
     * @param parDate the date when the flight is scheduled. Must not be null.
     * @param parDepartureTime the scheduled departure time. Must not be null.
     * @param parArrivalTime the scheduled arrival time. Must not be null.
     * @param parStatus the initial status of the flight. Must not be null.
     * @param parMaxSeats the maximum number of seats on the flight. Must be positive.
     * @param parFreeSeats the number of available seats. Must not be negative or exceed maxSeats.
     * @param parBookings the list of existing bookings. Must not be null but can be empty.
     * @param parTickets the list of existing tickets. Must not be null but can be empty.
     * @see FlightStatus
     * @see Booking
     * @see Ticket
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets ){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;
        this.bookings = (ArrayList<Booking>) parBookings;
        this.tickets = (ArrayList<Ticket>) parTickets;

    }

    /**
     * Constructs a new Flight with complete information including gate assignment.
     * <p>
     * Creates a flight with all available details including existing bookings,
     * tickets, and gate assignment. This is the most comprehensive constructor
     * for creating fully populated flight objects.
     * </p>
     *
     * @param parId the unique identifier for the flight. Must not be null or empty.
     * @param parCompanyName the name of the airline company. Must not be null or empty.
     * @param parDate the date when the flight is scheduled. Must not be null.
     * @param parDepartureTime the scheduled departure time. Must not be null.
     * @param parArrivalTime the scheduled arrival time. Must not be null.
     * @param parStatus the initial status of the flight. Must not be null.
     * @param parMaxSeats the maximum number of seats on the flight. Must be positive.
     * @param parFreeSeats the number of available seats. Must not be negative or exceed maxSeats.
     * @param parBookings the list of existing bookings. Must not be null but can be empty.
     * @param parTickets the list of existing tickets. Must not be null but can be empty.
     * @param parGate the gate assigned to this flight. Can be null if not assigned.
     * @see FlightStatus
     * @see Booking
     * @see Ticket
     * @see Gate
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, Gate parGate ){

        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.status = parStatus;
        this.maxSeats = parMaxSeats;
        this.freeSeats = parFreeSeats;
        this.bookings = (ArrayList<Booking>) parBookings;
        this.tickets = (ArrayList<Ticket>) parTickets;
        this.gate = parGate;

    }

    /**
     * Gets the unique identifier of this flight.
     * <p>
     * Returns the flight ID that uniquely identifies this flight within
     * the airport management system.
     * </p>
     *
     * @return the flight ID, never null
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the airline company name operating this flight.
     * <p>
     * Returns the name of the airline company responsible for operating
     * this flight.
     * </p>
     *
     * @return the company name, never null
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the airline company name for this flight.
     * <p>
     * Updates the airline company name. This method allows for company
     * changes due to operational requirements or airline partnerships.
     * </p>
     *
     * @param companyName the new company name. Should not be null or empty.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the scheduled date of this flight.
     * <p>
     * Returns the date when this flight is scheduled to operate.
     * </p>
     *
     * @return the flight date, never null
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the scheduled date for this flight.
     * <p>
     * Updates the flight date. This method is useful for rescheduling
     * flights due to operational requirements or weather conditions.
     * </p>
     *
     * @param date the new flight date. Must not be null.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the scheduled departure time of this flight.
     * <p>
     * Returns the time when this flight is scheduled to depart.
     * </p>
     *
     * @return the departure time, never null
     */
    public Time getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the scheduled departure time for this flight.
     * <p>
     * Updates the departure time. This method allows for schedule
     * adjustments due to operational requirements or delays.
     * </p>
     *
     * @param departureTime the new departure time. Must not be null.
     */
    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the scheduled arrival time of this flight.
     * <p>
     * Returns the time when this flight is scheduled to arrive at
     * its destination.
     * </p>
     *
     * @return the arrival time, never null
     */
    public Time getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the scheduled arrival time for this flight.
     * <p>
     * Updates the arrival time. This method allows for schedule
     * adjustments due to operational requirements or delays.
     * </p>
     *
     * @param arrivalTime the new arrival time. Must not be null.
     */
    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the current status of this flight.
     * <p>
     * Returns the operational status of the flight, such as PROGRAMMED,
     * DELAYED, DEPARTED, etc.
     * </p>
     *
     * @return the flight status, never null
     * @see FlightStatus
     */
    public FlightStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of this flight.
     * <p>
     * Updates the flight status to reflect operational changes
     * such as delays, cancellations, or departures.
     * </p>
     *
     * @param status the new flight status. Must not be null.
     * @see FlightStatus
     */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /**
     * Gets the maximum number of seats available on this flight.
     * <p>
     * Returns the total seating capacity of the aircraft assigned
     * to this flight.
     * </p>
     *
     * @return the maximum number of seats, always positive
     */
    public int getMaxSeats() {
        return maxSeats;
    }

    /**
     * Sets the maximum number of seats for this flight.
     * <p>
     * Updates the maximum seating capacity. This method may be used
     * when aircraft are substituted or seat configurations change.
     * </p>
     *
     * @param maxSeats the new maximum number of seats. Must be positive.
     */
    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    /**
     * Gets the number of available (unbooked) seats on this flight.
     * <p>
     * Returns the current number of seats that are still available
     * for booking on this flight.
     * </p>
     *
     * @return the number of free seats, never negative
     */
    public int getFreeSeats() {
        return freeSeats;
    }

    /**
     * Sets the number of available seats for this flight.
     * <p>
     * Updates the count of available seats. This method is typically
     * used when bookings are made or cancelled, or when seat
     * availability changes.
     * </p>
     *
     * @param freeSeats the new number of free seats. Must not be negative or exceed maxSeats.
     */
    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    /**
     * Gets the list of bookings associated with this flight.
     * <p>
     * Returns all booking records for passengers who have reserved
     * seats on this flight. The returned list provides access to
     * booking information and passenger details.
     * </p>
     *
     * @return the list of bookings, never null but may be empty
     * @see Booking
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings for this flight.
     * <p>
     * Updates the complete list of bookings associated with this flight.
     * This method is useful for bulk updates or when loading flight
     * data from storage.
     * </p>
     *
     * @param bookings the new list of bookings. Must not be null but can be empty.
     * @see Booking
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = (ArrayList<Booking>) bookings;
    }

    /**
     * Gets the list of tickets issued for this flight.
     * <p>
     * Returns all ticket records for passengers traveling on this flight.
     * Each ticket corresponds to a specific passenger and seat assignment.
     * </p>
     *
     * @return the list of tickets, never null but may be empty
     * @see Ticket
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Sets the list of tickets for this flight.
     * <p>
     * Updates the complete list of tickets associated with this flight.
     * This method is useful for bulk updates or when loading flight
     * data from storage.
     * </p>
     *
     * @param tickets the new list of tickets. Must not be null but can be empty.
     * @see Ticket
     */
    public void setTickets(List<Ticket> tickets) {
        this.tickets = (ArrayList<Ticket>) tickets;
    }

    /**
     * Gets the gate assigned to this flight.
     * <p>
     * Returns the airport gate where passengers board or disembark
     * from this flight. May be null if no gate has been assigned.
     * </p>
     *
     * @return the assigned gate, or null if no gate is assigned
     * @see Gate
     */
    public Gate getGate() {
        return gate;
    }

    /**
     * Sets the gate assignment for this flight.
     * <p>
     * Assigns an airport gate to this flight for passenger boarding
     * and disembarkation operations.
     * </p>
     *
     * @param gate the gate to assign to this flight. Can be null to remove gate assignment.
     * @see Gate
     */
    public void setGate(Gate gate) {
        this.gate = gate;
    }

    /**
     * Gets the localized month name for this flight's date.
     * <p>
     * Returns the Italian name of the month when this flight is scheduled.
     * This method is useful for display purposes in the Italian locale.
     * </p>
     * <p>
     * The returned month names are: Gennaio, Febbraio, Marzo, Aprile, Maggio,
     * Giugno, Luglio, Agosto, Settembre, Ottobre, Novembre, Dicembre.
     * </p>
     *
     * @return the Italian name of the month for this flight's date
     */
    public String getMonthName(){

        String[] monthNames = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

        return monthNames[this.getDate().getMonth()];
    }

}