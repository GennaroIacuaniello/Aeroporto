package model;

import java.sql.Time;
import java.util.ArrayList;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - rivedere lo status nel costruttore

 */



public class Booking {

    private BookingStatus status;
    private Customer buyer;
    private Flight bookedFlight;
    private Time bookingTime;
    private ArrayList<Ticket> tickets;

    public Booking(Customer parBuyer, Flight parBookedFlight, Time parBookingTime, ArrayList<Ticket> parTickets) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

        if(parBuyer != null){

            if(parBookedFlight != null){

                if(!parTickets.isEmpty()){

                    this.status = BookingStatus.PENDING;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingTime = parBookingTime;
                    this.tickets = parTickets;

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

    public Booking(BookingStatus parStatus, Customer parBuyer, Flight parBookedFlight, Time parBookingTime, ArrayList<Ticket> parTickets) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

        if(parBuyer != null){

            if(parBookedFlight != null){

                if(!parTickets.isEmpty()){

                    this.status = parStatus;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingTime = parBookingTime;
                    this.tickets = parTickets;

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

    public Booking(Customer parBuyer, Flight parBookedFlight, Time parBookingTime, Ticket parTicket) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

        if(parBuyer != null){

            if(parBookedFlight != null){

                if(parTicket != null){

                    this.status = BookingStatus.PENDING;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingTime = parBookingTime;
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

    public Booking(BookingStatus parStatus, Customer parBuyer, Flight parBookedFlight, Time parBookingTime, Ticket parTicket) throws InvalidBuyer, InvalidFlight, InvalidPassengerNumber {

        if(parBuyer != null){

            if(parBookedFlight != null){

                if(parTicket != null){

                    this.status = parStatus;
                    this.buyer = parBuyer;
                    this.bookedFlight = parBookedFlight;
                    this.bookingTime = parBookingTime;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Customer getBuyer() {
        return buyer;
    }

    //non si può modificare l'acquirente
    /*
    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }*/

    public Flight getBookedFlight() {
        return bookedFlight;
    }

    //non si può modificare il volo associato
    /*
    public void setBookedFlight(Flight bookedFlight) {
        this.bookedFlight = bookedFlight;
    }*/

    public Time getBookingTime() {
        return bookingTime;
    }

    //non si può modificare il momento della prenotazione
    /*
    public void setBookingTime(Time bookingTime) {
        this.bookingTime = bookingTime;
    }*/

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) throws InvalidPassengerNumber{

        if(!tickets.isEmpty()){
            this.tickets = tickets;
        }else{
            throw new InvalidPassengerNumber("La prenotazione deve avere almeno un passegero!");
        }

    }

}
