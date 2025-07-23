package model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * The Ticket class represents an individual passenger's ticket for a flight.
 * 
 * <p>This class serves as the core entity linking passengers to flights through bookings.
 * Each ticket represents one passenger's reservation and contains all relevant information
 * for travel including seat assignment, check-in status, passenger details, and associated
 * luggage. Tickets are created as part of the booking process and serve as the travel
 * document for passengers.</p>
 * 
 * <p>Key features of the ticket system:</p>
 * <ul>
 *   <li>Unique ticket number for identification and tracking</li>
 *   <li>Seat assignment management (can be assigned or unassigned)</li>
 *   <li>Check-in status tracking for departure procedures</li>
 *   <li>Association with flight, booking, and passenger entities</li>
 *   <li>Luggage management for the passenger</li>
 *   <li>Seat display formatting for user interfaces</li>
 * </ul>
 * 
 * <p>The ticket serves as a bridge between different system entities:</p>
 * <ul>
 *   <li>Links to {@link Flight} for travel details</li>
 *   <li>Links to {@link Booking} for reservation context</li>
 *   <li>Links to {@link Passenger} for traveler information</li>
 *   <li>Contains {@link Luggage} list for baggage management</li>
 * </ul>
 * 
 * <p>Seat numbering follows airline industry standards with a combination
 * of row numbers and letter designations (A-F for typical 6-seat-per-row
 * configurations).</p>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see Booking
 * @see Passenger
 * @see Luggage
 */
public class Ticket {

    /**
     * Unique identifier for this ticket.
     * This field is immutable once set during ticket creation and serves
     * as the primary identifier for ticket operations and tracking.
     */
    private final String ticketNumber;
    
    /**
     * Seat assignment for this ticket.
     * Can be null or -1 if no seat is assigned yet. Seat numbers follow
     * airline industry standards with 0-based indexing converted to
     * standard row/letter format for display.
     */
    private Integer seat = null;
    
    /**
     * Check-in status for this ticket.
     * Indicates whether the passenger has completed check-in procedures.
     * Defaults to false (not checked in) when ticket is created.
     */
    private boolean checkedIn = false;
    
    /**
     * The flight associated with this ticket.
     * This field is immutable once set during ticket creation to maintain
     * data integrity and prevent accidental modifications.
     */
    private final Flight flight;
    
    /**
     * The booking associated with this ticket.
     * This field is immutable once set during ticket creation and provides
     * the reservation context for this ticket.
     */
    private final Booking booking;
    
    /**
     * The passenger associated with this ticket.
     * This field is immutable once set during ticket creation and contains
     * all personal information for the traveler.
     */
    private final Passenger passenger;
    
    /**
     * List of luggage items associated with this ticket.
     * Contains all baggage registered for this passenger's travel.
     * Initialized as empty list and populated as luggage is added.
     */
    private ArrayList<Luggage> luggages = new ArrayList<>(0);

