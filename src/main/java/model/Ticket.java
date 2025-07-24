package model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a flight ticket in the airport management system.
 * <p>
 * This class encapsulates all information related to an individual passenger's
 * ticket for a specific flight. A ticket serves as the central entity that
 * connects passengers to their flights and bookings, managing seat assignments,
 * check-in status, and associated luggage.
 * </p>
 * <p>
 * The ticket maintains essential travel information including the unique ticket
 * number, seat assignment, check-in status, and references to the associated
 * flight, booking, and passenger. Each ticket represents one passenger
 * that will travel on a specific flight.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 *   <li>Unique ticket number identification</li>
 *   <li>Seat assignment and management</li>
 *   <li>Check-in status tracking</li>
 *   <li>Flight, booking, and passenger associations</li>
 *   <li>Luggage management for the ticket holder</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see Booking
 * @see Passenger
 * @see Luggage
 * @see controller.TicketController
 */
public class Ticket {

    /**
     * The unique identifier for this ticket.
     * <p>
     * This field stores the ticket number as a string value, providing
     * unique identification for each ticket in the system. The ticket
     * number is immutable once set and is used for all ticket operations.
     * This field is final as the ticket number cannot be changed after creation.
     * </p>
     */
    private final String ticketNumber;
    
    /**
     * The seat number assigned to this ticket.
     * <p>
     * This field represents the specific seat assignment for the passenger.
     * It can be null if no seat has been assigned yet, or -1 to indicate
     * no seat assignment.
     * </p>
     */
    private Integer seat = null;
    
    /**
     * The check-in status of this ticket.
     * <p>
     * This field indicates whether the passenger has completed the check-in
     * process for this flight. It defaults to false (not checked in) and
     * is updated when the passenger completes check-in procedures.
     * </p>
     */
    private boolean checkedIn = false;
    
    /**
     * The flight associated with this ticket.
     * <p>
     * This field maintains the reference to the flight for which this ticket
     * is valid. It is final as the flight association cannot be changed
     * after ticket creation. This reference is essential for flight operations.
     * </p>
     *
     * @see Flight
     */
    private final Flight flight;
    
    /**
     * The booking associated with this ticket.
     * <p>
     * This field maintains the reference to the booking that contains this ticket.
     * It is final as the booking association cannot be changed after ticket
     * creation.
     * </p>
     *
     * @see Booking
     */
    private final Booking booking;
    
    /**
     * The passenger associated with this ticket.
     * <p>
     * This field maintains the reference to the passenger who will travel
     * using this ticket. It is final as the passenger association cannot
     * be changed after ticket creation.
     * </p>
     *
     * @see Passenger
     */
    private final Passenger passenger;
    
    /**
     * The list of luggage associated with this ticket.
     * <p>
     * This field stores all luggage items that belong to the passenger
     * holding this ticket. It defaults to an empty list.
     * </p>
     *
     * @see Luggage
     */
    private ArrayList<Luggage> luggages = new ArrayList<>(0);

    /**
     * Constructs a new ticket with basic flight, booking, and passenger information.
     * <p>
     * Creates a ticket with essential associations to flight, booking, and passenger.
     * The seat assignment defaults to null and check-in status defaults to false.
     * </p>
     *
     * @param parTicketNumber the unique ticket number
     * @param parFlight the flight for this ticket
     * @param parBooking the booking containing this ticket
     * @param parPassenger the passenger for this ticket
     * @throws InvalidTicket if the ticket number is null
     * @throws InvalidPassengerNumber if the passenger is null
     * @throws InvalidBooking if the booking is null
     * @throws InvalidFlight if the flight is null
     */
    public Ticket(String parTicketNumber, Flight parFlight, Booking parBooking, Passenger parPassenger) throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight{

        if(parFlight != null){

            if(parBooking != null){

                if(parPassenger != null){

                    if(parTicketNumber != null){
                        this.ticketNumber = parTicketNumber;
                        this.flight = parFlight;
                        this.booking = parBooking;
                        this.passenger = parPassenger;
                    }else{
                        throw new InvalidTicket("Un biglietto deve avere un ticket number!");
                    }

                }else{
                    throw new InvalidPassengerNumber("Un biglietto deve essere associato ad un passeggero!");
                }

            }else{
                throw new InvalidBooking("Un biglietto deve essere associato ad una prenotazione!");
            }

        }else{
            throw new InvalidFlight("Un biglietto deve essere riferito ad un volo!");
        }

    }

