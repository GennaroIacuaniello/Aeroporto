package controller;

import model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ticket controller.
 */
public class TicketController {

    private Ticket ticket;
    private ArrayList<Ticket> searchBookingResult;

    /**
     * Sets ticket.
     *
     * @param ticket the ticket
     */
    public void setTicket (Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Gets ticket number.
     *
     * @return the ticket number
     */
    public String getTicketNumber () {
        return ticket.getTicketNumber();
    }

    /**
     * Gets seat.
     *
     * @return the seat
     */
    public Integer getSeat () {
        return ticket.getSeat();
    }

    /**
     * Is checked in boolean.
     *
     * @return the boolean
     */
    public boolean isCheckedIn () {
        return ticket.isCheckedIn();
    }

    /**
     * Gets flight.
     *
     * @return the flight
     */
    public Flight getFlight  () {
        return ticket.getFlight();
    }

    /**
     * Gets passenger.
     *
     * @return the passenger
     */
    public Passenger getPassenger () {
        return ticket.getPassenger();
    }

    /**
     * Gets booking.
     *
     * @return the booking
     */
    public Booking getBooking () {
        return ticket.getBooking();
    }

    /**
     * Gets luggages.
     *
     * @return the luggages
     */
    public List<Luggage> getLuggages () {
        return ticket.getLuggages();
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName () {
        return ticket.getPassenger().getFirstName();
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName () {
        return ticket.getPassenger().getLastName();
    }

    /**
     * Gets ssn.
     *
     * @return the ssn
     */
    public String getSSN () {
        return ticket.getPassenger().getPassengerSSN();
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public Date getBirthDate () {
        return ticket.getPassenger().getBirthDate();
    }

    /**
     * Gets search booking result.
     *
     * @return the search booking result
     */
    public List<Ticket> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets search booking result.
     *
     * @param searchBookingResult the search booking result
     */
    public void setSearchBookingResult(List<Ticket> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Ticket>) searchBookingResult;
    }

    /**
     * Gets search booking result ticket by ticket number.
     *
     * @param ticketNumber the ticket number
     * @return the search booking result ticket by ticket number
     */
    public Ticket getSearchBookingResultTicketByTicketNumber(String ticketNumber) {

        for (Ticket value : searchBookingResult) {

            if (value.getTicketNumber().equals(ticketNumber)) {
                return value;
            }

        }
        return null;

    }
}