    /**
     * Constructs a basic Ticket with essential information.
     * 
     * <p>Creates a ticket with the minimum required information. The seat
     * is left unassigned and check-in status is set to false. This constructor
     * is useful for initial ticket creation where seat assignment and check-in
     * will be handled later in the process.</p>
     *
     * @param parTicketNumber unique identifier for the ticket (must not be null)
     * @param parFlight the flight for this ticket (must not be null)
     * @param parBooking the booking associated with this ticket (must not be null)
     * @param parPassenger the passenger for this ticket (must not be null)
     * @throws InvalidTicket if parTicketNumber is null
     * @throws InvalidPassengerNumber if parPassenger is null
     * @throws InvalidBooking if parBooking is null
     * @throws InvalidFlight if parFlight is null
     */
    public Ticket(String parTicketNumber, Flight parFlight, Booking parBooking, Passenger parPassenger) 
            throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight {
        
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
     * Constructs a Ticket with seat assignment.
     * 
     * <p>Creates a ticket with a specific seat assignment. This constructor
     * is used when seat selection is done during the booking process.</p>
     *
     * @param parTicketNumber unique identifier for the ticket
     * @param parSeat the assigned seat number (0-based index)
     * @param parFlight the flight for this ticket
     * @param parBooking the booking associated with this ticket
     * @param parPassenger the passenger for this ticket
     * @throws InvalidTicket if parTicketNumber is null
     * @throws InvalidPassengerNumber if parPassenger is null
     * @throws InvalidBooking if parBooking is null
     * @throws InvalidFlight if parFlight is null
     */
    public Ticket(String parTicketNumber, int parSeat, Flight parFlight, Booking parBooking, Passenger parPassenger) 
            throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight {
        
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
     * Constructs a Ticket with seat and check-in status.
     * 
     * <p>Creates a ticket with seat assignment and check-in status. This
     * constructor is useful when loading existing tickets from the database
     * or when creating tickets with complete status information.</p>
     *
     * @param parTicketNumber unique identifier for the ticket
     * @param parSeat the assigned seat number
     * @param parCheckedIn whether the passenger is checked in
     * @param parFlight the flight for this ticket
     * @param parBooking the booking associated with this ticket
     * @param parPassenger the passenger for this ticket
     * @throws InvalidTicket if parTicketNumber is null
     * @throws InvalidPassengerNumber if parPassenger is null
     * @throws InvalidBooking if parBooking is null
     * @throws InvalidFlight if parFlight is null
     */
    public Ticket(String parTicketNumber, int parSeat, boolean parCheckedIn, Flight parFlight, 
                  Booking parBooking, Passenger parPassenger) 
            throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight {
        
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
     * Constructs a Ticket with luggage information.
     * 
     * <p>Creates a ticket that includes associated luggage items. This constructor
     * is used when tickets are created with pre-existing luggage information,
     * typically when loading data from the database.</p>
     *
     * @param parTicketNumber unique identifier for the ticket
     * @param parSeat the assigned seat number
     * @param parCheckedIn whether the passenger is checked in
     * @param parFlight the flight for this ticket
     * @param parBooking the booking associated with this ticket
     * @param parPassenger the passenger for this ticket
     * @param parLuggages the list of luggage items for this ticket
     * @throws InvalidTicket if parTicketNumber is null
     * @throws InvalidPassengerNumber if parPassenger is null
     * @throws InvalidBooking if parBooking is null
     * @throws InvalidFlight if parFlight is null
     */
    public Ticket(String parTicketNumber, int parSeat, boolean parCheckedIn, Flight parFlight, 
                  Booking parBooking, Passenger parPassenger, List<Luggage> parLuggages) 
            throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight {
        
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
     * Constructs a Ticket with new passenger creation.
     * 
     * <p>Creates a ticket and automatically creates a new passenger with the
     * provided personal information. This constructor is convenient for booking
     * processes where passenger information is collected and ticket creation
     * happens simultaneously.</p>
     *
     * @param parTicketNumber unique identifier for the ticket
     * @param parSeat the assigned seat number (can be null for unassigned)
     * @param parCheckedIn whether the passenger is checked in
     * @param parFlight the flight for this ticket
     * @param parBooking the booking associated with this ticket
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number or ID
     * @param parBirthDate the passenger's birth date
     * @throws InvalidTicket if parTicketNumber is null or passenger creation fails
     * @throws InvalidFlight if parFlight is null
     * @throws InvalidBooking if parBooking is null
     * @throws InvalidPassengerNumber if passenger information is invalid
     */
    public Ticket(String parTicketNumber, Integer parSeat, boolean parCheckedIn, Flight parFlight, 
                  Booking parBooking, String parFirstName, String parLastName, String parSSN, Date parBirthDate) 
            throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {
        
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
     * Constructs a Ticket with flight reconstruction for database loading.
     * 
     * <p>This constructor is designed for loading tickets from the database where
     * flight information needs to be reconstructed. It supports both arriving and
     * departing flights based on the provided parameters.</p>
     *
     * @param parTicketNumber unique identifier for the ticket
     * @param parSeat the assigned seat number
     * @param parCheckedIn whether the passenger is checked in
     * @param parId flight identifier
     * @param parCompanyName airline company name
     * @param parDate flight date
     * @param parDepartureTime flight departure time
     * @param parArrivalTime flight arrival time
     * @param parFlightStatus current flight status
     * @param parMaxSeats maximum seats on the flight
     * @param parFreeSeats available seats on the flight
     * @param parOrigin origin airport (for arriving flights)
     * @param parArrivalDelay arrival delay in minutes
     * @param parBookingStatus booking status
     * @param parBuyer customer who made the booking
     * @param parBookingDate date when booking was made
     * @param parFirstName passenger's first name
     * @param parLastName passenger's last name
     * @param parSSN passenger's social security number
     * @param parBirthDate passenger's birth date
     * @throws InvalidTicket if ticket creation fails
     * @throws InvalidBuyer if buyer information is invalid
     * @throws InvalidFlight if flight information is invalid
     * @throws InvalidPassengerNumber if passenger information is invalid
     */
    public Ticket(String parTicketNumber, Integer parSeat, boolean parCheckedIn, String parId, String parCompanyName, Date parDate,
                  Time parDepartureTime, Time parArrivalTime, FlightStatus parFlightStatus, int parMaxSeats, int parFreeSeats,
                  String parOrigin, int parArrivalDelay,
                  BookingStatus parBookingStatus, Customer parBuyer, Date parBookingDate,
                  String parFirstName, String parLastName, String parSSN, Date parBirthDate) 
            throws InvalidTicket, InvalidBuyer, InvalidFlight, InvalidPassengerNumber {
        
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
     * 
     * <p>Returns the ticket's unique identifier, which is used for tracking,
     * check-in procedures, and customer service operations.</p>
     *
     * @return the ticket number as a String
     */
    public String getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Gets the assigned seat number.
     * 
     * <p>Returns the seat assignment for this ticket. The seat number uses
     * 0-based indexing internally but can be converted to standard airline
     * format using {@link #printSeat()} method.</p>
     *
     * @return the seat number as an Integer
     */
    public Integer getSeat() {
        return seat;
    }

    /**
     * Sets the seat assignment.
     * 
     * <p>Assigns or changes the seat for this ticket. Seat assignment is
     * typically done during booking or check-in processes. The seat number
     * should be within the valid range for the aircraft.</p>
     *
     * @param seat the new seat number (0-based index), or -1 to unassign
     */
    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    /**
     * Checks if the passenger is checked in.
     * 
     * <p>Returns the check-in status for this ticket. Checked-in passengers
     * have completed departure procedures and are cleared for boarding.</p>
     *
     * @return true if the passenger is checked in, false otherwise
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Sets the check-in status.
     * 
     * <p>Updates the check-in status for this ticket. This method is typically
     * called during check-in procedures at the airport or through online
     * check-in systems.</p>
     *
     * @param checkedIn the new check-in status
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     * Gets the associated flight.
     * 
     * <p>Returns the flight for which this ticket is valid. This provides
     * access to all flight information including schedule, route, and status.</p>
     *
     * @return the flight associated with this ticket
     * @see Flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Gets the associated booking.
     * 
     * <p>Returns the booking that contains this ticket. This provides context
     * for the reservation including buyer information and booking status.</p>
     *
     * @return the booking associated with this ticket
     * @see Booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Gets the passenger information.
     * 
     * <p>Returns the passenger associated with this ticket, including all
     * personal information required for travel.</p>
     *
     * @return the passenger for this ticket
     * @see Passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Gets all luggage associated with this ticket.
     * 
     * <p>Returns the complete list of luggage items registered for this
     * ticket's passenger. This includes checked bags, carry-on items, and
     * special luggage types.</p>
     *
     * @return an unmodifiable view of the luggage list
     * @see Luggage
     */
    public List<Luggage> getLuggages() {
        return luggages;
    }

    /**
     * Sets the luggage list for this ticket.
     * 
     * <p>Replaces the current luggage list with the provided list. This method
     * automatically sets the ticket reference in each luggage item to maintain
     * proper associations.</p>
     *
     * @param luggages the new list of luggage items
     * @see Luggage
     */
    public void setLuggages(List<Luggage> luggages) {
        
        for (Luggage luggage : luggages) {
            luggage.setTicket(this);
        }
        
        this.luggages = (ArrayList<Luggage>) luggages;
    }

}