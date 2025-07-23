package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Booking class represents a flight reservation in the airport management system.
 * 
 * <p>This class manages all aspects of a flight booking, including customer information,
 * flight details, booking status, and associated tickets for passengers. A booking
 * serves as the central entity that connects customers to flights and manages the
 * reservation lifecycle from creation to completion or cancellation.</p>
 * 
 * <p>Key features of the booking system:</p>
 * <ul>
 *   <li>Booking status management through {@link BookingStatus} enumeration</li>
 *   <li>Customer (buyer) association and management</li>
 *   <li>Flight association for the booked journey</li>
 *   <li>Multiple ticket management for group bookings</li>
 *   <li>Booking date tracking for record keeping</li>
 *   <li>Validation of booking integrity and business rules</li>
 * </ul>
 * 
 * <p>The class supports various booking scenarios from single passenger bookings
 * to group reservations with multiple tickets. Each booking must have at least
 * one ticket and be associated with both a customer and a flight.</p>
 * 
 * <p>Booking statuses progress through different states:</p>
 * <ul>
 *   <li>{@link BookingStatus#PENDING} - Initial state when booking is created</li>
 *   <li>{@link BookingStatus#CONFIRMED} - When payment is processed and booking is confirmed</li>
 *   <li>{@link BookingStatus#CANCELLED} - When booking is cancelled by customer or system</li>
 * </ul>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Customer
 * @see Flight
 * @see Ticket
 * @see BookingStatus
 */
public class Booking {

    /**
     * Current status of the booking.
     * Tracks the booking lifecycle from creation to completion or cancellation.
     * @see BookingStatus
     */
    private BookingStatus status;
    
    /**
     * The customer who made this booking.
     * This field is immutable once set during booking creation to maintain
     * data integrity and audit trail.
     */
    private final Customer buyer;
    
    /**
     * The flight associated with this booking.
     * This field is immutable once set during booking creation to prevent
     * accidental modification of the core booking relationship.
     */
    private final Flight bookedFlight;

    /**
     * Date when the booking was created.
     * This field is immutable and serves as an audit trail for booking creation.
     * Used for reporting, customer service, and business analytics.
     */
    private final Date bookingDate;
    
    /**
     * List of tickets associated with this booking.
     * Contains all passenger tickets for this booking. Each ticket represents
     * one passenger's reservation on the flight. Minimum of one ticket required.
     */
    private ArrayList<Ticket> tickets;

    /**
     * Constructs a new Booking with complete ticket list.
     * 
     * <p>Creates a booking with a predefined list of tickets. This constructor
     * is typically used when all passenger information is available at booking
     * creation time. The booking status is set to {@link BookingStatus#PENDING}
     * by default.</p>
     *
     * @param parBuyer the customer making the booking (must not be null)
     * @param parBookedFlight the flight being booked (must not be null)
     * @param parBookingDate the date when the booking is made
     * @param parTickets the list of tickets for this booking (must not be empty)
     * @throws InvalidBuyer if parBuyer is null
     * @throws InvalidFlight if parBookedFlight is null
     * @throws InvalidPassengerNumber if parTickets is empty
     */
    public Booking(Customer parBuyer, Flight parBookedFlight, Date parBookingDate, List<Ticket> parTickets) 
            throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

        if(parBuyer != null){

            if(parBookedFlight != null){

                if(!parTickets.isEmpty()){

                    this.status = BookingStatus.PENDING;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingDate = parBookingDate;
                    this.tickets = (ArrayList<Ticket>) parTickets;

                }else{
                    throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
                }
            }else{
                throw new InvalidFlight("La prenotazione deve essere riferita ad un volo!");
            }

        }else{
            throw new InvalidBuyer("La prenotazione deve avere un acquirente!");
        }
    }

    /**
     * Constructs a new Booking with single ticket.
     * 
     * <p>Creates a booking for a single passenger. This constructor is convenient
     * for individual bookings and automatically creates the ticket list with the
     * provided ticket. The booking status is set to {@link BookingStatus#PENDING}.</p>
     *
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parBookingDate the date when the booking is made
     * @param parTicket the single ticket for this booking
     * @throws InvalidBuyer if parBuyer is null
     * @throws InvalidFlight if parBookedFlight is null
     * @throws InvalidPassengerNumber if parTicket is null
     */
    public Booking(Customer parBuyer, Flight parBookedFlight, Date parBookingDate, Ticket parTicket) 
            throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {
        if(parBuyer != null){

            if(parBookedFlight != null){

                if(!parTickets.isEmpty()){

                    this.status = parStatus;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingDate = parBookingDate;
                    this.tickets = (ArrayList<Ticket>) parTickets;

                }else{
                    throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
                }
            }else{
                throw new InvalidFlight("La prenotazione deve essere riferita ad un volo!");
            }

        }else{
            throw new InvalidBuyer("La prenotazione deve avere un acquirente!");
        }
    }

    /**
     * Constructs a Booking with explicit status and ticket list.
     * 
     * <p>Creates a booking with a specific status, useful when loading existing
     * bookings from the database or when creating bookings in non-pending states.</p>
     *
     * @param parStatus the initial booking status
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parBookingDate the date when the booking is made
     * @param parTickets the list of tickets for this booking
     * @throws InvalidBuyer if parBuyer is null
     * @throws InvalidFlight if parBookedFlight is null
     * @throws InvalidPassengerNumber if parTickets is empty
     */
    public Booking(BookingStatus parStatus, Customer parBuyer, Flight parBookedFlight, Date parBookingDate, List<Ticket> parTickets) 
            throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {
        if(parBuyer != null){

            if(parBookedFlight != null){

                if(parTicket != null){

                    this.status = BookingStatus.PENDING;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingDate = parBookingDate;
                    this.tickets = new ArrayList<>();
                    this.tickets.add(parTicket);

                }else{
                    throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
                }
            }else{
                throw new InvalidFlight("La prenotazione deve essere riferita ad un volo!");
            }

        }else{
            throw new InvalidBuyer("La prenotazione deve avere un acquirente!");
        }
    }

    /**
     * Constructs a Booking with explicit status and single ticket.
     * 
     * <p>Creates a booking with a specific status for a single passenger.
     * Useful for database loading or specific booking scenarios.</p>
     *
     * @param parStatus the initial booking status
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parBookingDate the date when the booking is made
     * @param parTicket the single ticket for this booking
     * @throws InvalidBuyer if parBuyer is null
     * @throws InvalidFlight if parBookedFlight is null
     * @throws InvalidPassengerNumber if parTicket is null
     */
    public Booking(BookingStatus parStatus, Customer parBuyer, Flight parBookedFlight, Date parBookingDate, Ticket parTicket) 
            throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {
        if(parBuyer != null){

            if(parBookedFlight != null){

                if(parTicket != null){

                    this.status = parStatus;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingDate = parBookingDate;
                    this.tickets = new ArrayList<>();
                    this.tickets.add(parTicket);

                }else{
                    throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
                }
            }else{
                throw new InvalidFlight("La prenotazione deve essere riferita ad un volo!");
            }

        }else{
            throw new InvalidBuyer("La prenotazione deve avere un acquirente!");
        }
    }

    /**
     * Constructs a Booking with existing passenger and ticket information.
     * 
     * <p>Creates a booking with a ticket that includes existing passenger information.
     * This constructor automatically creates the passenger and ticket objects based
     * on the provided parameters.</p>
     *
     * @param parStatus the initial booking status
     * @param parBookingDate the date when the booking is made
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parTicketNumber the ticket number for the passenger
     * @param parSeat the seat assignment (can be null for unassigned)
     * @param parCheckedIn whether the passenger is checked in
     * @param parPassenger the passenger information
     * @throws InvalidTicket if ticket creation fails
     * @throws InvalidFlight if parBookedFlight is null
     * @throws InvalidBooking if booking validation fails
     * @throws InvalidPassengerNumber if passenger information is invalid
     */
    public Booking(BookingStatus parStatus, Date parBookingDate, Customer parBuyer, Flight parBookedFlight,
                   String parTicketNumber, int parSeat, boolean parCheckedIn, Passenger parPassenger) 
            throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {
        
        
        this.status = parStatus;
        this.bookingDate = parBookingDate;
        this.buyer = parBuyer;
        this.bookedFlight = parBookedFlight;
        this.tickets = new ArrayList<>();
        this.tickets.add(new Ticket(parTicketNumber, parSeat, parCheckedIn, parBookedFlight, this, parPassenger));

    }

    /**
     * Constructs a Booking with new passenger creation.
     * 
     * <p>Creates a booking and automatically creates a new passenger with the
     * provided personal information. This constructor is useful for new bookings
     * where passenger information is collected during the booking process.</p>
     *
     * @param parStatus the initial booking status
     * @param parBookingDate the date when the booking is made
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parTicketNumber the ticket number for the passenger
     * @param parSeat the seat assignment (can be null for unassigned)
     * @param parCheckedIn whether the passenger is checked in
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number or ID
     * @param parBirthDate the passenger's birth date
     * @throws InvalidTicket if ticket creation fails
     * @throws InvalidFlight if parBookedFlight is null
     * @throws InvalidBooking if booking validation fails
     * @throws InvalidPassengerNumber if passenger information is invalid
     */
    public Booking(BookingStatus parStatus, Date parBookingDate, Customer parBuyer, Flight parBookedFlight,
                   String parTicketNumber, Integer parSeat, boolean parCheckedIn,
                   String parFirstName, String parLastName, String parSSN, Date parBirthDate) 
            throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {
        
        
        this.status = parStatus;
        this.bookingDate = parBookingDate;
        this.buyer = parBuyer;
        this.bookedFlight = parBookedFlight;
        this.tickets = new ArrayList<>();
        this.tickets.add(new Ticket(parTicketNumber, parSeat, parCheckedIn, parBookedFlight, this,
                                    parFirstName, parLastName, parSSN, parBirthDate));

    }

    /**
     * Gets the current booking status.
     * 
     * <p>Returns the current status of the booking, which indicates the
     * booking's position in its lifecycle (pending, confirmed, or cancelled).</p>
     *
     * @return the current booking status
     * @see BookingStatus
     */
    public BookingStatus getStatus() {
        return status;
    }

    /**
     * Sets the booking status.
     * 
     * <p>Updates the booking status to reflect changes in the booking lifecycle.
     * Status changes may trigger notifications, payment processing, or other
     * business logic depending on the implementation.</p>
     * 
     * <p>Common status transitions:</p>
     * <ul>
     *   <li>PENDING → CONFIRMED (after payment)</li>
     *   <li>PENDING → CANCELLED (customer cancellation)</li>
     *   <li>CONFIRMED → CANCELLED (cancellation after payment)</li>
     * </ul>
     *
     * @param status the new booking status
     * @throws IllegalArgumentException if status is null
     * @see BookingStatus
     */
    public void setStatus(BookingStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Booking status cannot be null");
        }
        this.status = status;
    }

    /**
     * Gets the customer who made this booking.
     * 
     * <p>Returns the buyer (customer) associated with this booking.
     * This information is used for customer service, billing, and
     * communication purposes.</p>
     *
     * @return the customer who made the booking
     * @see Customer
     */
    public Customer getBuyer() {
        return buyer;
    }

    /**
     * Gets the flight associated with this booking.
     * 
     * <p>Returns the flight that was booked. This provides access to
     * flight details such as schedule, route, and capacity information.</p>
     *
     * @return the booked flight
     * @see Flight
     */
    public Flight getBookedFlight() {
        return bookedFlight;
    }

    /**
     * Gets the booking creation date.
     * 
     * <p>Returns the date when this booking was created. This information
     * is used for reporting, customer service, and business analytics.</p>
     *
     * @return the date when the booking was made
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Gets all tickets associated with this booking.
     * 
     * <p>Returns the complete list of tickets for this booking. Each ticket
     * represents one passenger's reservation. The list includes ticket
     * details, passenger information, and seat assignments.</p>
     *
     * @return an unmodifiable view of the booking's tickets
     * @see Ticket
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Sets the ticket list for this booking.
     * 
     * <p>Replaces the current ticket list with the provided list. This method
     * should be used carefully to maintain booking integrity. The new ticket
     * list must contain at least one ticket.</p>
     * 
     * <p>This method is typically used when loading booking data from the
     * database or when modifying existing bookings through administrative
     * functions.</p>
     *
     * @param tickets the new list of tickets for this booking
     * @throws InvalidPassengerNumber if the ticket list is empty
     * @throws IllegalArgumentException if tickets is null
     * @see Ticket
     */
    public void setTickets(List<Ticket> tickets) throws InvalidPassengerNumber {
        if (tickets == null) {
            throw new IllegalArgumentException("Tickets list cannot be null");
        }
        if (tickets.isEmpty()) {
            throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passeggero!");
        }
        this.tickets = (ArrayList<Ticket>) tickets;
    }

}