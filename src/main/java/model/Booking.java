package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a flight booking in the airport management system.
 * <p>
 * This class encapsulates all information related to a flight reservation,
 * including the booking status, customer details, flight information, and
 * associated tickets. A booking serves as the central entity that links
 * customers to their flight reservations and manages the lifecycle of
 * travel arrangements.
 * </p>
 * <p>
 * The booking maintains a collection of tickets, where each ticket represents
 * an individual passenger on the flight. The class ensures data integrity
 * by validating that every booking has a valid buyer, flight, and at least
 * one passenger ticket. The booking status tracks the current state of the
 * reservation throughout its lifecycle.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 *   <li>Status management (PENDING, CONFIRMED, CANCELLED)</li>
 *   <li>Customer and flight association</li>
 *   <li>Multiple ticket management for group bookings</li>
 *   <li>Booking date tracking</li>
 *   <li>Data validation and integrity checks</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see BookingStatus
 * @see Customer
 * @see Flight
 * @see Ticket
 * @see Passenger
 */
public class Booking {

    /**
     * The current status of this booking.
     * <p>
     * Tracks the booking state throughout its lifecycle, from initial
     * creation to completion or cancellation. This field is mutable
     * to allow status updates as the booking progresses.
     * </p>
     *
     * @see BookingStatus
     */
    private BookingStatus status;
    
    /**
     * The customer who made this booking.
     * <p>
     * This field is final as the buyer cannot be changed after booking
     * creation. It represents the customer responsible for the reservation
     * and any associated payments.
     * </p>
     *
     * @see Customer
     */
    private final Customer buyer;
    
    /**
     * The flight associated with this booking.
     * <p>
     * This field is final as the flight cannot be changed after booking
     * creation. It represents the specific flight for which seats have
     * been reserved.
     * </p>
     *
     * @see Flight
     */
    private final Flight bookedFlight;

    /**
     * The date when this booking was created.
     * <p>
     * This field is final as the booking date represents the historical
     * record of when the reservation was made and cannot be modified.
     * </p>
     */
    private final Date bookingDate;
    
    /**
     * The list of tickets associated with this booking.
     * <p>
     * Each ticket represents an individual passenger on the flight.
     * The collection is implemented as an ArrayList for efficient
     * access and modification. The booking must contain at least
     * one ticket to be valid.
     * </p>
     *
     * @see Ticket
     */
    private ArrayList<Ticket> tickets;

    /**
     * Constructs a new booking with PENDING status and multiple tickets.
     * <p>
     * Creates a booking with default PENDING status, suitable for new
     * reservations that require confirmation. This constructor is used
     * for group bookings with multiple passengers.
     * </p>
     *
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parBookingDate the date of the booking
     * @param parTickets the list of tickets for passengers
     * @throws InvalidBuyer if the buyer is null
     * @throws InvalidFlight if the flight is null
     * @throws InvalidPassengerNumber if the tickets list is empty
     */
    public Booking(Customer parBuyer, Flight parBookedFlight, Date parBookingDate, List<Ticket> parTickets) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

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
     * Constructs a new booking with specified status and multiple tickets.
     * <p>
     * Creates a booking with a specific status, allowing for bookings
     * that are created in states other than PENDING. This constructor
     * is useful for importing existing bookings or creating confirmed
     * reservations directly.
     * </p>
     *
     * @param parStatus the initial status of the booking
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parBookingDate the date of the booking
     * @param parTickets the list of tickets for passengers
     * @throws InvalidBuyer if the buyer is null
     * @throws InvalidFlight if the flight is null
     * @throws InvalidPassengerNumber if the tickets list is empty
     */
    public Booking(BookingStatus parStatus, Customer parBuyer, Flight parBookedFlight, Date parBookingDate, List<Ticket> parTickets) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

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
     * Constructs a new booking with PENDING status and a single ticket.
     * <p>
     * Creates a booking with default PENDING status for a single passenger.
     * This constructor is convenient for individual bookings where only
     * one passenger is traveling.
     * </p>
     *
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parBookingDate the date of the booking
     * @param parTicket the ticket for the passenger
     * @throws InvalidBuyer if the buyer is null
     * @throws InvalidFlight if the flight is null
     * @throws InvalidPassengerNumber if the ticket is null
     */
    public Booking(Customer parBuyer, Flight parBookedFlight, Date parBookingDate, Ticket parTicket) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

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
     * Constructs a new booking with specified status and a single ticket.
     * <p>
     * Creates a booking with a specific status for a single passenger.
     * This constructor allows for direct creation of bookings in any
     * desired status state.
     * </p>
     *
     * @param parStatus the initial status of the booking
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parBookingDate the date of the booking
     * @param parTicket the ticket for the passenger
     * @throws InvalidBuyer if the buyer is null
     * @throws InvalidFlight if the flight is null
     * @throws InvalidPassengerNumber if the ticket is null
     */
    public Booking(BookingStatus parStatus, Customer parBuyer, Flight parBookedFlight, Date parBookingDate, Ticket parTicket) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

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
     * Constructs a new booking with an existing passenger object.
     * <p>
     * Creates a booking using an existing Passenger object, which is
     * useful when the passenger information is already available in
     * the system. The ticket is created internally using the provided
     * passenger data.
     * </p>
     *
     * @param parStatus the initial status of the booking
     * @param parBookingDate the date of the booking
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parTicketNumber the ticket number for the passenger
     * @param parSeat the seat number assignment
     * @param parCheckedIn the check-in status of the passenger
     * @param parPassenger the passenger object
     * @throws InvalidTicket if ticket creation fails
     * @throws InvalidFlight if the flight is invalid
     * @throws InvalidBooking if the booking data is invalid
     * @throws InvalidPassengerNumber if passenger data is invalid
     */
    public Booking(BookingStatus parStatus, Date parBookingDate, Customer parBuyer, Flight parBookedFlight,
                   String parTicketNumber, int parSeat, boolean parCheckedIn, Passenger parPassenger) throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {


        this.status = parStatus;
        this.bookingDate = parBookingDate;
        this.buyer = parBuyer;
        this.bookedFlight = parBookedFlight;
        this.tickets = new ArrayList<>();
        this.tickets.add(new Ticket(parTicketNumber, parSeat, parCheckedIn, parBookedFlight, this, parPassenger));

    }

