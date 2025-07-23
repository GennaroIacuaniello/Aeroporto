package model;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * The Flight class represents a flight in the airport management system.
 * 
 * <p>This class serves as the base class for all flight types and contains
 * common flight information such as company, schedule, capacity, and status.
 * It manages both bookings and tickets associated with the flight, and can
 * be assigned to a specific gate for departure or arrival operations.</p>
 * 
 * <p>The class supports different flight statuses through the {@link FlightStatus}
 * enumeration and provides comprehensive flight management capabilities including:</p>
 * <ul>
 *   <li>Flight identification and company information</li>
 *   <li>Schedule management (departure and arrival times)</li>
 *   <li>Capacity management (maximum and available seats)</li>
 *   <li>Booking and ticket management</li>
 *   <li>Gate assignment</li>
 *   <li>Flight status tracking</li>
 * </ul>
 * 
 * <p>This class is extended by {@link Departing} and {@link Arriving} classes
 * to provide specialized functionality for different flight types.</p>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Departing
 * @see Arriving
 * @see FlightStatus
 * @see Gate
 * @see Booking
 * @see Ticket
 */
public class Flight {

    /**
     * Unique identifier for the flight.
     * This field is immutable once set during construction.
     */
    private final String id;
    
    /**
     * Name of the airline company operating this flight.
     */
    private String companyName;
    
    /**
     * Date when the flight is scheduled to operate.
     */
    private Date date;
    
    /**
     * Scheduled departure time for the flight.
     */
    private Time departureTime;
    
    /**
     * Scheduled arrival time for the flight.
     */
    private Time arrivalTime;
    
    /**
     * Current status of the flight.
     * Defaults to {@link FlightStatus#PROGRAMMED} when created.
     */
    private FlightStatus status = FlightStatus.PROGRAMMED;
    
    /**
     * Maximum number of seats available on this flight.
     */
    private int maxSeats;
    
    /**
     * Current number of available (unbooked) seats.
     */
    private int freeSeats;
    
    /**
     * List of all bookings associated with this flight.
     * Initialized as empty list and populated as bookings are made.
     */
    private ArrayList<Booking> bookings = new ArrayList<>(0);
    
    /**
     * List of all tickets issued for this flight.
     * Tickets are typically created after successful payment of bookings.
     */
    private ArrayList<Ticket> tickets = new ArrayList<>(0);
    
    /**
     * Gate assigned to this flight, if any.
     * Null if no gate has been assigned yet.
     */
    private Gate gate = null;

