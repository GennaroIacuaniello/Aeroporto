package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private BookingStatus status;
    private final Customer buyer;
    private final Flight bookedFlight;

    private final Date bookingDate;
    private ArrayList<Ticket> tickets;

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

    public Booking(BookingStatus parStatus, Date parBookingDate, Customer parBuyer, Flight parBookedFlight,
                   String parTicketNumber, int parSeat, boolean parCheckedIn, Passenger parPassenger) throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {


        this.status = parStatus;
        this.bookingDate = parBookingDate;
        this.buyer = parBuyer;
        this.bookedFlight = parBookedFlight;
        this.tickets = new ArrayList<>();
        this.tickets.add(new Ticket(parTicketNumber, parSeat, parCheckedIn, parBookedFlight, this, parPassenger));

    }

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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public Flight getBookedFlight() {
        return bookedFlight;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) throws InvalidPassengerNumber{

        if(!tickets.isEmpty()){
            this.tickets = (ArrayList<Ticket>) tickets;
        }else{
            throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
        }

    }

}
