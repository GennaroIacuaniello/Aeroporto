package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Booking.
 */
public class Booking {

    private BookingStatus status;
    private final Customer buyer;
    private final Flight bookedFlight;

    private final Date bookingDate;
    private ArrayList<Ticket> tickets;

    /**
     * Instantiates a new Booking.
     *
     * @param parBuyer        the par buyer
     * @param parBookedFlight the par booked flight
     * @param parBookingDate  the par booking date
     * @param parTickets      the par tickets
     * @throws InvalidBuyer           the invalid buyer
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Instantiates a new Booking.
     *
     * @param parStatus       the par status
     * @param parBuyer        the par buyer
     * @param parBookedFlight the par booked flight
     * @param parBookingDate  the par booking date
     * @param parTickets      the par tickets
     * @throws InvalidBuyer           the invalid buyer
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Instantiates a new Booking.
     *
     * @param parBuyer        the par buyer
     * @param parBookedFlight the par booked flight
     * @param parBookingDate  the par booking date
     * @param parTicket       the par ticket
     * @throws InvalidBuyer           the invalid buyer
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Instantiates a new Booking.
     *
     * @param parStatus       the par status
     * @param parBuyer        the par buyer
     * @param parBookedFlight the par booked flight
     * @param parBookingDate  the par booking date
     * @param parTicket       the par ticket
     * @throws InvalidBuyer           the invalid buyer
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Instantiates a new Booking.
     *
     * @param parStatus       the par status
     * @param parBookingDate  the par booking date
     * @param parBuyer        the par buyer
     * @param parBookedFlight the par booked flight
     * @param parTicketNumber the par ticket number
     * @param parSeat         the par seat
     * @param parCheckedIn    the par checked in
     * @param parPassenger    the par passenger
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Instantiates a new Booking.
     *
     * @param parStatus       the par status
     * @param parBookingDate  the par booking date
     * @param parBuyer        the par buyer
     * @param parBookedFlight the par booked flight
     * @param parTicketNumber the par ticket number
     * @param parSeat         the par seat
     * @param parCheckedIn    the par checked in
     * @param parFirstName    the par first name
     * @param parLastName     the par last name
     * @param parSSN          the par ssn
     * @param parBirthDate    the par birth date
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Gets status.
     *
     * @return the status
     */
    public BookingStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /**
     * Gets buyer.
     *
     * @return the buyer
     */
    public Customer getBuyer() {
        return buyer;
    }

    /**
     * Gets booked flight.
     *
     * @return the booked flight
     */
    public Flight getBookedFlight() {
        return bookedFlight;
    }

    /**
     * Gets booking date.
     *
     * @return the booking date
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Gets tickets.
     *
     * @return the tickets
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Sets tickets.
     *
     * @param tickets the tickets
     * @throws InvalidPassengerNumber the invalid passenger number
     */
    public void setTickets(List<Ticket> tickets) throws InvalidPassengerNumber{

        if(!tickets.isEmpty()){
            this.tickets = (ArrayList<Ticket>) tickets;
        }else{
            throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
        }

    }

}
