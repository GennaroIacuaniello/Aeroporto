package model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private final String ticketNumber;
    private Integer seat = null;
    private boolean checkedIn = false;
    private final Flight flight;
    private final Booking booking;
    private final Passenger passenger;
    private ArrayList<Luggage> luggages = new ArrayList<>(0);

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

    public Ticket(String parTicketNumber, int parSeat, boolean parCheckedIn, Flight parFlight, Booking parBooking, Passenger parPassenger, ArrayList<Luggage> parLuggages) throws InvalidTicket, InvalidPassengerNumber, InvalidBooking, InvalidFlight{

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

                        this.luggages = parLuggages;

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

    public Ticket(String parTicketNumber, int parSeat, boolean parCheckedIn, String parId, String parCompanyName, Date parDate,
                  Time parDepartureTime, Time parArrivalTime, FlightStatus parFlightStatus, int parMaxSeats, int parFreeSeats,
                  String parOrigin, int parArrivalDelay,
                  BookingStatus parBookingStatus, Customer parBuyer, Date parBookingDate,
                  String parFirstName, String parLastName, String parSSN, Date parBirthDate) throws InvalidTicket, InvalidBuyer, InvalidFlight, InvalidPassengerNumber{

        if(parTicketNumber != null){
            this.ticketNumber = parTicketNumber;
            this.flight = new Arriving(parId, parCompanyName, parDate, parDepartureTime, parArrivalTime, parFlightStatus, parMaxSeats, parFreeSeats,
                    parOrigin, parArrivalDelay);
            this.booking = new Booking(parBookingStatus, parBuyer, this.flight, parBookingDate, this);
            this.passenger = new Passenger(parFirstName, parLastName, parSSN, parBirthDate, this);
        }else{
            throw new InvalidTicket("Un biglietto deve avere un ticket number!");
        }

    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Flight getFlight() {
        return flight;
    }

    public Booking getBooking() {
        return booking;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public ArrayList<Luggage> getLuggages() {
        return luggages;
    }

    public void setLuggages(ArrayList<Luggage> luggages) {

        for(Luggage luggage: luggages){
            luggage.setTicket(this);
        }

        this.luggages = luggages;
    }

    public String print_seat(){

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