    /**
     * Constructs a new booking with passenger details.
     * <p>
     * Creates a booking with individual passenger information provided
     * as separate parameters. This constructor is useful when passenger
     * data comes from form inputs or database records where individual
     * fields are available rather than a complete Passenger object.
     * </p>
     *
     * @param parStatus the initial status of the booking
     * @param parBookingDate the date of the booking
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @param parTicketNumber the ticket number for the passenger
     * @param parSeat the seat number assignment (can be null)
     * @param parCheckedIn the check-in status of the passenger
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number
     * @param parBirthDate the passenger's birth date
     * @throws InvalidTicket if ticket creation fails
     * @throws InvalidFlight if the flight is invalid
     * @throws InvalidBooking if the booking data is invalid
     * @throws InvalidPassengerNumber if passenger data is invalid
     */
    public Booking(BookingStatus parStatus, Date parBookingDate, Customer parBuyer, Flight parBookedFlight,
                   String parTicketNumber, Integer parSeat, boolean parCheckedIn,
                   String parFirstName, String parLastName, String parSSN, Date parBirthDate) throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {


        this.status = parStatus;
        this.bookingDate = parBookingDate;
        this.buyer = parBuyer;
        this.bookedFlight = parBookedFlight;
        this.tickets = new ArrayList<>();
        this.tickets.add(new Ticket(parTicketNumber, parSeat, parCheckedIn, parBookedFlight, this,
                                    parFirstName, parLastName, parSSN, parBirthDate));

    }

    /**
     * Gets the current status of this booking.
     * <p>
     * Returns the booking status which indicates the current state
     * of the reservation in its lifecycle. This information is used
     * for processing, display, and business logic decisions.
     * </p>
     *
     * @return the current booking status
     * @see BookingStatus
     */
    public BookingStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of this booking.
     * <p>
     * Updates the booking status to reflect changes in the reservation
     * state. This method is used to progress bookings through their
     * lifecycle or to cancel reservations.
     * </p>
     *
     * @param status the new booking status
     * @see BookingStatus
     */
    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /**
     * Gets the customer who made this booking.
     * <p>
     * Returns the customer object representing the buyer of this
     * reservation. This information is used for customer service,
     * billing, and account management purposes.
     * </p>
     *
     * @return the customer who made the booking
     * @see Customer
     */
    public Customer getBuyer() {
        return buyer;
    }

    /**
     * Gets the flight associated with this booking.
     * <p>
     * Returns the flight object representing the reserved flight.
     * This information is used for schedule management, seat
     * allocation, and passenger services.
     * </p>
     *
     * @return the flight associated with this booking
     * @see Flight
     */
    public Flight getBookedFlight() {
        return bookedFlight;
    }

    /**
     * Gets the date when this booking was created.
     * <p>
     * Returns the booking creation date, which is used for
     * record keeping, reporting, and business analytics.
     * This date cannot be modified after booking creation.
     * </p>
     *
     * @return the booking creation date
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Gets the list of tickets associated with this booking.
     * <p>
     * Returns all tickets for passengers included in this booking.
     * Each ticket represents an individual passenger and their
     * travel details. The list is never empty for a valid booking.
     * </p>
     *
     * @return the list of tickets for this booking, never null or empty
     * @see Ticket
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Sets the list of tickets for this booking.
     * <p>
     * Updates the complete list of tickets associated with this booking.
     * This method ensures that the booking maintains at least one ticket
     * to preserve data integrity. It is useful for bulk updates or
     * modifications to passenger lists.
     * </p>
     *
     * @param tickets the new list of tickets. Must not be null or empty.
     * @throws InvalidPassengerNumber if the tickets list is empty
     * @see Ticket
     */
    public void setTickets(List<Ticket> tickets) throws InvalidPassengerNumber{

        if(!tickets.isEmpty()){
            this.tickets = (ArrayList<Ticket>) tickets;
        }else{
            throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
        }

    }

}