    /**
     * Constructs a new ticket with seat assignment.
     * <p>
     * Creates a ticket with a specific seat assignment in addition to the
     * basic flight, booking, and passenger associations. This constructor
     * is used when seat selection is made during ticket creation.
     * </p>
     *
     * @param parTicketNumber the unique ticket number
     * @param parSeat the assigned seat number
     * @param parFlight the flight for this ticket
     * @param parBooking the booking containing this ticket
     * @param parPassenger the passenger for this ticket
     * @throws InvalidTicket if the ticket number is null
     * @throws InvalidPassengerNumber if the passenger is null
     * @throws InvalidBooking if the booking is null
     * @throws InvalidFlight if the flight is null
     */
    public Ticket(String parTicketNumber, int parSeat, Flight parFlight, Booking parBooking, Passenger parPassenger) throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight{

        if(parFlight != null){

            if(parBooking != null){

                if(parPassenger != null){

                    if(parTicketNumber != null){
                        this.ticketNumber = parTicketNumber;
                        this.seat = parSeat;
                        this.flight = parFlight;
                        this.booking = parBooking;
                        this.passenger = parPassenger;
                    }else{
                        throw new InvalidTicket("Un biglietto deve avere un ticket number!");
                    }

                }else{
                    throw new InvalidPassengerNumber("Un biglietto deve essere associato ad un passeggero!");
                }

            }else{
                throw new InvalidBooking("Un biglietto deve essere associato ad una prenotazione!");
            }

        }else{
            throw new InvalidFlight("Un biglietto deve essere riferito ad un volo!");
        }

    }

    /**
     * Constructs a new ticket with seat assignment and check-in status.
     * <p>
     * Creates a ticket with seat assignment and check-in status information.
     * This constructor is used for tickets where check-in has already been
     * processed or for importing existing ticket data.
     * </p>
     *
     * @param parTicketNumber the unique ticket number
     * @param parSeat the assigned seat number
     * @param parCheckedIn the check-in status
     * @param parFlight the flight for this ticket
     * @param parBooking the booking containing this ticket
     * @param parPassenger the passenger for this ticket
     * @throws InvalidTicket if the ticket number is null
     * @throws InvalidPassengerNumber if the passenger is null
     * @throws InvalidBooking if the booking is null
     * @throws InvalidFlight if the flight is null
     */
    public Ticket(String parTicketNumber, int parSeat, boolean parCheckedIn, Flight parFlight, Booking parBooking, Passenger parPassenger) throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight{

        if(parFlight != null){

            if(parBooking != null){

                if(parPassenger != null){

                    if(parTicketNumber != null){
                        this.ticketNumber = parTicketNumber;
                        this.seat = parSeat;
                        this.checkedIn = parCheckedIn;
                        this.flight = parFlight;
                        this.booking = parBooking;
                        this.passenger = parPassenger;
                    }else{
                        throw new InvalidTicket("Un biglietto deve avere un ticket number!");
                    }

                }else{
                    throw new InvalidPassengerNumber("Un biglietto deve essere associato ad un passeggero!");
                }

            }else{
                throw new InvalidBooking("Un biglietto deve essere associato ad una prenotazione!");
            }

        }else{
            throw new InvalidFlight("Un biglietto deve essere riferito ad un volo!");
        }

    }

