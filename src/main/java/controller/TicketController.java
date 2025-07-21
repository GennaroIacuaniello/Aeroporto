package controller;

import model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TicketController {

    private Ticket ticket;
    private ArrayList<Ticket> searchBookingResult;

    public TicketController () {}

    public void setTicket (Ticket ticket) {
        this.ticket = ticket;
    }

    public String getTicketNumber () {
        return ticket.getTicketNumber();
    }

    public Integer getSeat () {
        return ticket.getSeat();
    }

    public boolean isCheckedIn () {
        return ticket.isCheckedIn();
    }

    public Flight getFlight  () {
        return ticket.getFlight();
    }

    public Passenger getPassenger () {
        return ticket.getPassenger();
    }

    public Booking getBooking () {
        return ticket.getBooking();
    }

    public ArrayList<Luggage> getLuggages () {
        return ticket.getLuggages();
    }

    public String getFirstName () {
        return ticket.getPassenger().getFirstName();
    }

    public String getLastName () {
        return ticket.getPassenger().getLastName();
    }

    public String getSSN () {
        return ticket.getPassenger().getSSN();
    }

    public Date getBirthDate () {
        return ticket.getPassenger().getBirthDate();
    }

    public List<Ticket> getSearchBookingResult() {
        return searchBookingResult;
    }

    public void setSearchBookingResult(List<Ticket> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Ticket>) searchBookingResult;
    }

    public Ticket getSearchBookingResultTicketByTicketNumber(String ticketNumber) {

        for (Ticket value : searchBookingResult) {

            if (value.getTicketNumber().equals(ticketNumber)) {
                return value;
            }

        }
        return null;

    }
}
