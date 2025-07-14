package controller;

import model.*;

import java.sql.Date;
import java.util.ArrayList;

public class TicketController {

    private Ticket ticket;

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
}