    /**
     * Constructs a new ticket with luggage information.
     * <p>
     * Creates a comprehensive ticket with seat assignment, check-in status,
     * and associated luggage. This constructor establishes the ticket-luggage
     * relationships and is used for complete ticket creation with luggage.
     * </p>
     *
     * @param parTicketNumber the unique ticket number
     * @param parSeat the assigned seat number
     * @param parCheckedIn the check-in status
     * @param parFlight the flight for this ticket
     * @param parBooking the booking containing this ticket
     * @param parPassenger the passenger for this ticket
     * @param parLuggages the list of luggage associated with this ticket
     * @throws InvalidTicket if the ticket number is null
     * @throws InvalidPassengerNumber if the passenger is null
     * @throws InvalidBooking if the booking is null
     * @throws InvalidFlight if the flight is null
     */
    public Ticket(String parTicketNumber, int parSeat, boolean parCheckedIn, Flight parFlight, Booking parBooking, Passenger parPassenger, List<Luggage> parLuggages) throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight{

        if(parFlight != null){

            if(parBooking != null){

                if(parPassenger != null){

                    if(parTicketNumber != null){
                        this.ticketNumber = parTicketNumber;
                        this.seat = parSeat;
                        this.checkedIn = parCheckedIn;
                        this.flight = parFlight;
                        this.booking = parBooking;
                        this.passenger = parPassenger;

                        for(Luggage luggage: parLuggages){
                            luggage.setTicket(this);
                        }

                        this.luggages = (ArrayList<Luggage>) parLuggages;

                    }else{
                        throw new InvalidTicket("Un biglietto deve avere un ticket number!");
                    }

                }else{
                    throw new InvalidPassengerNumber("Un biglietto deve essere associato ad un passeggero!");
                }

            }else{
                throw new InvalidBooking("Un biglietto deve essere associato ad una prenotazione!");
            }

        }else{
            throw new InvalidFlight("Un biglietto deve essere riferito ad un volo!");
        }

    }

    /**
     * Constructs a new ticket with complete flight and booking data.
     * <p>
     * Creates a ticket by constructing the associated flight and booking objects
     * from individual parameters. This constructor is typically used when
     * creating tickets from database records.
     * </p>
     *
     * @param parTicketNumber the unique ticket number
     * @param parSeat the assigned seat number (can be null)
     * @param parCheckedIn the check-in status
     * @param parId the flight ID
     * @param parCompanyName the airline company name
     * @param parDate the flight date
     * @param parDepartureTime the flight departure time
     * @param parArrivalTime the flight arrival time
     * @param parFlightStatus the flight status
     * @param parMaxSeats the maximum seats on the flight
     * @param parFreeSeats the available seats on the flight
     * @param parOrigin the flight origin (for arriving flights)
     * @param parArrivalDelay the arrival delay in minutes
     * @param parBookingStatus the booking status
     * @param parBuyer the customer who made the booking
     * @param parBookingDate the booking creation date
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number
     * @param parBirthDate the passenger's birth date
     * @throws InvalidTicket if the ticket number is null
     * @throws InvalidBuyer if the buyer is invalid
     * @throws InvalidFlight if flight creation fails
     * @throws InvalidPassengerNumber if passenger creation fails
     */
    public Ticket(String parTicketNumber, Integer parSeat, boolean parCheckedIn, String parId, String parCompanyName, Date parDate,
                  Time parDepartureTime, Time parArrivalTime, FlightStatus parFlightStatus, int parMaxSeats, int parFreeSeats,
                  String parOrigin, int parArrivalDelay,
                  BookingStatus parBookingStatus, Customer parBuyer, Date parBookingDate,
                  String parFirstName, String parLastName, String parSSN, Date parBirthDate) throws InvalidTicket, InvalidBuyer, InvalidFlight, InvalidPassengerNumber{

        if(parTicketNumber != null){
            this.ticketNumber = parTicketNumber;

            if(parSeat == null){
                this.seat = -1;
            }else{
                this.seat = parSeat;
            }

            this.checkedIn = parCheckedIn;

            this.flight = new Arriving(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parFlightStatus, parMaxSeats, parFreeSeats,
                    parOrigin, parArrivalDelay);
            this.booking = new Booking(parBookingStatus, parBuyer, this.flight, parBookingDate, this);
            this.passenger = new Passenger(parFirstName, parLastName, parSSN, parBirthDate, this);
        }else{
            throw new InvalidTicket("Un biglietto deve avere un ticket number!");
        }

    }