    /**
     * Constructs a basic Flight with essential information.
     * 
     * <p>Creates a flight with the minimum required information for scheduling.
     * The flight status is set to {@link FlightStatus#PROGRAMMED} by default,
     * and free seats are initially set to match maximum seats.</p>
     *
     * @param parId unique identifier for the flight (must not be null or empty)
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time
     * @param parMaxSeats maximum number of seats on the aircraft
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, int parMaxSeats) {
        
        this.id = parId;
        this.companyName = parCompanyName;
        this.date = parDate;
        this.departureTime = parDepartureTime;
        this.arrivalTime = parArrivalTime;
        this.maxSeats = parMaxSeats;
        this.freeSeats = this.maxSeats;
    }

    /**
     * Constructs a basic Flight with essential information.
     * 
     * <p>Creates a flight with the minimum required information for scheduling.
     * The flight status is set to {@link FlightStatus#PROGRAMMED} by default,
     * and free seats are initially set to match maximum seats.</p>
     *
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parStatus        the par status
     * @param parMaxSeats      the par max seats
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
     * Constructs a Flight with additional status and capacity information.
     * 
     * <p>This constructor allows setting the initial flight status and
     * specifying the current number of free seats, which may be different
     * from the maximum seats if some seats are already reserved or blocked.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time
     * @param parStatus initial status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats) {
        
        
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
     * Constructs a Flight with gate assignment.
     * 
     * <p>This constructor includes gate assignment during flight creation,
     * which is useful when flights are created with pre-assigned gates.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time
     * @param parStatus initial status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parGate gate assigned to this flight
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats, Gate parGate) {
        
        
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
     * Constructs a Flight with existing bookings and tickets.
     * 
     * <p>This constructor is typically used when loading flight data from
     * the database, where bookings and tickets already exist for the flight.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parBookings existing bookings for this flight
     * @param parTickets existing tickets for this flight
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets) {
        
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
     * Constructs a complete Flight with all possible information.
     * 
     * <p>This is the most comprehensive constructor, including all flight
     * attributes. Typically used for database loading or complete flight
     * reconstruction.</p>
     *
     * @param parId unique identifier for the flight
     * @param parCompanyName name of the operating airline company
     * @param parDate date when the flight is scheduled
     * @param parDepartureTime scheduled departure time
     * @param parArrivalTime scheduled arrival time
     * @param parStatus current status of the flight
     * @param parMaxSeats maximum number of seats on the aircraft
     * @param parFreeSeats current number of available seats
     * @param parBookings existing bookings for this flight
     * @param parTickets existing tickets for this flight
     * @param parGate gate assigned to this flight
     */
    public Flight(String parId, String parCompanyName, Date parDate, Time parDepartureTime,
                  Time parArrivalTime, FlightStatus parStatus, int parMaxSeats, int parFreeSeats,
                  List<Booking> parBookings, List<Ticket> parTickets, Gate parGate) {
        
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
     * Gets the unique flight identifier.
     *
     * @return the flight ID as a String
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the airline company name.
     *
     * @return the name of the operating airline company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the airline company name.
     *
     * @param companyName the new company name to set
     * @throws IllegalArgumentException if companyName is null or empty
     */
    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome della compagnia aerea non può essere la stringa vuota o essere null!");
        }
        this.companyName = companyName;
    }

    /**
     * Gets the flight date.
     *
     * @return the scheduled date of the flight
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the flight date.
     *
     * @param date the new flight date
     * @throws IllegalArgumentException if date is null or in the past
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the scheduled departure time.
     *
     * @return the departure time
     */
    public Time getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the scheduled departure time.
     *
     * @param departureTime the new departure time
     * @throws IllegalArgumentException if departureTime is null
     */
    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the scheduled arrival time.
     *
     * @return the arrival time
     */
    public Time getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the scheduled arrival time.
     *
     * @param arrivalTime the new arrival time
     * @throws IllegalArgumentException if arrivalTime is null or before departure time
     */
    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the current flight status.
     *
     * @return the current status of the flight
     * @see FlightStatus
     */
    public FlightStatus getStatus() {
        return status;
    }

    /**
     * Sets the flight status.
     * 
     * <p>Updates the flight status to reflect current operational state.
     * Status changes may trigger notifications or other system actions.</p>
     *
     * @param status the new flight status
     * @throws IllegalArgumentException if status is null
     * @see FlightStatus
     */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    /**
     * Gets the maximum seat capacity.
     *
     * @return the maximum number of seats on this flight
     */
    public int getMaxSeats() {
        return maxSeats;
    }

    /**
     * Sets the maximum seat capacity.
     *
     * @param maxSeats the new maximum seat count
     * @throws IllegalArgumentException if maxSeats is negative or less than current bookings
     */
    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    /**
     * Gets the number of available seats.
     *
     * @return the current number of unbooked seats
     */
    public int getFreeSeats() {
        return freeSeats;
    }

    /**
     * Sets the number of available seats.
     * 
     * <p>This method should be used carefully to maintain data consistency.
     * Typically, free seats are calculated automatically based on bookings.</p>
     *
     * @param freeSeats the new number of available seats
     * @throws IllegalArgumentException if freeSeats is negative or exceeds maxSeats
     */
    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    /**
     * Gets all bookings for this flight.
     *
     * @return an unmodifiable view of the flight's bookings
     * @see Booking
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the flight's booking list.
     * 
     * <p>Replaces the current booking list. This method should be used
     * carefully to maintain booking consistency and seat availability.</p>
     *
     * @param bookings the new list of bookings
     * @throws IllegalArgumentException if bookings is null
     * @see Booking
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = (ArrayList<Booking>) bookings;
    }

    /**
     * Gets all tickets issued for this flight.
     *
     * @return an unmodifiable view of the flight's tickets
     * @see Ticket
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Sets the flight's ticket list.
     *
     * @param tickets the new list of tickets
     * @throws IllegalArgumentException if tickets is null
     * @see Ticket
     */
    public void setTickets(List<Ticket> tickets) {
        this.tickets = (ArrayList<Ticket>) tickets;
    }

    /**
     * Gets the assigned gate.
     *
     * @return the gate assigned to this flight, or null if no gate assigned
     * @see Gate
     */
    public Gate getGate() {
        return gate;
    }

    /**
     * Sets the assigned gate.
     * 
     * <p>Assigns a gate to this flight for departure or arrival operations.
     * Gate assignment is typically done closer to the flight's scheduled time.</p>
     *
     * @param gate the gate to assign to this flight, or null to unassign
     * @see Gate
     */
    public void setGate(Gate gate) {
        this.gate = gate;
    }

    /**
     * Gets the month name for the flight date.
     * 
     * <p>Returns the localized month name for display purposes.
     * This is a convenience method for UI components that need to
     * display month information.</p>
     *
     * @return the month name as a String, or empty string if date is null
     */
    public String getMonthName() {
        String[] monthNames = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

        return monthNames[this.getDate().getMonth()];
    }
}