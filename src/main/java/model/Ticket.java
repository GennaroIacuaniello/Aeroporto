package model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ticket.
 */
public class Ticket {

    private final String ticketNumber;
    private Integer seat = null;
    private boolean checkedIn = false;
    private final Flight flight;
    private final Booking booking;
    private final Passenger passenger;
    private ArrayList<Luggage> luggages = new ArrayList<>(0);

    /**
     * Instantiates a new Ticket.
     *
     * @param parTicketNumber the par ticket number
     * @param parFlight       the par flight
     * @param parBooking      the par booking
     * @param parPassenger    the par passenger
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidFlight          the invalid flight
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
     * Instantiates a new Ticket.
     *
     * @param parTicketNumber the par ticket number
     * @param parSeat         the par seat
     * @param parFlight       the par flight
     * @param parBooking      the par booking
     * @param parPassenger    the par passenger
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidFlight          the invalid flight
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
     * Instantiates a new Ticket.
     *
     * @param parTicketNumber the par ticket number
     * @param parSeat         the par seat
     * @param parCheckedIn    the par checked in
     * @param parFlight       the par flight
     * @param parBooking      the par booking
     * @param parPassenger    the par passenger
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidFlight          the invalid flight
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
     * Instantiates a new Ticket.
     *
     * @param parTicketNumber the par ticket number
     * @param parSeat         the par seat
     * @param parCheckedIn    the par checked in
     * @param parFlight       the par flight
     * @param parBooking      the par booking
     * @param parPassenger    the par passenger
     * @param parLuggages     the par luggages
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidFlight          the invalid flight
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
     * Instantiates a new Ticket.
     *
     * @param parTicketNumber  the par ticket number
     * @param parSeat          the par seat
     * @param parCheckedIn     the par checked in
     * @param parId            the par id
     * @param parCompanyName   the par company name
     * @param parDate          the par date
     * @param parDepartureTime the par departure time
     * @param parArrivalTime   the par arrival time
     * @param parFlightStatus  the par flight status
     * @param parMaxSeats      the par max seats
     * @param parFreeSeats     the par free seats
     * @param parOrigin        the par origin
     * @param parArrivalDelay  the par arrival delay
     * @param parBookingStatus the par booking status
     * @param parBuyer         the par buyer
     * @param parBookingDate   the par booking date
     * @param parFirstName     the par first name
     * @param parLastName      the par last name
     * @param parSSN           the par ssn
     * @param parBirthDate     the par birth date
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidBuyer           the invalid buyer
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Instantiates a new Ticket.
     *
     * @param parTicketNumber the par ticket number
     * @param parSeat         the par seat
     * @param parCheckedIn    the par checked in
     * @param parFlight       the par flight
     * @param parBooking      the par booking
     * @param parFirstName    the par first name
     * @param parLastName     the par last name
     * @param parSSN          the par ssn
     * @param parBirthDate    the par birth date
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidPassengerNumber the invalid passenger number
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
     * Gets ticket number.
     *
     * @return the ticket number
     */
    public String getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Gets seat.
     *
     * @return the seat
     */
    public Integer getSeat() {
        return seat;
    }

    /**
     * Sets seat.
     *
     * @param seat the seat
     */
    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    /**
     * Is checked in boolean.
     *
     * @return the boolean
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Sets checked in.
     *
     * @param checkedIn the checked in
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     * Gets flight.
     *
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Gets booking.
     *
     * @return the booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Gets passenger.
     *
     * @return the passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Gets luggages.
     *
     * @return the luggages
     */
    public List<Luggage> getLuggages() {
        return luggages;
    }

    /**
     * Sets luggages.
     *
     * @param luggages the luggages
     */
    public void setLuggages(List<Luggage> luggages) {

        for(Luggage luggage: luggages){
            luggage.setTicket(this);
        }

        this.luggages = (ArrayList<Luggage>) luggages;
    }

    /**
     * Print seat string.
     *
     * @return the string
     */
    public String printSeat(){

        if (this.seat == null || this.seat == -1) return "/";

        String literal;

        switch(this.seat%6){
            case 0: literal = "A"; break;
            case 1: literal = "B"; break;
            case 2: literal = "C"; break;
            case 3: literal = "D"; break;
            case 4: literal = "E"; break;
            case 5: literal = "F"; break;
            default: literal = "";
        }

        return Integer.toString((this.seat/6)+1) + literal;
    }

}