    /**
     * Constructs a new ticket with passenger details and existing flight/booking.
     * <p>
     * Creates a ticket with an existing flight and booking objects while creating
     * a new passenger from individual parameters. This constructor is useful
     * when flight and booking objects already exist, but passenger information
     * needs to be created from separate data fields.
     * </p>
     *
     * @param parTicketNumber the unique ticket number
     * @param parSeat the assigned seat number (can be null)
     * @param parCheckedIn the check-in status
     * @param parFlight the existing flight object
     * @param parBooking the existing booking object
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number
     * @param parBirthDate the passenger's birth date
     * @throws InvalidTicket if the ticket number is null
     * @throws InvalidFlight if the flight is null
     * @throws InvalidBooking if the booking is null
     * @throws InvalidPassengerNumber if passenger creation fails
     */
    public  Ticket(String parTicketNumber, Integer parSeat, boolean parCheckedIn, Flight parFlight, Booking parBooking,
                  String parFirstName, String parLastName, String parSSN, Date parBirthDate) throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {

        if(parTicketNumber != null){

            this.ticketNumber = parTicketNumber;
            if(parSeat == null){
                this.seat = -1;
            }else{
                this.seat = parSeat;
            }
            this.checkedIn = parCheckedIn;

            if(parFlight != null){
                this.flight = parFlight;
            }else{
                throw new InvalidFlight("Un biglietto deve essere riferito ad un volo!");
            }

            if(parBooking != null){
                this.booking = parBooking;
            }else{
                throw new InvalidBooking("Un biglietto deve essere associato ad una prenotazione!");
            }

            this.passenger = new Passenger(parFirstName, parLastName, parSSN, parBirthDate, this);

        }else{
            throw new InvalidTicket("Un biglietto deve avere un ticket number!");
        }

    }

    /**
     * Gets the unique ticket number.
     * <p>
     * Returns the ticket number that uniquely identifies this ticket
     * within the system. This number is used for all ticket operations
     * and passenger services.
     * </p>
     *
     * @return the ticket number, never null
     */
    public String getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Gets the seat number assigned to this ticket.
     * <p>
     * Returns the seat assignment for this ticket. The value can be null
     * if no seat has been assigned, or -1 to indicate no seat assignment.
     * </p>
     *
     * @return the seat number, can be null or -1 for no assignment
     */
    public Integer getSeat() {
        return seat;
    }

    /**
     * Sets the seat number for this ticket.
     * <p>
     * Updates the seat assignment for this ticket. This method is used
     * for seat selection and reassignment operations.
     * </p>
     *
     * @param seat the new seat number, can be null for no assignment
     */
    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    /**
     * Gets the check-in status of this ticket.
     * <p>
     * Returns whether the passenger has completed check-in procedures
     * for this ticket..
     * </p>
     *
     * @return true if checked in, false otherwise
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Gets the flight associated with this ticket.
     * <p>
     * Returns the flight object that this ticket is valid for.
     * This reference is immutable and provides access to all
     * flight-related information and operations.
     * </p>
     *
     * @return the associated flight, never null
     * @see Flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Gets the booking associated with this ticket.
     * <p>
     * Returns the booking object that contains this ticket.
     * This reference is immutable and provides access to
     * booking-related information and customer details.
     * </p>
     *
     * @return the associated booking, never null
     * @see Booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Gets the passenger associated with this ticket.
     * <p>
     * Returns the passenger object representing the person
     * who will travel using this ticket. This reference is
     * immutable and provides access to passenger information.
     * </p>
     *
     * @return the associated passenger, never null
     * @see Passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Gets the list of luggage associated with this ticket.
     * <p>
     * Returns all luggage items that belong to the passenger
     * holding this ticket. The list may be empty if no luggage
     * has been registered for this ticket.
     * </p>
     *
     * @return the list of luggage, never null but may be empty
     * @see Luggage
     */
    public List<Luggage> getLuggages() {
        return luggages;
    }

    /**
     * Sets the list of luggage for this ticket.
     * <p>
     * Updates the complete list of luggage associated with this ticket.
     * This method establishes the ticket-luggage relationships and is
     * used for luggage management operations. Each luggage item will
     * have its ticket reference updated to point to this ticket.
     * </p>
     *
     * @param luggages the new list of luggage items
     * @see Luggage
     */
    public void setLuggages(List<Luggage> luggages) {

        for(Luggage luggage: luggages){
            luggage.setTicket(this);
        }

        this.luggages = (ArrayList<Luggage>) luggages;
    }

}